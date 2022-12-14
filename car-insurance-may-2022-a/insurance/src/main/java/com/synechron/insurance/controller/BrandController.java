package com.synechron.insurance.controller;

import com.synechron.insurance.dto.car.BrandDto;
import com.synechron.insurance.dto.car.ModelDto;
import com.synechron.insurance.dto.crud.CreateOrUpdateBrandDto;
import com.synechron.insurance.dto.crud.CreateOrUpdateModelDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.mappers.BrandMapper;
import com.synechron.insurance.mappers.ModelMapper;
import com.synechron.insurance.model.car.Brand;
import com.synechron.insurance.model.car.Model;
import com.synechron.insurance.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService service;
    private final BrandMapper brandMapper;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BrandDto>> findAll(){
        List<BrandDto> brandsDto = service.findAll()
                .stream()
                .map(brandMapper::domainToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(brandsDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/models")
    public ResponseEntity<List<ModelDto>> findAllModelsByBrand(@PathVariable Long id) {
        List<ModelDto> modelsDto = service.findAllModelsByBrand(id)
                .stream()
                .map(modelMapper::domainToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(modelsDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Brand> createBrand(@Valid @RequestBody CreateOrUpdateBrandDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateBrandDto dto) throws NotFoundException {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @PostMapping("/{brandId}/models")
    public ResponseEntity<Model> createModel(@PathVariable Long brandId, @Valid @RequestBody CreateOrUpdateModelDto dto) throws NotFoundException {
        return new ResponseEntity<>(service.createModelForBrand(brandId, dto), HttpStatus.CREATED);
    }

    @PutMapping("{brandId}/models/{modelId}")
    public ResponseEntity<Model> updateModel(@PathVariable Long brandId, @PathVariable Long modelId, @Valid @RequestBody CreateOrUpdateModelDto dto) throws ValidationException, NotFoundException {
        return new ResponseEntity<>(service.updateModelForBrand(brandId, modelId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/{brandId}/models/{modelId}")
    public ResponseEntity<Boolean> deleteModel(@PathVariable Long brandId, @PathVariable Long modelId) throws ValidationException, NotFoundException {
        return new ResponseEntity<>(service.deleteModel(brandId, modelId), HttpStatus.OK);
    }

}
