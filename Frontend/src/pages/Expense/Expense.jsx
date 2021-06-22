import React, { useState } from "react";
import moment from "moment";
import { useIonViewDidEnter, useIonViewWillLeave } from "@ionic/react";

import ExpenseView from "./ExpenseView";

import { addExpense, editExpense } from "../../services/api";
import { getKey } from "../../utils/localStorage";

const Expense = ({ history, location }) => {

	const [name, setName] = useState("");
	const [description, setDescription] = useState("");
	const [amount, setAmount] = useState(0);
	const [programmed, setProgrammed] = useState(null);
	const [id, setId] = useState(null);
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");
	const [mode, setMode] = useState("creation");
	const [frecuence, setFrecuence] = useState(null);
	const [dayOfMonth, setDayOfMonth] = useState(null);
	const [dayOfWeek, setDayOfWeek] = useState(null);
	const [seconds, setSeconds] = useState(null);

	useIonViewDidEnter(() => {
		if(!getKey("token")) {
			history.push({ pathname: "/login" });
		} else {
			if(location.state) {
				const { name, description, amount, programmed, id, repetitionMilliSeconds, dayOfWeek, dayOfMonth } = location.state;
				setName(name);
				setDescription(description);
				setAmount(amount);
				setProgrammed(programmed);
				setId(id);

				if(repetitionMilliSeconds) {
					setFrecuence("custom");
					setSeconds(repetitionMilliSeconds / 1000);
				}
				if(dayOfWeek) {
					setFrecuence("weekly");
					setDayOfWeek(dayOfWeek);
				}
				if(dayOfMonth) {
					setFrecuence("monthly");
					setDayOfMonth(dayOfMonth);
				}
				setMode("edition");
			}
		}
	});

	useIonViewWillLeave(() => cleanup());

	const cleanup = () => {
		setName("");
		setDescription("");
		setAmount(0);
		setProgrammed(null);
		setId(null);
		setMode("creation");
		setFrecuence(null);
		setSeconds(null);
		setDayOfWeek(null);
		setDayOfMonth(null);
	};

	const onSubmit = (event) => {
		event.preventDefault();
		const date = moment().format("YYYY-MM-DD[T]HH:mm:ss");
		let dataToSend = { name, description, amount, date, programmed };
		if (programmed) {
			switch (frecuence) {
			case "weekly":
				dataToSend.dayOfWeek = dayOfWeek;
				break;
			case "monthly":
				dataToSend.dayOfMonth = dayOfMonth;
				break;
			case "custom":
				dataToSend.repetitionMilliSeconds = seconds * 1000;
				break;
			}
		}
		if(mode === "creation") {
			addExpense(dataToSend)
				.then(onResolve);
		} else {
			dataToSend.id = id;
			editExpense(dataToSend)
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
		<ExpenseView
			amount={amount}
			dayOfMonth={dayOfMonth}
			dayOfWeek={dayOfWeek}
			description={description}
			frecuence={frecuence}
			mode={mode}
			name={name}
			programmed={programmed}
			seconds={seconds}
			setAmount={setAmount}
			setDayOfMonth={setDayOfMonth}
			setDayOfWeek={setDayOfWeek}
			setDescription={setDescription}
			setFrecuence={setFrecuence}
			setName={setName}
			setProgrammed={setProgrammed}
			setSeconds={setSeconds}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			onSubmit={onSubmit}
		/>
	);
};

export default Expense;
