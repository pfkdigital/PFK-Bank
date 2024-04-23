package org.techtest.starling.service;

import org.techtest.starling.model.*;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * Account service
 */
public interface AccountService {
    /**
     * Get all accounts
     * @return Accounts
     */
    Accounts getAllAccounts();

    /**
     * Get account
     * @param accountUid the accounts unique identifier
     * @return Account
     */
    Account getAccount(UUID accountUid);

    /**
     * Get confirmation of funds request
     * @param accountUid the accounts unique identifier
     * @param targetAmountInMinorUnits the target amount in minor units
     * @return ConfirmationOfFundsResponse
     */
    ConfirmationOfFundsResponse getConfirmationOfFundsRequest(UUID accountUid, BigDecimal targetAmountInMinorUnits);

    /**
     * Get account balance
     * @param accountUid the accounts unique identifier
     * @return AccountBalance
     */
    AccountBalance getAccountBalance(UUID accountUid);
}
