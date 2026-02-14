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

package com.aviles.pro.one.utils.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aviles.pro.one.utils.service.FileStorageService;

import jakarta.annotation.PostConstruct;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private Path rootLocation;

    /***
     * Inicializa el almacenamiento local
     */
    @PostConstruct
    public void init() {
        try {
            this.rootLocation = Paths.get(uploadDir);
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el almacenamiento", e);
        }
    }

    /***
     * Almacena un archivo en el almacenamiento local
     * 
     * @param file el archivo a almacenar
     * @return el nombre del archivo almacenado
     */
    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Error al almacenar el archivo.");
            }
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(filename))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // esto es para evitar que el archivo se almacene fuera del directorio actual
                throw new RuntimeException(
                        "No se puede almacenar el archivo fuera del directorio actual.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo.", e);
        }
    }

    /***
     * Obtiene todos los archivos almacenados en el almacenamiento local
     * 
     * @return un Stream de Path con los archivos almacenados
     */
    @Override

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer los archivos almacenados", e);
        }

    }

    /***
     * Obtiene un archivo almacenado en el almacenamiento local
     * 
     * @param filename el nombre del archivo a obtener
     * @return el Path del archivo
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /***
     * Obtiene un archivo almacenado en el almacenamiento local como Resource
     * 
     * @param filename el nombre del archivo a obtener
     * @return el Resource del archivo
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(
                        "No se pudo leer el archivo: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al leer el archivo: " + filename, e);
        }
    }

    /***
     * Elimina todos los archivos almacenados en el almacenamiento local
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
