package com.bank.loans.controller;

import com.bank.loans.constants.LoanConstants;
import com.bank.loans.dto.ErrorResponseDTO;
import com.bank.loans.dto.LoansDTO;
import com.bank.loans.dto.ResponseDTO;
import com.bank.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="CRUD REST APIs for Loans in Bank",
        description = "CRUD API to do loan operations"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {
    private ILoansService iLoansService;
    @Operation(
            summary = "Create Loan Account in the Bank using Mobile Number",
            description = "REST API to create loan account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema=@Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(@RequestParam
                                                  @Pattern(regexp="($|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(LoanConstants.STATUS_201,LoanConstants.MESSAGE_201));
    }
    @Operation(
            summary = "REST API to fetch loan account details",
            description = "Fetch Loan details using Mobile Number"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            description = "HTTP Status Ok"),
            @ApiResponse(responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema=@Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestParam
                                                     @Pattern(regexp="($|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        LoansDTO loansDTO=iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDTO);
    }
    @Operation(
            summary = "Update Loan Account Details with Mobile Number",
            description = "REST API to update loan details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Expectation Failed",
                    content = @Content(schema=@Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoanDetails(@Valid @RequestBody LoansDTO loansDTO)
    {
        boolean isUpdated=iLoansService.updateLoan(loansDTO);
        if(isUpdated)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(LoanConstants.STATUS_200,LoanConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Delete Loan Account Details with Mobile Number",
            description = "REST API to delete loan details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Expectation Failed",
                    content = @Content(schema=@Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoanDetails(@RequestParam @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber)
    {
        boolean isDeleted=iLoansService.deleteLoan(mobileNumber);
        if(isDeleted)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(LoanConstants.STATUS_200,LoanConstants.MESSAGE_200));
        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_DELETE));
    }
}
