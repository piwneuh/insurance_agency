package com.synechron.insurance.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SubscriberDto extends PersonDto {
    private SubscriberRoleDto subscriberRole;

    public SubscriberDto(PersonDto personDto) {
        super(personDto);
    }
}
