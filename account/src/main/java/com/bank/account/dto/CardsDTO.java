package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Cards",
        description = "Schema to hold card information"
)
public class CardsDTO {
    @NotEmpty(message = "Mobile number can;t be empty")
    @Pattern(regexp="($|[0-9]{10})",message = "Mobile number must be 10 digits")
    @Schema(
            description="Mobile number of the customer", example="9999999999"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card Number can't be empty")
    @Pattern(regexp="($|[0-9]{12})", message = "Card number must be 12 digits")
    @Schema(
            description = "Card Number of the customer",
            example = "123412341234"
    )
    private String cardNumber;
    @Schema(
            description = "Card type",
            example = "Credit Card"
    )
    private String cardType;
    @Schema(
            description = "Card total limit",
            example = "100000"
    )
    private int totalLimit;
    @Schema(
            description = "Amount spent so far",
            example = "500"
    )
    private int amountUsed;
    @Schema(
            description = "Amount available for expenditure",
            example = "99500"
    )
    private int availableAmount;
}
