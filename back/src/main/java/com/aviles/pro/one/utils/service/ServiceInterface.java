
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

package com.aviles.pro.one.utils.service;

import java.util.List;

import org.springframework.lang.NonNull;

/**
 * Interfaz genérica para los servicios de la aplicación.
 * Define las operaciones CRUD básicas.
 *
 * @param <T>  Tipo de la entidad.
 * @param <ID> Tipo del identificador de la entidad (e.g., Long, Integer).
 */
public interface ServiceInterface<T, ID> {

    /**
     * Obtiene todos los registros.
     * 
     * @return Lista de entidades.
     */
    List<T> getAll();

    /**
     * Obtiene un registro por su ID.
     * 
     * @param id Identificador.
     * @return La entidad encontrada.
     */
    T getById(ID id);

    /**
     * Guarda un nuevo registro.
     * 
     * @param object Entidad a guardar.
     * @return La entidad guardada.
     */
    T save(T object);

    /**
     * Actualiza un registro existente.
     * 
     * @param id     Identificador del registro a actualizar.
     * @param object Datos actualizados.
     * @return La entidad actualizada.
     */
    T update(@NonNull ID id, T object);

    /**
     * Elimina un registro por su ID.
     * 
     * @param id Identificador.
     * @return La entidad eliminada (o null si no existía).
     */
    T delete(ID id);

    /**
     * Cuenta el total de registros.
     * 
     * @return Número total.
     */
    Long count();

    /**
     * Obtiene una lista paginada de registros.
     * 
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @return Lista de entidades.
     */
    List<T> getAllRange(int page, int size);
}
