package org.techtest.starling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.techtest.starling.model.Account;
import org.techtest.starling.model.AccountBalance;
import org.techtest.starling.model.Accounts;
import org.techtest.starling.model.CurrencyAndAmount;
import org.techtest.starling.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private UUID accountUid;

    @BeforeEach
    void setUp() {
        accountUid = UUID.randomUUID();
    }

    @DisplayName("Should return all accounts")
    @Test
    void shouldReturnAllAccounts() throws Exception {
        // Arrange
        Account account = new Account();
        account.setAccountUid(accountUid);
        account.setCurrency("GBP");
        account.setAccountType("PRIMARY");
        account.setDefaultCategory("INCOME");
        account.setCreatedAt("2021-01-01T00:00:00.000Z");
        account.setName("Test Account");

        Accounts accounts = Accounts.builder()
                .accounts(List.of(account))
                .build();

        when(accountService.getAllAccounts()).thenReturn(accounts);

        // Act
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/accounts")
                        .contentType("application/json")
                        .accept("application/json")
        );


        // Assert
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(accounts), resultActions.andReturn().getResponse().getContentAsString());

        verify(accountService).getAllAccounts();
    }

    @DisplayName("Should return account balance")
    @Test
    void shouldReturnAccountBalance() throws Exception {
        // Arrange
        AccountBalance accountBalance = new AccountBalance();
        CurrencyAndAmount clearedBalance = new CurrencyAndAmount();
        clearedBalance.setCurrency("GBP");
        clearedBalance.setMinorUnits(BigDecimal.valueOf(1000));
        accountBalance.setClearedBalance(clearedBalance);

        when(accountService.getAccountBalance(accountUid)).thenReturn(accountBalance);

        // Act
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/accounts/" + accountUid + "/balance")
                        .contentType("application/json")
                        .accept("application/json")
        );

        // Assert
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(accountBalance), resultActions.andReturn().getResponse().getContentAsString());

        verify(accountService).getAccountBalance(accountUid);
    }

}