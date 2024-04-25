package org.techtest.starling.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.techtest.starling.client.TransactionFeedClient;
import org.techtest.starling.model.FeedItems;
import org.techtest.starling.service.TransactionFeedService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionFeedServiceImpl implements TransactionFeedService {

    private final TransactionFeedClient transactionFeedClient;

    @Override
    public FeedItems getTransactionFeedBetween(
            UUID accountUid,
            String minTransactionTimestamp,
            String maxTransactionTimestamp) {
        log.info("Getting transaction feed between minTransactionTimestamp: {} and maxTransactionTimestamp: {}",
                minTransactionTimestamp, maxTransactionTimestamp);
        return transactionFeedClient.getTransactionFeedBetween(
                accountUid, minTransactionTimestamp, maxTransactionTimestamp);
    }
}
