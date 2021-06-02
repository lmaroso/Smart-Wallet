import React from "react";
import { IonButton } from "@ionic/react";

import "./Button.scss";

const Button = ({ cancel, className, children, disabled, type, onClick }) => {
	return (
		<div className="buttonContainer">
			<IonButton
				className={[className, "button", cancel && "cancel"].join(" ")}
				disabled={disabled}
				expand="block"
				type={type}
				onClick={onClick}
			>
				{children}
			</IonButton>
		</div>
	);
};

export default Button;
