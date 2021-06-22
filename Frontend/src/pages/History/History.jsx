import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";

import HistoryView from "./HistoryView";

import { getIncomeHistory, getExpenseHistory, deleteIncome , deleteExpense, cancelExpense, cancelIncome } from "../../services/api";
import { getKey } from "../../utils/localStorage";
import { SEGMENTS } from "./constants";

const History = ({ history }) => {
	const [loading, setLoading] = useState(false);
	const [isModalOpen, setIsModalOpen] = useState(false);
	const [selectedMovement, setSelectedMovement] = useState(null);
	const [incomes, setIncomes] = useState(null);
	const [expenses, setExpenses] = useState(null);
	const [segmentSelected, setSegmentSelected] = useState(SEGMENTS[0]);
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");
	const [showAlert, setShowAlert] = useState(false);
	const [alertMessage, setAlertMessage] = useState("");
	const [alertType, setAlertType] = useState(null);

	useIonViewDidEnter(() => {
		setLoading(true);
		if(getKey("token")) {
			getIncomeHistory()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							const incomes = data.map(movement => {
								return {
									...movement,
									type: "income"
								};
							});
							setIncomes(incomes);
							setLoading(false);
						} else if (status !== 400) {
							setToastType("error");
							setToastText(data);
							setLoading(false);
							setShouldShowToast(true);
						} else setLoading(false);
					}, 1000);
				});
			getExpenseHistory()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							const expenses = data.map(movement => {
								return {
									...movement,
									type: "expense"
								};
							});
							setExpenses(expenses);
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
	}, []);

	const onChangeSegment = event => setSegmentSelected(event.detail.value);

	const createModal = movement => {
		setSelectedMovement(movement);
		setIsModalOpen(true);
	};

	const onCloseModal = () => {
		setIsModalOpen(false);
	};

	const onEdit = () => {
		setIsModalOpen(false);
		history.push({
			pathname: `/${selectedMovement.type}`,
			state: selectedMovement
		});
	};
	const onDelete = () => {
		setAlertMessage("¿Está seguro que desea eliminar el registro?");
		setAlertType("delete");
		setShowAlert(true);
	};

	const onDismissAlert = () => setShowAlert(false);

	const onAcceptAlert = () => {
		if (alertType === "delete") {
			if (selectedMovement.type === "income") deleteIncomeMovement();
			else deleteExpenseMovement();
		} else {
			if (selectedMovement.type === "income") cancelIncomeMovement();
			else cancelExpenseMovement();
		}
		setShowAlert(false);
	};

	const cancelIncomeMovement = () => 
		cancelIncome(selectedMovement.id)
			.then(({ status, data }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Se canceló el movimiento programado exitosamente");
					setLoading(false);
					setShouldShowToast(true);
					setIsModalOpen(false);
				} else {
					setToastType("error");
					setToastText(data);
					setLoading(false);
					setShouldShowToast(true);
				}
			});

	const cancelExpenseMovement = () => 
		cancelExpense(selectedMovement.id)
			.then(({ status, data }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Se canceló el movimiento programado exitosamente");
					setLoading(false);
					setShouldShowToast(true);
					setIsModalOpen(false);
				} else {
					setToastType("error");
					setToastText(data);
					setLoading(false);
					setShouldShowToast(true);
				}
			});

	const onClickCancel = () => {
		setAlertMessage("¿Está seguro que desea cancelar el movimiento programado?");
		setAlertType("cancel");
		setShowAlert(true);
	};

	const deleteIncomeMovement = () =>
		deleteIncome(selectedMovement.id)
			.then(({ status, data }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Se eliminó el registro exitosamente");
					setLoading(false);
					setShouldShowToast(true);
					let updatedIncomes = incomes;
					updatedIncomes.filter(income => income.id !== selectedMovement.id);
					setIncomes(updatedIncomes);
					setIsModalOpen(false);
				} else {
					setToastType("error");
					setToastText(data);
					setLoading(false);
					setShouldShowToast(true);
				}
			});


	const deleteExpenseMovement = () => 
		deleteExpense(selectedMovement.id)
			.then(({ status, data }) => {
				if (status === 200 || status === 201) {
					setToastType("success");
					setToastText("Se eliminó el registro exitosamente");
					setLoading(false);
					setShouldShowToast(true);
					let updatedExpenses = expenses;
					updatedExpenses.filter(expense => expense.id !== selectedMovement.id);
					setExpenses(updatedExpenses);
					setIsModalOpen(false);
				} else {
					setToastType("error");
					setToastText(data);
					setLoading(false);
					setShouldShowToast(true);
				}
			});

	return (
		<HistoryView
			alertMessage={alertMessage}
			createModal={createModal}
			expenses={expenses}
			incomes={incomes}
			isModalOpen={isModalOpen}
			loading={loading && (!expenses || !incomes)}
			segmentSelected={segmentSelected}
			segments={SEGMENTS}
			selectedMovement={selectedMovement}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			showAlert={showAlert}
			toastText={toastText}
			toastType={toastType}
			onAcceptAlert={onAcceptAlert}
			onChange={onChangeSegment}
			onClickCancel={onClickCancel}
			onCloseModal={onCloseModal}
			onDelete={onDelete}
			onDismissAlert={onDismissAlert}
			onEdit={onEdit}
		/>
	);
};

export default History;
