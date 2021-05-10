import React from "react";
import { IonItem, IonLabel, IonInput } from "@ionic/react";

// import "./Input.scss";
import { INPUT_FIELDS } from "./constants";

const Input = ({ inputmode, label, onChange, placeholder, required, type, value }) => {
	return (
		<IonItem>
			<IonLabel position="floating">{type ? INPUT_FIELDS[type].label : label}</IonLabel>
			<IonInput
				inputmode={type ? INPUT_FIELDS[type].inputmode : inputmode}
				placeholder={type ? INPUT_FIELDS[type].placeholder : placeholder}
				required={required}
				type={type && INPUT_FIELDS[type].type}
				value={value}
				onIonChange={onChange}
				// className={[className, "button", cancel && "cancel"].join(" ")}
			/>
		</IonItem>
	);
};

export default Input;
