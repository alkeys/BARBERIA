package com.aviles.pro.one.services.files;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aviles.pro.one.utils.service.impl.LocalFileStorageService;

/**
 * servicio para manejar los archivos validaciones y demas antes de subirlo al
 * servidor
 * y que se guarde en la base de datos
 */
@Service
public class FilesService {
    private final LocalFileStorageService localFileStorageService;

    public FilesService(LocalFileStorageService localFileStorageService) {
        this.localFileStorageService = localFileStorageService;
    }

    public String uploadFile(MultipartFile file) {
        try {
            if (!validateFile(file)) {
                return "El archivo no es valido";
            }
            localFileStorageService.store(file);
            return "archivo subido correctamente";
        } catch (Exception e) {
            return "Error al subir el archivo: " + e.getMessage();
        }
    }

    public String downloadFile() {
        try (Stream<Path> files = localFileStorageService.loadAll()) {
            return files.map(Path::toString).collect(Collectors.joining(", "));
        } catch (Exception e) {
            return "Error al descargar el archivo: " + e.getMessage();
        }
    }



    /**
     * descarga todos los archivos que se subieron al servidor
     * 
     * @return
     */
    public String downloadAllFiles() {
        try (Stream<Path> files = localFileStorageService.loadAll()) {
            return files.map(Path::toString).collect(Collectors.joining(", "));
        } catch (Exception e) {
            return "Error al descargar el archivo: " + e.getMessage();
        }
    }

    public boolean validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacio");
        }
        // el archivo no puede ser mayor a 10MB
        if (file.getSize() > 10485760) {
            throw new IllegalArgumentException("El archivo no puede ser mayor a 10MB");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("El archivo debe tener un nombre valido");
        }

        String lowerCaseFilename = originalFilename.toLowerCase();

        // el archivo no tiene que ser exe o ejecutable o codigo fuente
        // c,pithon,java,php,html,css,js,ts,etc
        if (lowerCaseFilename.endsWith(".exe") || lowerCaseFilename.endsWith(".bat")
                || lowerCaseFilename.endsWith(".sh") || lowerCaseFilename.endsWith(".c")
                || lowerCaseFilename.endsWith(".py") || lowerCaseFilename.endsWith(".java")
                || lowerCaseFilename.endsWith(".php") || lowerCaseFilename.endsWith(".html")
                || lowerCaseFilename.endsWith(".css") || lowerCaseFilename.endsWith(".js")
                || lowerCaseFilename.endsWith(".ts")) {
            throw new IllegalArgumentException("El archivo no puede ser ejecutable");
        }

        // solo tiene que ser pdf, doc, docx, xls, xlsx, png, jpg, jpeg webp
        if (!lowerCaseFilename.endsWith(".pdf") && !lowerCaseFilename.endsWith(".doc") &&
                !lowerCaseFilename.endsWith(".docx") && !lowerCaseFilename.endsWith(".xls") &&
                !lowerCaseFilename.endsWith(".xlsx") && !lowerCaseFilename.endsWith(".png") &&
                !lowerCaseFilename.endsWith(".jpg") && !lowerCaseFilename.endsWith(".jpeg") &&
                !lowerCaseFilename.endsWith(".webp")) {
            throw new IllegalArgumentException("El archivo no es valido");
        }
        return true;
    }

}
