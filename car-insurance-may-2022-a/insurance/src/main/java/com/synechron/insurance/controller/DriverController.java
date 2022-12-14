package com.synechron.insurance.controller;

import com.synechron.insurance.dto.users.DriverDto;
import com.synechron.insurance.dto.car.RiskDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.mappers.DriverMapper;
import com.synechron.insurance.model.car.Risk;
import com.synechron.insurance.model.users.Driver;
import com.synechron.insurance.service.DriverService;
import com.synechron.insurance.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService service;
    private final RiskService riskService;
    private final DriverMapper mapper;

    @GetMapping("/{jmbg}")
    public ResponseEntity<DriverDto> findByJmbg(@PathVariable String jmbg) throws NotFoundException {
        Driver driver = service.findByJmbg(jmbg);
        return new ResponseEntity<>(mapper.domainToDto(driver), HttpStatus.OK);
    }

    @GetMapping("/risks")
    public ResponseEntity<Collection<RiskDto>> findAllRisks() {
        List<Risk> risks = riskService.findAll();
        return new ResponseEntity<>(mapper.mapRisksDomainToDto(risks), HttpStatus.OK);
    }

    @PostMapping("/risks")
    public ResponseEntity<Collection<RiskDto>> createRisk(@RequestBody Risk risk){
        List<Risk> risks = riskService.create(risk);
        return new ResponseEntity<>(mapper.mapRisksDomainToDto(risks), HttpStatus.OK);
    }

    @DeleteMapping("/risks/{id}")
    public ResponseEntity<Boolean> deleteRisk(@PathVariable Long id) throws NotFoundException {
        Boolean deleted = riskService.delete(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
