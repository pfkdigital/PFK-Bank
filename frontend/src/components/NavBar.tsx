"use client";

import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuList,
} from "@/components/ui/navigation-menu";
import Image from "next/image";

const NavBar = () => {
  return (
    <NavigationMenu>
      <NavigationMenuList>
        <NavigationMenuItem>
          <Image
            src={"/logo.png"}
            alt={"starling-bank-icon"}
            height={50}
            width={50}
          />
        </NavigationMenuItem>
        <NavigationMenuItem>
          <h1 className={"pl-2 text-2xl text-white font-bold"}>
            Starling Bank Tech Test
          </h1>
        </NavigationMenuItem>
      </NavigationMenuList>
    </NavigationMenu>
  );
};

export default NavBar;
