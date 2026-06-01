package com.bank.loans.service.impl;

import com.bank.loans.constants.LoanConstants;
import com.bank.loans.dto.LoansDTO;
import com.bank.loans.entity.Loans;
import com.bank.loans.exception.LoanAlreadyExistsException;
import com.bank.loans.exception.ResourceNotFoundException;
import com.bank.loans.mapper.LoansMapper;
import com.bank.loans.repository.LoansRepository;
import com.bank.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {
    private LoansRepository loansRepository;
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent())
            throw new LoanAlreadyExistsException("Loan already registered with this mobile number "+mobileNumber);
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan=new Loans();
        long randomLoanNumber=100000000000L+new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDTO fetchLoan(String mobileNumber) {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber));
        return LoansMapper.mapToLoansDTO(loans,new LoansDTO());
    }

    @Override
    public boolean updateLoan(LoansDTO loansDTO) {
        Loans loans=loansRepository.findByLoanNumber(loansDTO.getLoanNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","Loan Number", loansDTO.getLoanNumber()));
                LoansMapper.mapToLoans(loansDTO,loans);
                loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber));
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
