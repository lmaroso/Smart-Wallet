import React from "react";
import { IonContent, IonCard, IonCardHeader, IonCardTitle, IonCardSubtitle } from "@ionic/react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Toast from "../../components/Toast/Toast";
import Loading from "../../components/Loading/Loading";

import { numberToPesos } from "../../utils/utils";

const DashboardView = ({
	balance,
	loading,
	setShouldShowToast,
	shouldShowToast,
	toastText,
	toastType
}) => (
	<PageWrapper>
		<Loading isOpen={loading} />
		<IonContent>
			<IonCard color="tertiary">
				<IonCardHeader>
					<IonCardSubtitle>Dinero actual</IonCardSubtitle>
					<IonCardTitle className="ion-text-end">
						<h1>    
							{numberToPesos(balance)}
						</h1>
					</IonCardTitle>
				</IonCardHeader>
			</IonCard>
		</IonContent>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default DashboardView;
