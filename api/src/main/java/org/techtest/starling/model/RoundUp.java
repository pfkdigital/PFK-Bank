package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoundUp {
    private UUID goalCategoryUid;
    private CurrencyAndAmount amount;
}
