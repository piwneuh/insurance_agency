package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.City;
import com.synechron.insurance.model.users.Country;
import com.synechron.insurance.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryRepository repository;
    private final CityService cityService;

    public List<Country> findAll() {
        return repository.findAllByIsDeleted(false);
    }

    public List<City> findAllCitiesByCountryId(Long id) throws NotFoundException {
        Country country = findById(id);

        return country.getCity();
    }

    public Country findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Country with id %d not found", id)));
    }

    public Country create(Country country) {
        return repository.save(country);
    }

    public Country update(Long id, Country country) throws NotFoundException {
        Country countryToUpdate = findById(id);

        countryToUpdate.setName(country.getName());
        countryToUpdate.setCity(country.getCity());

        return repository.save(countryToUpdate);
    }

    public City addCity(Long id, City city) throws NotFoundException {
        Country country = findById(id);
        city.setCountry(country);

        return cityService.save(city);
    }

    public City updateCity(Long id, City city, Long cityId) throws NotFoundException {
        Country country = findById(id);

        City cityToUpdate = country.getCity().stream()
                .filter(c -> c.getId().equals(cityId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("City with id %d not found", cityId)));

        cityToUpdate.setName(city.getName());
        cityToUpdate.setZipcode(city.getZipcode());

        repository.save(country);

        return cityToUpdate;

    }

    public Boolean delete(Long id) throws NotFoundException {
        Country country = findById(id);
        country.setIsDeleted(true);
        repository.save(country);
        return true;
    }

    public Boolean deleteCity(Long id, Long cityId) throws NotFoundException {
        Country country = findById(id);

        if (country.getCity().stream().noneMatch(c -> c.getId().equals(cityId))) {
            throw new NotFoundException(String.format("City with id %d not found", cityId));
        }
        return cityService.delete(cityId);
    }
}
