package com.synechron.insurance.controller;

import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.dto.crud.CreateOrUpdateInsuranceItemDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.mappers.InsuranceItemMapper;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.service.InsuranceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/insurance-items")
public class InsuranceItemController {
    private final InsuranceItemService service;
    private final InsuranceItemMapper mapper;

    @GetMapping
    public ResponseEntity<List<InsuranceItemDto>> findAll() {
        List<InsuranceItemDto> insuranceItemDtos = service.findAll().stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(insuranceItemDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InsuranceItemDto> create(@Valid @RequestBody CreateOrUpdateInsuranceItemDto dto) {
        InsuranceItem insuranceItem = service.create(dto);
        return new ResponseEntity<>(mapper.domainToDto(insuranceItem), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceItemDto> update(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateInsuranceItemDto dto) throws NotFoundException {
        InsuranceItem insuranceItem = service.update(id, dto);
        return new ResponseEntity<>(mapper.domainToDto(insuranceItem), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws NotFoundException {
        service.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
