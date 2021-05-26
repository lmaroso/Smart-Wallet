import React from "react";
import { IonLoading } from "@ionic/react";

const Loading = ({ isOpen, onDidDismiss }) => (
	<IonLoading
		isOpen={isOpen}
		message="Cargando..."
		onDidDismiss={onDidDismiss}
	/>
);

export default Loading;