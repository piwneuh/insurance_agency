package com.synechron.insurance.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synechron.insurance.dto.car.CarDto;
import com.synechron.insurance.dto.payment.PolicyDto;
import com.synechron.insurance.dto.proposal.FranchiseDto;
import com.synechron.insurance.dto.proposal.InsuranceItemDto;
import com.synechron.insurance.dto.proposal.InsurancePlanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProposalDto {
    private Long id;
    private Boolean isValid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    private SubscriberDto subscriber;
    private CarDto car;
    private Set<DriverDto> drivers;
    private InsurancePlanDto insurancePlan;
    private List<InsuranceItemDto> insuranceItems;
    private List<FranchiseDto> franchises;
    private PolicyDto policy;
    private Double amount;
}
