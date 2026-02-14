package com.aviles.pro.one.controllers.citas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.models.citas.Cita;
import com.aviles.pro.one.services.citas.CitasService;
import com.aviles.pro.one.utils.controller.AbstractController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/citas")
@Tag(name = "Citas", description = "API de citas")
public class CitasController extends AbstractController<Cita, Long, CitasService> {
    private final CitasService citasService;

    public CitasController(CitasService citasService) {
        this.citasService = citasService;
    }

    @Override
    public CitasService getService() {
        return this.citasService;
    }

}