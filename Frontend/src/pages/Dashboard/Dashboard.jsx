import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";
import moment from "moment";

import DashboardView from "./DashboardView";

import { getKey } from "../../utils/localStorage";
import { getBalance, getIncomeHistoryWithDates, getExpenseHistoryWithDates } from "../../services/api";

const Dashboard = ({ history }) => {

	const [loading, setLoading] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [balance, setBalance] = useState(0);
	const [movements, setMovements] = useState(null);

	useIonViewDidEnter(() => {
		const initialDate = moment().subtract(1, "w").format("YYYY-MM-DD[T]HH:mm:ss");
		const endDate = moment().format("YYYY-MM-DD[T]HH:mm:ss");

		if(getKey("token")) {
			setLoading(true);
			let movements = [];

			setTimeout(() => {
				getBalance()
					.then(({ status, data }) => {
						if (status === 200 || status === 201) {
							setBalance(data);
						} else if (status !== 400) {
							setToastType("error");
							setToastText(data);
							setShouldShowToast(true);
						} else setLoading(false);
					});
	
				getIncomeHistoryWithDates(initialDate, endDate)
					.then(({ status, data }) => {
						if (status === 200 || status === 201) {
							const incomes = data.map(movement => {
								return {
									...movement,
									type: "income"
								};
							});
							movements.push(...incomes);
						} else if (status !== 400) {
							setToastType("error");
							setToastText(data);
							setShouldShowToast(true);
						} else setLoading(false);
					});
	
				getExpenseHistoryWithDates(initialDate, endDate)
					.then(({ status, data }) => {
						if (status === 200 || status === 201) {
							const expenses = data.map(movement => {
								return {
									...movement,
									type: "expense"
								};
							});
							movements.push(...expenses);
							movements.sort((movementA, movementB) => moment(movementB.date).isAfter(moment(movementA.date)));
							setMovements(movements);
							setLoading(false);
						} else if (status !== 400) {
							setToastType("error");
							setToastText(data);
							setLoading(false);
							setShouldShowToast(true);
						} else setLoading(false);
					});
			}, 1000);
		} else {
			history.push({ pathname: "/login" });
		}
	}, [history]);

	return (
		<DashboardView
			balance={balance}
			loading={loading}
			movements={movements}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
		/>
	);
};

export default Dashboard;