package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.SubscriberRole;
import com.synechron.insurance.repository.SubscriberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscriberRoleService {
    private final SubscriberRoleRepository repository;

    public SubscriberRole findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Subscriber role with id %d not found", id)));
    }

    public SubscriberRole findByName(String name) throws NotFoundException {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Subscriber role with name %s not found", name)));
    }
}
