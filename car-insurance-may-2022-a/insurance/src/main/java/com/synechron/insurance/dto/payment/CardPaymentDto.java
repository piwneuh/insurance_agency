package com.synechron.insurance.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardPaymentDto {
    private CardTypeDto cardType;
    private String cardHolder;
}
