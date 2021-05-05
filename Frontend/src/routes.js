import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import Home from "./pages/Home/Home";

const Routes = () => (
	<BrowserRouter>
		<Route render={({ history, location }) => {
			history.listen((location) => window._mfq && window._mfq.push(["newPageView", location.pathname]));
			return (
				<Switch location={location}>
					{/* <Route exact component={NoPage} path="/404" /> */}
					<Route exact component={Home} path="/" />
					{/* <Route exact component={NoPage} path="/" /> */}
				</Switch>
			);}
		} />
	</BrowserRouter>
);
export default Routes;
