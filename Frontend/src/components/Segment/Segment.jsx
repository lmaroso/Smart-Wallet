import React from "react";
import { IonSegment, IonSegmentButton, IonLabel } from "@ionic/react";

const Segment = ({ values, defaultValue, onChange }) => (
	<IonSegment value={defaultValue} onIonChange={onChange}>
		{values.map((value, index) => (
			<IonSegmentButton key={index} value={value}>
				<IonLabel>{value}</IonLabel>
			</IonSegmentButton>
		))}
	</IonSegment>
);

export default Segment;