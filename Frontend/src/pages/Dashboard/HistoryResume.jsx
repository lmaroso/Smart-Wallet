import React from "react";
import { IonCard, IonCardHeader, IonCardTitle, IonCardContent } from "@ionic/react";
import { useHistory } from "react-router-dom";

import HistoryList from "../../pages/History/HistoryList";


const HistoryResume = ({ movements }) => {
	let history = useHistory();

	const onClickHistory = () => history.push("/history");
    
	return (
		<IonCard onClick={onClickHistory}>
			<IonCardHeader>
				<IonCardTitle>Historial última semana</IonCardTitle>
				<IonCardContent className="ion-padding-vertical ion-no-padding">
					<HistoryList historySelected={movements} onSelectItem={onClickHistory} />
				</IonCardContent>
			</IonCardHeader>
		</IonCard>
	);};

export default HistoryResume;