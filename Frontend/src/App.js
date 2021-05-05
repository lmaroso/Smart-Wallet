import "@ionic/core/css/core.css";
import "@ionic/core/css/ionic.bundle.css";
import React from "react";
import moment from "moment";
import "./App.scss";
import "moment/locale/es";
import{ IonApp } from "@ionic/react";

import Routes from "./routes";

moment.locale("es");

const App = () => (
	<IonApp>
		<Routes />
	</IonApp>
);

export default App;