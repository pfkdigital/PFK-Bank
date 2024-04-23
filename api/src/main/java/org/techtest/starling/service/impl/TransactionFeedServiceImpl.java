package org.techtest.starling.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.techtest.starling.client.TransactionFeedClient;
import org.techtest.starling.model.FeedItems;
import org.techtest.starling.service.TransactionFeedService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionFeedServiceImpl implements TransactionFeedService {

    private final TransactionFeedClient transactionFeedClient;

    @Override
    public FeedItems getWeeksTransactionFeed(UUID accountUid, UUID categoryUid) {
        log.info("Getting weeks transaction feed for accountUid: {} and categoryUid: {}", accountUid, categoryUid);

        String changesSince = getStartOfWeek();
        return transactionFeedClient.getWeeksTransactionFeed(accountUid, categoryUid, changesSince);
    }

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

    /**
     * Get the start of the week in ISO8601 format
     *
     * @return String
     */
    public String getStartOfWeek() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        return startOfWeek.format(formatter);
    }

    /**
     * Convert a long date to ISO8601 format
     *
     * @param timestamp long
     * @return String
     */
    public String convertToISO(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        return zonedDateTime.format(formatter);
    }
}
