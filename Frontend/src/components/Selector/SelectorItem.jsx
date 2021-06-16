import React from "react";
import { IonSelectOption } from "@ionic/react";

import "./SelectorItem.scss";

const SelectorItem = ({ disabled, value, name }) => {
	return (
		<IonSelectOption disabled={disabled} value={value}>{name}</IonSelectOption>
	);
};

export default SelectorItem;
