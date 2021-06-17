import React from "react";

import SelectorItem from "../components/Selector/SelectorItem";

export const getDayOfWeek = day => {
	let chosenDay;
	switch (day) {
	case 1:
		chosenDay = "Domingo";
		break;
	case 2:
		chosenDay = "Lunes";
		break;
	case 3:
		chosenDay = "Martes";
		break;
	case 4:
		chosenDay = "Miércoles";
		break;
	case 5:
		chosenDay = "Jueves";
		break;
	case 6:
		chosenDay = "Viernes";
		break;
	case 7:
		chosenDay = "Sábado";
		break;
	}
	return chosenDay;
};

export const isProgrammedSelectorOptions = () => {
	const options = [
		{
			name: "Es programado",
			value: true
		}, {
			name: "No es programado",
			value: false
		}
	];
	return options.map(
		option => <SelectorItem key={option.name} name={option.name} value={option.value} />
	);
};

export const frecuenceSelectorOptions = () => {
	const options = [
		{
			name: "Semanalmente",
			value: "weekly"
		}, {
			name: "Mensualmente",
			value: "monthly"
		}, {
			name: "Personalizada",
			value: "custom"
		}
	];
	return options.map(
		option => <SelectorItem key={option.name} name={option.name} value={option.value} />
	);
};

export const weeklySelectorOptions = () => {
	const createOptions = () => {
		let options = [];
		for (let i = 1; i <= 7; i++) {
			const name = getDayOfWeek(i);
			options.push({ name, value: i });
		}
		return options;
	};

	const options = createOptions();
	
	return options.map(
		option => <SelectorItem key={option.name} name={option.name} value={option.value} />
	);
};

export const monthlySelectorOptions = () => {
	let options = [];
	for (let i = 1; i <= 31; i++) {
		options.push({ name: i ,value: i });
	}
	return options.map(
		option => <SelectorItem key={option.name} name={option.name} value={option.value} />
	);
};

export const numberToPesos = number => number.toLocaleString(
	"es-AR",
	{
		style: "currency",
		currency: "ARS"
	}
);