package org.techtest.starling.service;

import org.techtest.starling.model.*;

import java.util.UUID;

/**
 * Savings goal service
 */
public interface SavingsGoalService {

    /**
     * Get all savings goals
     * @param accountUid the accounts unique identifier
     * @return SavingsGoals
     */
    SavingGoalsV2 getSavingsGoals(UUID accountUid);

    /**
     * Get single savings goal
     * @param accountUid the accounts unique identifier
     * @param savingsGoalUid the savings goal unique identifier
     * @return SavingGoal
     */
    SavingGoalV2 getSingleSavingsGoal(UUID accountUid, UUID savingsGoalUid);

    /**
     * Transfer money to savings goal
     * @param topUpRequestV2 the top-up request
     * @param accountUid the accounts unique identifier
     * @param savingsGoalUid the savings goal unique identifier
     * @return SavingsGoalTransferResponse
     */
    SavingsGoalTransferResponseV2 transferMoneyToSavingsGoal(TopUpRequestV2 topUpRequestV2, UUID accountUid, UUID savingsGoalUid);

    /**
     * Create new savings goals
     * @param accountUid the accounts unique identifier
     * @param savingsGoalRequestV2 the savings goal request
     * @return CreateOrUpdateSavingsGoalResponse
     */
    CreateOrUpdateSavingsGoalResponseV2 createNewSavingsGoals(UUID accountUid, SavingsGoalRequestV2 savingsGoalRequestV2);
}
