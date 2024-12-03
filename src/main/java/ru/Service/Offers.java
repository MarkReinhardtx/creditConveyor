package ru.Service;


import ru.DTO.LoanApplicationRequestDTO;
import ru.DTO.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class Offers {

    private final Scoring scoring;

    public LoanOfferDTO createOffer(Boolean isInsuranceEnabled,
                                    Boolean isSalaryClient,
                                    LoanApplicationRequestDTO request) {
        BigDecimal totalAmount = scoring.totalAmount(request.getAmount(), isInsuranceEnabled);

        BigDecimal rate = scoring.rate(isInsuranceEnabled, isSalaryClient);


        return new LoanOfferDTO()
                .requestedAmount(request.getAmount())
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .rate(rate)
                .monthlyPayment(scoring.monthlyPayment(totalAmount, request.getTerm(), rate));
    }

    public List<LoanOfferDTO> generateOffers(LoanApplicationRequestDTO request) {
        return List.of(
                createOffer(false, false, request),
                createOffer(true, false, request),
                createOffer(false, true, request),
                createOffer(true, true, request),
                createOffer(false, true, request)
        );
    }
}

