import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Toast from "../../components/Toast/Toast";

const RegisterView = ({
	email,
	name,
	password,
	setEmail,
	setName,
	setPassword,
	setShouldShowToast,
	shouldShowToast,
	toastText,
	toastType,
	onSubmit
}) => (
	<PageWrapper hideMenu>
		<form onSubmit={onSubmit}>
			<Input
				required
				custom="name"
				value={name}
				onChange={event => setName(event.detail.value)}
			/>
			<Input
				required
				custom="email"
				value={email}
				onChange={event => setEmail(event.detail.value)}
			/>
			<Input
				required
				custom="password"
				value={password}
				onChange={event => setPassword(event.detail.value)}
			/>
			<Button type="submit">
				Registrarse
			</Button>
		</form>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default RegisterView;