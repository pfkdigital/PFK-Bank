package org.techtest.starling.service;

import org.techtest.starling.model.FeedItems;

import java.util.UUID;

/**
 * Transaction feed service
 */
public interface TransactionFeedService {
    /**
     * Get all transactions between two dates
     *
     * @param accountUid              accountUid
     * @param minTransactionTimestamp minTransactionTimestamp
     * @param maxTransactionTimestamp maxTransactionTimestamp
     * @return FeedItems
     */
    FeedItems getTransactionFeedBetween(
            UUID accountUid,
            String minTransactionTimestamp,
            String maxTransactionTimestamp);
}
