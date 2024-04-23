package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsGoalRequestV2 {
    private String name;
    private String currency;
    private CurrencyAndAmount target;
    private String base64EncodedPhoto;
}
