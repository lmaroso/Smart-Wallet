import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Toast from "../../components/Toast/Toast";

const LoginView = ({
	password,
	setPassword,
	setShouldShowToast,
	setUsername,
	shouldShowToast,
	toastText,
	toastType,
	username,
	onSubmit
}) => (
	<PageWrapper hideMenu>
		<form onSubmit={onSubmit}>
			<Input
				required
				custom="email"
				value={username}
				onChange={event => setUsername(event.detail.value)}
			/>
			<Input
				required
				custom="password"
				value={password}
				onChange={event => setPassword(event.detail.value)}
			/>
			<Button type="submit">
				Login
			</Button>
		</form>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default LoginView;