package com.synechron.insurance.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankPaymentDto {
    private String transactionNo;
    private String bankName;
}
