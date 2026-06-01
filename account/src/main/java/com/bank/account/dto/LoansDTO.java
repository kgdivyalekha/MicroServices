package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name="Loans",description = "Schema to hold loan information")
public class LoansDTO {
    @Schema(description = "Mobile number of the customer", example = "9999999999")
    @NotEmpty(message = "Mobile Number can't be empty")
    @Pattern(regexp="($|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Loan account number of the customer", example = "123412341234")
    @NotEmpty(message = "Loan Number can't be empty")
    @Pattern(regexp="($|[0-9]{12})", message = "Loan number must be 12 digits")
    private String loanNumber;

    @Schema(description = "Loan account type", example = "Home Loan")
    @NotEmpty(message = "Loan type can't be empty")
    private String loanType;

    @Schema(description = "Total loan amount", example = "100000")
    @Positive(message = "Total Loan amount must be positive")
    private int totalLoan;

    @Schema(description = "Total amount paid", example = "50000")
    @PositiveOrZero(message = "Amount paid must be either zero or positive")
    private int amountPaid;

    @Schema(description = "Total amount outstanding", example = "50000")
    @PositiveOrZero(message = "Outstanding paid must be either zero or positive")
    private int outstandingAmount;

}
