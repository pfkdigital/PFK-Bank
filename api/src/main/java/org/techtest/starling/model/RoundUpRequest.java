package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoundUpRequest {
    private UUID accountUid;
    private UUID savingsGoalUid;
    private String minTransactionTimestamp;
    private String maxTransactionTimestamp;
}
