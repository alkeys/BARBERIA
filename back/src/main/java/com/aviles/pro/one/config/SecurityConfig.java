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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aviles.pro.one.security.JwtAuthenticationFilter;
import com.aviles.pro.one.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtTokenProvider jwtTokenProvider;

        public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
                this.jwtTokenProvider = jwtTokenProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        @Qualifier("usuarioService") UserDetailsService userDetailsService)
                        throws Exception {
                JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider,
                                userDetailsService);

                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests

                                                // enpoint sin autenticación para login y refresh token
                                                .requestMatchers(ListAuth.ENPONT_WHITELIST).permitAll()
                                                .requestMatchers(ListAuth.SWAGGER_AUTH_WHITELIST).permitAll()

                                                // enpoint con autenticación para administradores
                                                .requestMatchers(ListAuth.AUTH_BLACKLIST).hasRole("ADMIN")

                                                // enpoint con autenticación para conductores
                                                .requestMatchers(ListAuth.AUTH_BLACKLIST_COMBRADORES)
                                                .hasRole("COBRADOR")

                                                // enpoint con autenticación para manager
                                                .requestMatchers(ListAuth.AUTH_BLACKLIST_MANAGER)
                                                .hasRole("MANAGER")

                                                // enpoint con autenticación para multimedia
                                                .requestMatchers(ListAuth.multimediaWhitelist).permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Usar
                                                                                                         // sesiones sin
                                                                                                         // estado
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añadir
                                                                                                                       // el
                                                                                                                       // filtro
                                                                                                                       // JWT

                return http.build();
        }

        /**
         * Bean para encriptar contraseñas
         * 
         * @return
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}