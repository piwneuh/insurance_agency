package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.car.ModelDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.car.Model;
import com.synechron.insurance.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    @Autowired
    private ModelService modelService;
    @Autowired
    private BrandMapper brandMapper;

    public Model dtoToDomain(ModelDto dto) throws NotFoundException {
        return modelService.findById(dto.getId());
    }

    public ModelDto domainToDto(Model model) {
        return ModelDto.builder()
                .id(model.getId())
                .name(model.getName())
                .years(model.getYears())
                .brand(brandMapper.domainToDto(model.getBrand()))
                .build();
    }
}
