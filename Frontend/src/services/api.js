import axios from "axios";

const api = axios.create({
	baseURL: "http://localhost:8080",
	headers: { "Content-Type": "application/json" }
});

const handleResponse = response => {
	return {
		data: response.data,
		status: response.status,
		headers: response.headers
	};
};

const handleError = ({ response }) => {
	return {
		data: response.data.message,
		status: response.status,
		headers: response.headers
	};
};

export const register = body => api.post("/register", body)
	.then(handleResponse)
	.catch(handleError);

export const login = body => api.post("/login", body)
	.then(handleResponse)
	.catch(handleError);