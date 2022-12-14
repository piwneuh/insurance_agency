package com.synechron.insurance.controller;

import com.synechron.insurance.dto.payment.PaymentModesResponse;
import com.synechron.insurance.service.PaymentModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment-modes")
public class PaymentModeController {
    private final PaymentModeService service;

    @GetMapping
    public ResponseEntity<PaymentModesResponse> findAll() {
        PaymentModesResponse response = PaymentModesResponse.builder()
                .paymentModes(service.findAll())
                .cardTypes(service.findAllCardTypes())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
