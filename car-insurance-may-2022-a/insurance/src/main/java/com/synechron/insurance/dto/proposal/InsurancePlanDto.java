package com.synechron.insurance.dto.proposal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePlanDto {
    private Long id;
    private String name;
    private Boolean isPremium;
    private List<InsuranceItemDto> insuranceItems;
}
