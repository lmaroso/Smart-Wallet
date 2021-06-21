import React from "react";

import PageWrapper from "../../components/PageWrapper/PageWrapper";
import Toast from "../../components/Toast/Toast";
import Loading from "../../components/Loading/Loading";
import Balance from "./Balance";
import HistoryResume from "./HistoryResume";

const DashboardView = ({
	balance,
	loading,
	movements,
	setShouldShowToast,
	shouldShowToast,
	toastText,
	toastType
}) => (
	<PageWrapper>
		<Loading isOpen={loading} />
		<Balance balance={balance} />
		<HistoryResume movements={movements} />
		<Toast isOpen={shouldShowToast} message={toastText} type={toastType} onDidDismiss={() => setShouldShowToast(false)} />
	</PageWrapper>
);

export default DashboardView;
