import React from "react";
import { IonPage, IonContent } from "@ionic/react";

import Footer from "../components/Footer";
import Header from "../components/Header";

import "./PageWrapper.scss";

const PageWrapper = ({ children }) => (
	<IonPage className="content">
		<Header />
		<IonContent>
			{children}
		</IonContent>
		<Footer />
	</IonPage>
);

export default PageWrapper;