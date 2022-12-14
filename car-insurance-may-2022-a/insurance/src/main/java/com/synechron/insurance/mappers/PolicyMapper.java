package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.payment.PaymentDto;
import com.synechron.insurance.dto.payment.PolicyDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.model.payment.Payment;
import com.synechron.insurance.model.payment.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PolicyMapper {
    @Autowired
    private PaymentMapper paymentMapper;

    public Policy dtoToDomain(PolicyDto dto) throws ValidationException, NotFoundException {
        return Policy.builder()
                .dateSigned(LocalDateTime.now())
                .moneyReceivedDate(LocalDateTime.now())
                .payment(mapPaymentDtoToDomain(dto.getPayment()))
                .build();
    }

    public PolicyDto domainToDto(Policy policy) {
        return PolicyDto.builder()
                .payment(paymentMapper.domainToDto(policy.getPayment()))
                .build();
    }

    private Payment mapPaymentDtoToDomain(PaymentDto dto) throws ValidationException, NotFoundException {
        return paymentMapper.dtoToDomain(dto);
    }
}
