package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBalance {
    private CurrencyAndAmount clearedBalance;
    private CurrencyAndAmount effectiveBalance;
    private CurrencyAndAmount pendingTransactions;
    private CurrencyAndAmount acceptedOverdraft;
    private CurrencyAndAmount amount;
    private CurrencyAndAmount totalClearedBalance;
    private CurrencyAndAmount totalEffectiveBalance;
}
