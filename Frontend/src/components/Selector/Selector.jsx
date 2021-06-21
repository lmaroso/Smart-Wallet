import React from "react";
import { IonSelect, IonItem, IonLabel } from "@ionic/react";

import "./Selector.scss";

const Selector = ({ disabled, children, label, placeholder, value, onChange }) => {
	return (
		<IonItem className="SelectorContainer">
			{label && <IonLabel>{label}</IonLabel>}
			<IonSelect
				disabled={disabled}
				interface="ion-action-sheet"
				placeholder={placeholder}
				value={value}
				onIonChange={onChange}
			>
				{children}
			</IonSelect>
		</IonItem>
	);
};

export default Selector;
