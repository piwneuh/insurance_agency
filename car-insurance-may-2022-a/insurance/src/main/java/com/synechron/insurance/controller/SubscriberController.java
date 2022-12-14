package com.synechron.insurance.controller;

import com.synechron.insurance.dto.users.SubscriberDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.mappers.SubscriberMapper;
import com.synechron.insurance.model.users.Subscriber;
import com.synechron.insurance.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscribers")
public class SubscriberController {
    private final SubscriberService service;
    private final SubscriberMapper mapper;

    @GetMapping("/{jmbg}")
    public ResponseEntity<SubscriberDto> findByJmbg(@PathVariable String jmbg) throws NotFoundException {
        Subscriber subscriber = service.findByJmbg(jmbg);
        return new ResponseEntity<>(mapper.domainToDto(subscriber), HttpStatus.OK);
    }
}
