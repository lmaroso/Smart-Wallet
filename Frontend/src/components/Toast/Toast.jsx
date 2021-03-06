import React from "react";
import { IonToast } from "@ionic/react";

import "./Toast.scss";

import { TOAST_TYPE } from "./constants";

const Toast = ({ id, isOpen, message, onDidDismiss, type }) => (
	<IonToast
		buttons={TOAST_TYPE[type].buttons}
		cssClass={TOAST_TYPE[type].cssClass}
		duration={3000}
		id={id}
		isOpen={isOpen}
		message={message}
		onDidDismiss={onDidDismiss}
	/>
);

export default Toast;
