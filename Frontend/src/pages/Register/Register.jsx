import React, { useState } from "react";

import RegisterView from "./RegisterView";

import { register } from "../../services/api";

const Register = () => {

	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	const onClickRegister = () => {
		register({ name, email, password })
			.then(({ status, data }) => {
				if (status === 200) {
					setToastType("success");
					setToastText("El usuario se registró exitosamente");
				} else if (status >= 400) {
					setToastType("error");
					setToastText(data);
				}
				setShouldShowToast(true);
			});
	};
	return (
		<RegisterView
			email={email}
			name={name}
			password={password}
			setEmail={setEmail}
			setName={setName}
			setPassword={setPassword}
			setShouldShowToast={setShouldShowToast}
			setToastType={setToastType}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			onClickRegister={onClickRegister}
		/>
	);
};

export default Register;
