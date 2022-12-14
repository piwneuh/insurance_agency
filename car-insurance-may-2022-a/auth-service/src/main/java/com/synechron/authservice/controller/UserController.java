package com.synechron.authservice.controller;

import com.synechron.authservice.dto.UserDto;
import com.synechron.authservice.exception.NotFoundException;
import com.synechron.authservice.exception.NotUniqueException;
import com.synechron.authservice.mapper.UserMapper;
import com.synechron.authservice.model.User;
import com.synechron.authservice.model.UserRole;
import com.synechron.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(service.findAll().stream().map(mapper::domainToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<UserRole>> findAllRoles() {
        return new ResponseEntity<>(service.findAllRoles(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDto> register(@RequestBody UserDto dto) throws NotUniqueException {
        User user = service.register(mapper.dtoToDomain(dto));
        return new ResponseEntity<>(mapper.domainToDto(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) throws NotUniqueException, NotFoundException {
        User user = service.update(id, mapper.dtoToDomain(dto));
        return new ResponseEntity<>(mapper.domainToDto(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
