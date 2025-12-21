package com.avtoplace.mediaservice.service;

import com.avtoplace.mediaservice.model.CarAd;
import com.avtoplace.mediaservice.repository.CarAdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarAdService {

    private final CarAdRepository carAdRepository;

    // Сохраняем объявление
    public CarAd saveCarAd(CarAd carAd) {
        return carAdRepository.save(carAd);
    }

    // Получаем все объявления
    public List<CarAd> getAllAds() {
        return carAdRepository.findAll();
    }

    // Получаем одно объявление по ID
    public CarAd getAdById(UUID id) {
        return carAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CarAd not found"));
    }

    // Удаляем объявление по ID
    public void deleteAd(UUID id) {
        carAdRepository.deleteById(id);
    }
}
