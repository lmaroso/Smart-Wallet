import React from "react";
import { IonPage, IonImg } from "@ionic/react";

import Button from "../../components/Button/Button";

import "./NoPage.scss";

const NoPage = ({ history }) => (
	<IonPage className="content">
		<div className="imageContainer">
			<IonImg src="/images/not-found.png"/>
			<Button onClick={() => history.push({ pathname: "/" })}>
                Ir al inicio
			</Button>
		</div>
	</IonPage>
);

export default NoPage;