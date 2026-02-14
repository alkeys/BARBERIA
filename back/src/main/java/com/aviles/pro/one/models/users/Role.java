package com.aviles.pro.one.models.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles", indexes = {
        @Index(name = "idx_nombre", columnList = "nombre"),
        @Index(name = "idx_descripcion", columnList = "descripcion")
})
@Schema(description = "Roles para los usuarios ")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del rol", example = "1")
    private Long id;

    // nombre del rol unique = true es paraque no se repita
    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre del rol", example = "ADMIN")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descripción del rol", example = "Descripción del rol")
    private String descripcion;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Usuario> usuarios;
}
