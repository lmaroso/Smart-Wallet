import React from "react";
import { IonItem, IonIcon, IonLabel } from "@ionic/react";

const MenuItem = ({ icon, name, route }) =>  (
	<IonItem button routerLink={route}>
		<IonIcon color="tertiary" icon={icon} size="large" slot="start" />
		<IonLabel>{name}</IonLabel>
	</IonItem>
);

export default MenuItem;