package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.City;
import com.synechron.insurance.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository repository;

    public City findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("City with id %d not found", id)));
    }

    public City save(City city) {
        return repository.save(city);
    }

    public Boolean delete(Long cityId) {
        City city = repository.getById(cityId);
        city.setIsDeleted(true);
        repository.save(city);
        return true;
    }
}
