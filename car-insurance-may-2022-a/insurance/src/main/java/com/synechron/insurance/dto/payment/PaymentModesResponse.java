package com.synechron.insurance.dto.payment;

import com.synechron.insurance.model.payment.CardType;
import com.synechron.insurance.model.payment.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentModesResponse {
    private List<PaymentMode> paymentModes;
    private List<CardType> cardTypes;
}
