package com.aviles.pro.one.dto.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Validate token")
public class ValidateToken {

    @Schema(description = "Hora de expiracion", example = "2026-01-23T21:32:55.000+00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String horaExpiracion;

    public ValidateToken() {
    }

    public ValidateToken(String horaExpiracion) {
        this.horaExpiracion = horaExpiracion;
    }
}
