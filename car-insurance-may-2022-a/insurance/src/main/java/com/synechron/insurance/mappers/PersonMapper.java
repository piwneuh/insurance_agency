package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.users.*;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.users.*;
import com.synechron.insurance.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    @Autowired
    private CityService cityService;

    public Person dtoToDomain(PersonDto dto) throws NotFoundException {
        return Person.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .maritalStatus(dto.getMaritalStatus())
                .gender(dto.getGender())
                .jmbg(dto.getJmbg())
                .birth(dto.getBirth())
                .contact(mapContactDtoToDomain(dto.getContact()))
                .address(mapAddressDtoToDomain(dto.getAddress()))
                .build();
    }

    public PersonDto domainToDto(Person person) {
        return PersonDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .maritalStatus(person.getMaritalStatus())
                .gender(person.getGender())
                .jmbg(person.getJmbg())
                .birth(person.getBirth())
                .contact(mapContactDomainToDto(person.getContact()))
                .address(mapAddressDomainToDto(person.getAddress()))
                .build();
    }

    private Contact mapContactDtoToDomain(ContactDto dto) {
        return Contact.builder()
                .homePhone(dto.getHomePhone())
                .mobilePhone(dto.getMobilePhone())
                .email(dto.getEmail())
                .build();
    }

    private Address mapAddressDtoToDomain(AddressDto dto) throws NotFoundException {
        City city = cityService.findById(dto.getCity().getId());
        return Address.builder()
                .street(dto.getStreet())
                .streetNumber(dto.getStreetNumber())
                .city(city)
                .build();
    }

    private ContactDto mapContactDomainToDto(Contact contact) {
        return ContactDto.builder()
                .homePhone(contact.getHomePhone())
                .mobilePhone(contact.getMobilePhone())
                .email(contact.getEmail())
                .build();
    }

    private AddressDto mapAddressDomainToDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .city(mapCityDomainToDto(address.getCity()))
                .build();
    }

    private CityDto mapCityDomainToDto(City city) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .zipCode(city.getZipcode())
                .country(mapCountryDomainToDto(city.getCountry()))
                .build();
    }

    private CountryDto mapCountryDomainToDto(Country country) {
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }
}
