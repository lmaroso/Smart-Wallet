import { IonHeader, IonToolbar, IonTitle, IonButtons, IonMenuButton } from "@ionic/react";
import React from "react";

import "./Header.scss";

const Header = ({ title }) => (
	<IonHeader>
		<IonToolbar color="primary">
			<IonButtons slot="start">
				<IonMenuButton />
			</IonButtons>
			<IonTitle>{title ? title : "SmartWallet"}</IonTitle>
		</IonToolbar>
	</IonHeader>
);
export default Header;