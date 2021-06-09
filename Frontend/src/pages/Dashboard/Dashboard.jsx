import React  from "react";
import { useIonViewDidEnter } from "@ionic/react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";

import { getKey } from "../../utils/localStorage";

const Dashboard = ({ history }) => {

	useIonViewDidEnter(() => {
		if(!getKey("token")) {
			history.push({ pathname: "/login" });
		}
	}, [history]);

	return (
		<PageWrapper>
			Dashboard
		</PageWrapper>
	);
};

export default Dashboard;