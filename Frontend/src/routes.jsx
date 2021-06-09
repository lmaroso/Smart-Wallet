import React from "react";
import { Route } from "react-router-dom";
import { IonRouterOutlet } from "@ionic/react";
import { IonReactRouter } from "@ionic/react-router";

import NoPage from "./pages/NoPage/NoPage";
import Register from "./pages/Register/Register";
import Login from "./pages/Login/Login";
import Dashboard from "./pages/Dashboard/Dashboard";
import Income from "./pages/Income/Income";
import Profile from "./pages/Profile/Profile";
import Expense from "./pages/Expense/Expense";
import History from "./pages/History/History";
import Test from "./pages/Test/Test";

const Routes = () => (
	<IonReactRouter>
		<IonRouterOutlet>
			<Route exact component={Dashboard} path="/" />
			<Route exact component={Register} path="/register" />
			<Route exact component={Login} path="/login" />
			<Route exact component={Dashboard} path="/dashboard" />
			<Route exact component={Income} path="/income" />
			<Route exact component={Profile} path="/profile" />
			<Route exact component={Expense} path="/expense" />
			<Route exact component={History} path="/history" />
			<Route exact component={Test} path="/test" />
			<Route component={NoPage} />
		</IonRouterOutlet>
	</IonReactRouter>
);
export default Routes;
