package com.synechron.insurance.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscriber extends Person {
    @OneToOne
    private SubscriberRole subscriberRole;

    public Subscriber(Person person){
        super(person);
        this.id = person.getId();
    }
}
