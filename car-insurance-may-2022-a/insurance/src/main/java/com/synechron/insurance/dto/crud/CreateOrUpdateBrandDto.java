package com.synechron.insurance.dto.crud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateBrandDto {
    @NotBlank(message = "Brand name not provided")
    private String name;
}
