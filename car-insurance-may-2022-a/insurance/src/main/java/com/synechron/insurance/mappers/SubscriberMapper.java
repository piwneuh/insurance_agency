package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.users.PersonDto;
import com.synechron.insurance.dto.users.SubscriberDto;
import com.synechron.insurance.dto.users.SubscriberRoleDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.Person;
import com.synechron.insurance.model.users.Subscriber;
import com.synechron.insurance.model.users.SubscriberRole;
import com.synechron.insurance.service.SubscriberRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private SubscriberRoleService subscriberRoleService;

    public Subscriber dtoToDomain(SubscriberDto dto) throws NotFoundException {
        Person person = personMapper.dtoToDomain(dto);
        Subscriber subscriber = new Subscriber(person);
        subscriber.setSubscriberRole(mapSubscriberRoleDtoToDomain(dto.getSubscriberRole()));

        return subscriber;
    }

    public SubscriberDto domainToDto(Subscriber subscriber) {
        PersonDto personDto = personMapper.domainToDto(subscriber);
        SubscriberDto subscriberDto = new SubscriberDto(personDto);
        subscriberDto.setSubscriberRole(mapSubscriberRoleDomainToDto(subscriber.getSubscriberRole()));

        return subscriberDto;
    }

    private SubscriberRole mapSubscriberRoleDtoToDomain(SubscriberRoleDto dto) throws NotFoundException {
        return subscriberRoleService.findById(dto.getId());
    }

    private SubscriberRoleDto mapSubscriberRoleDomainToDto(SubscriberRole subscriberRole) {
        return SubscriberRoleDto.builder()
                .id(subscriberRole.getId())
                .name(subscriberRole.getName())
                .build();
    }
}
