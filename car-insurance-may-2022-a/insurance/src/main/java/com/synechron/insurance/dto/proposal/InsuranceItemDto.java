package com.synechron.insurance.dto.proposal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InsuranceItemDto {
    private Long id;
    private String name;
    private Boolean isOptional;
    private Integer amount;
}
