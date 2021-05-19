import React from "react";
import { IonSelect } from "@ionic/react";

const Selector = ({ children, placeholder, value, onChange }) => {
	return (
		<>
			<IonSelect
				interface="ion-action-sheet"
				placeholder={placeholder}
				value={value}
				onIonChange={onChange}
			>
				{children}
			</IonSelect>
		</>
	);
};

export default Selector;
