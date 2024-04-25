"use client";

import { getTransactions } from "@/lib/api-functions";
import React, { useEffect } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { currencyFormat } from "@/lib/currency-format";
import { formatIsoDateString } from "@/lib/date-format";
import { DatePicker } from "@/components/DatePicker";
import { getSelectedWeekTimestamp } from "@/lib/get-selected-week-timestamp";
import NoTransactionsFound from "@/components/NoTransactionsFound";

interface TransactionFeedProps {
  accountUid: string | null;
  selectedDate: Date;
  setSelectedDate: React.Dispatch<React.SetStateAction<Date>>;
}

const TransactionFeed = ({
  accountUid,
  selectedDate,
  setSelectedDate,
}: TransactionFeedProps) => {
  const [transactions, setTransactions] = React.useState<FeedItem[]>([]);

  useEffect(() => {
    if (accountUid && selectedDate) {
      const { minTransactionTimestamp, maxTransactionTimestamp } =
        getSelectedWeekTimestamp(selectedDate);
      getTransactions(
        accountUid,
        minTransactionTimestamp,
        maxTransactionTimestamp
      ).then((data) => {
        setTransactions(data.feedItems);
      });
    }
  }, [accountUid, selectedDate]);

  const renderTransactions = () => {
    return (
      <>
        <Table className={"w-full"}>
          <TableCaption>A list of your recent transactions.</TableCaption>
          <TableHeader>
            <TableRow className={"bg-violet-700 hover:bg-violet-500"}>
              <TableHead className="w-[100px]">Name</TableHead>
              <TableHead>Direction</TableHead>
              <TableHead>Date</TableHead>
              <TableHead>Status</TableHead>
              <TableHead className="text-right">Amount</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {transactions?.map((transaction: FeedItem, index: number) => {
              return (
                <TableRow key={index}>
                  <TableCell>{transaction.source}</TableCell>
                  <TableCell>{transaction.direction}</TableCell>
                  <TableCell>
                    {formatIsoDateString(
                      transaction.transactionTime.toLocaleString()
                    )}
                  </TableCell>
                  <TableCell>{transaction.status}</TableCell>
                  <TableCell className="text-right">
                    {currencyFormat(
                      transaction.amount.currency,
                      transaction.amount.minorUnits
                    )}
                  </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </>
    );
  };
  return (
    <>
      <div className={"w-full flex justify-end mb-2.5"}>
        <DatePicker
          selectedDate={selectedDate}
          setSelectedDate={setSelectedDate}
        />
      </div>
      {transactions && transactions.length ? (
        renderTransactions()
      ) : (
        <NoTransactionsFound />
      )}
    </>
  );
};

export default TransactionFeed;
