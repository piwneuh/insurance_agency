package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.service.InsuranceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsuranceItemMapper {
    @Autowired
    private InsuranceItemService service;

    public InsuranceItemDto domainToDto(InsuranceItem insuranceItem) {
        return InsuranceItemDto.builder()
                .id(insuranceItem.getId())
                .name(insuranceItem.getName())
                .isOptional(insuranceItem.getIsOptional())
                .amount(insuranceItem.getAmount())
                .build();
    }

    public InsuranceItem dtoToDomain(InsuranceItemDto dto) throws NotFoundException {
        return service.findById(dto.getId());
    }
}
