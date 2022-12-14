package com.synechron.insurance.service;

import com.synechron.insurance.dto.users.PersonDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.exceptions.ValidationException;
import com.synechron.insurance.mappers.DriverMapper;
import com.synechron.insurance.mappers.PersonMapper;
import com.synechron.insurance.model.car.Car;
import com.synechron.insurance.model.payment.Policy;
import com.synechron.insurance.model.proposal.Franchise;
import com.synechron.insurance.model.proposal.InsuranceItem;
import com.synechron.insurance.model.proposal.InsurancePlan;
import com.synechron.insurance.model.proposal.Proposal;
import com.synechron.insurance.model.users.Driver;
import com.synechron.insurance.model.users.Person;
import com.synechron.insurance.model.users.Subscriber;
import com.synechron.insurance.model.users.User;
import com.synechron.insurance.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProposalService {
    private final ProposalRepository repository;
    private final ModelService modelService;
    private final SubscriberService subscriberService;
    private final PersonMapper personMapper;
    private final SubscriberRoleService subscriberRoleService;
    private final JmsTemplate jmsTemplate;

    public Proposal create(PersonDto dto) throws NotFoundException {
        Subscriber subscriber;
        try {
            subscriber = subscriberService.findByJmbg(dto.getJmbg());
        } catch (NotFoundException e) {
            Person person = personMapper.dtoToDomain(dto);
            subscriber = new Subscriber(person);
            subscriber.setSubscriberRole(subscriberRoleService.findByName("prospect"));
        }

        Proposal proposal = new Proposal(subscriber, LocalDateTime.now());

        return repository.save(proposal);
    }

    public Proposal addCarToProposal(Long id, Car car) throws NotFoundException, ValidationException {
        Proposal proposal = findById(id);
        modelService.validateModelYear(car.getModel(), car.getYear());
        proposal.setCar(car);

        return repository.save(proposal);
    }

    public Proposal addDriversToProposal(Long id, Set<Driver> drivers) throws NotFoundException {
        Proposal proposal = findById(id);
        proposal.setDrivers(drivers);

        return repository.save(proposal);
    }


    public List<Proposal> findAll(){
        return repository.findAll();
    }

    public Proposal findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Proposal with id %d not found", id)));
    }

    public Proposal addInsuranceToProposal(Long id, InsurancePlan insurancePlan, List<InsuranceItem> insuranceItems, List<Franchise> franchises) throws NotFoundException, ValidationException {
        Proposal proposal = findById(id);
        proposal.setInsurancePlan(insurancePlan);

        for (InsuranceItem insuranceItem : insuranceItems) {
            if(!insurancePlan.getInsuranceItems().contains(insuranceItem)) {
                throw new ValidationException(String.format("Insurance plan %s does not include %s", insurancePlan.getName(), insuranceItem.getName()));
            }
            if(insuranceItem.getIsOptional()) {
                throw new ValidationException(String.format("%s is an optional insurance item and can only be added via franchise", insuranceItem.getName()));
            }
        }
        proposal.setInsuranceItems(insuranceItems);

        for (Franchise franchise : franchises) {
            if(!franchise.getInsuranceItem().getIsOptional()) {
                throw new ValidationException(String.format("%s insurance item cannot be added via franchise as it is not optional", franchise.getInsuranceItem().getName()));
            }
        }
        proposal.setFranchises(franchises);
        proposal.calculateAmount();

        return repository.save(proposal);
    }

    public Proposal addPolicyToProposal(Long id, Policy policy) throws NotFoundException {
        Proposal proposal = findById(id);
        policy.setAmount(proposal.getAmount());
        proposal.setPolicy(policy);

        return repository.save(proposal);
    }

    public Proposal validate(Long id) throws NotFoundException {
        Proposal proposal = findById(id);
        proposal.setIsValid(true);
        proposal.getSubscriber().setSubscriberRole(subscriberRoleService.findByName("client"));
        sendMessage(proposal);

        return repository.save(proposal);
    }

    @Transactional
    public String generateReport(Long id) throws NotFoundException, FileNotFoundException, JRException {
        Proposal proposal = findById(id);
        File file = ResourceUtils.getFile("classpath:Proposal.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(proposal));
        Map<String, Object> params = new HashMap<>();
        params.put("drivers", new JRBeanCollectionDataSource(proposal.getDrivers()));
        params.put("insurance_items", new JRBeanCollectionDataSource(proposal.getInsuranceItems()));
        params.put("franchises", new JRBeanCollectionDataSource(proposal.getFranchises()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        String path = Paths.get("").toAbsolutePath().toString() + "/temp/" + UUID.randomUUID().toString() + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, path);
        
        return path;
    }

    private void sendMessage(Proposal proposal){
        Map<String, Object> map = new HashMap<>();
        map.put("proposal", proposal.getId());
        map.put("subscriberEmail", proposal.getSubscriber().getContact().getEmail());
        jmsTemplate.convertAndSend("report-queue",  map);
    }
}
