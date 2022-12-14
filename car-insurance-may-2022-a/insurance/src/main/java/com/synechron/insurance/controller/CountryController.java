package com.synechron.insurance.controller;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.City;
import com.synechron.insurance.model.users.Country;
import com.synechron.insurance.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService service;

    @GetMapping
    public ResponseEntity<Collection<Country>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}/cities")
    public ResponseEntity<Collection<City>> findAllCitiesByCountryId(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.findAllCitiesByCountryId(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Country> create(@RequestBody Country country) {
        return new ResponseEntity<>(service.create(country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody Country country) throws NotFoundException {
        return new ResponseEntity<>(service.update(id, country), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/cities")
    public ResponseEntity<City> addCity(@PathVariable Long id, @RequestBody City city) throws NotFoundException {
        return new ResponseEntity<>(service.addCity(id, city), HttpStatus.OK);
    }

    @PutMapping("/{id}/cities/{cityId}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city, @PathVariable Long cityId) throws NotFoundException {
        return new ResponseEntity<>(service.updateCity(id, city, cityId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/cities/{cityId}")
    public ResponseEntity<Boolean> deleteCity(@PathVariable Long id, @PathVariable Long cityId) throws NotFoundException {
        return new ResponseEntity<>(service.deleteCity(id, cityId), HttpStatus.OK);
    }
}

