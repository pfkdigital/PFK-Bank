import React from 'react';
import ProgressWheel from "@/components/ProgressWheel";
import {currencyFormat} from "@/lib/currency-format";

interface SavingsGoalDetailsProps {
    savingsGoal: SavingGoal
}

const SavingsGoalDetails = ({savingsGoal}: SavingsGoalDetailsProps) => {
    return (
        <div className={"flex items-center ml-5"}>
            <ProgressWheel progress={savingsGoal.savedPercentage}/>
            <div className={"ml-5"}>
                <p className={"text-purple-800 font-bold mb-2.5"}>Target: {currencyFormat(savingsGoal.target.currency,savingsGoal.target.minorUnits)}</p>
                <p className={"text-purple-800 font-bold"}>Amount saved: {currencyFormat(savingsGoal.totalSaved.currency,savingsGoal.totalSaved.minorUnits)}</p>
            </div>
        </div>
    );
};

export default SavingsGoalDetails;