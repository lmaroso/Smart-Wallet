import React from "react";
import { IonModal, IonIcon } from "@ionic/react";
import { pencilOutline, closeOutline } from "ionicons/icons";

import Button from "../Button/Button";

import "./Modal.scss";

const Modal = ({ children, isOpen, onClickEdit, onClickClose }) => (
	<div>
		<IonModal
			cssClass="modal"
			isOpen={isOpen}
			swipeToClose={true}
		>
			{children}

			<div className="twoButtons" >
				<Button className="small" onClick={onClickEdit}>
					<IonIcon icon={pencilOutline} size="small" slot="start" />
					Editar
				</Button>
				<Button cancel className="small" onClick={onClickClose} >
					<IonIcon icon={closeOutline} size="small" slot="start" />
					Cerrar
				</Button>
			</div>
		</IonModal>
	</div>
);

export default Modal;