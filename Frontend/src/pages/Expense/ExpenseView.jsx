import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Selector from "../../components/Selector/Selector";
import SelectorItem from "../../components/Selector/SelectorItem";
import Toast from "../../components/Toast/Toast";

const selectorOptions = () => {
	const options = [
		{
			name: "Sí",
			value: true
		}, {
			name: "No",
			value: false
		}
	];
	return options.map(
		option => <SelectorItem key={option.name} name={option.name} value={option.value} />
	);
};

const ExpenseView = ({
	amount,
	description,
	name,
	programmed,
	setAmount,
	setDescription,
	setName,
	setProgrammed,
	setShouldShowToast,
	shouldShowToast,
	toastText,
	toastType,
	onSubmit
}) => (
	<PageWrapper>
		<form onSubmit={onSubmit}>
			<Input
				inputmode="text"
				label="Nombre"
				placeholder="Ingrese un nombre del nuevo gasto"
				required={true}
				type="text"
				value={name}
				onChange={event => setName(event.detail.value)}
			/>
			<Input
				inputmode="text"
				label="Descripción"
				placeholder="Descripción del nuevo gasto"
				required={true}
				type="text"
				value={description}
				onChange={event => setDescription(event.detail.value)}
			/>
			<Input
				inputmode="numeric"
				label="Monto"
				placeholder="Ingrese un monto"
				required={true}
				type="number"
				value={amount}
				onChange={event => setAmount(parseInt(event.detail.value))}
			/>
			<Selector
				placeholder="¿Es un gasto programado?"
				value={programmed}
				onChange={event => setProgrammed(event.detail.value)}
			>
				{selectorOptions()}
			</Selector>
			<Button type="submit">
                Guardar
			</Button>
		</form>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default ExpenseView;