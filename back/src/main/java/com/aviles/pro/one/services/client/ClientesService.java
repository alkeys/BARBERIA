package com.aviles.pro.one.services.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.aviles.pro.one.models.client.Cliente;
import com.aviles.pro.one.repositories.client.ClientesRepository;
import com.aviles.pro.one.utils.service.ServiceAbstract;

@Service
public class ClientesService extends ServiceAbstract<Cliente, Long> {

    private final ClientesRepository clientesRepository;

    public ClientesService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @Override
    protected JpaRepository<Cliente, Long> getRepository() {
        return this.clientesRepository;
    }

}
