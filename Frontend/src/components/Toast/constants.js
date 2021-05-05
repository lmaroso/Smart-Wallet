
import { closeCircleOutline, checkmarkCircleOutline } from "ionicons/icons";

import "./Toast.scss";

export const TOAST_TYPE = {
	error: {
		cssClass: "toastError",
		buttons: [
			{
				side: "start",
				icon: closeCircleOutline,
			},
		],
	},
	success: {
		cssClass: "toastSuccess",
		buttons: [
			{
				side: "start",
				icon: checkmarkCircleOutline,
			},
		]
	}
};