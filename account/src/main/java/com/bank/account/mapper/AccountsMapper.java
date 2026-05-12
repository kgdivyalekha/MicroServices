package com.bank.account.mapper;

import com.bank.account.dto.AccountsDTO;
import com.bank.account.entity.Accounts;

public class AccountsMapper {
    public static AccountsDTO mapToAccountsDTO(Accounts accounts, AccountsDTO accountsDTO)
    {
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }
    public static Accounts mapToAccounts(Accounts accounts, AccountsDTO accountsDTO)
    {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }
}
