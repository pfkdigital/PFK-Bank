import Image from "next/image";
import {getAccounts} from "@/lib/api-functions";

interface HomeProps {
    accounts: Account[];
}

const accounts = await getAccounts();

export default function Home({accounts}: HomeProps) {
    return (
        <main className="flex min-h- flex-col items-center justify-between p-2 ">
            <h1 className="text-4xl font-bold">Starling Bank Tech Test</h1>
            <div className="flex flex-col items-center justify-center">
                <Image src="/logo.png" alt="starling-bank-icon" height={100} width={100}/>
                <h2 className="text-2xl font-bold">Accounts</h2>
                <ul className="flex flex-col items-center justify-center">
                    {accounts?.map((account) => (
                        <li key={account.accountUid} className="flex flex-col items-center justify-center">
                            <p className="text-xl font-bold">{account.name}</p>
                            <p className="text-lg">{account.accountType}</p>
                            <p className="text-lg">{account.currency}</p>
                        </li>
                    ))}
                </ul>
            </div>
        </main>
    );
}
