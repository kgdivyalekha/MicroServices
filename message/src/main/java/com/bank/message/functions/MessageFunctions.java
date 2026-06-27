package com.bank.message.functions;

import com.bank.message.dto.AccountMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {
    private static final Logger log= LoggerFactory.getLogger(MessageFunctions.class);
    @Bean
    public Function<AccountMessageDTO,AccountMessageDTO> email()
    {
        return accountsMessageDTO->{
            log.info("Sending email with the details: "+accountsMessageDTO.toString());
            return accountsMessageDTO;
        };
    }
    @Bean
    public Function<AccountMessageDTO,Long> sms()
    {
        return accountsMessageDTO->{
            log.info("Sending sms with the details: "+accountsMessageDTO.toString());
            return accountsMessageDTO.accountNumber();
        };
    }
}
