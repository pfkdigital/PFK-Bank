package org.techtest.starling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.techtest.starling.enums.Direction;
import org.techtest.starling.model.CurrencyAndAmount;
import org.techtest.starling.model.FeedItem;
import org.techtest.starling.model.FeedItems;
import org.techtest.starling.service.TransactionFeedService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TransactionFeedController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TransactionFeedControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionFeedService transactionFeedService;

    private UUID accountUid;
    private UUID categoryUid;

    @BeforeEach()
    void setUp() {
        //Arrange
        accountUid = UUID.randomUUID();
        categoryUid = UUID.randomUUID();
    }

    @Test
    @DisplayName("Should return all transactions for an account for a week")
    void shouldReturnAllTransactionsForAnAccountForAWeek() throws Exception {
        //Arrange
        String changesSince = "2024-04-02T00:00:00.000Z";
        FeedItem feedItem = FeedItem.builder()
                .feedItemUid(UUID.randomUUID())
                .categoryUid(categoryUid)
                .amount(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(100)).build())
                .direction(Direction.OUT)
                .sourceAmount(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(100)).build())
                        .build();

        FeedItems feedItems = FeedItems.builder().feedItems(List.of(feedItem)).build();

        when(transactionFeedService.getWeeksTransactionFeed(accountUid, categoryUid)).thenReturn(feedItems);
        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/transactions/account/{accountUid}/category/{categoryUid}", accountUid, categoryUid).queryParam("changesSince", changesSince));

        //Assert
        assertEquals(200, resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(feedItems), resultActions.andReturn().getResponse().getContentAsString());

        verify(transactionFeedService).getWeeksTransactionFeed(accountUid, categoryUid);
    }

    @Test
    @DisplayName("Should return all transactions for an account between two dates")
    void shouldReturnAllTransactionsForAnAccountBetweenTwoDates() throws Exception {
        //Arrange
        String minTransactionTimestamp = "2024-04-02T00:00:00.000Z";
        String maxTransactionTimestamp = "2024-04-20T00:00:00.000Z";
        FeedItem feedItem = FeedItem.builder()
                .feedItemUid(UUID.randomUUID())
                .categoryUid(categoryUid)
                .amount(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(100)).build())
                .direction(Direction.OUT)
                .sourceAmount(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(100)).build())
                .build();

        FeedItems feedItems = FeedItems.builder().feedItems(List.of(feedItem)).build();

        when(transactionFeedService.getTransactionFeedBetween(accountUid, minTransactionTimestamp, maxTransactionTimestamp)).thenReturn(feedItems);
        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/transactions/account/{accountUid}/time-between", accountUid).queryParam("minTransactionTimestamp", minTransactionTimestamp).queryParam("maxTransactionTimestamp", maxTransactionTimestamp));

        //Assert
        assertEquals(200, resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(feedItems), resultActions.andReturn().getResponse().getContentAsString());

        verify(transactionFeedService).getTransactionFeedBetween(accountUid, minTransactionTimestamp, maxTransactionTimestamp);
    }
}
