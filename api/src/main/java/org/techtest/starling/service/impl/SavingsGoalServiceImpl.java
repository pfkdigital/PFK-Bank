package org.techtest.starling.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.techtest.starling.client.SavingsGoalClient;
import org.techtest.starling.exception.SavingGoalNotFoundException;
import org.techtest.starling.model.*;
import org.techtest.starling.service.SavingsGoalService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsGoalServiceImpl implements SavingsGoalService {

    private final SavingsGoalClient savingsGoalClient;

    @Override
    public SavingGoalsV2 getSavingsGoals(UUID accountUid) {
        log.info("Getting savings goals for accountUid: {}", accountUid);
        return savingsGoalClient.getSavingsGoals(accountUid);
    }

    @Override
    public SavingGoalV2 getSingleSavingsGoal(UUID accountUid, UUID savingsGoalUid) {
        log.info("Getting single savings goal for accountUid: {} and savingsGoalUid: {}", accountUid, savingsGoalUid);
        SavingGoalsV2 savingGoals = savingsGoalClient.getSavingsGoals(accountUid);
        return savingGoals.getSavingsGoalList().stream()
                .filter(savingGoal -> savingGoal.getSavingsGoalUid().equals(savingsGoalUid))
                .findFirst().orElseThrow(() -> new SavingGoalNotFoundException("Savings goal not found"));
    }

    @Override
    public CreateOrUpdateSavingsGoalResponseV2 createNewSavingsGoals(UUID accountUid, SavingsGoalRequestV2 savingsGoalRequestV2) {
        log.info("Creating a new savings goal for accountUid: {}", accountUid);
        return savingsGoalClient.createNewSavingsGoals(accountUid, savingsGoalRequestV2);
    }

    @Override
    public SavingsGoalTransferResponseV2 transferMoneyToSavingsGoal(TopUpRequestV2 topUpRequest, UUID accountUid, UUID savingsGoalUid) {
        log.info("Transferring money to savings goal for accountUid: {} and savingsGoalUid: {}", accountUid, savingsGoalUid);
        UUID transferUid = UUID.randomUUID();

        return savingsGoalClient.transferMoneyToSavingsGoal(topUpRequest, accountUid, savingsGoalUid, transferUid);
    }
}
