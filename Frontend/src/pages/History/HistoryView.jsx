import React from "react";
import { IonList } from "@ionic/react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Segment from "../../components/Segment/Segment";
import Toast from "../../components/Toast/Toast";
import Loading from "../../components/Loading/Loading";
import Modal from "../../components/Modal/Modal";
import HistoryItem from "./HistoryItem";
import HistoryList from "./HistoryList";

const HistoryView = ({
	createModal,
	expenses,
	incomes,
	isModalOpen,
	loading,
	segmentSelected,
	segments,
	selectedMovement,
	setShouldShowToast,
	shouldShowToast,
	toastText,
	toastType,
	onChange,
	onCloseModal,
	onEdit
}) => (
	<PageWrapper>
		<Loading isOpen={loading} />
		<Modal
			isOpen={isModalOpen}
			onClickClose={onCloseModal}
			onClickEdit={onEdit}
		>
			{selectedMovement && <HistoryItem movement={selectedMovement}/>}
		</Modal>
		<Segment defaultValue={segmentSelected} values={segments} onChange={onChange} />
		<IonList>
			{segmentSelected === "Ingresos"
				? <HistoryList historyColor="success" historySelected={incomes} onSelectItem={createModal} />
				: <HistoryList historyColor="danger" historySelected={expenses} onSelectItem={createModal} />
			}
		</IonList>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default HistoryView;
