package com.synechron.insurance.controller;

import com.synechron.insurance.dto.car.CarDto;
import com.synechron.insurance.dto.payment.PolicyDto;
import com.synechron.insurance.dto.proposal.FranchiseDto;
import com.synechron.insurance.dto.proposal.InsuranceDto;
import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.dto.users.DriverDto;
import com.synechron.insurance.dto.users.PersonDto;
import com.synechron.insurance.dto.users.ProposalDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.mappers.*;
import com.synechron.insurance.model.car.Car;
import com.synechron.insurance.model.payment.Policy;
import com.synechron.insurance.model.proposal.Franchise;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.model.proposal.InsurancePlan;
import com.synechron.insurance.model.proposal.Proposal;
import com.synechron.insurance.model.users.Driver;
import com.synechron.insurance.service.ProposalService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proposals")
public class ProposalController {
    private final ProposalService service;
    private final CarMapper carMapper;
    private final DriverMapper driverMapper;
    private final InsurancePlanMapper insurancePlanMapper;
    private final InsuranceItemMapper insuranceItemMapper;
    private final FranchiseMapper franchiseMapper;
    private final PolicyMapper policyMapper;
    private final ProposalMapper proposalMapper;

    @PostMapping
    public ResponseEntity<ProposalDto> create(@Valid @RequestBody PersonDto personDto) throws NotFoundException {
        Proposal proposal = service.create(personDto);
        return new ResponseEntity<>(proposalMapper.domainToDto(proposal), HttpStatus.CREATED);
    }
    @PatchMapping("/{id}/car")
    public ResponseEntity<ProposalDto> addCarToProposal(@PathVariable Long id, @Valid @RequestBody CarDto carDto) throws NotFoundException, ValidationException {
        Car car = carMapper.dtoToDomain(carDto);
        Proposal proposal = service.addCarToProposal(id, car);

        return new ResponseEntity<>(proposalMapper.domainToDto(proposal), HttpStatus.OK);
    }

    @PatchMapping("/{id}/drivers")
    public ResponseEntity<ProposalDto> addDriversToProposal(@PathVariable Long id, @RequestBody @Valid Set<DriverDto> driversDto) throws NotFoundException {
        Set<Driver> drivers = new HashSet<>();
        for (DriverDto dto: driversDto) {
            drivers.add(driverMapper.dtoToDomain(dto));
        }

        Proposal proposal = service.addDriversToProposal(id, drivers);

        return new ResponseEntity<>(proposalMapper.domainToDto(proposal), HttpStatus.OK);
    }

    @PatchMapping("/{id}/insurance")
    public ResponseEntity<ProposalDto> addInsuranceToProposal(@PathVariable Long id, @Valid @RequestBody InsuranceDto insuranceDto) throws NotFoundException, ValidationException {
        InsurancePlan insurancePlan = insurancePlanMapper.dtoToDomain(insuranceDto.getInsurancePlan());
        List<InsuranceItem> insuranceItems = new ArrayList<>();
        for(InsuranceItemDto dto: insuranceDto.getInsuranceItems()) {
            insuranceItems.add(insuranceItemMapper.dtoToDomain(dto));
        }
        List<Franchise> franchises = new ArrayList<>();
        for (FranchiseDto dto: insuranceDto.getFranchises()) {
            franchises.add(franchiseMapper.dtoToDomain(dto));
        }

        Proposal proposal = service.addInsuranceToProposal(id, insurancePlan, insuranceItems, franchises);

        return new ResponseEntity<>(proposalMapper.domainToDto(proposal), HttpStatus.OK);
    }

    @PatchMapping("/{id}/policy")
    public ResponseEntity<ProposalDto> addPolicyToProposal(@PathVariable Long id, @Valid @RequestBody PolicyDto policyDto) throws ValidationException, NotFoundException {
        Policy policy = policyMapper.dtoToDomain(policyDto);
        Proposal proposal = service.addPolicyToProposal(id, policy);

        return new ResponseEntity<>(proposalMapper.domainToDto(proposal), HttpStatus.OK);
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<ProposalDto> validateProposal(@PathVariable Long id) throws NotFoundException {
        Proposal proposal = service.validate(id);

        return new ResponseEntity<>(proposalMapper.domainToDto(proposal), HttpStatus.OK);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<Resource> generateReport(@PathVariable Long id) throws JRException, IOException, NotFoundException {
        String path = service.generateReport(id);
        File file = ResourceUtils.getFile(path);
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(file.toPath()));
        
        try {
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } finally {
            file.delete();
         }
    }

    @GetMapping
    public ResponseEntity<List<ProposalDto>> getAll(){
        List<ProposalDto> ProposalDtos= service.findAll().stream()
                .map(proposal -> {
                    try {
                        return proposalMapper.domainToDto(proposal);
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(ProposalDtos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProposalDto> get(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(proposalMapper.domainToDto(service.findById(id)), HttpStatus.OK);
    }
}
