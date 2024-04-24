package org.techtest.starling.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.techtest.starling.model.*;

import java.util.UUID;

@Tag(name = "Savings Goal", description = "The Savings Goal API")
public interface SavingsGoalApi {

    @Operation(summary = "Get all savings goals", description = "fetches all savings goals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all savings goals", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalsV2.class))}),
            @ApiResponse(responseCode = "404", description = "Savings goals not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/account/{accountUid}")
    ResponseEntity<?> getSavingsGoals(@PathVariable UUID accountUid);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created new savings goals", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalV2.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Savings goals not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/account/{accountUid}")
    ResponseEntity<?> createNewSavingsGoals(@PathVariable UUID accountUid, @RequestBody SavingsGoalRequestV2 savingsGoalRequestV2);

    @Operation(summary = "Transfer money to savings goal", description = "transfers money to a specific savings goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully transferred money to savings goal", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SavingsGoalTransferResponseV2.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Savings goal not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/account/{accountUid}/savings-goal/{savingsGoalUid}")
    ResponseEntity<?> transferMoneyToSavingsGoal(@PathVariable UUID accountUid, @PathVariable UUID savingsGoalUid, @RequestBody TopUpRequestV2 topUpRequestV2);
}
