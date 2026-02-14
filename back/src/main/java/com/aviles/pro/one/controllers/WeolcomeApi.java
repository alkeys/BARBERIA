package com.aviles.pro.one.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class WeolcomeApi {

    @GetMapping("/api/welcome")
    @Operation(summary = "Welcome to the API", description = "Welcome to the API para administradores")
    public String welcome() {
        return "Welcome to the API";
    }

    @GetMapping("/api/cobrador")
    @Operation(summary = "Welcome to the API", description = "Welcome to the API para cobradores")
    public String cobrador() {
        return "Welcome to the API Cobrador";
    }

    @GetMapping("/api/manager")
    @Operation(summary = "Welcome to the API", description = "Welcome to the API para manager")
    public String manager() {
        return "Welcome to the API Manager";
    }
}
