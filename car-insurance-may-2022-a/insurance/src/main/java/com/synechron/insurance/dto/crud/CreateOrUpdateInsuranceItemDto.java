package com.synechron.insurance.dto.crud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateInsuranceItemDto {
    @NotBlank(message = "Insurance item name not provided")
    private String name;
    @NotNull(message = "Insurance item optionality not provided")
    private Boolean isOptional;
    @NotNull(message = "Insurance item amount not provided")
    @Positive(message = "Insurance item amount must be positive")
    private Integer amount;
}
