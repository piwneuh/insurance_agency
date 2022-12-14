package com.synechron.insurance.model.proposal;

import com.synechron.insurance.model.car.AccidentHistory;
import com.synechron.insurance.model.car.Car;
import com.synechron.insurance.model.payment.Policy;
import com.synechron.insurance.model.users.Driver;
import com.synechron.insurance.model.users.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Proposal implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isValid;

    @Column
    private LocalDateTime creationDate;

    @Column
    private Double amount;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Subscriber subscriber;

    @ManyToOne(cascade = CascadeType.ALL)
    private Car car;

    @OneToOne(cascade = CascadeType.ALL)
    private Policy policy;

    @ManyToOne
    private InsurancePlan insurancePlan;

    @ManyToMany
    private List<InsuranceItem> insuranceItems;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Franchise> franchises;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Driver> drivers;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted=false;

    public Proposal(Subscriber s, LocalDateTime d){
        this.subscriber = s;
        this.creationDate = d;
        this.isValid = false;
        this.isDeleted = false;
    }

    public void calculateAmount() {
        double amount = 0;
        for (InsuranceItem item: insuranceItems) {
            amount += item.getAmount();
        }
        for (Franchise franchise: franchises) {
            amount += franchise.getInsuranceItem().getAmount().doubleValue()*((double)franchise.getPercentage()/100);
        }
        int riskCount = 0;
        for (Driver driver: drivers) {
            int responsibleAccidents = (int) driver.getAccidentHistories().stream()
                    .filter(AccidentHistory::getWasResponsible).count();
            amount *= Math.pow(1.4, responsibleAccidents);
            riskCount += driver.getRisks().size();
        }

        amount *= Math.pow(1.05, riskCount);
        this.amount = amount;
    }
}
