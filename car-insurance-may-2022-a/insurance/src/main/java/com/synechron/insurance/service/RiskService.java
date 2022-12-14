package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.car.Risk;
import com.synechron.insurance.repository.DriverRepository;
import com.synechron.insurance.repository.RiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RiskService {
    private final RiskRepository repository;
    private final DriverRepository driverRepository;

    public List<Risk> findAll() {
        return repository.findAll();
    }
    public Risk findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Risk with id %d not found", id)));
    }

    public List<Risk> create(Risk risk) {
        repository.save(risk);
        return repository.findAll();
    }

    public Boolean delete(Long id) throws NotFoundException {
        Risk risk = findById(id);
        driverRepository.findDriversByRisksContaining(risk).forEach(driver -> {
            driver.getRisks().remove(risk);
            driverRepository.save(driver);
        });
        repository.deleteById(id);
        return true;
    }
}
