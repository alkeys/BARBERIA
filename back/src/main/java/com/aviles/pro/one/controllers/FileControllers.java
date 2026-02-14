package com.aviles.pro.one.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aviles.pro.one.services.files.FilesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/files")
public class FileControllers {

    private final FilesService filesService;

    public FileControllers(FilesService filesService) {
        this.filesService = filesService;
    }

    /**
     * para subir archivos
     * 
     * @param file
     * @return
     */
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir un archivo", description = "Subir un archivo al servidor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo subido correctamente"),
            @ApiResponse(responseCode = "400", description = "Archivo no valido"),
            @ApiResponse(responseCode = "500", description = "Error al subir el archivo"),
            @ApiResponse(responseCode = "415", description = "Tipo de archivo no soportado"),
            @ApiResponse(responseCode = "404", description = "Archivo no encontrado"),
            @ApiResponse(responseCode = "409", description = "Archivo ya existe"),
            @ApiResponse(responseCode = "413", description = "Archivo muy grande")
    })
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return filesService.uploadFile(file);
        } catch (Exception e) {
            return "Error al subir el archivo: " + e.getMessage();
        }
    }

    @GetMapping("download/{filename}")
    @Operation(summary = "Descargar un archivo", description = "Descargar un archivo del servidor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo descargado correctamente"),
            @ApiResponse(responseCode = "400", description = "Archivo no valido"),
            @ApiResponse(responseCode = "500", description = "Error al descargar el archivo"),
            @ApiResponse(responseCode = "415", description = "Tipo de archivo no soportado"),
            @ApiResponse(responseCode = "404", description = "Archivo no encontrado"),
            @ApiResponse(responseCode = "409", description = "Archivo ya existe"),
            @ApiResponse(responseCode = "413", description = "Archivo muy grande")
    })
    public String downloadFile() {
        try {
            return filesService.downloadFile();
        } catch (Exception e) {
            return "Error al descargar el archivo: " + e.getMessage();
        }
    }



    @GetMapping("downloadAllFiles")
    @Operation(summary = "Descargar todos los archivos", description = "Descargar todos los archivos del servidor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivos descargados correctamente"),
            @ApiResponse(responseCode = "400", description = "Archivos no validos"),
            @ApiResponse(responseCode = "500", description = "Error al descargar los archivos"),
            @ApiResponse(responseCode = "415", description = "Tipo de archivo no soportado"),
            @ApiResponse(responseCode = "404", description = "Archivos no encontrados"),
            @ApiResponse(responseCode = "409", description = "Archivos ya existen"),
            @ApiResponse(responseCode = "413", description = "Archivos muy grandes")
    })
    public String downloadAllFiles() {
        try {
            return filesService.downloadAllFiles();
        } catch (Exception e) {
            return "Error al descargar los archivos: " + e.getMessage();
        }
    }
}
