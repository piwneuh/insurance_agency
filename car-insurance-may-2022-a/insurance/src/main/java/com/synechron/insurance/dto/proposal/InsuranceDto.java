package com.synechron.insurance.dto.proposal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceDto {
    @NotNull(message = "Insurance plan not provided")
    private InsurancePlanDto insurancePlan;
    @NotEmpty(message = "Insurance items not provided")
    private List<InsuranceItemDto> insuranceItems;
    private List<FranchiseDto> franchises;
}
