import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";

import LoginView from "./LoginView";

import { login } from "../../services/api";
import { setKey, getKey } from "../../utils/localStorage";

const Login = ({ history }) => {

	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	useIonViewDidEnter(() => {
		if(getKey("token")) history.push({ pathname: "/dashboard" });
	}, [history]);

	const onSubmit = (event) => {
		event.preventDefault();
		login({ username, password })
			.then(({ status, data, headers }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Login exitoso! Ingresando...");
					setKey("token", headers.authorization);
					setKey("userid", headers.userid);
					setTimeout(() => {
						history.push({ pathname: "/dashboard" });
					},  1000);
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
		
		<LoginView
			password={password}
			setPassword={setPassword}
			setShouldShowToast={setShouldShowToast}
			setUsername={setUsername}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			username={username}
			onSubmit={onSubmit}
		/>
	);
};

export default Login;
