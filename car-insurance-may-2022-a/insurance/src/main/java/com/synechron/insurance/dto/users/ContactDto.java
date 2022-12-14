package com.synechron.insurance.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {
    @NotBlank(message = "Home phone number not provided")
    private String homePhone;
    @NotBlank(message = "Mobile phone number not provided")
    private String mobilePhone;
    @NotBlank(message = "Email not provided")
    private String email;
}
