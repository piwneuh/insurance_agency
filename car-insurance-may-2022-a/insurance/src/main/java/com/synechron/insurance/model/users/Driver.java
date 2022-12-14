package com.synechron.insurance.model.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synechron.insurance.model.car.AccidentHistory;
import com.synechron.insurance.model.car.Risk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver extends Person {
    @Column
    private String licenceNum;

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime licenceObtained;

    @Column
    private Integer yearsInsured;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "driver_risks",
            joinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "risks_id", referencedColumnName = "id"))
    private Set<Risk> risks = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<AccidentHistory> accidentHistories;

    public Driver(Person person) {
        super(person);
        this.id = person.getId();
    }
}
