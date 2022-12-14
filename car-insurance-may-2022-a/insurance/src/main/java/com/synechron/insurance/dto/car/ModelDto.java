package com.synechron.insurance.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelDto {
    private Long id;
    private String name;
    private List<Integer> years;
    private BrandDto brand;
}
