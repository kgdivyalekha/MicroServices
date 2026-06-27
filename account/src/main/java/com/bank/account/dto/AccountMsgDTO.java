package com.bank.account.dto;

public record AccountMsgDTO(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber) {
}
