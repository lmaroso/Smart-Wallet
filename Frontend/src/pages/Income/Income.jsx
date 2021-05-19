import React, { useState } from "react";
import moment from "moment";

import IncomeView from "./IncomeView";

import { addIncome } from "../../services/api";

const Income = ({ history }) => {

	const [name, setName] = useState("");
	const [description, setDescription] = useState("");
	const [amount, setAmount] = useState(0);
	const [programmed, setProgrammed] = useState(null);
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	const onSubmit = (event) => {
		event.preventDefault();
		const date = moment().format("YYYY-MM-DD[T]HH:mm:ss");
		addIncome({ name, description, amount, date, programmed })
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
		<IncomeView
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

export default Income;
