import React from "react";
import { IonList, IonContent } from "@ionic/react";

import MenuItem from "./MenuItem";
import { MENU_ITEMS } from "./constants";

const renderItems = () => MENU_ITEMS.map(({ icon, menuName, route }, index) => 
	<MenuItem key={index} icon={icon} name={menuName} route={route} />
);

const MenuList = () => (
	<IonContent>
		<IonList>
			{renderItems()}
		</IonList>
	</IonContent>
);

export default MenuList;