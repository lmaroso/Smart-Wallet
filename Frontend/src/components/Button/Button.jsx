import React from "react";
import { IonButton } from "@ionic/react";

import "./Button.scss";

const Button = ({ /*cancel, className, */children, disabled, expand, type, onClick }) => {
	return (
		<>
			<IonButton
				// className={[className, "button", cancel && "cancel"].join(" ")}
				disabled={disabled}
				expand={expand}
				size="big"
				type={type}
				onClick={onClick}
			>
				{children}
			</IonButton>
		</>
	);
};

export default Button;
