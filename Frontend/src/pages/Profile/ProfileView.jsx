import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Toast from "../../components/Toast/Toast";
import Loading from "../../components/Loading/Loading";

const ProfileView = ({
	email,
	loading,
	name,
	setEmail,
	setLoading,
	setName,
	setShouldShowToast,
	shouldShowToast,
	toastText,
	toastType,
	onSubmit
}) => (
	<PageWrapper>
		<Loading
			isOpen={loading}
			onDidDismiss={() => setLoading(false)}
		/>
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
			<Button type="submit">
				Guardar cambios
			</Button>
		</form>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default ProfileView;