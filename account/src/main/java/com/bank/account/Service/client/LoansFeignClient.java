package com.bank.account.Service.client;

import com.bank.account.dto.CardsDTO;
import com.bank.account.dto.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")

public interface LoansFeignClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestParam String mobileNumber);
}
