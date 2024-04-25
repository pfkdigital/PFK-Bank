import AccountContainer from "@/components/AccountContainer";

interface HomeProps {
  accounts: Accounts;
}

export default async function Home() {
  return (
    <main className="flex min-h- flex-col items-start justify-between p-2">
      <AccountContainer />
    </main>
  );
}
