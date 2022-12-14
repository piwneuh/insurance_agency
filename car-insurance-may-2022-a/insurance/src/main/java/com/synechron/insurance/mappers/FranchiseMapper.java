package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.proposal.FranchiseDto;
import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.model.proposal.Franchise;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.service.InsuranceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FranchiseMapper {
    @Autowired
    private InsuranceItemService insuranceItemService;

    public Franchise dtoToDomain(FranchiseDto dto) throws NotFoundException, ValidationException {
        InsuranceItem insuranceItem = insuranceItemService.findById(dto.getInsuranceItem().getId());
        if (!insuranceItem.getIsOptional()) {
            throw new ValidationException("Franchise insurance item must be optional");
        }

        return Franchise.builder()
                .insuranceItem(insuranceItem)
                .percentage(dto.getPercentage())
                .build();
    }

    public FranchiseDto domainToDto(Franchise franchise) {
        return FranchiseDto.builder()
                .insuranceItem(
                        InsuranceItemDto.builder()
                                .id(franchise.getInsuranceItem().getId())
                                .name(franchise.getInsuranceItem().getName())
                                .isOptional(franchise.getInsuranceItem().getIsOptional())
                                .amount(franchise.getInsuranceItem().getAmount())
                                .build())
                .percentage(franchise.getPercentage())
                .build();
    }

}
