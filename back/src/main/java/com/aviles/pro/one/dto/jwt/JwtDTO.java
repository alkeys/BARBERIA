package com.aviles.pro.one.dto.jwt;

import com.aviles.pro.one.models.users.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description="JWT DTO")
public class JwtDTO {
    @Schema(description="JWT expiration date", example="2022-01-01T00:00:00.000")
    private String horaExpiracion;
    @Schema(description="JWT role", example="ROLE_ADMIN")
    private String rolUsuario;
    @Schema(description="JWT user", example="1")
    private Usuario usuario;

    public JwtDTO(String horaExpiracion, String rolUsuario, Usuario usuario) {
        this.horaExpiracion = horaExpiracion;
        this.rolUsuario = rolUsuario;
        this.usuario = usuario;
    }
}
