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

import { getKey } from "./utils/localStorage";

const Routes = () => (
	<IonReactRouter>
		<IonRouterOutlet>
			<Route exact path="/" render={() => getKey("token") ? <Dashboard /> : <Register /> }/>
			<Route exact path="/register" render={() => getKey("token") ? <Dashboard /> : <Register /> } />
			<Route exact path="/login" render={() => getKey("token") ? <Dashboard /> : <Login /> } />
			<Route exact component={Dashboard} path="/dashboard" render={() => getKey("token") ? <Dashboard /> : <Login /> } />
			<Route exact component={Income} path="/income" render={() => getKey("token") ? <Income /> : <Login /> } />
			<Route exact component={Profile} path="/profile" render={() => getKey("token") ? <Profile /> : <Login /> } />
			<Route exact component={Expense} path="/expense" render={() => getKey("token") ? <Expense /> : <Login /> } />
			<Route exact component={History} path="/history" render={() => getKey("token") ? <History /> : <Login /> } />
			<Route exact component={Test} path="/test" />
			<Route component={NoPage} />
		</IonRouterOutlet>
	</IonReactRouter>
);
export default Routes;
