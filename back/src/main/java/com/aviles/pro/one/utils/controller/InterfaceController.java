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

/**
 * Interfaz que define los metodos basicos de un controlador
 * 
 * @param <T> tipo de entidad
 */
public interface InterfaceController<T, ID> {

    /**
     * Obtiene todos los registros de la entidad
     * 
     * @return lista de registros
     */
    ResponseEntity<List<T>> findAll();

    /**
     * Obtiene un registro por id
     * 
     * @param id id del registro
     * @return registro
     */
    ResponseEntity<T> findById(ID id);

    /**
     * Guarda un registro
     * 
     * @param entity registro a guardar
     * @return registro guardado
     */
    ResponseEntity<T> save(T entity);

    /**
     * Actualiza un registro
     * 
     * @param entity registro a actualizar
     * @return registro actualizado
     */
    ResponseEntity<T> update(ID id, T entity);

    /**
     * Elimina un registro
     * 
     * @param id id del registro
     */
    ResponseEntity<T> delete(ID id);

    /**
     * Obtiene un registro por rango
     * 
     * @param page numero de pagina
     * @param size numero de registros por pagina
     * @return lista de registros
     */
    ResponseEntity<List<T>> getAllRange(int page, int size);

}
