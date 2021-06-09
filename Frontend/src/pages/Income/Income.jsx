import React, { useState } from "react";
import moment from "moment";
import { useIonViewDidEnter } from "@ionic/react";

import IncomeView from "./IncomeView";

import { addIncome, editIncome } from "../../services/api";
import { getKey } from "../../utils/localStorage";

const Income = ({ history, location }) => {

	const [name, setName] = useState("");
	const [description, setDescription] = useState("");
	const [amount, setAmount] = useState(0);
	const [programmed, setProgrammed] = useState(null);
	const [id, setId] = useState(null);
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");
	const [mode, setMode] = useState("creation");

	useIonViewDidEnter(() => {
		if(!getKey("token")) {
			history.push({ pathname: "/login" });
		} else {
			if(location.state) {
				const { name, description, amount, programmed, id } = location.state;
				setName(name);
				setDescription(description);
				setAmount(amount);
				setProgrammed(programmed);
				setId(id);
				setMode("edition");
			}
		}
	}, [history, location]);

	const onSubmit = (event) => {
		event.preventDefault();
		const date = moment().format("YYYY-MM-DD[T]HH:mm:ss");
		if(mode === "creation") {
			addIncome({ name, description, amount, date, programmed })
				.then(onResolve);
		} else {
			editIncome({ id, name, description, amount, date, programmed })
				.then(onResolve);
		}
	};

	const onResolve = ({ status, data }) => {
		if (status === 200 || status === 201) {
			setToastType("success");
			setToastText("Guardado exitosamente");
			setTimeout(() => {
				history.push({ pathname: "/history" });
			},  1000);
		} else if (status >= 400) {
			setToastType("error");
			setToastText(data);
		}
		setShouldShowToast(true);
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
