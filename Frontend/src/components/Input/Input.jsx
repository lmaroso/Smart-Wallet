import React from "react";
import { IonItem, IonLabel, IonInput } from "@ionic/react";

import "./Input.scss";
import { INPUT_FIELDS } from "./constants";

const Input = ({ disabled, inputmode, label, onChange, placeholder, required, type, custom, value }) => {
	return (
		<IonItem className="inputContainer">
			<IonLabel position="floating">{custom ? INPUT_FIELDS[custom].label : label}</IonLabel>
			<IonInput
				disabled={disabled}
				inputmode={custom ? INPUT_FIELDS[custom].inputmode : inputmode}
				placeholder={custom ? INPUT_FIELDS[custom].placeholder : placeholder}
				required={required}
				type={custom ? INPUT_FIELDS[custom].type : type}
				value={value}
				onIonChange={onChange}
			/>
		</IonItem>
	);
};

export default Input;
