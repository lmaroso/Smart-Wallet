import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Selector from "../../components/Selector/Selector";
import Toast from "../../components/Toast/Toast";

import { isProgrammedSelectorOptions, frecuenceSelectorOptions, monthlySelectorOptions, weeklySelectorOptions } from "../../utils/utils";

const ExpenseView = ({
	amount,
	dayOfMonth,
	dayOfWeek,
	description,
	frecuence,
	mode,
	name,
	programmed,
	seconds,
	setAmount,
	setDayOfMonth,
	setDayOfWeek,
	setDescription,
	setFrecuence,
	setName,
	setProgrammed,
	setSeconds,
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
				disabled={mode === "edition"}
				placeholder="¿Es un gasto programado?"
				value={programmed}
				onChange={event => setProgrammed(event.detail.value)}
			>
				{isProgrammedSelectorOptions()}
			</Selector>
			{programmed && (
				<>
					<Selector
						disabled={mode === "edition"}
						placeholder="¿Con qué frecuencia se deberá realizar el movimiento?"
						value={frecuence}
						onChange={event => setFrecuence(event.detail.value)}
					>
						{frecuenceSelectorOptions()}
					</Selector>
					{frecuence === "monthly" && (
						<Selector
							label="Se repite cada mes el día"
							value={dayOfMonth}
							onChange={event => setDayOfMonth(event.detail.value)}
						>
							{monthlySelectorOptions()}
						</Selector>
					)}
					{frecuence === "weekly" && (
						<Selector
							label="Se repite cada semana el"
							value={dayOfWeek}
							onChange={event => setDayOfWeek(event.detail.value)}
						>
							{weeklySelectorOptions()}
						</Selector>
					)}
					{frecuence === "custom" && (
						<Input
							disabled={mode === "edition"}
							inputmode="numeric"
							label="Se repite cada X segundos"
							placeholder="Ingrese una cantidad de segundos"
							type="number"
							value={seconds}
							onChange={event => setSeconds(parseInt(event.detail.value))}
						/>
					)}
				</>
			)}
			<Button type="submit">
                Guardar
			</Button>
		</form>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default ExpenseView;