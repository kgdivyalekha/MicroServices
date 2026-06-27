package com.bank.account.Service;

import com.bank.account.dto.CustomerDTO;
import com.bank.account.exception.CustomerAlreadyExistsException;

public interface IAccountsService {
    void createAccount(CustomerDTO customerDTO);
    CustomerDTO fetchAccountDetails(String mobileNumber);
    boolean updateAccount(CustomerDTO customerDTO);
    boolean deleteAccount(String mobileNumber);
    boolean updateCommunicationStatus(Long accountNumber);
}
