package com.aviles.pro.one.security;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Interceptor para validar JWT en conexiones WebSocket
 * Extrae el token del parámetro de query 'token' durante el handshake
 */
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtChannelInterceptor(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Extraer el token del parámetro de query
            List<String> tokenList = accessor.getNativeHeader("token");

            if (tokenList == null || tokenList.isEmpty()) {
                throw new IllegalArgumentException("Token JWT no proporcionado en la conexión WebSocket");
            }

            String token = tokenList.get(0);

            try {
                // Validar el token
                if (!jwtTokenProvider.validateToken(token)) {
                    throw new IllegalArgumentException("Token JWT inválido o expirado");
                }

                // Extraer el username del token
                String username = jwtTokenProvider.extractUsername(token);

                // Cargar los detalles del usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Crear la autenticación
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Establecer el usuario autenticado en el contexto de Spring Security
                accessor.setUser(authentication);

            } catch (Exception e) {
                throw new IllegalArgumentException("Error al autenticar la conexión WebSocket: " + e.getMessage());
            }
        }

        return message;
    }
}
