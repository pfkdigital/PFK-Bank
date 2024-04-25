package org.techtest.starling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtest.starling.controller.api.SavingsGoalApi;
import org.techtest.starling.model.SavingGoalsV2;
import org.techtest.starling.model.TopUpRequestV2;
import org.techtest.starling.service.SavingsGoalService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/savings-goals")
@RequiredArgsConstructor
public class SavingsGoalController implements SavingsGoalApi {
    private final SavingsGoalService savingsGoalService;

    @GetMapping("/account/{accountUid}")
    public ResponseEntity<SavingGoalsV2> getSavingsGoals(@PathVariable UUID accountUid) {
        return ResponseEntity.ok(savingsGoalService.getSavingsGoals(accountUid));
    }

    @PutMapping("/account/{accountUid}/savings-goal/{savingsGoalUid}")
    public ResponseEntity<?> transferMoneyToSavingsGoal(@PathVariable UUID accountUid, @PathVariable UUID savingsGoalUid, @RequestBody TopUpRequestV2 topUpRequestV2) {
        return ResponseEntity.ok(savingsGoalService.transferMoneyToSavingsGoal(topUpRequestV2, accountUid, savingsGoalUid));
    }
}
