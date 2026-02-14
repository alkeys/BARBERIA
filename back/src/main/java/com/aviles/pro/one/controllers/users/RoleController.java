package com.aviles.pro.one.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviles.pro.one.models.users.Role;
import com.aviles.pro.one.services.users.RoleService;
import com.aviles.pro.one.utils.controller.AbstractController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Administrar Roles", description = "APIs para gestionar roles")
public class RoleController extends AbstractController<Role, Long, RoleService> {

    @Autowired
    private RoleService roleService;

    @Override
    protected RoleService getService() {
        return roleService;
    }

    @Override
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role entity) {
        Role existing = roleService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setNombre(entity.getNombre());
        existing.setDescripcion(entity.getDescripcion());

        return ResponseEntity.ok(roleService.save(existing));
    }
}
