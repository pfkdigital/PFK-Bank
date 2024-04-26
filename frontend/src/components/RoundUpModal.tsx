import React from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import {toast} from "sonner";
import { roundUpTransaction } from "@/lib/api-functions";
import { getSelectedWeekTimestamp } from "@/lib/get-selected-week-timestamp";


interface RoundUpModalProps {
  accountUid: string;
  savingsGoalUid: string;
  selectedDate: Date;
  setUpdatedSavingGoal: (updatedSavingGoal: boolean) => void;
}

const RoundUpModal = ({
  accountUid,
  savingsGoalUid,
  selectedDate,
    setUpdatedSavingGoal
}: RoundUpModalProps) => {
  const handleRoundUp = () => {
    if (!selectedDate) return;
    setUpdatedSavingGoal(false);
    const { minTransactionTimestamp, maxTransactionTimestamp } =
      getSelectedWeekTimestamp(selectedDate);
    const roundUpRequest: RoundUpRequestType = {
      accountUid,
      savingsGoalUid,
      minTransactionTimestamp,
      maxTransactionTimestamp,
    };
    try {
      roundUpTransaction(roundUpRequest).then((data) => {
        setUpdatedSavingGoal(true);
        toast.success(data.roundUpMessage);
      });
    } catch (error) {
      console.error("Error rounding up transaction: ", error);
      toast.error("Error rounding up transaction");
    }
  };
  return (
    <AlertDialog>
      <AlertDialogTrigger className={"ml-5 bg-violet-700 text-white disabled:bg-violet-700 p-2 rounded-md font-bold"}>
        Round Up
      </AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>Would you like to round up ?</AlertDialogTitle>
          <AlertDialogDescription>
            This will send the total value of all your round ups to your savings goal.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancel</AlertDialogCancel>
          <AlertDialogAction onClick={handleRoundUp}>
            Round up
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
};

export default RoundUpModal;
