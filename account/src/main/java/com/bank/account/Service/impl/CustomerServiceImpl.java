package com.bank.account.Service.impl;

import com.bank.account.Service.ICustomerService;
import com.bank.account.Service.client.CardsFeignClient;
import com.bank.account.Service.client.LoansFeignClient;
import com.bank.account.dto.AccountsDTO;
import com.bank.account.dto.CardsDTO;
import com.bank.account.dto.CustomerDetailsDTO;
import com.bank.account.dto.LoansDTO;
import com.bank.account.entity.Accounts;
import com.bank.account.entity.Customer;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.mapper.AccountsMapper;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repository.AccountsRepository;
import com.bank.account.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );
        Accounts accounts =accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","Customer ID",customer.getCustomerId().toString()));
        CustomerDetailsDTO customerDetailsDTO=CustomerMapper.mapToCustomerDetailsDTO(customer,new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts,new AccountsDTO()));
        ResponseEntity<LoansDTO> loansDTOResponseEntity=loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDTO.setLoansDTO(loansDTOResponseEntity.getBody());
        ResponseEntity<CardsDTO> cardsDTOResponseEntity=cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());
        return customerDetailsDTO;
    }
}
