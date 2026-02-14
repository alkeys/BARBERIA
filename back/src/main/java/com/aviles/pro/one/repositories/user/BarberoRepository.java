package com.aviles.pro.one.repositories.user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aviles.pro.one.models.users.Barbero;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {

    @Query("""
                SELECT b FROM Barbero b
                WHERE b.activo = true
                AND b.id NOT IN (
                    SELECT c.barbero.id FROM Cita c
                    WHERE c.fecha = :fecha
                    AND c.hora = :hora
                    AND c.estado IN ('PENDIENTE', 'CONFIRMADA')
                )
            """)
    List<Barbero> findBarberosDisponibles(
            @Param("fecha") LocalDate fecha,
            @Param("hora") LocalTime hora);
}
