import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";

import ProfileView from "./ProfileView";

import { getProfile, editProfile } from "../../services/api";
import { getKey } from "../../utils/localStorage";

const Profile = ({ history }) => {

	const [loading, setLoading] = useState(false);
	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	useIonViewDidEnter(() => {
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
			history.push({ pathname: "/login" });
		}
	}, [history]);

	const onSubmit = (event) => {
		event.preventDefault();
		editProfile({ name, email })
			.then(({ status, data }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Cambios guardados exitosamente");
				} else {
					setToastType("error");
					setToastText(data);
				}
				setShouldShowToast(true);
			});
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
