package com.synechron.insurance.dto.payment;

import com.synechron.insurance.model.payment.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    @NotNull(message = "Payment mode not provided")
    private PaymentMode paymentMode;
    @NotNull(message = "Payment info not provided")
    @Size(min=2, max=2, message = "Payment info not provided")
    private Map<String, Object> payment;
}
