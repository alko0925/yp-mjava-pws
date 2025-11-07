package ru.ya.spring3pw.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import ru.ya.spring3pw.service.FilesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    // POST-эндпоинт для загрузки файла
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return filesService.upload(file);
    }

    // GET-эндпоинт для скачивания файла
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "filename") String filename) {
        Resource file = filesService.download(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
