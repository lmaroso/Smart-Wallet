import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Toast from "../../components/Toast/Toast";
import Segment from "../../components/Segment/Segment";

const LoginView = ({
	name,
	password,
	segments,
	segmentSelected,
	setName,
	setPassword,
	setShouldShowToast,
	setUsername,
	shouldShowToast,
	toastText,
	toastType,
	username,
	onChangeSegment,
	onSubmit
}) => (
	<PageWrapper hideMenu>
		<Segment defaultValue={segmentSelected} values={segments} onChange={onChangeSegment} />
		<form onSubmit={onSubmit}>
			{segmentSelected === segments[1] && (
				<Input
					required
					custom="name"
					value={name}
					onChange={event => setName(event.detail.value)}
				/>
			)}
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
				{segmentSelected}
			</Button>
		</form>
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default LoginView;