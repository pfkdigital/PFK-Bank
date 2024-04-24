"use client"

import {NavigationMenu, NavigationMenuItem, NavigationMenuList} from "@/components/ui/navigation-menu";
import Image from "next/image";

const Footer = () => {
    return (
        <footer className={"fixed bottom-0 left-0 w-screen h-20 flex justify-center items-center bg-blue-950"}>
            <p className={"text-xl text-white font-bold"}>Tech Test by Nuh Ali - Thank you for your time </p>
        </footer>
    );
};

export default Footer;