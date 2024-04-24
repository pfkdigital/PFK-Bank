import {toast} from "@/components/ui/use-toast";

const baseUrl = 'http://localhost:8080/api/v1';

export const getAccounts = async () => {
    try {
        const response = await fetch(`${baseUrl}/accounts`);
        return await response.json();
    } catch (error) {
        console.error('Error fetching accounts: ', error);
        toast('Error fetching accounts')
    }
}