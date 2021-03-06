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

export const getProfile = () => api.get(`/getProfile/${getKey("userid")}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const editProfile = body => api.post(`/edit/${getKey("userid")}`, body, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const addExpense = body => api.post("/addExpense", { userId: parseInt(getKey("userid")), ...body }, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const editExpense = body => api.post("/editExpense", { userId: parseInt(getKey("userid")), ...body }, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const getExpenseHistory = () => api.get(`/getExpenseHistory/${getKey("userid")}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const deleteExpense = id => api.delete(`/deleteExpense/${parseInt(getKey("userid"))}/${id}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const addIncome = body => api.post("/addIncome", { userId: parseInt(getKey("userid")), ...body }, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const getIncomeHistory = () => api.get(`/getIncomeHistory/${getKey("userid")}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const editIncome = body => api.post("/editIncome", { userId: parseInt(getKey("userid")), ...body }, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const deleteIncome = id => api.delete(`/deleteIncome/${parseInt(getKey("userid"))}/${id}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const getBalance = () => api.get(`/balance/${getKey("userid")}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const getIncomeHistoryWithDates = (initialDate, endDate) => api.get(`/getIncomeHistory/${getKey("userid")}/${initialDate}/${endDate}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const getExpenseHistoryWithDates = (initialDate, endDate) => api.get(`/getExpenseHistory/${getKey("userid")}/${initialDate}/${endDate}`, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const cancelIncome = id => api.post(`/cancelIncome/${id}`, { userId: parseInt(getKey("userid")) }, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);

export const cancelExpense = id => api.post(`/cancelExpense/${id}`, { userId: parseInt(getKey("userid")) }, {
	headers: { Authorization: getKey("token"), ...headers }
})
	.then(handleResponse)
	.catch(handleError);