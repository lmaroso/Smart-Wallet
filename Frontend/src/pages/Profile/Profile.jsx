import React, { useState, useEffect } from "react";

import ProfileView from "./ProfileView";

import { getProfile } from "../../services/api";
import { getKey } from "../../utils/localStorage";

const Profile = ({ history }) => {

	const [loading, setLoading] = useState(false);
	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	useEffect(() => {
		setLoading(true);
		if(getKey("token")) {
			getProfile()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							setName(data.name);
							setEmail(data.email);
							setLoading(false);
						} else {
							setToastType("error");
							setToastText(data);
							setLoading(false);
							setShouldShowToast(true);
						}
					}, 1000);
				});
		} else {
			history.push({ pathname: "/" });
		}
	}, [history]);

	const onSubmit = (event) => {
		event.preventDefault();
		setToastType("success");
		setToastText("Cambios guardados exitosamente");
		setShouldShowToast(true);
	};

	return (
		<ProfileView
			email={email}
			loading={loading}
			name={name}
			setEmail={setEmail}
			setLoading={setLoading}
			setName={setName}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			onSubmit={onSubmit}
		/>
	);
};

export default Profile;
