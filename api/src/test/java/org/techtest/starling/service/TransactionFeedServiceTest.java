package org.techtest.starling.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techtest.starling.client.TransactionFeedClient;
import org.techtest.starling.model.FeedItem;
import org.techtest.starling.model.FeedItems;
import org.techtest.starling.service.impl.TransactionFeedServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionFeedServiceTest {
    @Mock
    private TransactionFeedClient transactionFeedClient;

    @InjectMocks
    private TransactionFeedServiceImpl transactionFeedService;

    @Test
    @DisplayName("Test get weeks transaction feed")
    void testGetWeeksTransactionFeed() {
        // Arrange
        TransactionFeedServiceImpl spyTransactionService = Mockito.spy(transactionFeedService);
        UUID accountUid = UUID.randomUUID();
        UUID categoryUid = UUID.randomUUID();
        String changesSince = "2024-04-02T00:00:00.000Z";

        FeedItems testFeedItems = new FeedItems();
        FeedItem testFeedItem = new FeedItem();
        testFeedItem.setFeedItemUid(UUID.randomUUID());
        testFeedItem.setCategoryUid(categoryUid);
        testFeedItems.setFeedItems(List.of(testFeedItem));

        when(transactionFeedClient.getWeeksTransactionFeed(accountUid, categoryUid,changesSince)).thenReturn(testFeedItems);
        when(spyTransactionService.getStartOfWeek()).thenReturn(changesSince);


        // Act
        FeedItems result = spyTransactionService.getWeeksTransactionFeed(accountUid, categoryUid);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getFeedItems().size());
        assertEquals(categoryUid, result.getFeedItems().get(0).getCategoryUid());
        assertEquals(testFeedItem.getFeedItemUid(), result.getFeedItems().get(0).getFeedItemUid());

        verify(transactionFeedClient).getWeeksTransactionFeed(accountUid, categoryUid,changesSince);
    }

    @Test
    @DisplayName("Test get all transactions")
    void testGetAllTransactions() {
        // Arrange
        UUID accountUid = UUID.randomUUID();
        UUID categoryUid = UUID.randomUUID();
        String minTransactionTimestamp = "2024-04-02T00:00:00.000Z";
        String maxTransactionTimestamp = "2024-04-20T00:00:00.000Z";

        FeedItem testFeedItem = new FeedItem();
        testFeedItem.setFeedItemUid(UUID.randomUUID());
        testFeedItem.setCategoryUid(categoryUid);

        FeedItems testFeedItems = new FeedItems();
        testFeedItems.setFeedItems(List.of(testFeedItem));

        when(transactionFeedClient.getTransactionFeedBetween(accountUid, minTransactionTimestamp, maxTransactionTimestamp)).thenReturn(testFeedItems);

        // Act
        FeedItems result = transactionFeedService.getTransactionFeedBetween(accountUid, minTransactionTimestamp, maxTransactionTimestamp);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getFeedItems().size());
        assertEquals(categoryUid, result.getFeedItems().get(0).getCategoryUid());
        assertEquals(testFeedItem.getFeedItemUid(), result.getFeedItems().get(0).getFeedItemUid());

        verify(transactionFeedClient).getTransactionFeedBetween(accountUid, minTransactionTimestamp, maxTransactionTimestamp);
    }
}
