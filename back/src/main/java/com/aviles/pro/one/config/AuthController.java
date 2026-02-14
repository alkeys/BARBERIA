/*
 * Copyright 2026 Alex Aviles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 */

package com.aviles.pro.one.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.dto.jwt.JwtDTO;
import com.aviles.pro.one.dto.jwt.ParametrosParaTokent;
import com.aviles.pro.one.dto.jwt.ValidateToken;
import com.aviles.pro.one.models.users.Usuario;
import com.aviles.pro.one.security.CustomUserDetails;
import com.aviles.pro.one.security.JwtTokenProvider;
import com.aviles.pro.one.utils.service.CookieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@Tag(name = "Autenticación", description = "Controlador de autenticación")
public class AuthController {

    @Value("${spring.cookie.expiration}")
    private int cookieExpiration;

    // --- Dependencias para Autenticación Interna ---
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final CookieService cookieService;

    public AuthController(AuthenticationConfiguration authenticationConfiguration,
            JwtTokenProvider jwtTokenProvider,
            @Qualifier("usuarioService") UserDetailsService userDetailsService,
            CookieService cookieService) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.cookieService = cookieService;
    }

    /***
     * 
     * @param authenticationRequest // los datos del usuario para autenticar dto
     * @param response              esto es para guardar el token en el cookie
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    @Operation(summary = "Autenticar usuario", description = "Autentica un usuario y retorna un token JWT con  los datos del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para autenticar", content = @Content),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al autenticar", content = @Content)
    })
    public ResponseEntity<JwtDTO> createAuthenticationToken(@RequestBody ParametrosParaTokent authenticationRequest,
            HttpServletResponse response) throws Exception {
        String usuario = authenticationRequest.getUsuario();
        String password = authenticationRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario, password));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(usuario); // el usuario autenticado

        // si el usuario no existe
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // si el usuario no es un CustomUserDetails osea no existe
        if (!(userDetails instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        final String jwt = jwtTokenProvider.generateToken(userDetails); // Genera el token
        final String horaExpiracion = jwtTokenProvider.extractExpiration(jwt).toString(); // Extrae la fecha de
                                                                                          // expiración
        final String rolUsuario = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Usuario usuarioa = ((CustomUserDetails) userDetails).getUsuario();

        if (usuarioa.getActivo() == false) {
            // si el usuario no esta activo
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 60*60*24 = 1 dia
        cookieService.setCookie(response, "jwt", jwt, cookieExpiration);

        return ResponseEntity.ok(new JwtDTO(horaExpiracion, rolUsuario, usuarioa));
    }

    @PostMapping("/validate-token")
    @Operation(summary = "Validar token", description = "Valida un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para validar el token", content = @Content),
            @ApiResponse(responseCode = "404", description = "El token no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al validar el token", content = @Content)
    })
    public ResponseEntity<Boolean> validateToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String token = cookieService.getCookieValue(request, "jwt");
        if (token == null) {
            return ResponseEntity.badRequest().body(false);
        }
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body(false);
        }
        String usuarioName = jwtTokenProvider.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioName);
        Usuario usuario = ((CustomUserDetails) userDetails).getUsuario();
        if (usuario.getActivo() == false) {
            logout(request, response); // cierra la sesion
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.ok(true);
    }

    // --- renovar token ---
    @PostMapping("/refresh-token")
    @Operation(summary = "Renovar token", description = "Renueva un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token renovado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Token inválido", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para renovar el token", content = @Content),
            @ApiResponse(responseCode = "404", description = "El token no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al renovar el token", content = @Content)
    })
    public ResponseEntity<ValidateToken> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String token = cookieService.getCookieValue(request, "jwt");
        if (token == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body(null);
        }
        // extrae el username del token
        String username = jwtTokenProvider.extractUsername(token);
        // carga los detalles del usuario usando el nombre de usuario extraido
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Genera un nuevo token
        final String jwt = jwtTokenProvider.generateToken(userDetails);
        cookieService.setCookie(response, "jwt", jwt, cookieExpiration);

        return ResponseEntity.ok(new ValidateToken(jwtTokenProvider.extractExpiration(jwt).toString()));
    }

    // elimina el token mediante el usuario autenticado
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sesión cerrada exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para cerrar sesión", content = @Content),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al cerrar sesión", content = @Content)
    })
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = cookieService.getCookieValue(request, "jwt");
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().build();
        }
        cookieService.removeCookie(response, "jwt");
        return ResponseEntity.ok().build();
    }

}
