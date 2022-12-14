package com.synechron.insurance.dto.crud;

import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrUpdateInsurancePlanDto {
    @NotBlank(message = "Insurance plan name not provided")
    private String name;
    @NotNull(message = "Insurance plan distinction not provided")
    private Boolean isPremium;
    @NotEmpty(message = "Insurance plan items not provided")
    private List<InsuranceItemDto> insuranceItems;
}
