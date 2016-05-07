package app.rowing.jobakker.rowingapp.views.card;

import app.rowing.jobakker.rowingapp.sensors.api.Sensor;

public class CardData {
    private final Sensor displaySensor;
    private final String headerText;

    public CardData(Sensor displaySensor, String headerText) {
        this.displaySensor = displaySensor;
        this.headerText = headerText;
    }

    public Sensor getDisplaySensor() {
        return displaySensor;
    }

    public String getHeaderText() {
        return headerText;
    }
}
