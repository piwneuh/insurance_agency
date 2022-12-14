package com.synechron.insurance.model.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PaymentMode paymentMode;

    @OneToOne(cascade = {CascadeType.ALL})
    private ChequePayment chequePayment;

    @OneToOne(cascade = {CascadeType.ALL})
    private CardPayment cardPayment;

    @OneToOne(cascade = {CascadeType.ALL})
    private  BankPayment bankPayment;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted=false;

    public Object getPayment() {
        switch(paymentMode.getName()) {
            case "cheque":
                return chequePayment;
            case "card":
                return cardPayment;
            case "bank":
                return bankPayment;
            default:
                return null;
        }
    }
}
