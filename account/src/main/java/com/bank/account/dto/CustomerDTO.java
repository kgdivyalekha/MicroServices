package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Schema(
        name="Customer",
        description = "Schema to hold customer details"
)
@Data
public class CustomerDTO {
    @Schema(
            description = "Name of the customer",
            example = "Avery Bank"
    )
    @NotEmpty(message = "Name can't be blank or null")
    @Size(min=2, max=30,message = "The length of the name should be between 2 and 30 chars long")
    private String name;
    @Schema(
            description = "Email of the customer",
            example = "Avery@bank.com"
    )
    @NotEmpty(message = "Email can't be blank or null")
    @Email(message = "Invalid Email address")
    private String email;
    @Schema(
            description = "Mobile number of the customer",
            example = "9999999999"
    )
    @NotEmpty(message = "Mobile Number can't be blank or null")
    @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number be 10 digits only")
    private String mobileNumber;
    private AccountsDTO accountsDTO;
}
