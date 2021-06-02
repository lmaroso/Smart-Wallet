import React from "react";
import { IonLabel, IonItem, IonNote, IonItemGroup } from "@ionic/react";
import moment from "moment";

import { PROGRAMMED } from "./constants";

const HistoryItem = ({ movement }) => {
	const formattedMovement = [
		["Nombre", movement.name],
		["Descripción", movement.description],
		["Monto", `$${movement.amount}`],
		["Fecha", moment(movement.date,"YYYY-MM-DD[T]HH:mm:ss").format("DD-MM-YYYY HH:mm:ss")],
		["Es programado", PROGRAMMED[movement.programmed.toString()]]
	];

	return (
		<IonItemGroup>
			{formattedMovement.map((movement, index) => (
				<IonItem key={index}>
					<IonLabel>{movement[0]}</IonLabel>
					<IonNote slot="end">{movement[1]}</IonNote>
				</IonItem>
			))}
		</IonItemGroup>
	);
};

export default HistoryItem;