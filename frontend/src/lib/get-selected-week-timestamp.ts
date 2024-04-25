export function getSelectedWeekTimestamp(date: Date) {
  const sevenDaysAgo = new Date(date.getTime() - 7 * 24 * 60 * 60 * 1000);

  sevenDaysAgo.setHours(23, 59, 59, 1000);

  return {
    minTransactionTimestamp: sevenDaysAgo.toISOString(),
    maxTransactionTimestamp: date.toISOString(),
  };
}
