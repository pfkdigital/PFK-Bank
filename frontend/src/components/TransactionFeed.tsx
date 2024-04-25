"use client"

import {getTransactions} from "@/lib/api-functions";
import {getTransactionTimestamps} from "@/lib/generate-time-stamps";
import React, {useEffect} from "react";

interface TransactionFeedProps {
    accountUid: string | null;
}

const TransactionFeed = ({accountUid}: TransactionFeedProps) => {
    const [transactions, setTransactions] = React.useState<FeedItem[] | null>(null);

    useEffect(() => {
        const {minTransactionTimestamp, maxTransactionTimestamp} = getTransactionTimestamps();
        if (accountUid) {
            getTransactions(accountUid, minTransactionTimestamp, maxTransactionTimestamp).then((data) => {
                setTransactions(data.feedItems);
            });
        }
    }, [accountUid]);

    const renderTransactions = () => {
        return (
            <div className={""}>
                {transactions?.map((transaction: FeedItem, index: number) => {
                    return (
                        <div key={index} className="flex justify-between p-2 border-2 mb-2.5">
                            <div>
                                <p>{transaction.source}</p>
                                <p>{transaction.direction}</p>
                            </div>
                            <div>
                                <p>{transaction.amount.minorUnits}</p>
                                <p>{transaction.amount.currency}</p>
                            </div>
                        </div>
                    )
                })}
            </div>
        )
    }

    return <>{transactions && renderTransactions()}</>
};

export default TransactionFeed;