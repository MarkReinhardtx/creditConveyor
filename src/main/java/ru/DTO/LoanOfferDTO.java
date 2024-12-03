package ru.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class LoanOfferDTO {
    public Long applicationId;
    public BigDecimal requestedAmount;
    public BigDecimal totalAmount;
    public Integer term;
    public BigDecimal monthlyPayment;
    public BigDecimal rate;
    public Boolean isInsuranceEnabled;
    public Boolean isSalaryClient;


    public LoanOfferDTO isSalaryClient(Boolean isSalaryClient){
        this.isSalaryClient = isSalaryClient;
        return this;

    }
    public LoanOfferDTO isInsuranceEnabled(Boolean isInsuranceEnabled){
        this.isInsuranceEnabled = isInsuranceEnabled;
        return this;

    }

    public LoanOfferDTO rate(BigDecimal rate){
        this.rate = rate;
        return this;

    }

    public LoanOfferDTO monthlyPayment(BigDecimal monthlyPayment){
        this.monthlyPayment = monthlyPayment;
        return this;

    }
    public LoanOfferDTO term(Integer term){
        this.term = term;
        return this;

    }
    public LoanOfferDTO applicationId(Long applicationId){
        this.applicationId = applicationId;
        return this;

    }
    public LoanOfferDTO requestedAmount(BigDecimal requestedAmount){
        this.requestedAmount = requestedAmount;
        return this;

    }

    public LoanOfferDTO totalAmount(BigDecimal totalAmount){
        this.totalAmount = totalAmount;
        return this;

    }

}
