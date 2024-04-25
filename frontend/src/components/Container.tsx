"use client"

import React from 'react';
import ControlPanel from "@/components/ControlPanel";
import TransactionFeed from "@/components/TransactionFeed";

const Container = () => {
    const [selectedAccountUid, setSelectedAccountUid] = React.useState<string | null>(null);
    return (
        <>
            <ControlPanel  setSelectedAccountUid={setSelectedAccountUid}/>
            <TransactionFeed accountUid={selectedAccountUid} />   
        </>
    );
};

export default Container;