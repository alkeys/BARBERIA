package com.aviles.pro.one.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.models.users.Barbero;
import com.aviles.pro.one.services.users.BarberoService;
import com.aviles.pro.one.utils.controller.AbstractController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/barberos")
@Tag(name = "Barberos", description = "API de barberos")
public class BarberoController extends AbstractController<Barbero, Long, BarberoService> {
    private final BarberoService barberoService;

    public BarberoController(BarberoService barberoService) {
        this.barberoService = barberoService;
    }

    @Override
    public BarberoService getService() {
        return this.barberoService;
    }

}
