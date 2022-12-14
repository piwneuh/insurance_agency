package com.synechron.insurance.dto.crud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateModelDto {
    @NotBlank(message = "Model name not provided")
    private String name;
    @NotEmpty(message = "Model years not provided")
    private List<Integer> years;
}
