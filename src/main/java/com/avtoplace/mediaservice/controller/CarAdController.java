package com.avtoplace.mediaservice.controller;

import com.avtoplace.mediaservice.dto.CarAdResponse;
import com.avtoplace.mediaservice.dto.CreateCarAdRequest;
import com.avtoplace.mediaservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/media/api/ads")
@RequiredArgsConstructor
public class CarAdController {

    private final MediaService mediaService;

    // Создание объявления с фотографиями
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CarAdResponse createAd(
            @RequestPart("data")CreateCarAdRequest request,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
    ) throws Exception {
         return mediaService.createAd(request, photos);
    }

    // Получение всех объявлений
    @GetMapping
    public List<CarAdResponse> getAllAds() {
        return mediaService.getAll();
    }

    // Получение объявления по ID
    @GetMapping("/{id}")
    public CarAdResponse getById(@PathVariable UUID id) {
        return mediaService.getById(id);
    }

    // Удаление объявления по ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        mediaService.deleteCarAd(id);
    }
}
