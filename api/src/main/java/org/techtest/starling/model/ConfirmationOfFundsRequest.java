package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmationOfFundsRequest {
    private UUID accountUid;
    private BigDecimal targetAmountInMinorUnits;
}
