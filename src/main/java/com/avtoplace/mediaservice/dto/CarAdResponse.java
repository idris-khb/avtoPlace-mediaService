package com.avtoplace.mediaservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CarAdResponse {
    private UUID id;
    private String brand;
    private String model;
    private Integer year;
    private Double price;
    private List<String> images;
    private String city;
    private Integer mileage;
    private String condition;
    private String description;
    private String phone;
    private String vin;
}
