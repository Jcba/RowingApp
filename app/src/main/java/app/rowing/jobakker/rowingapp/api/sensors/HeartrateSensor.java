package app.rowing.jobakker.rowingapp.api.sensors;

public interface HeartrateSensor {
    /**
     * Called when a heartbeat is detected
     *
     * @param heartrate
     */
    void heartbeat(int heartrate);
}
