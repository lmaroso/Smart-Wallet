import fetch from "isomorphic-unfetch";

import encodeFormData from "./encodeFormData";

const HOST = "http://localhost:8080";
const GET = "GET";
const POST = "POST";

const api = (path, options) => {
	const { method, body, type } = {
		method: GET,
		body: {},
		...options,
	};
	const headers = { "Content-Type": "application/json" };
	if (type === "form") headers["Content-Type"] = "application/x-www-form-urlencoded";

	if (method === GET)
		return fetch(`${HOST}${path}`, {
			method,
			headers,
		}).then(response => response.text());
	return fetch(`${HOST}${path}`, {
		method,
		headers,
		body: type === "form" ? encodeFormData(body) : JSON.stringify(body),
	}).then((response) => response.json());
};   

//Requests

// export const get = () => api("", {
// 	method: GET
// });

export const register = body => api("/register", {
	method: POST,
	body: { ...body }
});

