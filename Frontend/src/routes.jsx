import React from "react";
import { Route, Redirect } from "react-router-dom";
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
			<Route exact path="/" render={() => getKey("token") ? <Redirect to="/dashboard" /> : <Redirect to="/register" /> }/>
			<Route exact path="/register" render={() => !getKey("token") ? <Register /> : <Redirect to="/dashboard" /> } />
			<Route exact path="/login" render={() => !getKey("token") ? <Login /> : <Redirect to="/dashboard" /> } />
			<Route exact component={Dashboard} path="/dashboard" render={() => getKey("token") ? <Dashboard /> : <Redirect to="/login" /> } />
			<Route exact component={Income} path="/income" render={() => getKey("token") ? <Income /> : <Redirect to="/login" /> } />
			<Route exact component={Profile} path="/profile" render={() => getKey("token") ? <Profile /> : <Redirect to="/login" /> } />
			<Route exact component={Expense} path="/expense" render={() => getKey("token") ? <Expense /> : <Redirect to="/login" /> } />
			<Route exact component={History} path="/history" render={() => getKey("token") ? <History /> : <Redirect to="/login" /> } />
			<Route exact component={Test} path="/test" />
			<Route component={NoPage} />
		</IonRouterOutlet>
	</IonReactRouter>
);
export default Routes;
