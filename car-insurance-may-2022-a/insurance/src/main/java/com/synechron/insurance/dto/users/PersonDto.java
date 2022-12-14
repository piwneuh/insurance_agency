package com.synechron.insurance.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synechron.insurance.model.users.Gender;
import com.synechron.insurance.model.users.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonDto {
    @NotBlank(message = "First name not provided")
    protected String firstName;
    @NotBlank(message = "Last name not provided")
    protected String lastName;
    @NotNull(message = "Marital status not provided")
    protected MaritalStatus maritalStatus;
    @NotNull(message = "Gender not provided")
    protected Gender gender;
    @NotBlank(message = "JMBG not provided")
    protected String jmbg;
    @NotNull(message = "Date of birth not provided")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    protected LocalDateTime birth;
    @NotNull(message = "Contact info not provided")
    @Valid
    protected ContactDto contact;
    @NotNull(message = "Address not provided")
    @Valid
    protected AddressDto address;

    public PersonDto(PersonDto personDto) {
        this.firstName = personDto.firstName;
        this.lastName = personDto.lastName;
        this.maritalStatus = personDto.maritalStatus;
        this.gender = personDto.gender;
        this.jmbg = personDto.jmbg;
        this.birth = personDto.birth;
        this.contact = personDto.contact;
        this.address = personDto.address;
    }
}
