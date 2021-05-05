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
		register({name, email, password })
			.then(() => {
				// eslint-disable-next-line no-console
				// console.log(response);
				// if (response.status === 200) {
				setToastType("success");
				setToastText("El usuario se registró exitosamente");
				// } else if (response.status >= 400) {
				// 	setToastType("error");
				// 	setToastText(response.message);
				// }
				setShouldShowToast(true);
			});
		// eslint-disable-next-line no-console
		// .catch((error) => console.log(error));
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
