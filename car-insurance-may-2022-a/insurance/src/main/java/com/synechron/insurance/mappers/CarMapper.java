package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.car.CarDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.car.Car;
import com.synechron.insurance.model.car.Model;
import com.synechron.insurance.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelMapper modelMapper;

    public Car dtoToDomain(CarDto dto) throws NotFoundException {
        Model model = modelService.findById(dto.getModel().getId());

        return Car.builder()
                .model(model)
                .licenceNum(dto.getLicenceNum())
                .year(dto.getYear())
                .build();
    }

    public CarDto domainToDto(Car car) throws NotFoundException {
        return CarDto.builder()
                .model(modelMapper.domainToDto(car.getModel()))
                .year(car.getYear())
                .licenceNum(car.getLicenceNum())
                .build();
    }
}
