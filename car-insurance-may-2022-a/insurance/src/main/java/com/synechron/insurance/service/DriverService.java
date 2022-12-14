package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.Driver;
import com.synechron.insurance.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DriverService {
    private final DriverRepository repository;

    public Driver findByJmbg(String jmbg) throws NotFoundException {
        return repository.findByJmbg(jmbg)
                .orElseThrow(() -> new NotFoundException(String.format("Driver with %s jmbg not found", jmbg)));
    }
}
