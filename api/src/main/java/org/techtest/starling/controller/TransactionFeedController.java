package org.techtest.starling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtest.starling.controller.api.TransactionFeedApi;
import org.techtest.starling.model.FeedItems;
import org.techtest.starling.service.TransactionFeedService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionFeedController implements TransactionFeedApi {
    private final TransactionFeedService transactionFeedService;

    @GetMapping("/account/{accountUid}/category/{categoryUid}")
    public ResponseEntity<FeedItems> getFeedItems(
            @PathVariable UUID accountUid, @PathVariable UUID categoryUid) {
        return new ResponseEntity<>(
                transactionFeedService.getWeeksTransactionFeed(accountUid, categoryUid), HttpStatus.OK);
    }

    @GetMapping("/account/{accountUid}/time-between")
    public ResponseEntity<FeedItems> getFeedItems(
            @PathVariable UUID accountUid,
            @RequestParam("minTransactionTimestamp") String minTransactionTimestamp,
            @RequestParam("maxTransactionTimestamp") String maxTransactionTimestamp) {
        return new ResponseEntity<>(
                transactionFeedService.getTransactionFeedBetween(
                        accountUid, minTransactionTimestamp, maxTransactionTimestamp),
                HttpStatus.OK);
    }
}
