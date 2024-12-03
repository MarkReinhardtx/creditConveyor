package ru.controller;

import ru.DTO.CreditDTO;
import ru.DTO.LoanApplicationRequestDTO;
import ru.DTO.LoanOfferDTO;
import ru.DTO.ScoringDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.Service.Offers;
import ru.Service.Scoring;

import java.util.List;
@RestController
@RequestMapping("/conveyor")
@RequiredArgsConstructor
public class CreditController {

    private final Offers offers;
    private final Scoring scoring;

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDTO>> offers(@RequestBody LoanApplicationRequestDTO request) {
        return ResponseEntity.ok(offers.generateOffers(request));
    }

    @PostMapping("/calculation")
    public ResponseEntity<CreditDTO> calculate(@RequestBody ScoringDataDTO scoringData) {
        return ResponseEntity.ok(scoring.credit(scoringData));
    }
}

