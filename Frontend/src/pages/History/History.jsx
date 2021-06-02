import React, { useState, useEffect } from "react";

import HistoryView from "./HistoryView";

import { getIncomeHistory, getExpenseHistory } from "../../services/api";
import { getKey } from "../../utils/localStorage";
import { SEGMENTS } from "./constants";

const History = ({ history }) => {

	const [isModalOpen, setIsModalOpen] = useState(false);
	const [selectedMovement, setSelectedMovement] = useState(null);
	const [incomes, setIncomes] = useState(null);
	const [expenses, setExpenses] = useState(null);
	const [segmentSelected, setSegmentSelected] = useState(SEGMENTS[0]);
	const [shouldShowToast, setShouldShowToast] = useState(false);
	const [toastType, setToastType] = useState("success");
	const [toastText, setToastText] = useState("");

	useEffect(() => {
		if(getKey("token")) {
			getIncomeHistory()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							setIncomes(data);
						} else {
							setToastType("error");
							setToastText(data);
							setShouldShowToast(true);
						}
					}, 1000);
				});
			getExpenseHistory()
				.then(({ status, data }) => {
					setTimeout(() => {
						if (status === 200 || status === 201) {
							setExpenses(data);
						} else {
							setToastType("error");
							setToastText(data);
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

	const onClickGetBack = () => history.push({ pathname: "/dashboard" });
    
	return (
		<HistoryView
			createModal={createModal}
			expenses={expenses}
			incomes={incomes}
			isModalOpen={isModalOpen}
			loading={!expenses || !incomes}
			segmentSelected={segmentSelected}
			segments={SEGMENTS}
			selectedMovement={selectedMovement}
			setShouldShowToast={setShouldShowToast}
			shouldShowToast={shouldShowToast}
			toastText={toastText}
			toastType={toastType}
			onChange={onChangeSegment}
			onClickGetBack={onClickGetBack}
			onCloseModal={onCloseModal}
		/>
	);
};

export default History;
