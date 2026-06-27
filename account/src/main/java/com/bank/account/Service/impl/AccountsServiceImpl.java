package com.bank.account.Service.impl;

import com.bank.account.Service.IAccountsService;
import com.bank.account.constants.AccountConstants;
import com.bank.account.dto.AccountMsgDTO;
import com.bank.account.dto.AccountsDTO;
import com.bank.account.dto.CustomerDTO;
import com.bank.account.entity.Accounts;
import com.bank.account.entity.Customer;
import com.bank.account.exception.CustomerAlreadyExistsException;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.mapper.AccountsMapper;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repository.AccountsRepository;
import com.bank.account.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private static final Logger logger= LoggerFactory.getLogger(AccountsServiceImpl.class);
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;
    /**
     * @param customerDTO
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) throws CustomerAlreadyExistsException {
        Customer customer= CustomerMapper.mapToCustomer(new Customer(),customerDTO);
        Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if(optionalCustomer.isPresent())
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: "+ customerDTO.getMobileNumber());
        Customer savedCustomer=customerRepository.save(customer);
        Accounts savedAccount=accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);
    }
    private void sendCommunication(Accounts accounts, Customer customer)
    {
        var accountMsgDTO=new AccountMsgDTO(accounts.getAccountNumber(),customer.getName(),customer.getEmail(),customer.getMobileNumber());
        logger.info("Sending Communication request for details : {}", accountMsgDTO);
        var result=streamBridge.send("sendCommunication-out-0",accountMsgDTO);
        logger.info("Is the communication successfully processed? : {}",result);
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDTO fetchAccountDetails(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );
        Accounts accounts =accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","Customer ID",customer.getCustomerId().toString()));
        CustomerDTO customerDTO=CustomerMapper.mapToCustomerDTO(customer,new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts,new AccountsDTO()));
        return customerDTO;
    }

    /**
     * @param customerDTO
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated=false;
        AccountsDTO accountsDTO=customerDTO.getAccountsDTO();
        if(accountsDTO!=null)
        {
            Accounts accounts=accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","Account Number",accountsDTO.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accounts,accountsDTO);
            accounts=accountsRepository.save(accounts);
            Long customerID= accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerID).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer","Customer ID",customerID.toString())
            );
            CustomerMapper.mapToCustomer(customer,customerDTO);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated=false;
        if(accountNumber!=null)
        {
            Accounts accounts=accountsRepository.findById(accountNumber)
                    .orElseThrow(()-> new ResourceNotFoundException("Account", "Account Number",accountNumber.toString()));
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated=true;
        }
        return isUpdated;
    }

    private Accounts createNewAccount(Customer customer)
    {
        Accounts newAccount=new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNum=1000000000L+new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNum);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }
}
