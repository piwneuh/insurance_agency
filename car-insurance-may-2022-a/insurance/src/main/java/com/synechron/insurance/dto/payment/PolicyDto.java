package com.synechron.insurance.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyDto {
    @NotNull(message = "Payment info not provided")
    @Valid
    private PaymentDto payment;
}
