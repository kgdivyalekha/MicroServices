package com.bank.loans.controller;

import com.bank.loans.constants.LoanConstants;
import com.bank.loans.dto.ErrorResponseDTO;
import com.bank.loans.dto.LoansContactInfoDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

@Validated
public class LoansController {
    private static final Logger logger= LoggerFactory.getLogger(LoansController .class);
    private ILoansService iLoansService;
    @Value("${build.version}")
    private String buildVersion;
    @Autowired
    private Environment environment;
    @Autowired
    private LoansContactInfoDTO loansContactInfoDTO;

    public LoansController(ILoansService iLoansService) {
        this.iLoansService = iLoansService;
    }

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
    public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestHeader("bank-correlation-id") String correlationId,@RequestParam
                                                     @Pattern(regexp="($|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        logger.debug("fetchLoanDetails method start");
        LoansDTO loansDTO=iLoansService.fetchLoan(mobileNumber);
        logger.debug("fetchLoanDetails method end");
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
    @Operation(summary="Get Build Info of Rest API",
            description = "REST API to fetch build information of the application")
    @ApiResponse(responseCode = "200",
            description = "HTTP Status OK")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo()
    {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(summary="Get Maven Version",
            description = "REST API to fetch maven Version")
    @ApiResponse(responseCode = "200",
            description = "HTTP Status OK")
    @GetMapping("/maven-version")
    public ResponseEntity<String> getMavenVersion()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("MAVEN_HOME"));
    }
    @Operation(summary="Get Contact Information",
            description = "REST API to fetch contact details")
    @ApiResponse(responseCode = "200",
            description = "HTTP Status OK")

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDTO> getContactInfo()
    {
        logger.debug("Invoked Loans ContactInfo API");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDTO);
    }
}
