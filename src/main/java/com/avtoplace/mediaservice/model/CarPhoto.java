package com.avtoplace.mediaservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "car_photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarPhoto {

    @Id
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_ad_id", nullable = false)
    private CarAd carAd;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer position;

    public void setFilename(String key) {
        this.url = key; // сохраняем ключ S3 в поле url
    }

    public String getFilename() {
        return this.url; // возвращаем ключ S3
    }
}
