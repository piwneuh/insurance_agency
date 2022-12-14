package com.synechron.insurance.mappers;

import com.synechron.insurance.dto.car.AccidentHistoryDto;
import com.synechron.insurance.dto.users.DriverDto;
import com.synechron.insurance.dto.users.PersonDto;
import com.synechron.insurance.dto.car.RiskDto;
import com.synechron.insurance.exceptions.NotFoundException;
import com.synechron.insurance.model.car.AccidentHistory;
import com.synechron.insurance.model.car.Risk;
import com.synechron.insurance.model.users.Driver;
import com.synechron.insurance.model.users.Person;
import com.synechron.insurance.service.DriverService;
import com.synechron.insurance.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class DriverMapper {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private DriverService driverService;
    @Autowired
    private RiskService riskService;

    public Driver dtoToDomain(DriverDto dto) throws NotFoundException {
        Driver driver;
        try {
            driver = driverService.findByJmbg(dto.getJmbg());
            updateDriver(driver, dto);
            return driver;
        } catch (NotFoundException e) {
            Person person = personMapper.dtoToDomain(dto);
            driver = new Driver(person);
        }

        driver.setLicenceNum(dto.getLicenceNum());
        driver.setRisks(mapRisksDtoToDomain(dto.getRisks()));
        driver.setAccidentHistories(mapAccidentHistoriesDtoToDomain(dto.getAccidentHistories()));
        driver.setLicenceObtained(dto.getLicenceObtained());
        driver.setYearsInsured(dto.getYearsInsured());

        return driver;
    }

    public DriverDto domainToDto(Driver driver) {
        PersonDto personDto = personMapper.domainToDto(driver);
        DriverDto dto = new DriverDto(personDto);
        dto.setLicenceNum(driver.getLicenceNum());
        dto.setLicenceObtained(driver.getLicenceObtained());
        dto.setYearsInsured(driver.getYearsInsured());
        dto.setRisks(mapRisksDomainToDto(driver.getRisks()));
        dto.setAccidentHistories(mapAccidentHistoriesDomainToDto(driver.getAccidentHistories()));

        return dto;
    }

    public void updateDriver(Driver driver, DriverDto dto) throws NotFoundException {
        driver.setRisks(mapRisksDtoToDomain(dto.getRisks()));
        driver.setAccidentHistories(mapAccidentHistoriesDtoToDomain(dto.getAccidentHistories()));
    }

    public Set<Risk> mapRisksDtoToDomain(Collection<RiskDto> risksDto) throws NotFoundException {
        Set<Risk> risks = new HashSet<>();
        for (RiskDto riskDto: risksDto) {
            risks.add(riskService.findById(riskDto.getId()));
        }

        return risks;
    }

    public Set<RiskDto> mapRisksDomainToDto(Collection<Risk> risks) {
        Set<RiskDto> risksDto = new HashSet<>();
        for (Risk risk: risks) {
            risksDto.add(
                    RiskDto.builder()
                            .id(risk.getId())
                            .description(risk.getDescription())
                            .build()
            );
        }

        return risksDto;
    }
    private Set<AccidentHistory> mapAccidentHistoriesDtoToDomain(Collection<AccidentHistoryDto> accidentHistoriesDto) {
        Set<AccidentHistory> accidentHistories = new HashSet<>();
        for (AccidentHistoryDto accidentHistoryDto: accidentHistoriesDto) {
            AccidentHistory accidentHistory =
                    AccidentHistory.builder()
                            .happened(accidentHistoryDto.getHappened())
                            .wasResponsible(accidentHistoryDto.getWasResponsible())
                            .description(accidentHistoryDto.getDescription())
                            .isDeleted(false)
                            .build();
            if (accidentHistoryDto.getId() != null)
                accidentHistory.setId(accidentHistoryDto.getId());
            accidentHistories.add(accidentHistory);
        }

        return accidentHistories;
    }

    private Set<AccidentHistoryDto> mapAccidentHistoriesDomainToDto(Collection<AccidentHistory> accidentHistories) {
        Set<AccidentHistoryDto> accidentHistoriesDto = new HashSet<>();
        for (AccidentHistory accidentHistory: accidentHistories) {
            accidentHistoriesDto.add(
                    AccidentHistoryDto.builder()
                            .id(accidentHistory.getId())
                            .happened(accidentHistory.getHappened())
                            .wasResponsible(accidentHistory.getWasResponsible())
                            .description(accidentHistory.getDescription())
                            .build()
            );
        }

        return accidentHistoriesDto;
    }
}
