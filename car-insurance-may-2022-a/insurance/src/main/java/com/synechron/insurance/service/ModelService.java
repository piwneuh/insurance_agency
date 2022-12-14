package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.model.car.Model;
import com.synechron.insurance.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelService {
    private final ModelRepository repository;

    public Model findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Model not found"));
    }

    public void validateModelYear(Model model, Integer year) throws ValidationException {
        for(Integer y: model.getYears()) {
            if (y.compareTo(year) == 0) {
                return;
            }
        }
        throw new ValidationException(String.format("Car model %s with year %d does not exist", model.getName(), year));
    }

    public List<Model> findAllByBrandId(Long id) {
        return repository.findAllByBrandIdAndIsDeleted(id, false);
    }

    public Model save(Model model) {
        return repository.save(model);
    }

    public Boolean delete(Long modelId) throws NotFoundException {
        Model model = findById(modelId);
        model.setIsDeleted(true);
        repository.save(model);
        return true;
    }
}
