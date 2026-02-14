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

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();

    /**
     * Almacena un archivo en el almacenamiento local
     * 
     * @param file el archivo a almacenar
     * @return el nombre del archivo almacenado
     */
    String store(MultipartFile file);

    /**
     * Obtiene todos los archivos almacenados en el almacenamiento local
     * 
     * @return un Stream de Path con los archivos almacenados
     */
    Stream<Path> loadAll();

    /**
     * Obtiene un archivo almacenado en el almacenamiento local
     * 
     * @param filename el nombre del archivo a obtener
     * @return el Path del archivo
     */
    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
