package ru.Service;

import ru.DTO.CreditDTO;
import ru.DTO.ScoringDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.DTO.PaymentScheduleElement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Scoring {

    private static final String FUNDING_RATE = "15.00";

    private static final BigDecimal CURRENT_RATE = new BigDecimal(FUNDING_RATE);

    private static final String INSURANCE_DISCOUNT = "4.00";

    private static final String SALARY_CLIENT_DISCOUNT = "1.00";

    private static final String INSURANCE_BASE_PRICE = "10000.00";

    private static final String BASE_LOAN_AMOUNT = "200000.00";

    private static final String INSURANCE_LOAN_AMOUNT_MULTIPLICAND = "0.05";

    private static final Integer BASE_PERIODS_AMOUNT_IN_YEAR = 12;

    private static final Integer DEFAULT_DECIMAL_SCALE = 2;

    public BigDecimal rate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {

        BigDecimal rate = new BigDecimal(CURRENT_RATE.toString());

        if (isInsuranceEnabled) {
            rate = rate.subtract(new BigDecimal(INSURANCE_DISCOUNT));
        }
        if (isSalaryClient) {
            rate = rate.subtract(new BigDecimal(SALARY_CLIENT_DISCOUNT));
        }

        return rate;
    }

    public BigDecimal totalAmount(BigDecimal amount, Boolean isInsuranceEnabled) {
        if (isInsuranceEnabled) {
            BigDecimal insurancePrice = new BigDecimal(INSURANCE_BASE_PRICE);
            if (amount.compareTo(new BigDecimal(BASE_LOAN_AMOUNT)) > 0) {
                insurancePrice = insurancePrice
                        .add(amount
                                .multiply(new BigDecimal(INSURANCE_LOAN_AMOUNT_MULTIPLICAND)));
            }
            return amount.add(insurancePrice);
        } else {
            return amount;
        }
    }

    public BigDecimal monthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal rate) {

        BigDecimal monthlyRateAbsolute = rate.divide(BigDecimal.valueOf(100), 3, RoundingMode.CEILING);

        BigDecimal monthlyRate = monthlyRateAbsolute.divide(new BigDecimal(BASE_PERIODS_AMOUNT_IN_YEAR), 6, RoundingMode.CEILING);

        BigDecimal intermediateCoefficient = (BigDecimal.ONE.add(monthlyRate)).pow(term)
                .setScale(5, RoundingMode.CEILING);

        BigDecimal annuityCoefficient = monthlyRate.multiply(intermediateCoefficient)
                .divide(intermediateCoefficient.subtract(BigDecimal.ONE), RoundingMode.CEILING);

        BigDecimal monthlyPayment = totalAmount.multiply(annuityCoefficient).setScale(2, RoundingMode.CEILING);

        return monthlyPayment;
    }

    public BigDecimal PSK(List<PaymentScheduleElement> paymentSchedule, BigDecimal requestedAmount, Integer term) {

        BigDecimal paymentAmount = paymentSchedule
                .stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        BigDecimal termInYears = scaleAndRound(BigDecimal.valueOf(term),
                new BigDecimal(BASE_PERIODS_AMOUNT_IN_YEAR));

        BigDecimal intermediateCoefficient = scaleAndRound(paymentAmount, requestedAmount)
                .subtract(BigDecimal.ONE);

        BigDecimal psk = intermediateCoefficient.divide(termInYears, 3, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100));

        return psk;
    }

    public BigDecimal scaleAndRound(BigDecimal number, BigDecimal divisor) {
        return number.divide(divisor, DEFAULT_DECIMAL_SCALE, RoundingMode.CEILING);
    }

    public List<PaymentScheduleElement> calculatePaymentSchedule(BigDecimal totalAmount, Integer term,
                                                                 BigDecimal rate, BigDecimal monthlyPayment) {
        BigDecimal remainingDebt = totalAmount.setScale(2, RoundingMode.CEILING);
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        LocalDate paymentDate = LocalDate.now();

        paymentSchedule.add(new PaymentScheduleElement()
                .number(0)
                .date(paymentDate)
                .totalPayment(BigDecimal.ZERO)
                .remainingDebt(remainingDebt)
                .interestPayment(BigDecimal.ZERO)
                .debtPayment(BigDecimal.ZERO));

        for (int i = 1; i < term + 1; i++) {
            paymentDate = paymentDate.plusMonths(1);

            BigDecimal interestPayment = interest(remainingDebt, rate).setScale(2, RoundingMode.CEILING);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);

            remainingDebt = remainingDebt.subtract(debtPayment);

            paymentSchedule.add(new PaymentScheduleElement()
                    .number(i)
                    .date(paymentDate)
                    .totalPayment(monthlyPayment)
                    .remainingDebt(remainingDebt)
                    .interestPayment(interestPayment)
                    .debtPayment(debtPayment));
        }

        return paymentSchedule;
    }

    public BigDecimal interest(BigDecimal remainingDebt, BigDecimal rate) {
        BigDecimal monthlyRateAbsolute = rate.divide(BigDecimal.valueOf(100), RoundingMode.CEILING);

        BigDecimal monthlyRate = monthlyRateAbsolute.divide(new BigDecimal(BASE_PERIODS_AMOUNT_IN_YEAR), 10, RoundingMode.CEILING);

        return remainingDebt.multiply(monthlyRate);
    }

    public CreditDTO credit(ScoringDataDTO scoringData) {

        BigDecimal totalAmount = totalAmount(scoringData.getAmount(),
                scoringData.getIsInsuranceEnabled());

        BigDecimal requestedAmount = scoringData.getAmount();

        BigDecimal rate = rate(scoringData.getIsInsuranceEnabled(), scoringData.getIsSalaryClient());

        Integer term = scoringData.getTerm();

        BigDecimal monthlyPayment = monthlyPayment(totalAmount, term, rate);

        List<PaymentScheduleElement> paymentSchedule = calculatePaymentSchedule(totalAmount, scoringData.getTerm(), rate, monthlyPayment);

        return new CreditDTO()
                .amount(totalAmount)
                .monthlyPayment(monthlyPayment(totalAmount, term, rate))
                .psk(PSK(paymentSchedule, requestedAmount, term))
                .paymentSchedule(paymentSchedule)
                .term(term)
                .rate(rate)
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .isSalaryClient(scoringData.getIsSalaryClient());
    }

}
