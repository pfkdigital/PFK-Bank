"use client";

import React from "react";
import AccountControl from "@/components/AccountControl";
import TransactionFeed from "@/components/TransactionFeed";
import SavingGoal from "@/components/SavingGoal";

const AccountContainer = () => {
  const [selectedAccountUid, setSelectedAccountUid] =
    React.useState<string>("");
  const [selectedSavingGoalUid, setSelectedSavingGoalUid] =
    React.useState<string>("");
  const [selectedDate, setSelectedDate] = React.useState<Date>(() => {
    const endDate = new Date();
    endDate.setHours(23, 59, 59, 999);
    return endDate;
  });
  return (
    <div className={"w-full min-h-screen"}>
      <AccountControl
        selectedAccountUid={selectedAccountUid}
        setSelectedAccountUid={setSelectedAccountUid}
      />
      {selectedAccountUid && (
        <SavingGoal
          accountUid={selectedAccountUid}
          savingGoalUid={selectedSavingGoalUid}
          setSelectedSavingGoalUid={setSelectedSavingGoalUid}
          selectedDate={selectedDate}
        />
      )}
      {selectedAccountUid && (
        <TransactionFeed
          accountUid={selectedAccountUid}
          selectedDate={selectedDate}
          setSelectedDate={setSelectedDate}
        />
      )}
    </div>
  );
};

export default AccountContainer;
