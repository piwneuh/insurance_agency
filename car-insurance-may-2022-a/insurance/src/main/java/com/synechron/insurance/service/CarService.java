package com.synechron.insurance.service;

import com.synechron.insurance.model.car.Car;
import com.synechron.insurance.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;

    public Car save(Car car) {
        return carRepository.save(car);
    }
}
