package com.aviles.pro.one.repositories.citas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviles.pro.one.models.citas.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>{
    
}
