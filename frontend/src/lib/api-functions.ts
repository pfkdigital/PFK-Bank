const baseUrl = "http://localhost:8080/api/v1";

export const getAccounts = async () => {
  try {
    const response = await fetch(`${baseUrl}/accounts`);

    return await response.json();
  } catch (error) {
    console.error("Error fetching accounts: ", error);
  }
};

export const getAccountBalance = async (accountUid: string) => {
  try {
    const response = await fetch(`${baseUrl}/accounts/${accountUid}/balance`);
    return await response.json();
  } catch (error) {
    console.error("Error fetching account balance: ", error);
  }
};

export const getTransactions = async (
  accountUid: string,
  minTransactionTimestamp: string,
  maxTransactionTimestamp: string
) => {
  try {
    const response = await fetch(
      `${baseUrl}/transactions/account/${accountUid}/time-between?minTransactionTimestamp=${minTransactionTimestamp}&maxTransactionTimestamp=${maxTransactionTimestamp}`
    );
    return await response.json();
  } catch (error) {
    console.error("Error fetching transactions: ", error);
  }
};

export const getSavingGoals = async (accountUid: string) => {
  try {
    const response = await fetch(
      `${baseUrl}/savings-goals/account/${accountUid}`
    );
    return await response.json();
  } catch (error) {
    console.error("Error fetching saving goals: ", error);
  }
};

export const roundUpTransaction = async (
  roundUpRequest: RoundUpRequestType
) => {
  try {
    const response = await fetch(`${baseUrl}/roundup`, {
      method: "PUT",
      body: JSON.stringify(roundUpRequest),
      headers: { "Content-Type": "application/json" },
    });
    return await response.json();
  } catch (error) {
    console.error("Error rounding up transaction: ", error);
  }
};
