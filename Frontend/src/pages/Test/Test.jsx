import React from "react";
import { IonApp, IonMenu, IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem, IonIcon, IonLabel, IonButtons, IonMenuButton, IonButton } from "@ionic/react";

const Test = () => (
	<IonApp>
		<IonMenu content-id="main-content" side="start">
			<IonHeader>
				<IonToolbar translucent>
					<IonTitle>Menu</IonTitle>
				</IonToolbar>
			</IonHeader>
			<IonContent>
				<IonList>
					<IonItem button routeLink="/dashboard">
						<IonIcon name="mail" slot="start" />
						<IonLabel>Inbox</IonLabel>
					</IonItem>
					<IonItem>
						<IonIcon name="paper-plane" slot="start" />
						<IonLabel>Outbox</IonLabel>
					</IonItem>
					<IonItem>
						<IonIcon name="heart" slot="start" />
						<IonLabel>Favorites</IonLabel>
					</IonItem>
					<IonItem>
						<IonIcon name="archive" slot="start" />
						<IonLabel>Archived</IonLabel>
					</IonItem>
					<IonItem>
						<IonIcon name="trash" slot="start" />
						<IonLabel>Trash</IonLabel>
					</IonItem>
					<IonItem>
						<IonIcon name="warning" slot="start" />
						<IonLabel>Spam</IonLabel>
					</IonItem>
				</IonList>
			</IonContent>
		</IonMenu>

		<div className="ion-page" id="main-content">
			<IonHeader>
				<IonToolbar>
					<IonButtons slot="start">
						<IonMenuButton />
					</IonButtons>
					<IonTitle>Inbox</IonTitle>
				</IonToolbar>
			</IonHeader>
			<IonContent class="ion-padding">
				<IonButton expand="block" onclick="openMenu()">Open Menu</IonButton>
			</IonContent>
		</div>
	</IonApp>
);

export default Test;