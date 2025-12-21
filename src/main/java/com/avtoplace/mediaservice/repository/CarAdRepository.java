package com.avtoplace.mediaservice.repository;

import com.avtoplace.mediaservice.model.CarAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarAdRepository extends JpaRepository<CarAd, UUID> {
}
