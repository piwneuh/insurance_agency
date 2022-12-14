package com.synechron.insurance.service;

import com.synechron.insurance.dto.crud.CreateOrUpdateInsuranceItemDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.repository.InsuranceItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InsuranceItemService {
    private final InsuranceItemRepository repository;

    public InsuranceItem findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Insurance item not found"));
    }

    public List<InsuranceItem> findAll() {
        return repository.findAllByIsDeleted(false);
    }

    public InsuranceItem create(CreateOrUpdateInsuranceItemDto dto) {
        InsuranceItem insuranceItem = InsuranceItem.builder()
                .name(dto.getName())
                .isOptional(dto.getIsOptional())
                .amount(dto.getAmount())
                .isDeleted(false)
                .build();

        return repository.save(insuranceItem);
    }

    public InsuranceItem update(Long id, CreateOrUpdateInsuranceItemDto dto) throws NotFoundException {
        InsuranceItem insuranceItem = findById(id);
        insuranceItem.setName(dto.getName());
        insuranceItem.setIsOptional(dto.getIsOptional());
        insuranceItem.setAmount(dto.getAmount());

        return repository.save(insuranceItem);
    }

    public boolean contains(InsuranceItem insuranceItem) {
        return repository.findById(insuranceItem.getId()).isPresent();
    }

    public Boolean delete(Long id) throws NotFoundException {
        InsuranceItem insuranceItem = findById(id);
        insuranceItem.setIsDeleted(true);
        repository.save(insuranceItem);
        return true;
    }
}
