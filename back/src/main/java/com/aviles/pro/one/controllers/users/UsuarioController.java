package com.aviles.pro.one.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.models.users.Usuario;
import com.aviles.pro.one.services.users.UsuarioService;
import com.aviles.pro.one.utils.controller.AbstractController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Administrar Usuarios", description = "APIs para gestionar usuarios")
public class UsuarioController extends AbstractController<Usuario, Long, UsuarioService> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected UsuarioService getService() {
        return usuarioService;
    }

        // --- elimina el token de otro usaurio pasando el usuario a eliminar
    @PostMapping("/desactivar/{username}")
    @Operation(summary = "Desactivar usuario", description = "Desactiva el usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario desactivado exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para desactivar usuario", content = @Content),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al desactivar usuario", content = @Content)
    })
    public ResponseEntity<Usuario> desactivar(@PathVariable String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        usuario.setActivo(false);
        usuarioService.update(usuario.getId(), usuario);
        return ResponseEntity.ok().build();
    }

    // --- activa el token de otro usaurio pasando el usuario a activar
    @PostMapping("/activar/{username}")
    @Operation(summary = "Activar usuario", description = "Activa el usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario activado exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para activar usuario", content = @Content),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al activar usuario", content = @Content)
    })
    public ResponseEntity<Usuario> activar(@PathVariable String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        usuario.setActivo(true);
        usuarioService.update(usuario.getId(), usuario);
        return ResponseEntity.ok().build();
    }
}
