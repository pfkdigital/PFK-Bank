package org.techtest.starling.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.techtest.starling.model.AccountBalance;
import org.techtest.starling.model.Accounts;
import org.techtest.starling.model.ErrorResponse;

import java.util.UUID;

@Tag(name = "Account", description = "The Account API")
public interface AccountApi {
    @Operation(summary = "Get all accounts", description = "fetches all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all accounts", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Accounts.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    ResponseEntity<Accounts> getAllAccounts();

    @Operation(summary = "Get account balance", description = "fetches the balance of a specific account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved account balance"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable UUID accountUid);
}
