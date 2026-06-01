package com.bank.account.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)

public class CustomerAlreadyExistsException extends RuntimeException{

    public CustomerAlreadyExistsException(String message)
    {
        super(message);

    }
}
