import React from "react";
import { IonItem, IonIcon, IonLabel } from "@ionic/react";

const MenuItem = ({ icon, name, route, onClick }) =>  (
	<IonItem button routerLink={route} onClick={onClick}>
		<IonIcon color="tertiary" icon={icon} size="large" slot="start" />
		<IonLabel>{name}</IonLabel>
	</IonItem>
);

export default MenuItem;