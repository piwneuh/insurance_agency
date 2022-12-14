package com.synechron.insurance.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechron.insurance.dto.payment.BankPaymentDto;
import com.synechron.insurance.dto.payment.CardPaymentDto;
import com.synechron.insurance.dto.payment.ChequePaymentDto;
import com.synechron.insurance.dto.payment.PaymentDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.model.payment.*;
import com.synechron.insurance.service.PaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentMapper {
    @Autowired
    private PaymentModeService paymentModeService;
    @Autowired
    private ObjectMapper objectMapper;

    public Payment dtoToDomain(PaymentDto dto) throws NotFoundException, ValidationException {
        Payment payment = new Payment();
        PaymentMode paymentMode = paymentModeService.findById(dto.getPaymentMode().getId());
        switch (paymentMode.getName()) {
            case "cheque":
                ChequePaymentDto chequePaymentDto = objectMapper.convertValue(dto.getPayment(), ChequePaymentDto.class);
                payment.setChequePayment(mapChequePaymentDtoToDomain(chequePaymentDto));
                break;
            case "card":
                CardPaymentDto cardPaymentDto = objectMapper.convertValue(dto.getPayment(), CardPaymentDto.class);
                payment.setCardPayment(mapCardPaymentDtoToDomain(cardPaymentDto));
                break;
            case "bank":
                BankPaymentDto bankPaymentDto = objectMapper.convertValue(dto.getPayment(), BankPaymentDto.class);
                payment.setBankPayment(mapBankPaymentDtoToDomain(bankPaymentDto));
                break;
            default:
                throw new ValidationException("Unsupported payment mode");
            }

            payment.setPaymentMode(paymentMode);
            return payment;
    }

    public PaymentDto domainToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .payment(objectMapper.convertValue(payment.getPayment(), Map.class))
                .paymentMode(payment.getPaymentMode())
                .build();
    }

    private ChequePayment mapChequePaymentDtoToDomain(ChequePaymentDto dto) {
        return ChequePayment.builder()
                .chequeNo(dto.getChequeNo())
                .chequeDate(dto.getChequeDate())
                .build();
    }

    private CardPayment mapCardPaymentDtoToDomain(CardPaymentDto dto) throws NotFoundException {
        CardType cardType = paymentModeService.findCardTypeById(dto.getCardType().getId());
        return CardPayment.builder()
                .cardHolder(dto.getCardHolder())
                .cardType(cardType)
                .build();
    }

    private BankPayment mapBankPaymentDtoToDomain(BankPaymentDto dto) {
        return BankPayment.builder()
                .bankName(dto.getBankName())
                .transactionNo(dto.getTransactionNo())
                .build();
    }
}
