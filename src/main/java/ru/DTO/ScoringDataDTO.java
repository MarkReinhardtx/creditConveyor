package ru.DTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ScoringDataDTO {
    public BigDecimal amount;
    public Integer term;
    public String firstName;
    public String lastName;
    public String middleName;
    public Enum gender;
    public LocalDate birthdate;
    public String passportSeries;
    public String passportNumber;
    public LocalDate passportIssueDate;
    public String passportIssueBranch;
    public MaritalStatus maritalStatus;
    public Integer dependentAmount;
    public String account;
    public Boolean isInsuranceEnabled;
    public Boolean isSalaryClient;



    public enum MaritalStatus {
        MARRIED, WIDOWED, SEPARATED, DIVORCED, SINGLE
    }

    public ScoringDataDTO(BigDecimal amount,
                          Integer term,
                          String firstName,
                          String lastName,
                          String middleName,
                          Enum gender,
                          LocalDate birthdate,
                          String passportSeries,
                          String passportNumber,
                          LocalDate passportIssueDate,
                          String passportIssueBranch,
                          MaritalStatus maritalStatus,
                          Integer dependentAmount,
                          String account,
                          Boolean isInsuranceEnabled,
                          Boolean isSalaryClient) {
        this.amount = amount;
        this.term = term;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.passportIssueDate = passportIssueDate;
        this.passportIssueBranch = passportIssueBranch;
        this.maritalStatus = maritalStatus;
        this.dependentAmount = dependentAmount;
        this.account = account;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }
}
