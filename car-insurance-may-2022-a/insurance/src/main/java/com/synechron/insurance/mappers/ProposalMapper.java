package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.users.ProposalDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.proposal.Proposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProposalMapper {
    @Autowired
    private SubscriberMapper subscriberMapper;
    @Autowired
    private InsurancePlanMapper insurancePlanMapper;
    @Autowired
    private FranchiseMapper franchiseMapper;
    @Autowired
    private DriverMapper driverMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private PolicyMapper policyMapper;

    public ProposalDto domainToDto(Proposal proposal) throws NotFoundException {
        ProposalDto.ProposalDtoBuilder builder = ProposalDto.builder()
                .id(proposal.getId())
                .isValid(proposal.getIsValid())
                .creationDate(proposal.getCreationDate());
        if (proposal.getSubscriber() != null) {
            builder.subscriber(subscriberMapper.domainToDto(proposal.getSubscriber()));
        }
        if (proposal.getCar() != null) {
            builder.car(carMapper.domainToDto(proposal.getCar()));
        }
        if (proposal.getDrivers() != null) {
            builder.drivers(proposal.getDrivers().stream()
                    .map(driverMapper::domainToDto)
                    .collect(Collectors.toSet()));
        }
        if (proposal.getInsurancePlan() != null) {
            builder.insurancePlan(insurancePlanMapper.domainToDto(proposal.getInsurancePlan()));
        }
        if (proposal.getInsuranceItems() != null) {
            builder.insuranceItems(insurancePlanMapper.mapInsuranceItemsDomainToDto(proposal.getInsuranceItems()));
        }
        if (proposal.getFranchises() != null) {
            builder.franchises(proposal.getFranchises().stream()
                    .map(franchiseMapper::domainToDto)
                    .collect(Collectors.toList()));
        }
        if (proposal.getPolicy() != null) {
            builder.policy(policyMapper.domainToDto(proposal.getPolicy()));
        }
        if (proposal.getAmount() != null) {
            builder.amount(proposal.getAmount());
        }

        return builder.build();
    }
}
