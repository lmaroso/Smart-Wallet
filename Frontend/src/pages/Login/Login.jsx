import React, { useState } from "react";

// import LoginView from "./LoginView";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Toast from "../../components/Toast/Toast";

import { login } from "../../services/api";

const Register = () => {

	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	const onClickRegister = () => {
		login({ username, password })
			.then((response) => {
				//eslint-disable-next-line no-console
				console.log(response);
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
		<PageWrapper>
			<Input
				required={true}
				type="email"
				value={username}
				onChange={event => setUsername(event.detail.value)}
			/>
			<Input
				required={true}
				type="password"
				value={password}
				onChange={event => setPassword(event.detail.value)}
			/>
			<Button expand="block" onClick={() => onClickRegister()}>
            Login
			</Button>
			<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
		</PageWrapper>
		// <LoginView
		// 	username={username}
		// 	password={password}
		// 	setUsername={setUsername}
		// 	setPassword={setPassword}
		// 	setShouldShowToast={setShouldShowToast}
		// 	setToastType={setToastType}
		// 	shouldShowToast={shouldShowToast}
		// 	toastText={toastText}
		// 	toastType={toastType}
		// 	onClickRegister={onClickRegister}
		// />
	);
};

export default Register;