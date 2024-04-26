import React from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Input } from "@/components/ui/input";
import { toast } from "sonner";
import { createSavingGoal } from "@/lib/api-functions";
import { Button } from "@/components/ui/button";

interface SavingsGoalModalProps {
  accountUid: string;
  setUpdatedSavingGoal: (updatedSavingGoal: boolean) => void;
}

const SavingsGoalModal = ({
  accountUid,
  setUpdatedSavingGoal,
}: SavingsGoalModalProps) => {
  const [savingsGoalName, setSavingsGoalName] = React.useState<string>("");
  const [savingsGoalTarget, setSavingsGoalTarget] = React.useState<number>(0);

  const handleSavingsGoalNameChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSavingsGoalName(event.target.value);
  };

  const handleSavingsGoalTargetChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSavingsGoalTarget(Number(event.target.value));
  };

  const handleSubmit = async (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();

    const createSavingsGoalRequest: CreateSavingsGoalType = {
      name: savingsGoalName,
      currency: "GBP",
      target: {
        currency: "GBP",
        minorUnits: savingsGoalTarget * 100,
      },
      base64EncodedPhoto: "",
    };
    try {
      await createSavingGoal(accountUid, createSavingsGoalRequest).then(
        (data) => {
          setUpdatedSavingGoal(true);
          toast.success("Savings goal created successfully");
        }
      );
    } catch (error) {
      console.error("Error creating savings goal: ", error);
      toast.error("Error creating savings goal");
    }
  };
  return (
    <>
      <AlertDialog>
        <AlertDialogTrigger
          className={
            "bg-violet-700 text-white disabled:bg-violet-700 p-2 rounded-lg font-bold"
          }
        >
          Create Savings Goal
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Create a new savings goal</AlertDialogTitle>
            <form onSubmit={handleSubmit} id={"new-saving-goal-form"}>
              <label htmlFor="savingsGoalName">Name</label>
              <Input
                type="text"
                name="savingsGoalName"
                id="savingsGoalName"
                onChange={handleSavingsGoalNameChange}
                required={true}
                className={"bg-zinc-200 mb-5"}
              />
              <label htmlFor="savingsGoalTarget">Target</label>
              <Input
                type="number"
                name="savingsGoalTarget"
                id="savingsGoalTarget"
                step="0.01"
                onChange={handleSavingsGoalTargetChange}
                required={true}
                className={"bg-zinc-200"}
              />
            </form>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancel</AlertDialogCancel>
            <AlertDialogAction type={"submit"} form={"new-saving-goal-form"}>
              Create
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};
export default SavingsGoalModal;
