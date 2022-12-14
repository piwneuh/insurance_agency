package com.synechron.insurance.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto {
    @NotNull(message = "Model not provided")
    private ModelDto model;
    @NotNull(message = "Car model year not provided")
    private Integer year;
    @NotBlank(message = "License number not provided")
    private String licenceNum;
}
