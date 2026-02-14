package com.aviles.pro.one.models.users;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios", indexes = {
        @Index(name = "idx_usuario", columnList = "usuario"),
        @Index(name = "idx_nombre_completo", columnList = "nombre_completo"),
        @Index(name = "idx_activo", columnList = "activo"),
        @Index(name = "idx_fecha_creacion", columnList = "fecha_creacion"),
        // para el auctenticacion
        @Index(name = "idx_usuario_activo", columnList = "usuario, password_hash"),
})
@Schema(description = "entidad de usuarios para el registros etc")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Schema(description = "Role del usuario")
    private Role role;

    @Column(name = "nombre_completo")
    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    private String nombreCompleto;

    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre de usuario", example = "jdoe")
    private String usuario;

    @Column(name = "password_hash", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Encrypted password", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String passwordHash;

    @Column(nullable = false)
    @Schema(description = "User active status", example = "true")
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    @Schema(description = "Account creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (activo == null) {
            activo = true;
        }
    }
}
