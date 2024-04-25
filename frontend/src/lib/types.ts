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

interface FeedItems {
  feedItems: FeedItem[];
}

interface FeedItem {
  feedItemUid: string;
  categoryUid: string;
  amount: CurrencyAndAmount;
  sourceAmount: CurrencyAndAmount;
  direction: Direction;
  updatedAt: Date;
  transactionTime: Date;
  settlementTime: Date;
  retryAllocationUntilTime: Date;
  source: string;
  sourceSubType: string;
  status: string;
  transactingApplicationUserUid: string;
  counterPartyType: string;
  counterPartyUid: string;
  counterPartyName: string;
  counterPartySubEntityUid: string;
  counterPartySubEntityName: string;
  counterPartySubEntityIdentifier: string;
  counterPartySubEntitySubIdentifier: string;
  exchangeRate: number;
  totalFees: number;
  totalFeeAmount: CurrencyAndAmount;
  reference: string;
  country: string;
  spendingCategory: string;
  userNote: string;
  roundUp: RoundUp;
  hasAttachment: boolean;
  hasReceipt: boolean;
  batchPaymentDetails: BatchPaymentDetails;
}

interface CurrencyAndAmount {
  currency: string;
  minorUnits: number;
}

enum Direction {
  INCOMING,
  OUTGOING,
}

interface RoundUp {
  goalCategoryId: string;
  amount: CurrencyAndAmount;
}

interface BatchPaymentDetails {
  batchPaymentUid: string;
  batchPaymentType: string;
}

interface AccountBalance {
  clearedBalance: CurrencyAndAmount;
  effectiveBalance: CurrencyAndAmount;
  pendingTransactions: CurrencyAndAmount;
  acceptedOverdraft: CurrencyAndAmount;
  amount: CurrencyAndAmount;
  totalClearedBalance: CurrencyAndAmount;
  totalEffectiveBalance: CurrencyAndAmount;
}

interface SavingGoal {
  savingsGoalUid: string;
  name: string;
  target: CurrencyAndAmount;
  totalSaved: CurrencyAndAmount;
  savedPercentage: number;
  state: string;
}

interface SavingGoals {
  savingGoals: SavingGoal[];
}

interface CreateSavingsGoalType {
  name: string;
  currency: string;
  target: CurrencyAndAmount;
  base64EncodedPhoto: string;
}

interface RoundUpRequestType {
  savingsGoalUid: string;
  accountUid: string;
  minTransactionTimestamp: string;
  maxTransactionTimestamp: string;
}

interface TransferResponseV2 {
  transferUid: string;
  success: boolean;
}

interface RoundUpResponseType {
  roundUpMessage: string;
  transferResponseV2: TransferResponseV2;
}
