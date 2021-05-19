import React, { useState } from "react";

// import LoginView from "./LoginView";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Toast from "../../components/Toast/Toast";

import { login } from "../../services/api";
import { setKey } from "../../utils/localStorage";

const Register = ({ history }) => {

	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	const onSubmit = (event) => {
		event.preventDefault();
		login({ username, password })
			.then(({ status, data, headers }) => {
				if (status === 200) {
					setToastType("success");
					setToastText("Login exitoso! Ingresando...");
					setKey("token", headers.authorization);
					setTimeout(() => {
						history.push({ pathname: "/dashboard" });
					}, 4000);
				} else if (status === 403) {
					setToastType("error");
					setToastText("La combinacion de usuario y contraseña ingresada no existe");
				} else {
					setToastType("error");
					setToastText(data);
				}
				setShouldShowToast(true);
			});
	};
	return (
		<PageWrapper>
			<form onSubmit={onSubmit}>
				<Input
					required
					custom="email"
					value={username}
					onChange={event => setUsername(event.detail.value)}
				/>
				<Input
					required
					custom="password"
					value={password}
					onChange={event => setPassword(event.detail.value)}
				/>
				<Button expand="block" type="submit">
				Login
				</Button>
			</form>
			<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
		</PageWrapper>
		// <LoginView
		// 	username={username}
		// 	password={password}
		// 	setUsername={setUsername}
		// 	setPassword={setPassword}
		// 	setShouldShowToast={setShouldShowToast}
		// 	shouldShowToast={shouldShowToast}
		// 	toastText={toastText}
		// 	toastType={toastType}
		// 	onSubmit={onSubmit}
		// />
	);
};

export default Register;
