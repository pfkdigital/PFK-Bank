package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoundUpResponse {
    private SavingsGoalTransferResponseV2 transferResponseV2;
    private String roundUpMessage;
}
