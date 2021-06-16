import React, { useState } from "react";
import { useIonViewDidEnter } from "@ionic/react";

import HistoryView from "./HistoryView";

import { getIncomeHistory, getExpenseHistory, deleteIncome , deleteExpense} from "../../services/api";
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
						}
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
						}
					}, 1000);
				});
		} else {
			history.push({ pathname: "/login" });
		}
	}, [history]);

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
		setShowAlert(true);
	};

	const onDismissAlert = () => setShowAlert(false);

	const onAcceptAlert = () => {
		if (selectedMovement.type === "income") deleteIncomeMovement();
		else deleteExpenseMovement();
		setShowAlert(false);
	};

	const deleteIncomeMovement = () =>
		deleteIncome(selectedMovement.id)
			.then(onResolveMovementDeletion);

	const deleteExpenseMovement = () => 
		deleteExpense(selectedMovement.id)
			.then(onResolveMovementDeletion);

	const onResolveMovementDeletion = ({ status, data }) => {
		if (status === 200 || status === 201) {
			setToastType("success");
			setToastText(data);
			setLoading(false);
			setShouldShowToast(true);
		} else {
			setToastType("error");
			setToastText(data);
			setLoading(false);
			setShouldShowToast(true);
		}
	};

	return (
		<HistoryView
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
			onCloseModal={onCloseModal}
			onDelete={onDelete}
			onDismissAlert={onDismissAlert}
			onEdit={onEdit}
		/>
	);
};

export default History;
