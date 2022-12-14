package com.synechron.insurance.service;

import com.synechron.insurance.dto.crud.CreateOrUpdateBrandDto;
import com.synechron.insurance.dto.crud.CreateOrUpdateModelDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.model.car.Brand;
import com.synechron.insurance.model.car.Model;
import com.synechron.insurance.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository repository;
    private final ModelService modelService;

    public List<Brand> findAll() {
        return repository.findAllByIsDeleted(false);
    }

    public List<Model> findAllModelsByBrand(Long id) {
        return modelService.findAllByBrandId(id);
    }

    public Brand findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id %d not found", id)));
    }
    public Brand create(CreateOrUpdateBrandDto dto) {
        Brand brand = Brand.builder()
                .name(dto.getName())
                .isDeleted(false)
                .build();

        return repository.save(brand);
    }

    public Brand update(Long id, CreateOrUpdateBrandDto dto) throws NotFoundException {
        Brand brandToUpdate = findById(id);
        brandToUpdate.setName(dto.getName());

        return repository.save(brandToUpdate);
    }

    public Model createModelForBrand(Long brandId, CreateOrUpdateModelDto dto) throws NotFoundException {
        Brand brand = findById(brandId);

        Model model = Model.builder()
                .name(dto.getName())
                .years(dto.getYears())
                .brand(brand)
                .isDeleted(false)
                .build();

        return modelService.save(model);
    }

    public Model updateModelForBrand(Long brandId, Long modelId, CreateOrUpdateModelDto dto) throws NotFoundException, ValidationException {
        Brand brand = findById(brandId);
        Model model = modelService.findById(modelId);

        if (!model.getBrand().equals(brand)) {
            throw new ValidationException(String.format("Brand %s does not have model %s", brand.getName(), model.getName()));
        }

        model.setName(dto.getName());
        model.setYears(dto.getYears());

        return modelService.save(model);
    }

    public Boolean delete(Long id) {
        Brand brand = repository.getById(id);
        brand.setIsDeleted(true);
        repository.save(brand);
        return true;
    }

    public Boolean deleteModel(Long brandId, Long modelId) throws NotFoundException, ValidationException {
        Model model = modelService.findById(modelId);
        if (!model.getBrand().getId().equals(brandId)) {
            throw new ValidationException(String.format("Brand %s does not have model %s", model.getBrand().getName(), model.getName()));
        }
        return modelService.delete(modelId);
    }
}
