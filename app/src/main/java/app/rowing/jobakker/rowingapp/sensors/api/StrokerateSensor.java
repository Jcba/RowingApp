package app.rowing.jobakker.rowingapp.sensors.api;

public interface StrokerateSensor extends Sensor{
    /**
     * Called when a stroke has been made
     *
     * @param strokerate
     */
    void stroke(int strokerate);
}
