import React, { useState, useEffect } from "react";
import { IonLabel, IonItem, IonNote, IonItemGroup } from "@ionic/react";
import moment from "moment";

import { PROGRAMMED } from "./constants";
import { getDayOfWeek } from "../../utils/utils";

const HistoryItem = ({ movement }) => {
	const [selectedMovement, setSelectedMovement] = useState(null);

	useEffect(() => {
		let formattedMovement = [
			["Nombre", movement.name],
			["Descripción", movement.description],
			["Monto", `$${movement.amount}`],
			["Fecha", moment(movement.date,"YYYY-MM-DD[T]HH:mm:ss").format("DD-MM-YYYY HH:mm:ss")],
			["Es programado", PROGRAMMED[movement.programmed.toString()]]
		];
		if (movement.programmed) {
			if (movement.repetitionMilliSeconds) formattedMovement.push(["Se repite cada", `${movement.repetitionMilliSeconds / 1000} segundos`]);
			if (movement.dayOfWeek) formattedMovement.push(["Se repite cada semana el", `${getDayOfWeek(movement.dayOfWeek)}`]);
			if (movement.dayOfMonth) formattedMovement.push(["Se repite cada mes el día", `${movement.dayOfMonth}`]);
		}
		setSelectedMovement(formattedMovement);
	// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<IonItemGroup>
			{selectedMovement && selectedMovement.map((movement, index) => (
				<IonItem key={index}>
					<IonLabel>{movement[0]}</IonLabel>
					<IonNote slot="end">{movement[1]}</IonNote>
				</IonItem>
			))}
		</IonItemGroup>
	);
};

export default HistoryItem;