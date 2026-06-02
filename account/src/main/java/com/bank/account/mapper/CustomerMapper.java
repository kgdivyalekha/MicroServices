package com.bank.account.mapper;

import com.bank.account.dto.CustomerDTO;
import com.bank.account.dto.CustomerDetailsDTO;
import com.bank.account.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapToCustomerDTO(Customer customer,CustomerDTO customerDTO)
    {
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        return customerDTO;
    }
    public static Customer mapToCustomer(Customer customer,CustomerDTO customerDTO)
    {
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        return customer;
    }
    public static CustomerDetailsDTO mapToCustomerDetailsDTO(Customer customer,CustomerDetailsDTO customerDetailsDTO)
    {
        customerDetailsDTO.setName(customer.getName());
        customerDetailsDTO.setEmail(customer.getEmail());
        customerDetailsDTO.setMobileNumber(customerDetailsDTO.getMobileNumber());
        return customerDetailsDTO;
    }
}
