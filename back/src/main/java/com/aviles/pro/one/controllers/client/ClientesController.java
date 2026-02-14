package com.aviles.pro.one.controllers.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.models.client.Cliente;
import com.aviles.pro.one.services.client.ClientesService;
import com.aviles.pro.one.utils.controller.AbstractController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "API de clientes")
public class ClientesController extends AbstractController<Cliente, Long, ClientesService> {
    private final ClientesService clienteService;

    public ClientesController(ClientesService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public ClientesService getService() {
        return this.clienteService;
    }

}
