package org.techtest.starling.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.techtest.starling.client.AccountClient;
import org.techtest.starling.model.*;
import org.techtest.starling.service.AccountService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountClient accountClient;

    @Override
    public Accounts getAllAccounts() {
        return accountClient.getAllAccounts();
    }

    @Override
    public Account getAccount(UUID accountUid) {
        log.info("Getting account for accountUid: {}", accountUid);
        Accounts accounts = accountClient.getAllAccounts();
        return accounts.getAccounts().stream()
                .filter(account -> account.getAccountUid().equals(accountUid))
                .findFirst().orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public ConfirmationOfFundsResponse getConfirmationOfFundsRequest(UUID accountUid, BigDecimal targetAmountInMinorUnits) {
        log.info("Getting confirmation of funds for accountUid: {} and targetAmountInMinorUnits: {}", accountUid, targetAmountInMinorUnits);
        return accountClient.getConfirmationOfFunds(accountUid, targetAmountInMinorUnits);
    }

    @Override
    public AccountBalance getAccountBalance(UUID accountUid) {
        log.info("Getting account balance for accountUid: {}", accountUid);
        return accountClient.getAccountBalance(accountUid);
    }
}
