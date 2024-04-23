package org.techtest.starling.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techtest.starling.client.AccountClient;
import org.techtest.starling.exception.AccountNotFoundException;
import org.techtest.starling.model.Account;
import org.techtest.starling.model.AccountBalance;
import org.techtest.starling.model.Accounts;
import org.techtest.starling.model.CurrencyAndAmount;
import org.techtest.starling.service.impl.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Test get all accounts")
    public void testGetAllAccounts() {
        // Arrange
        Account testAccount = new Account();
        UUID accountUid = UUID.randomUUID();
        testAccount.setAccountUid(accountUid);
        testAccount.setAccountType("PRIMARY");
        testAccount.setCurrency("GBP");

        Accounts testAccounts = new Accounts();
        testAccounts.setAccounts(List.of(testAccount));

        when(accountClient.getAllAccounts()).thenReturn(testAccounts);

        // Act
        Accounts accounts = accountService.getAllAccounts();

        // Assert
        assertNotNull(accounts);
        assertEquals(1, accounts.getAccounts().size());
        assertEquals(accountUid, accounts.getAccounts().get(0).getAccountUid());

        verify(accountClient, times(1)).getAllAccounts();
    }

    @Test
    @DisplayName("Test get account")
    public void testGetAccount() {
        // Arrange
        Account testAccount = new Account();
        UUID accountUid = UUID.randomUUID();
        testAccount.setAccountUid(accountUid);
        testAccount.setAccountType("PRIMARY");
        testAccount.setCurrency("GBP");

        Accounts testAccounts = new Accounts();
        testAccounts.setAccounts(List.of(testAccount));

        when(accountClient.getAllAccounts()).thenReturn(testAccounts);

        // Act
        Account account = accountService.getAccount(accountUid);

        // Assert
        assertNotNull(account);
        assertEquals(accountUid, account.getAccountUid());

        verify(accountClient, times(1)).getAllAccounts();
    }

    @Test
    @DisplayName("Test get account throws exception for invalid accountUid")
    public void testGetAccountThrowsExceptionForInvalidAccountUid() {
        // Arrange
        UUID invalidAccountUid = UUID.randomUUID();
        when(accountClient.getAllAccounts()).thenThrow(new AccountNotFoundException("Account not found"));

        // Act and Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(invalidAccountUid));

        verify(accountClient, times(1)).getAllAccounts();
    }

    @Test
    @DisplayName("Test get account balance")
    public void testGetAccountBalance() {
        // Arrange
        Account testAccount = new Account();
        UUID accountUid = UUID.randomUUID();
        testAccount.setAccountUid(accountUid);
        testAccount.setAccountType("PRIMARY");
        testAccount.setCurrency("GBP");

        Accounts testAccounts = new Accounts();
        testAccounts.setAccounts(List.of(testAccount));

        when(accountClient.getAllAccounts()).thenReturn(testAccounts);

        // Act
        Account account = accountService.getAccount(accountUid);

        // Assert
        assertNotNull(account);
        assertEquals(accountUid, account.getAccountUid());

        verify(accountClient, times(1)).getAllAccounts();
    }

    @Test
    @DisplayName("Get account balance returns expected balance")
    public void getAccountBalanceReturnsExpectedBalance() {
        // Arrange
        UUID accountUid = UUID.randomUUID();
        CurrencyAndAmount clearedBalance = CurrencyAndAmount.builder()
                .minorUnits(new BigDecimal("1000.00"))
                .currency("GBP")
                .build();

        AccountBalance expectedBalance = AccountBalance.builder()
                .clearedBalance(clearedBalance)
                .build();
        when(accountClient.getAccountBalance(accountUid)).thenReturn(expectedBalance);

        // Act
        AccountBalance actualBalance = accountService.getAccountBalance(accountUid);

        // Assert
        assertEquals(expectedBalance.getClearedBalance().getMinorUnits(), actualBalance.getClearedBalance().getMinorUnits());

        verify(accountClient, times(1)).getAccountBalance(accountUid);
    }

    @Test
    @DisplayName("Get account balance throws exception for invalid accountUid")
    public void getAccountBalanceThrowsExceptionForInvalidAccountUid() {
        // Arrange
        UUID invalidAccountUid = UUID.randomUUID();
        when(accountClient.getAccountBalance(invalidAccountUid)).thenThrow(new AccountNotFoundException("Account not found"));

        // Act and Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountBalance(invalidAccountUid));

        verify(accountClient, times(1)).getAccountBalance(invalidAccountUid);
    }
}
