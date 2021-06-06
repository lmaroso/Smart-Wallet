import React from "react";
import { IonList, IonContent } from "@ionic/react";

import MenuItem from "./MenuItem";
import { MENU_ITEMS } from "./constants";

const renderItems = () => MENU_ITEMS.map(({ icon, menuName, route, onClick }, index) => 
	<MenuItem key={index} icon={icon} name={menuName} route={route} onClick={onClick} />
);

const MenuList = () => (
	<IonContent>
		<IonList>
			{renderItems()}
		</IonList>
	</IonContent>
);

export default MenuList;