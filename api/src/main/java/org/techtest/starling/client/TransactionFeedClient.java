package org.techtest.starling.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.techtest.starling.exception.ApiRuntimeException;
import org.techtest.starling.model.FeedItems;

import java.util.UUID;

// This class is used to interact with the Starling transaction feed API endpoints
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TransactionFeedClient {
    private final BaseHttpClient baseHttpClient;

    @Value("${starling.transaction.transactions-between.endpoint}")
    private String transactionFeedBetween;

    /**
     * Get all transactions between two dates
     *
     * @param accountUid              accountUid
     * @param minTransactionTimestamp minTransactionTimestamp
     * @param maxTransactionTimestamp maxTransactionTimestamp
     * @return FeedItems
     */
    public FeedItems getTransactionFeedBetween(UUID accountUid, String minTransactionTimestamp, String maxTransactionTimestamp) {
        log.info("Getting all transactions between two dates");
        return baseHttpClient
                .getRestClient()
                .get()
                .uri(transactionFeedBetween, accountUid, minTransactionTimestamp, maxTransactionTimestamp)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error while fetching all transactions between two dates");
                    throw new ApiRuntimeException(response,"Error while fetching all transactions between two dates");
                })
                .body(FeedItems.class);
    }
}
