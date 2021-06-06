import { barChartOutline, documentTextOutline, walletOutline, cashOutline, personOutline, exitOutline } from "ionicons/icons";

import { deleteKey } from "../../utils/localStorage";

export const MENU_ITEMS = [
	{
		menuName: "Dashboard",
		icon: barChartOutline,
		route: "/dashboard"
	},
	{
		menuName: "Cargar gasto",
		icon: cashOutline,
		route: "/expense"
	},
	{
		menuName: "Cargar Ingreso",
		icon: walletOutline,
		route: "/income"
	},
	{
		menuName: "Historial",
		icon: documentTextOutline,
		route: "/history"
	},
	{
		menuName: "Perfil",
		icon: personOutline,
		route: "/profile"
	},
	{
		menuName: "Salir",
		icon: exitOutline,
		route: "/login",
		onClick: () => deleteKey("token")
	},
];

