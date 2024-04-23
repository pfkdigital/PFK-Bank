package org.techtest.starling.service;

import org.techtest.starling.model.RoundUpRequest;
import org.techtest.starling.model.RoundUpResponse;

/**
 * Rounding up service
 */
public interface RoundingUpService {

    /**
     * Rounds up the transactions for the account and transfers the round up amount to the saving goal.
     *
     * @param roundUpRequest The round up request.
     * @return The round up response.
     */
    RoundUpResponse roundUp(RoundUpRequest roundUpRequest);
}
