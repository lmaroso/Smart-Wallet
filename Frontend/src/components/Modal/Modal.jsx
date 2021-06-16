import React from "react";
import { IonModal, IonIcon, IonButton } from "@ionic/react";
import { pencilOutline, closeOutline } from "ionicons/icons";

import Button from "../Button/Button";

import "./Modal.scss";

const Modal = ({ children, isOpen, onClickClose, onClickDelete, onClickEdit }) => (
	<div>
		<IonModal
			cssClass="modal"
			isOpen={isOpen}
			swipeToClose={true}
		>
			<IonButton className="closeButton" slot="icon-only" onClick={onClickClose}>
				<IonIcon icon={closeOutline} size="big" />
			</IonButton>
			{children}
			<div className="twoButtons">
				<Button className="small" onClick={onClickEdit}>
					<IonIcon icon={pencilOutline} size="small" slot="start" />
					Editar
				</Button>
				<Button cancel className="small" onClick={onClickDelete} >
					<IonIcon icon={closeOutline} size="small" slot="start" />
					Eliminar
				</Button>
			</div>
		</IonModal>
	</div>
);

export default Modal;