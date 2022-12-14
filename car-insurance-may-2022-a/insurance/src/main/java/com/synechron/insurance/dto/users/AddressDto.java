package com.synechron.insurance.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    @NotBlank(message = "Street not provided")
    private String street;
    @NotBlank(message = "Street number not provided")
    private String streetNumber;
    @NotNull(message = "City not provided")
    private CityDto city;
}
