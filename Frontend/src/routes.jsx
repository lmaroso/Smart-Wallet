import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import Register from "./pages/Register/Register";

const Routes = () => (
	<BrowserRouter>
		<Route render={({ history, location }) => {
			history.listen((location) => window._mfq && window._mfq.push(["newPageView", location.pathname]));
			return (
				<Switch location={location}>
					{/* <Route exact component={NoPage} path="/404" /> */}
					<Route exact component={Register} path="/" />
					{/* <Route exact component={NoPage} path="/" /> */}
				</Switch>
			);}
		} />
	</BrowserRouter>
);
export default Routes;
