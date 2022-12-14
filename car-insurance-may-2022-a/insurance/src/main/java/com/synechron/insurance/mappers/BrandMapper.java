package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.car.BrandDto;
import com.synechron.insurance.model.car.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public BrandDto domainToDto(Brand brand) {
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
