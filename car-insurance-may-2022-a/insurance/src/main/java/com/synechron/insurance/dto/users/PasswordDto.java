package com.synechron.insurance.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    @NotBlank(message = "Token not provided")
    private String tok;
    @NotBlank(message = "Password not provided")
    private String password;
}
