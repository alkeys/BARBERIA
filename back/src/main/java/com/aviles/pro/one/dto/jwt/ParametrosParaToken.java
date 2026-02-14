package com.aviles.pro.one.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Parametros para generar un token")
public class ParametrosParaToken {
    @Schema(description = "Nombre de usuario", example = "usuario")
    private String usuario;
    @Schema(description = "Contraseña", example = "password")
    private String password;
}
