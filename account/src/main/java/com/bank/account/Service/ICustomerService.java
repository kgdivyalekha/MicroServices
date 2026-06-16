package com.bank.account.Service;

import com.bank.account.dto.CustomerDetailsDTO;

public interface ICustomerService {
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber,String correlationId);
}
