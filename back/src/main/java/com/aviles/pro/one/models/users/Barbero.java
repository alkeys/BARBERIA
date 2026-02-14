package com.aviles.pro.one.models.users;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "barberos")
public class Barbero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @ColumnDefault("true")
    @Column(name = "activo")
    private Boolean activo;

    @ColumnDefault("true")
    @Column(name = "disponible")
    private Boolean disponible;

    // id usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}