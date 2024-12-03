package ru.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CreditDTO {
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private List<PaymentScheduleElement> paymentSchedule;

    public CreditDTO isSalaryClient(Boolean isSalaryClient){
        this.isSalaryClient = isSalaryClient;
        return this;

    }

    public CreditDTO isInsuranceEnabled(Boolean isInsuranceEnabled){
        this.isInsuranceEnabled = isInsuranceEnabled;
        return this;

    }

    public CreditDTO term(Integer term){
        this.term = term;
        return this;

    }
    public CreditDTO rate(BigDecimal rate){
        this.rate = rate;
        return this;

    }

    public CreditDTO paymentSchedule(List<PaymentScheduleElement> paymentSchedule){
        this.paymentSchedule = paymentSchedule;
        return this;

    }

    public CreditDTO amount(BigDecimal amount){
        this.amount = amount;
        return this;

    }
    public CreditDTO monthlyPayment(BigDecimal monthlyPayment){
        this.monthlyPayment = monthlyPayment;
        return this;

    }
    public CreditDTO psk(BigDecimal psk){
        this.psk = psk;
        return this;

    }

}
