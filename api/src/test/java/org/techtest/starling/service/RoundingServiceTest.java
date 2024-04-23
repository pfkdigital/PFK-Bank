package org.techtest.starling.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techtest.starling.enums.Direction;
import org.techtest.starling.exception.InsufficientFundsException;
import org.techtest.starling.model.*;
import org.techtest.starling.service.impl.RoundingUpServiceImpl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundingServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private SavingsGoalService savingsGoalService;

    @Mock
    private TransactionFeedService transactionFeedService;

    @InjectMocks
    private RoundingUpServiceImpl roundingUpService;

    @DisplayName("Should round up transactions and transfer to savings goal")
    @Test
    void shouldRoundUpTransactionsAndTransferToSavingsGoal() {
        // Arrange
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();
        RoundingUpServiceImpl spy = spy(roundingUpService);
        RoundUpRequest roundUpRequest = RoundUpRequest.builder()
                .accountUid(accountUid)
                .savingsGoalUid(savingsGoalUid)
                .minTransactionTimestamp(String.valueOf(ZonedDateTime.now().minusDays(7)))
                .maxTransactionTimestamp(String.valueOf(ZonedDateTime.now()))
                .build();
        Account account = Account.builder()
                .currency("GBP")
                .accountType("PRIMARY")
                .defaultCategory("INCOME")
                .createdAt(String.valueOf(ZonedDateTime.now()))
                .name("Test Account")
                .build();
        when(accountService.getAccount(roundUpRequest.getAccountUid())).thenReturn(account);
        SavingGoalV2 savingGoal = SavingGoalV2.builder()
                .name("Test Savings Goal")
                .savingsGoalUid(savingsGoalUid)
                .target(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)))
                .totalSaved(new CurrencyAndAmount("GBP", BigDecimal.ZERO))
                .savedPercentage(0)
                .build();
        when(savingsGoalService.getSingleSavingsGoal(roundUpRequest.getAccountUid(), roundUpRequest.getSavingsGoalUid())).thenReturn(savingGoal);


        FeedItem feedItem = FeedItem.builder()
                .direction(Direction.OUT)
                .amount(new CurrencyAndAmount("GBP", BigDecimal.valueOf(10)))
                .build();
        FeedItems feedItems = FeedItems.builder()
                .feedItems(List.of(feedItem))
                .build();

        when(transactionFeedService.getTransactionFeedBetween(roundUpRequest.getAccountUid(), roundUpRequest.getMinTransactionTimestamp(), roundUpRequest.getMaxTransactionTimestamp())).thenReturn(feedItems);

        doReturn(BigDecimal.valueOf(10)).when(spy).sumUpTransactionFeed(eq(feedItems), any(Account.class));
        ConfirmationOfFundsResponse confirmationOfFundsResponse = ConfirmationOfFundsResponse.builder()
                .requestedAmountAvailableToSpend(true)
                .accountWouldBeInOverdraftIfRequestedAmountSpent(false)
                .build();
        when(accountService.getConfirmationOfFundsRequest(any(UUID.class), eq(BigDecimal.valueOf(10)))).thenReturn(confirmationOfFundsResponse);

        CurrencyAndAmount testAmount = CurrencyAndAmount.builder()
                .currency(account.getCurrency())
                .minorUnits(BigDecimal.TEN)
                .build();

        TopUpRequestV2 topUpRequestV2 = TopUpRequestV2.builder().amount(testAmount).build();

        SavingsGoalTransferResponseV2 savingsGoalTransferResponseV2 = SavingsGoalTransferResponseV2.builder()
                .transferUid(UUID.randomUUID())
                .success(true)
                .build();
        when(savingsGoalService.transferMoneyToSavingsGoal(topUpRequestV2, roundUpRequest.getAccountUid(), roundUpRequest.getSavingsGoalUid())).thenReturn(savingsGoalTransferResponseV2);
        // Action
        RoundUpResponse roundUpResponse = spy.roundUp(roundUpRequest);

        // Assert
        assertNotNull(roundUpResponse);
        assertEquals("Round up of 10 transferred to saving goal: Test Savings Goal", roundUpResponse.getRoundUpMessage());
        assertEquals(savingsGoalTransferResponseV2, roundUpResponse.getTransferResponseV2());

        verify(accountService).getAccount(accountUid);
        verify(savingsGoalService).getSingleSavingsGoal(accountUid, savingsGoalUid);
        verify(transactionFeedService).getTransactionFeedBetween(accountUid, roundUpRequest.getMinTransactionTimestamp(), roundUpRequest.getMaxTransactionTimestamp());
        verify(spy).sumUpTransactionFeed(feedItems, account);
        verify(accountService).getConfirmationOfFundsRequest(accountUid, BigDecimal.valueOf(10));
        verify(savingsGoalService).transferMoneyToSavingsGoal(topUpRequestV2, accountUid, savingsGoalUid);
        verify(spy).roundUp(roundUpRequest);
        verify(spy).sumUpTransactionFeed(feedItems, account);
        verify(accountService).getConfirmationOfFundsRequest(accountUid, BigDecimal.valueOf(10));
        verify(savingsGoalService).transferMoneyToSavingsGoal(topUpRequestV2, accountUid, savingsGoalUid);
    }

    @DisplayName("Should throw exception when round up amount will put account into overdraft")
    @Test
    void shouldThrowExceptionWhenRoundUpAmountWillPutAccountIntoOverdraft() {
        // Arrange
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();
        RoundingUpServiceImpl spy = spy(roundingUpService);
        RoundUpRequest roundUpRequest = RoundUpRequest.builder()
                .accountUid(accountUid)
                .savingsGoalUid(savingsGoalUid)
                .minTransactionTimestamp(String.valueOf(ZonedDateTime.now().minusDays(7)))
                .maxTransactionTimestamp(String.valueOf(ZonedDateTime.now()))
                .build();
        Account account = Account.builder()
                .currency("GBP")
                .accountType("PRIMARY")
                .defaultCategory("INCOME")
                .createdAt(String.valueOf(ZonedDateTime.now()))
                .name("Test Account")
                .build();
        when(accountService.getAccount(roundUpRequest.getAccountUid())).thenReturn(account);
        SavingGoalV2 savingGoal = SavingGoalV2.builder()
                .name("Test Savings Goal")
                .savingsGoalUid(savingsGoalUid)
                .target(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)))
                .totalSaved(new CurrencyAndAmount("GBP", BigDecimal.ZERO))
                .savedPercentage(0)
                .build();
        when(savingsGoalService.getSingleSavingsGoal(roundUpRequest.getAccountUid(), roundUpRequest.getSavingsGoalUid())).thenReturn(savingGoal);

        FeedItem feedItem = FeedItem.builder()
                .direction(Direction.OUT)
                .amount(new CurrencyAndAmount("GBP", BigDecimal.valueOf(10)))
                .build();
        FeedItems feedItems = FeedItems.builder().feedItems(List.of(feedItem)).build();
        when(transactionFeedService.getTransactionFeedBetween(roundUpRequest.getAccountUid(), roundUpRequest.getMinTransactionTimestamp(), roundUpRequest.getMaxTransactionTimestamp())).thenReturn(feedItems);

        doReturn(BigDecimal.valueOf(10)).when(spy).sumUpTransactionFeed(eq(feedItems), any(Account.class));
        ConfirmationOfFundsResponse confirmationOfFundsResponse = ConfirmationOfFundsResponse.builder()
                .requestedAmountAvailableToSpend(false)
                .accountWouldBeInOverdraftIfRequestedAmountSpent(true)
                .build();

        when(accountService.getConfirmationOfFundsRequest(any(UUID.class), eq(BigDecimal.valueOf(10)))).thenReturn(confirmationOfFundsResponse);

        // Action & Assert
        assertThrows(InsufficientFundsException.class, () -> spy.roundUp(roundUpRequest));
        verify(accountService).getAccount(accountUid);
        verify(savingsGoalService).getSingleSavingsGoal(accountUid, savingsGoalUid);
        verify(transactionFeedService).getTransactionFeedBetween(accountUid, roundUpRequest.getMinTransactionTimestamp(), roundUpRequest.getMaxTransactionTimestamp());
        verify(spy).sumUpTransactionFeed(feedItems, account);
        verify(accountService).getConfirmationOfFundsRequest(accountUid, BigDecimal.valueOf(10));
    }

}