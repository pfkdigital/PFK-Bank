package org.techtest.starling.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.techtest.starling.exception.ApiRuntimeException;
import org.techtest.starling.model.*;

import java.math.BigDecimal;

import java.util.UUID;

// This class is used to interact with the Starling account API endpoints
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AccountClient {
    private final BaseHttpClient baseHttpClient;

    @Value("${starling.account.all-accounts.endpoint}")
    private String allAccountsUrl;
    @Value("${starling.account.confirmation-of-funds.endpoint}")
    private String confirmationOfFundsUrl;
    @Value("${starling.account.balance.endpoint}")
    private String accountBalanceUrl;

    /**
     * Get all accounts
     *
     * @return Accounts
     */
    public Accounts getAllAccounts() {
        log.info("Getting all accounts");
        return baseHttpClient
                .getRestClient()
                .get()
                .uri(allAccountsUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    log.error("Error while fetching all accounts. Status code: {}", response.getStatusCode());
                    throw new ApiRuntimeException(response, "Error while fetching all accounts. Status code: " + response.getStatusCode());
                }))
                .body(Accounts.class);
    }

    /**
     * Get confirmation of funds
     *
     * @param accountUid               accountUid
     * @param targetAmountInMinorUnits targetAmountInMinorUnits
     * @return ConfirmationOfFundsResponse
     */
    public ConfirmationOfFundsResponse getConfirmationOfFunds(UUID accountUid, BigDecimal targetAmountInMinorUnits) {
        log.info("Getting confirmation of funds for accountUid: {}", accountUid);
        return baseHttpClient
                .getRestClient()
                .get()
                .uri(confirmationOfFundsUrl, accountUid, targetAmountInMinorUnits)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    log.error("Error while fetching confirmation of funds. Status code: {}", response.getStatusCode());
                    throw new ApiRuntimeException(response,"Error while fetching confirmation of funds. Status code: " + response.getStatusCode());
                }))
                .body(ConfirmationOfFundsResponse.class);
    }

    /**
     * Get account balance
     *
     * @param accountUid accountUid
     * @return AccountBalance
     */
    public AccountBalance getAccountBalance(UUID accountUid) {
        log.info("Getting account balance for accountUid: {}", accountUid);
        return baseHttpClient
                .getRestClient()
                .get()
                .uri(accountBalanceUrl, accountUid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    log.error("Error while fetching account balance. Status code: {}", response.getStatusCode());
                    throw new ApiRuntimeException(response,"Error while fetching account balance. Status code: " + response.getStatusCode());
                }))
                .body(AccountBalance.class);
    }
}
