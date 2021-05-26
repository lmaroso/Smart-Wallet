import React, { useEffect } from "react";

import { getKey } from "../../utils/localStorage";

const Dashboard = ({ history }) => {

	useEffect(() => {
		if(!getKey("token")) {
			history.push({ pathname: "/" });
		}
	}, [history]);

	return (
		<div>Dashboard</div>
	);
};

export default Dashboard;