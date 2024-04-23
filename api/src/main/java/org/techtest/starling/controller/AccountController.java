package org.techtest.starling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techtest.starling.controller.api.AccountApi;
import org.techtest.starling.model.AccountBalance;
import org.techtest.starling.model.Accounts;
import org.techtest.starling.service.AccountService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Accounts> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{accountUid}/balance")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable UUID accountUid) {
        return new ResponseEntity<>(accountService.getAccountBalance(accountUid), HttpStatus.OK);
    }
}
