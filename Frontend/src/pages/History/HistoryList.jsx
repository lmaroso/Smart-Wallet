import React from "react";
import { IonLabel, IonItem, IonNote } from "@ionic/react";

const HistoryList = ({ historySelected, historyColor, onSelectItem }) => {
	if (historySelected) {
		return historySelected.map((history, index) => (
			<IonItem key={index} button onClick={() => onSelectItem(history)}>
				<IonLabel>{history.name}</IonLabel>
				<IonNote color={historyColor} slot="end">{`$${history.amount}`}</IonNote>
			</IonItem>
		));
	} else {
		return (
			<IonItem>
				<IonNote>No se registran movimientos</IonNote>
			</IonItem>
		);
	}
};

export default HistoryList;