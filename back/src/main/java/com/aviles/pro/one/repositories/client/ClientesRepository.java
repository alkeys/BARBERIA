package com.aviles.pro.one.repositories.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviles.pro.one.models.client.Cliente;



public interface ClientesRepository extends JpaRepository<Cliente, Long> {
    
}
