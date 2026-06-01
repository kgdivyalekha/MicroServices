package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold account information"
)
@Data
public class AccountsDTO {
    @Schema(
            description = "Account Number of the Bank account",
            example="1234567890"
    )
    @NotEmpty(message = "Account Number must not be empty or blank")
    @Pattern(regexp="$|[0-9]{10}", message = "Account number must be 10 digits")
    private Long accountNumber;
    @Schema(
            description = "Type of the account",
            example = "Savings/Current"
    )
    @NotEmpty(message = "Account Type can't be empty")
    private String accountType;
    @Schema(
            description = "Address of the branch"
    )
    @NotEmpty(message = "Branch Address can't be empty")
    private String branchAddress;
}
