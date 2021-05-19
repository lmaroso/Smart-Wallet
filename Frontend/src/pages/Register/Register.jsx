import React, { useState } from "react";

import RegisterView from "./RegisterView";

import { register } from "../../services/api";

const Register = ({ history }) => {

	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	const onSubmit = (event) => {
		event.preventDefault();
		register({ name, email, password })
			.then(({ status, data }) => {
				if (status === 200) {
					setToastType("success");
					setToastText("Se registró exitosamente. Por favor verificá tu correo para activar el usuario");
					setTimeout(() => {
						history.push({ pathname: "/login" });
					}, 4000);
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
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			onSubmit={onSubmit}
		/>
	);
};

export default Register;
