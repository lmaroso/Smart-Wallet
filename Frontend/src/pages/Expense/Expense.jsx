import React, { useState, useEffect } from "react";
import moment from "moment";

import ExpenseView from "./ExpenseView";

import { addExpense } from "../../services/api";
import { getKey } from "../../utils/localStorage";

const Expense = ({ history }) => {

	const [name, setName] = useState("");
	const [description, setDescription] = useState("");
	const [amount, setAmount] = useState(0);
	const [programmed, setProgrammed] = useState(null);
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	useEffect(() => {
		if(!getKey("token")) {
			history.push({ pathname: "/" });
		}
	}, [history]);

	const onSubmit = (event) => {
		event.preventDefault();
		const date = moment().format("YYYY-MM-DD[T]HH:mm:ss");
		addExpense({ name, description, amount, date, programmed })
			.then(({ status, data }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Guardado exitosamente");
					setTimeout(() => {
						history.push({ pathname: "/dashboard" });
					}, 4000);
				} else if (status >= 400) {
					setToastType("error");
					setToastText(data);
				}
				setShouldShowToast(true);
			});
	};
	return (
		<ExpenseView
			amount={amount}
			description={description}
			name={name}
			programmed={programmed}
			setAmount={setAmount}
			setDescription={setDescription}
			setName={setName}
			setProgrammed={setProgrammed}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			onSubmit={onSubmit}
		/>
	);
};

export default Expense;
