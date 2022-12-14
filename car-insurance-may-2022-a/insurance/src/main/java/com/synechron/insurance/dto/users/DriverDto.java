package com.synechron.insurance.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synechron.insurance.dto.car.AccidentHistoryDto;
import com.synechron.insurance.dto.car.RiskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DriverDto extends PersonDto {
    @NotBlank(message = "License number not provided")
    private String licenceNum;
    @NotNull(message = "Date license obtained not provided")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime licenceObtained;
    @NotNull(message = "Number of years insured not provided")
    private Integer yearsInsured;
    private Set<RiskDto> risks;
    private Set<AccidentHistoryDto> accidentHistories;

    public DriverDto(PersonDto personDto) {
        super(personDto);
    }
}
