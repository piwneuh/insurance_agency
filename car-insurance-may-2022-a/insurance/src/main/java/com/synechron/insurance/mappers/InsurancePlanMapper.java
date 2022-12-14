package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.dto.proposal.InsurancePlanDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.model.proposal.InsurancePlan;
import com.synechron.insurance.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InsurancePlanMapper {
    @Autowired
    private InsurancePlanService service;
    @Autowired
    private InsuranceItemMapper insuranceItemMapper;

    public InsurancePlanDto domainToDto(InsurancePlan insurancePlan) {
        return InsurancePlanDto.builder()
                .id(insurancePlan.getId())
                .name(insurancePlan.getName())
                .isPremium(insurancePlan.getIsPremium())
                .insuranceItems(mapInsuranceItemsDomainToDto(insurancePlan.getInsuranceItems()))
                .build();

    }

    public InsurancePlan dtoToDomain(InsurancePlanDto dto) throws NotFoundException {
        return service.findById(dto.getId());
    }

    public List<InsuranceItemDto> mapInsuranceItemsDomainToDto(List<InsuranceItem> insuranceItems) {
        return insuranceItems.stream()
                .map(insuranceItem -> insuranceItemMapper.domainToDto(insuranceItem))
                .collect(Collectors.toList());
    }
}
