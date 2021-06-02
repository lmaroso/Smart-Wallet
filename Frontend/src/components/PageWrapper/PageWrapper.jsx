import React from "react";
import { IonPage, IonContent } from "@ionic/react";

import Footer from "../Footer/Footer";
import Header from "../Header/Header";

import "./PageWrapper.scss";

const PageWrapper = ({ children, showFooter }) => (
	<IonPage className="content">
		<Header />
		<IonContent className="marginSides">
			{children}
		</IonContent>
		{showFooter && <Footer />}
	</IonPage>
);

export default PageWrapper;