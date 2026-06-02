package com.bank.account.controller;

import com.bank.account.Service.ICustomerService;
import com.bank.account.dto.CustomerDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})

@Tag(name="REST API for Customer details",
        description ="Manages fetch customer details - account, loans, cards")
public class CustomerController {
    private final ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @GetMapping("/fetchCustomerDetails")
    @Operation(summary="Fetch Account Rest API",
            description = "REST API to fetch customer details based on a mobile number")
    @ApiResponse(responseCode = "200",
            description = "HTTP Status OK")

    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(@RequestParam
                                                                   @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digits") String mobileNumber)
    {
        CustomerDetailsDTO customerDetailsDTO=iCustomerService.fetchCustomerDetails(mobileNumber);
                return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDTO);
    }
}
