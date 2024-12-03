package ru.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentScheduleElement {
    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingDebt;


    public PaymentScheduleElement number(Integer number) {
        this.number = number;
        return this;
    }
        public PaymentScheduleElement date(LocalDate date){
            this.date = date;
            return this;

        }

    public PaymentScheduleElement totalPayment(BigDecimal totalPayment){
        this.totalPayment = totalPayment;
        return this;

    }
    public PaymentScheduleElement interestPayment(BigDecimal interestPayment){
        this.interestPayment = interestPayment;
        return this;

    }

    public PaymentScheduleElement debtPayment(BigDecimal debtPayment){
        this.debtPayment = debtPayment;
        return this;

    }

    public PaymentScheduleElement remainingDebt(BigDecimal remainingDebt){
        this.remainingDebt = remainingDebt;
        return this;

    }
}
