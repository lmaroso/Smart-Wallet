import { IonHeader, IonToolbar, IonTitle } from "@ionic/react";
import React from "react";

import "./Header.scss";

const Header = () => (
	<div>
		<IonHeader>
			<IonToolbar color="primary">
				<IonTitle>SmartWallet</IonTitle>
			</IonToolbar>
		</IonHeader>
	</div>
);
export default Header;