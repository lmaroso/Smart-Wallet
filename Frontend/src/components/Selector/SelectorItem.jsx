import React from "react";
import { IonSelectOption } from "@ionic/react";

const SelectorItem = ({ disabled, value, name }) => {
	return (
		<IonSelectOption disabled={disabled} value={value}>{name}</IonSelectOption>
	);
};

export default SelectorItem;
