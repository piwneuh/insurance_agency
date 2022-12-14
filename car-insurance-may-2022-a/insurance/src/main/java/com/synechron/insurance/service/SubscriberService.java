package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.Subscriber;
import com.synechron.insurance.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscriberService {
    private final SubscriberRepository repository;

    public Subscriber findByJmbg(String jmbg) throws NotFoundException {
        return repository.findByJmbg(jmbg)
                .orElseThrow(() -> new NotFoundException(String.format("Subscriber with %s jmbg not found", jmbg)));
    }
}
