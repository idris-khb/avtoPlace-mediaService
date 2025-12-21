package com.avtoplace.mediaservice.repository;

import com.avtoplace.mediaservice.model.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, UUID> {

    // Получаем все фото по объявлению, в порядке позиции
    List<CarPhoto> findByCarAdIdOrderByPositionAsc(UUID carAdId);
}
