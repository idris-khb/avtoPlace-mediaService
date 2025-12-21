package com.avtoplace.mediaservice.controller;

import com.avtoplace.mediaservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String key = file.getOriginalFilename();
            mediaService.uploadFile(key, file.getInputStream(), file.getSize(), file.getContentType());
            return ResponseEntity.ok("Файл загружен: " + key);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Ошибка загрузки");
        }
    }
}
