import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import NoPage from "./pages/NoPage/NoPage";
import Register from "./pages/Register/Register";
import Login from "./pages/Login/Login";
import Dashboard from "./pages/Dashboard/Dashboard";
import Income from "./pages/Income/Income";
import Profile from "./pages/Profile/Profile";
import Expense from "./pages/Expense/Expense";
import History from "./pages/History/History";

const Routes = () => (
	<BrowserRouter>
		<Route render={({ history, location }) => {
			history.listen((location) => window._mfq && window._mfq.push(["newPageView", location.pathname]));
			return (
				<Switch location={location}>
					<Route exact component={Register} path="/" />
					<Route exact component={Register} path="/register" />
					<Route exact component={Login} path="/login" />
					<Route exact component={Dashboard} path="/dashboard" />
					<Route exact component={Income} path="/income" />
					<Route exact component={Profile} path="/profile" />
					<Route exact component={Expense} path="/expense" />
					<Route exact component={History} path="/history" />
					<Route exact component={NoPage} path="*" />
				</Switch>
			);}
		} />
	</BrowserRouter>
);
export default Routes;
