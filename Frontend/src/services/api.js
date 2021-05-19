import axios from "axios";

import { getKey } from "../utils/localStorage";

const headers = { "Content-Type": "application/json" };

const api = axios.create({
	baseURL: "http://localhost:8080",
	headers
});

const handleResponse = response => {
	return {
		data: response.data,
		status: response.status,
		headers: response.headers
	};
};

const handleError = ({ response }) => {
	if(response) {
		return {
			data: response.data.message,
			status: response.status,
			headers: response.headers
		};
	} else {
		return {
			data: "Error inesperado. Por favor, intente en unos momentos",
			status: 500,
			headers: {}
		};
	}
};

export const register = body => api.post("/register", body)
	.then(handleResponse)
	.catch(handleError);

export const login = body => api.post("/login", body)
	.then(handleResponse)
	.catch(handleError);

export const addIncome = body => api.post("/addIncome", body, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);