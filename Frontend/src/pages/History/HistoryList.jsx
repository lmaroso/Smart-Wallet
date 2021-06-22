import React from "react";
import { IonLabel, IonItem, IonNote } from "@ionic/react";

import { HISTORY_COLOR } from "./constants";
import { numberToPesos } from "../../utils/utils";

const HistoryList = ({ historySelected, onSelectItem }) => {
	if (historySelected) {
		return historySelected.map((history, index) => (
			<IonItem key={index} button onClick={() => onSelectItem(history)}>
				<IonLabel>{history.name}</IonLabel>
				<IonNote color={HISTORY_COLOR[history.type]} slot="end">{numberToPesos(history.amount)}</IonNote>
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