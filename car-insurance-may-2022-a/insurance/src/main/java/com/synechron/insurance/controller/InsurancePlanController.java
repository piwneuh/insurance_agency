package com.synechron.insurance.controller;

import com.synechron.insurance.dto.proposal.InsurancePlanDto;
import com.synechron.insurance.dto.crud.CreateOrUpdateInsurancePlanDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.mappers.InsurancePlanMapper;
import com.synechron.insurance.model.proposal.InsurancePlan;
import com.synechron.insurance.service.InsurancePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/insurance-plans")
public class InsurancePlanController {
    private final InsurancePlanService service;
    private final InsurancePlanMapper mapper;

    @GetMapping
    public ResponseEntity<List<InsurancePlanDto>> findAll() {
        List<InsurancePlanDto> insurancePlansDto = new ArrayList<>();
        for (InsurancePlan insurancePlan : service.findAll()) {
            InsurancePlanDto insurancePlanDto = mapper.domainToDto(insurancePlan);
            insurancePlansDto.add(insurancePlanDto);
        }
        return new ResponseEntity<>(insurancePlansDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InsurancePlanDto> create(@Valid @RequestBody CreateOrUpdateInsurancePlanDto dto) throws NotFoundException {
        InsurancePlan insurancePlan = service.create(dto);
        return new ResponseEntity<>(mapper.domainToDto(insurancePlan), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsurancePlanDto> update(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateInsurancePlanDto dto) throws NotFoundException {
        InsurancePlan insurancePlan = service.update(id, dto);
        return new ResponseEntity<>(mapper.domainToDto(insurancePlan), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws NotFoundException {
        service.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
