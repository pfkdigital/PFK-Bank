package org.techtest.starling.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.techtest.starling.model.ErrorResponse;
import org.techtest.starling.model.FeedItems;

import java.util.UUID;

@Tag(name = "Transaction Feed", description = "The Transaction Feed API")
public interface TransactionFeedApi {

    @Operation(summary = "Get feed items", description = "fetches feed items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved feed items", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItems.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    ResponseEntity<FeedItems> getFeedItems(
            @PathVariable UUID accountUid, @PathVariable UUID categoryUid);

    @Operation(summary = "Get feed items", description = "fetches feed items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved feed items", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItems.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    ResponseEntity<FeedItems> getFeedItems(
            @PathVariable UUID accountUid,
            @RequestParam("minTransactionTimestamp") String minTransactionTimestamp,
            @RequestParam("maxTransactionTimestamp") String maxTransactionTimestamp);
}
