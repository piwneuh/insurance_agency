package com.synechron.insurance.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskDto {
    private Long id;
    @NotBlank(message = "Risk description not provided")
    private String description;
}
