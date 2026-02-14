package com.aviles.pro.one.config;

public final class ListAuth {
        // --- Listas de Autenticación ---
        /**
         * Lista de rutas que se permiten sin autenticación para el swagger y otras
         * rutas
         */
        public static final String[] SWAGGER_AUTH_WHITELIST = {
                        "/api/usuarios/save",
                        "/api/roles/save",
                        "/",
                        "/index.html",
                        "/ws",
                        "/ws/**"
        };

        /**
         * Lista de rutas que se permiten sin autenticación para el login y refresh
         * token
         */
        public static final String[] ENPONT_WHITELIST = {
                        "/api/authenticate",
                        "/api/validate-token",
                        "/api/refresh-token",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
        };

        /**
         * Lista de rutas que se requieren autenticación para acceder modo admin
         */
        public static final String[] AUTH_BLACKLIST = {
                        "/api/**"
        };

        /**
         * Lista para manager
         */
        public static final String[] AUTH_BLACKLIST_MANAGER = {
                        "/api/manager/**"
        };

        /**
         * Lista de rutas que se requieren autenticación para acceder modo conductor
         */
        public static final String[] AUTH_BLACKLIST_COMBRADORES = {
                        "/api/cobrador/**"
        };

        /**
         * Lista de rutas que se permiten sin autenticación para subir archivos y ver
         * datos
         */
        public static final String[] multimediaWhitelist = {
                        // ruta de subida de imagenes de documentos pra poder ver las fotos
                        "/uploads/**"
        };

}
