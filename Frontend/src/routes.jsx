import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import Register from "./pages/Register/Register";
import Login from "./pages/Login/Login";
import Dashboard from "./pages/Dashboard/Dashboard";
import Income from "./pages/Income/Income";

const Routes = () => (
	<BrowserRouter>
		<Route render={({ history, location }) => {
			history.listen((location) => window._mfq && window._mfq.push(["newPageView", location.pathname]));
			return (
				<Switch location={location}>
					{/* <Route exact component={NoPage} path="/404" /> */}
					<Route exact component={Register} path="/" />
					<Route exact component={Register} path="/register" />
					<Route exact component={Login} path="/login" />
					<Route exact component={Dashboard} path="/dashboard" />
					<Route exact component={Income} path="/income" />
				</Switch>
			);}
		} />
	</BrowserRouter>
);
export default Routes;
