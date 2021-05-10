import { IonHeader, IonToolbar, IonTitle } from "@ionic/react";
import React from "react";

import "./Header.scss";

const Header = () => (
	<div>
		<IonHeader>
			<IonToolbar className="header">
				<IonTitle>SmartWallet</IonTitle>
			</IonToolbar>
		</IonHeader>
	</div>
);
export default Header;