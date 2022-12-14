package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.payment.CardType;
import com.synechron.insurance.model.payment.PaymentMode;
import com.synechron.insurance.repository.CardTypeRepository;
import com.synechron.insurance.repository.PaymentModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentModeService {
    private final PaymentModeRepository repository;
    private final CardTypeRepository cardTypeRepository;

    public List<PaymentMode> findAll() {
        return repository.findAll();
    }

    public PaymentMode findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment method not found"));
    }

    public List<CardType> findAllCardTypes() {
        return cardTypeRepository.findAll();
    }

    public CardType findCardTypeById(Long id) throws NotFoundException {
        return cardTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment card type not found"));
    }
}
