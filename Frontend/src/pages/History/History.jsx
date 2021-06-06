import React, { useState, useEffect } from "react";

import HistoryView from "./HistoryView";

import { getIncomeHistory, getExpenseHistory } from "../../services/api";
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

	useEffect(() => {
		setLoading(true);
		if(getKey("token")) {
			getIncomeHistory()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							setIncomes(data);
							setLoading(false);
						} else {
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
							setExpenses(data);
							setLoading(false);
						} else {
							setToastType("error");
							setToastText(data);
							setLoading(false);
							setShouldShowToast(true);
						}
					}, 1000);
				});
		} else {
			history.push({ pathname: "/" });
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
			toastText={toastText}
			toastType={toastType}
			onChange={onChangeSegment}
			onCloseModal={onCloseModal}
		/>
	);
};

export default History;
