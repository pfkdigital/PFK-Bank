package org.techtest.starling.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.techtest.starling.model.ErrorResponse;
import org.techtest.starling.model.RoundUpRequest;
import org.techtest.starling.model.RoundUpResponse;

@Tag(name = "Rounding Up", description = "The Rounding Up API")
public interface RoundingUpApi {

    @Operation(summary = "Round up transactions", description = "rounds up transactions to the nearest whole number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully rounded up transactions", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RoundUpResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Insufficient funds", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    ResponseEntity<RoundUpResponse> roundUp(@RequestBody RoundUpRequest roundUpRequest);
}
