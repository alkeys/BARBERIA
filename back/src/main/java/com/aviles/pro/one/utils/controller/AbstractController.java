/*
 * Copyright 2026 Alex Aviles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 */

package com.aviles.pro.one.utils.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.aviles.pro.one.utils.service.ServiceAbstract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Clase abstracta que implementa los metodos basicos de un controlador
 * 
 * @param <T>  tipo de entidad
 * @param <ID> tipo de id
 */
public abstract class AbstractController<T, ID, S extends ServiceAbstract<T, ID>>
        implements InterfaceController<T, ID> {

    protected abstract S getService();

    @Override
    @GetMapping("getAll")
    @Operation(summary = "Obtener todos los registros", description = "Retorna una lista de todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros obtenidos exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para obtener registros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al obtener los registros", content = @Content)
    })
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @Override
    @GetMapping("getById/{id}")
    @Operation(summary = "Obtener registro por ID", description = "Busca una entidad específica por su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acceso denegado al recurso", content = @Content),
            @ApiResponse(responseCode = "404", description = "El registro no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al buscar el registro", content = @Content)
    })
    public ResponseEntity<T> findById(@PathVariable ID id) {
        T entity = getService().getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    @Override
    @PostMapping("save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para crear registros", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto: El registro ya existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al intentar guardar", content = @Content)
    })
    @Operation(summary = "Guardar registro", description = "Crea un nuevo registro en el sistema")
    public ResponseEntity<T> save(@RequestBody T entity) {
        try {
            // verifica que no sea nulo
            if (entity == null) {
                return ResponseEntity.badRequest().build();
            }
            // guarda el registro
            T savedEntity = getService().save(entity);
            // verifica que no sea nulo
            if (savedEntity == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(savedEntity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping("update/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para modificar este registro", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró el registro", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al actualizar", content = @Content)
    })
    @Operation(summary = "Actualizar registro", description = "Actualiza los datos de un registro existente")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        try {
            // revisa si existe el registro
            if (getService().getById(id) == null) {
                return ResponseEntity.notFound().build();
            }
            // actualiza el registro
            T result = getService().update(id, entity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // error al actualizar
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @DeleteMapping("delete/{id}")
    @Operation(summary = "Eliminar registro", description = "Elimina un registro del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos suficientes (Se requiere ADMIN)", content = @Content),
            @ApiResponse(responseCode = "404", description = "El registro no existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al procesar la eliminación", content = @Content)
    })
    public ResponseEntity<T> delete(@PathVariable ID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        // revisa si existe el registro
        if (getService().getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        T deletedEntity = getService().delete(id);
        if (deletedEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedEntity);
    }

    @Override
    @GetMapping("getAllRange/{page}/{size}")
    @Operation(summary = "Obtener registros paginados", description = "Retorna una lista paginada de registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros obtenidos exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para obtener los registros", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontraron registros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al obtener los registros", content = @Content)
    })
    public ResponseEntity<List<T>> getAllRange(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(getService().getAllRange(page, size));
    }

}
