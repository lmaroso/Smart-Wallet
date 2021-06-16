import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";

import DashboardView from "./DashboardView";

import { getKey } from "../../utils/localStorage";
import { getBalance } from "../../services/api";

const Dashboard = ({ history }) => {

	const [loading, setLoading] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [balance, setBalance] = useState(null);

	useIonViewDidEnter(() => {
		setLoading(true);
		if(getKey("token")) {
			getBalance()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							setBalance(data);
							setLoading(false);
						} else if (status !== 400) {
							setToastType("error");
							setToastText(data);
							setLoading(false);
							setShouldShowToast(true);
						} else setLoading(false);
					}, 1000);
				});
		} else {
			history.push({ pathname: "/login" });
		}
	}, [history]);

	return (
		<DashboardView
			balance={balance}
			loading={loading}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
		/>
	);
};

export default Dashboard;