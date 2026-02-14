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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aviles.pro.one.utils.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase abstracta base para los servicios, implementando la lógica CRUD común.
 *
 * @param <T>  Tipo de entidad.
 * @param <ID> Tipo de la clave primaria de la entidad.
 */
@Slf4j
public abstract class ServiceAbstract<T, ID> implements ServiceInterface<T, ID> {

    /**
     * Debe proporcionar el repositorio JPA correspondiente.
     * * @return JpaRepository para la entidad T.
     */
    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    public List<T> getAll() {
        try {
            log.debug("Obteniendo todos los registros");
            List<T> result = getRepository().findAll();
            log.debug("Se encontraron {} registros", result.size());
            return result;
        } catch (Exception e) {
            log.error("Error al obtener todos los registros: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public T getById(ID id) {
        try {
            log.debug("Obteniendo registro con id: {}", id);
            T result = getRepository().findById(id).orElse(null);
            if (result != null) {
                log.debug("Registro encontrado: {}", result);
            } else {
                log.warn("Registro con id {} no encontrado", id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error al obtener el registro con id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public T save(T object) {
        try {
            log.debug("Guardando registro: {}", object);
            T result = getRepository().save(object);
            log.debug("Registro guardado exitosamente: {}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error al guardar el registro: {}", e.getMessage(), e);
            throw e;
        }
    }

    /*
     *   
     */
    @Override
    public T update(@NonNull ID id, T object) {
        if (id == null || object == null) {
            throw new IllegalArgumentException("El ID y el objeto no pueden ser nulos");
        }

        log.debug("Actualizando registro con id: {}", id);

        T entity = getRepository().findById(id).orElse(null);
        if (entity == null) {
            log.warn("Registro con id {} no encontrado para actualización", id);
            return null;
        }

        try {
            // Copia todo MENOS el ID
            org.springframework.beans.BeanUtils.copyProperties(object, entity, "id");

            T result = getRepository().save(entity);
            log.debug("Registro actualizado exitosamente: {}", result);
            return result;

        } catch (Exception e) {
            log.error("Error al actualizar el registro con id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public T delete(ID id) {
        try {
            log.debug("Eliminando registro con id: {}", id);
            Optional<T> entity = getRepository().findById(id);
            if (entity.isPresent()) {
                getRepository().deleteById(id);
                log.debug("Registro eliminado exitosamente");
                return entity.get();
            } else {
                log.warn("Registro con id {} no encontrado para eliminación", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error al eliminar el registro con id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Long count() {
        try {
            return getRepository().count();
        } catch (Exception e) {
            log.error("Error al contar los registros: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<T> getAllRange(int page, int size) {
        try {
            log.debug("Obteniendo página {} de registros con tamaño {}", page, size);
            List<T> result = getRepository().findAll(PageRequest.of(page, size)).getContent();
            log.debug("Se encontraron {} registros en la página", result.size());
            return result;
        } catch (Exception e) {
            log.error("Error al obtener la página {} con tamaño {}: {}", page, size, e.getMessage(), e);
            throw e;
        }
    }
}
