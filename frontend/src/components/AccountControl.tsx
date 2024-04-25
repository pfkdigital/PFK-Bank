"use client";

import React, { useEffect } from "react";
import { getAccountBalance, getAccounts } from "@/lib/api-functions";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { currencyFormat } from "@/lib/currency-format";

interface ControlPanelProps {
  selectedAccountUid: string;
  setSelectedAccountUid: (accountUid: string) => void;
}

const AccountControl = ({
  selectedAccountUid,
  setSelectedAccountUid,
}: ControlPanelProps) => {
  const [accounts, setAccounts] = React.useState<Account[] | null>(null);
  const [balance, setBalance] = React.useState<AccountBalance | null>(null);
  useEffect(() => {
    getAccounts().then((data) => {
      setAccounts(data.accounts);
    });
  }, []);

  useEffect(() => {
    if (selectedAccountUid) {
      getAccountBalance(selectedAccountUid).then((data) => {
        setBalance(data);
      });
    }
  }, [selectedAccountUid]);

  const renderSelect = () => {
    return (
      <Select
        value={selectedAccountUid}
        onValueChange={(value) => setSelectedAccountUid(value)}
      >
        <SelectTrigger className="w-[180px]">
          <SelectValue placeholder="Select an account" />
        </SelectTrigger>
        <SelectContent>
          {accounts?.map((account: Account, index: number) => (
            <SelectItem key={account.accountUid} value={account.accountUid}>
              {account.name}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    );
  };

  const renderAccountBalance = () => {
    if (balance) {
      return (
        <Button variant={"secondary"}>
          Account Balance:{" "}
          {currencyFormat(
            balance.totalClearedBalance.currency,
            balance.totalClearedBalance.minorUnits
          )}
        </Button>
      );
    }
    return null;
  };

  return (
    <form className={"flex justify-start w-full my-4"}>
      <div className={"flex justify-between w-full"}>
        {accounts && renderSelect()}
        {balance && renderAccountBalance()}
      </div>
    </form>
  );
};

export default AccountControl;
