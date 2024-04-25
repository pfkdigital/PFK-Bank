"use client";

import React, { useEffect } from "react";
import { getSavingGoals } from "@/lib/api-functions";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import SavingsGoalDetails from "@/components/SavingsGoalDetails";
import RoundUpModal from "@/components/RoundUpModal";

interface SavingGoalProps {
  accountUid: string;
  savingGoalUid: string;
  setSelectedSavingGoalUid: (savingGoalUid: string) => void;
  selectedDate: Date;
}

const SavingGoal = ({
  accountUid,
  setSelectedSavingGoalUid,
  savingGoalUid,
  selectedDate,
}: SavingGoalProps) => {
  const [savingGoals, setSavingGoals] = React.useState<SavingGoal[]>();
  const [selectedSavingGoal, setSelectedSavingGoal] =
    React.useState<SavingGoal>();
  const [updatedSavingGoal, setUpdatedSavingGoal] = React.useState<boolean>(false);
  useEffect(() => {
    if (accountUid) {
      getSavingGoals(accountUid).then((data) => {
        setSavingGoals(data.savingsGoalList);
      });
    }
  }, [accountUid]);

  useEffect(() => {
    if (savingGoalUid) {
      const selectedSavingGoal = savingGoals?.find(
        (savingGoal: SavingGoal) => savingGoal.savingsGoalUid === savingGoalUid
      );
      setSelectedSavingGoal(selectedSavingGoal);
    }
  }, [savingGoalUid, savingGoals]);

  useEffect(() => {
    if(updatedSavingGoal) {
      getSavingGoals(accountUid).then((data) => {
        setSavingGoals(data.savingsGoalList);
      });
    }
  }, [updatedSavingGoal]);

  return (
    <div className={"w-full h-auto my-10 flex justify-between items-center"}>
      <div className={"flex"}>
        <Select
          value={savingGoalUid}
          onValueChange={(value) => setSelectedSavingGoalUid(value)}
        >
          <SelectTrigger className="w-[180px]">
            <SelectValue placeholder="Select a Saving Goal" />
          </SelectTrigger>
          <SelectContent>
            {savingGoals?.map((savingGoal: SavingGoal, index: number) => (
              <SelectItem key={index} value={savingGoal.savingsGoalUid}>
                {savingGoal.name}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
        {savingGoalUid && (
          <RoundUpModal
            accountUid={accountUid}
            savingsGoalUid={savingGoalUid}
            selectedDate={selectedDate}
            setUpdatedSavingGoal={setUpdatedSavingGoal}
          />
        )}
      </div>
      {selectedSavingGoal && (
        <SavingsGoalDetails savingsGoal={selectedSavingGoal} />
      )}
    </div>
  );
};

export default SavingGoal;
