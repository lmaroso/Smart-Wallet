import React from "react";
import { IonModal, IonIcon, IonButton, IonGrid, IonCol, IonRow } from "@ionic/react";
import {  closeOutline } from "ionicons/icons";

import Button from "../Button/Button";

import "./Modal.scss";

const Modal = ({ children, isOpen, onClickClose, onClickDelete, onClickEdit, onClickCancel }) => (
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
			<IonGrid>
				<IonRow>
					<IonCol size="4">
						<Button onClick={onClickEdit}>
							Editar
						</Button>
					</IonCol>
					<IonCol size="4">
						<Button onClick={onClickCancel}>
							Cancelar
						</Button>
					</IonCol>
					<IonCol size="4">
						<Button cancel onClick={onClickDelete} >
							Eliminar
						</Button>
					</IonCol>
				</IonRow>
			</IonGrid>
		</IonModal>
	</div>
);

export default Modal;