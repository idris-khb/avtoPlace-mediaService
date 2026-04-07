package com.avtoplace.mediaservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCarAdRequest {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer year;

    @NotNull
    @Min(0)
    private Double price;

    @NotBlank
    private String city;

    @NotNull
    @Min(0)
    private Integer mileage;

    @NotBlank
    private String condition;

    private String description;

    @NotBlank
    private String phone;

    // опционально (если VIN используешь)
    private String vin;
}
