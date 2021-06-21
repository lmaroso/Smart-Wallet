import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";

import LoginView from "./LoginView";

import { login, register } from "../../services/api";
import { setKey, getKey } from "../../utils/localStorage";
import { SEGMENTS } from "./constants";

const Login = ({ history }) => {

	const [name, setName] = useState("");
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");
	const [segmentSelected, setSegmentSelected] = useState(SEGMENTS[0]);

	useIonViewDidEnter(() => {
		if(getKey("token")) history.push({ pathname: "/dashboard" });
	}, [history]);

	const onSubmitLogin = () => login({ username, password })
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

	const onSubmitRegister = () => register({ name, email: username, password })
		.then(({ status, data }) => {
			if (status === 200 || status === 201) {
				setToastType("success");
				setToastText("Se registró exitosamente. Por favor verificá tu correo para activar el usuario");
				setSegmentSelected(SEGMENTS[0]);
			} else if (status >= 400) {
				setToastType("error");
				setToastText(data);
			}
			setShouldShowToast(true);
		});

	const onSubmit = (event) => {
		event.preventDefault();
		segmentSelected === SEGMENTS[0]
			? onSubmitLogin()
			: onSubmitRegister();
		
	};

	const onChangeSegment = event => setSegmentSelected(event.detail.value);

	return (
		
		<LoginView
			name={name}
			password={password}
			segmentSelected={segmentSelected}
			segments={SEGMENTS}
			setName={setName}
			setPassword={setPassword}
			setShouldShowToast={setShouldShowToast}
			setUsername={setUsername}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			username={username}
			onChangeSegment={onChangeSegment}
			onSubmit={onSubmit}
		/>
	);
};

export default Login;
