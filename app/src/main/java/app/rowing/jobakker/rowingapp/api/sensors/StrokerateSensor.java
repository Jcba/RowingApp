package app.rowing.jobakker.rowingapp.api.sensors;

public interface StrokerateSensor{
    /**
     * Called when a stroke has been made
     *
     * @param strokerate
     */
    void stroke(int strokerate);
}
