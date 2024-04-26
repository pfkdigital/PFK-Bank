package org.techtest.starling.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.techtest.starling.enums.Direction;
import org.techtest.starling.exception.InsufficientFundsException;
import org.techtest.starling.model.*;
import org.techtest.starling.service.AccountService;
import org.techtest.starling.service.RoundingUpService;
import org.techtest.starling.service.SavingsGoalService;
import org.techtest.starling.service.TransactionFeedService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class RoundingUpServiceImpl implements RoundingUpService {
    private final AccountService accountService;
    private final SavingsGoalService savingsGoalService;
    private final TransactionFeedService transactionFeedService;

    @Override
    public RoundUpResponse roundUp(RoundUpRequest roundUpRequest) {
        log.info("Rounding up for roundUpRequest: {}", roundUpRequest);

        // Get Account
        log.info("Getting account for accountUid: {}", roundUpRequest.getAccountUid());
        Account account = accountService.getAccount(roundUpRequest.getAccountUid());
        log.info("Account: {}", account);

        // Get Saving Goal
        log.info("Getting saving goal of savingsGoalUid: {}", roundUpRequest.getSavingsGoalUid());
        SavingGoalV2 savingGoal = savingsGoalService.getSingleSavingsGoal(roundUpRequest.getAccountUid(), roundUpRequest.getSavingsGoalUid());
        log.info("Saving goal: {}", savingGoal);

        // Get Transaction Feed
        log.info("Getting settled transaction feed for accountUid: {}", roundUpRequest.getAccountUid());
        FeedItems feedItems = transactionFeedService.getTransactionFeedBetween(roundUpRequest.getAccountUid(), roundUpRequest.getMinTransactionTimestamp(), roundUpRequest.getMaxTransactionTimestamp());
        log.info("Found {} settled transactions", feedItems.getFeedItems().size());


        // Calculate round up
        log.info("Calculating round up for account: {} and feedItems: {}", account, feedItems);
        BigDecimal roundUpAmount = sumUpTransactionFeed(feedItems, account);
        Currency currency = Currency.getInstance(account.getCurrency());
        log.info("Round up amount: {}", roundUpAmount);

        // Confirm that the round up amount will not put the account into overdraft
        log.info("Confirming that the round up amount will not put the account into overdraft");
        ConfirmationOfFundsResponse confirmationOfFundsResponse = accountService.getConfirmationOfFundsRequest(roundUpRequest.getAccountUid(), roundUpAmount);
        log.info("Confirmation of funds response: {}", confirmationOfFundsResponse);

        boolean requestedAmountAvailableToSpend = confirmationOfFundsResponse.isRequestedAmountAvailableToSpend();
        boolean accountWouldBeInOverdraftIfRequestedAmountSpent = confirmationOfFundsResponse.isAccountWouldBeInOverdraftIfRequestedAmountSpent();

        if (!requestedAmountAvailableToSpend || accountWouldBeInOverdraftIfRequestedAmountSpent) {
            log.warn("Round up amount will put the account into overdraft");
            throw new InsufficientFundsException("Round up amount will put the account into overdraft");
        }

        log.info("Round up will be transferred to saving goal");
        // Transfer round up to saving goal
        CurrencyAndAmount currencyAndAmount = CurrencyAndAmount.builder()
                .currency(account.getCurrency())
                .minorUnits(roundUpAmount)
                .build();

        TopUpRequestV2 topUpRequestV2 = TopUpRequestV2.builder()
                .amount(currencyAndAmount)
                .build();

        SavingsGoalTransferResponseV2 savingsGoalTransferResponseV2 = savingsGoalService.transferMoneyToSavingsGoal(topUpRequestV2, roundUpRequest.getAccountUid(), roundUpRequest.getSavingsGoalUid());
        log.info("Savings goal transfer response: {}", savingsGoalTransferResponseV2);

        return RoundUpResponse.builder()
                .transferResponseV2(savingsGoalTransferResponseV2)
                .roundUpMessage("Round up of " + currency.getSymbol() + roundUpAmount.movePointLeft(2) + " transferred to saving goal: " + savingGoal.getName())
                .build();
    }

    /**
     * Rounds up the transaction amount to the nearest whole number and returns the remainder.
     *
     * @param transaction The transaction amount.
     * @return The remainder.
     */
    public BigDecimal roundUpTransaction(BigDecimal transaction) {
        BigDecimal transactionAmount = transaction.movePointLeft(2);
        BigDecimal rounded = transactionAmount.setScale(0, RoundingMode.CEILING);

        BigDecimal remainder = rounded.subtract(transactionAmount);

        log.info("Transaction: {}, Rounded: {}, Remainder: {}", transactionAmount, rounded, remainder);
        return remainder;
    }

    /**
     * Sums the round up amounts for the transactions in the feed.
     *
     * @param transactionFeed The transaction feed.
     * @param account         The account.
     * @return The sum of the round up amounts.
     */
    public BigDecimal sumUpTransactionFeed(FeedItems transactionFeed, Account account) {
        BigDecimal roundupSum = transactionFeed
                .getFeedItems()
                .stream()
                .filter(feedItem -> feedItem.getDirection().equals(Direction.OUT))
                .map(FeedItem::getAmount)
                .filter(amount -> amount.getCurrency().equals(account.getCurrency()))
                .map(CurrencyAndAmount::getMinorUnits)
                .map(this::roundUpTransaction)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal roundedMinorUnits = roundupSum.movePointRight(2);
        log.info("Sum of roundups: {}", roundupSum);
        return roundedMinorUnits;
    }
}
