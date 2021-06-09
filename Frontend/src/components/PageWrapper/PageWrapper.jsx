import React from "react";
import { IonPage, IonContent } from "@ionic/react";

import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import Menu from "../Menu/Menu";

import "./PageWrapper.scss";

const PageWrapper = ({ children, showFooter, hideMenu }) => (
	<>
		<Menu />
		<IonPage className="content" id="sw-page-content">
			<Header hideMenu={hideMenu} />
			<IonContent className="marginSides">
				{children}
			</IonContent>
			{showFooter && <Footer />}
		</IonPage>
	</>
);

export default PageWrapper;