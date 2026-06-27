package com.bank.account.functions;

import com.bank.account.Service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountFunctions {
    private static final Logger log= LoggerFactory.getLogger(AccountFunctions.class);
    @Bean
    public Consumer<Long> updateCommunication(IAccountsService iAccountsService)
    {
        return accountNumber-> {
          log.info("Updating Communication Status for the account number: "+ accountNumber.toString());
          iAccountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
