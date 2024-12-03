package ru.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class LoanApplicationRequestDTO {
    public BigDecimal amount;
    public Integer term;
    public String firstName;
    public String lastName;
    public String middleName;
    public String email;
    public LocalDate birthdate;
    public String passportSeries;
    public String passportNumber;

}
