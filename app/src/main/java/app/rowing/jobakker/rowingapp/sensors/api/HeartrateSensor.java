package app.rowing.jobakker.rowingapp.sensors.api;

public interface HeartrateSensor extends Sensor {
    /**
     * Called when a heartbeat is detected
     *
     * @param heartrate
     */
    void heartbeat(int heartrate);
}
