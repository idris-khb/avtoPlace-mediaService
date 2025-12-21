package com.avtoplace.mediaservice.controller;

import com.avtoplace.mediaservice.model.CarAd;
import com.avtoplace.mediaservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarAdController {

    private final MediaService mediaService;

    // Создание объявления с фотографиями
    @PostMapping
    public ResponseEntity<CarAd> createAd(
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam Double price,
            @RequestParam List<MultipartFile> photos
    ) throws Exception {
        CarAd ad = new CarAd();
        ad.setBrand(brand);
        ad.setModel(model);
        ad.setPrice(price);

        CarAd savedAd = mediaService.saveCarAdWithPhotos(ad, photos);
        return ResponseEntity.ok(savedAd);
    }

    // Получение всех объявлений
    @GetMapping
    public ResponseEntity<List<CarAd>> getAllAds() {
        return ResponseEntity.ok(mediaService.getAllCarAds());
    }

    // Получение объявления по ID
    @GetMapping("/{id}")
    public ResponseEntity<CarAd> getAd(@PathVariable UUID id) {
        return ResponseEntity.ok(mediaService.getCarAdById(id));
    }

    // Удаление объявления по ID
    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable UUID id) {
        mediaService.deleteCarAd(id);
    }
}
