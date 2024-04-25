export const currencyFormat = (currency: string, amount: number) => {
  const balance = new Intl.NumberFormat("en-GB", {
    style: "currency",
    currency,
  });

  return balance.format(amount / 100);
};
