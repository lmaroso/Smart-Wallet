import React from "react";
import { IonModal } from "@ionic/react";

import Button from "../Button/Button";

import "./Modal.scss";

const Modal = ({ children, isOpen, onClickClose }) => (
	<div>
		<IonModal
			cssClass="modal"
			isOpen={isOpen}
			swipeToClose={true}
		>
			{children}
			<Button className="closeButton" onClick={onClickClose}>Cerrar</Button>
		</IonModal>
	</div>
);

export default Modal;