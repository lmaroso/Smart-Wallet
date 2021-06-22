import React from "react";
import { IonAlert } from "@ionic/react";

const Alert = ({ isOpen, message, onAccept, onDismiss }) => {

	const BUTTONS = [
		{
			text: "cancelar",
			role: "cancel",
			handler: onDismiss
		},
		{
			text: "OK",
			handler: onAccept
		}
	];
    
	return (
		<IonAlert
			buttons={BUTTONS}
			header="Alerta"
			isOpen={isOpen}
			message={message}
			onDidDismiss={onDismiss}
		/>
	);
};

export default Alert;