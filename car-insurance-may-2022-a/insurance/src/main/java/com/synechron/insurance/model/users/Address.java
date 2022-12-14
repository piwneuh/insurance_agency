package com.synechron.insurance.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class Address implements Serializable {
    @Column
    public String street;

    @Column
    public String streetNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="city_id")
    public City city;
}
