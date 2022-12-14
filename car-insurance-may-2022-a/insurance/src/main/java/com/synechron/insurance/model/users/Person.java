package com.synechron.insurance.model.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Enumerated(EnumType.ORDINAL)
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column
    private String jmbg;

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime birth;

    @Embedded
    private Contact contact;

    @Embedded
    public Address address;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted=false;

    public Person(Person person) {
        this.address = person.getAddress();
        this.id = person.getId();
        this.gender = person.getGender();
        this.lastName=person.getLastName();
        this.firstName = person.getFirstName();
        this.contact = person.getContact();
        this.birth = person.getBirth();
        this.maritalStatus = person.getMaritalStatus();
        this.jmbg = person.getJmbg();
        this.isDeleted = false;
    }
}
