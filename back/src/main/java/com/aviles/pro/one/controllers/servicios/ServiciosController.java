package com.aviles.pro.one.controllers.servicios;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.models.servicios.Servicio;
import com.aviles.pro.one.services.servicios.ServiciosService;
import com.aviles.pro.one.utils.controller.AbstractController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/servicios")
@Tag(name = "Servicios", description = "API de servicios")
public class ServiciosController extends AbstractController<Servicio, Long, ServiciosService> {
    private final ServiciosService serviciosService;

    public ServiciosController(ServiciosService serviciosService) {
        this.serviciosService = serviciosService;
    }

    @Override
    public ServiciosService getService() {
        return this.serviciosService;
    }

}
