interface Account {
    accountUid: string;
    accountType: string;
    defaultCategory: string;
    currency: string;
    createdAt: string;
    name: string;
}

interface Accounts {
    accounts: Account[];
}