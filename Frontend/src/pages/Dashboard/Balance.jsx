import React from "react";
import { IonCard, IonCardHeader, IonCardTitle, IonCardSubtitle } from "@ionic/react";

import { numberToPesos } from "../../utils/utils";

const Balance = ({ balance }) => (
	<IonCard color="tertiary">
		<IonCardHeader>
			<IonCardSubtitle>Dinero actual</IonCardSubtitle>
			<IonCardTitle className="ion-text-end">
				<h1>    
					{numberToPesos(balance)}
				</h1>
			</IonCardTitle>
		</IonCardHeader>
	</IonCard>
);

export default Balance;
