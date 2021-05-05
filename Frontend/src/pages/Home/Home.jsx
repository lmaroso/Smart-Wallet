import React/*, { useState, useEffect }*/ from "react";
import { IonButton, IonIcon } from "@ionic/react";
import { camera } from "ionicons/icons";

import PageWrapper from "../../components/PageWrapper";
import { register } from "../../services/api";

const Home = () => {
	const alert = () => {
		register({name: "Lucas", email:"marosolucas@gmail.com", password: "123456" })
			.then(response => {
				// eslint-disable-next-line no-console
				console.log(response);
			});
	};
	return (
		<PageWrapper>
			<IonButton onClick={() => alert()}>
				<IonIcon icon={camera} />
			</IonButton>
		</PageWrapper>
	);
};

export default Home;
