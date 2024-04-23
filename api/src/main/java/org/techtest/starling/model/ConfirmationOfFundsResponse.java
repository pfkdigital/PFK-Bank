package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmationOfFundsResponse {
    private boolean requestedAmountAvailableToSpend;
    private boolean accountWouldBeInOverdraftIfRequestedAmountSpent;
}
