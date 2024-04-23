package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoalsV2 {
    private List<SavingGoalV2> savingsGoalList;
}
