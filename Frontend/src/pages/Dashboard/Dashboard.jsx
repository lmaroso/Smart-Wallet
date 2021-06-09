import React, { useEffect } from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";

import { getKey } from "../../utils/localStorage";

const Dashboard = ({ history }) => {

	useEffect(() => {
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