package org.techtest.starling.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private List<ErrorDetail> errors;
    @Schema(description = "Indicates if the request was successful", example = "false")
    private boolean success;
}
