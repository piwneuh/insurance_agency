package com.synechron.insurance.dto.proposal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FranchiseDto {
    private InsuranceItemDto insuranceItem;
    private int percentage;
}
