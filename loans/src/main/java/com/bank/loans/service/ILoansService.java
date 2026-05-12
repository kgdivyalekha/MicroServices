package com.bank.loans.service;

import com.bank.loans.dto.LoansDTO;

public interface ILoansService {
    void createLoan(String mobileNumber);
    LoansDTO fetchLoan(String mobileNumber);
    boolean updateLoan(LoansDTO loansDTO);
    boolean deleteLoan(String mobileNumber);
}
