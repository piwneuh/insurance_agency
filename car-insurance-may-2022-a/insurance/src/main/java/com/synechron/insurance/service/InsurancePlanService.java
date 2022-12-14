package com.synechron.insurance.service;

import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.dto.crud.CreateOrUpdateInsurancePlanDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.model.proposal.InsurancePlan;
import com.synechron.insurance.repository.InsurancePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InsurancePlanService {
    private final InsurancePlanRepository repository;
    private final InsuranceItemService insuranceItemService;

    public InsurancePlan findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Insurance plan not found"));
    }

    public List <InsurancePlan> findAll() {
        return repository.findAllByIsDeleted(false);
    }

    public InsurancePlan create(CreateOrUpdateInsurancePlanDto dto) throws NotFoundException {
        List<InsuranceItem> insuranceItems = new ArrayList<>();
        for (InsuranceItemDto insuranceItemDto: dto.getInsuranceItems()) {
            insuranceItems.add(insuranceItemService.findById(insuranceItemDto.getId()));
        }

        InsurancePlan insurancePlan = InsurancePlan.builder()
                .name(dto.getName())
                .isPremium(dto.getIsPremium())
                .insuranceItems(insuranceItems)
                .isDeleted(false)
                .build();

        return repository.save(insurancePlan);
    }

    public InsurancePlan update(Long id, CreateOrUpdateInsurancePlanDto dto) throws NotFoundException {
        InsurancePlan insurancePlan = findById(id);

        List<InsuranceItem> insuranceItems = new ArrayList<>();
        for (InsuranceItemDto insuranceItemDto: dto.getInsuranceItems()) {
            insuranceItems.add(insuranceItemService.findById(insuranceItemDto.getId()));
        }

        insurancePlan.setName(dto.getName());
        insurancePlan.setIsPremium(dto.getIsPremium());
        insurancePlan.setInsuranceItems(insuranceItems);

        return repository.save(insurancePlan);
    }

    public Boolean delete(Long id) throws NotFoundException {
        InsurancePlan insurancePlan = findById(id);
        insurancePlan.setIsDeleted(true);
        repository.save(insurancePlan);
        return true;
    }
}
