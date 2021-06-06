import React from "react";
import { IonMenu } from "@ionic/react";

import Header from "../Header/Header";
import MenuList from "./MenuList";

const Menu = ({ disabled }) => (
	<IonMenu content-id="sw-page-content" disabled={disabled} side="start">
		<Header title="Menu" />
		<MenuList />
	</IonMenu>
);

export default Menu;