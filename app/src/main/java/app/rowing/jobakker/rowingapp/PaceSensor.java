package app.rowing.jobakker.rowingapp;

/**
 * Created by JOBAKKER on 30-6-2015.
 */
public interface PaceSensor {
    /**
     * Called when a change in speed has been detected
     *
     * @param speed:
     *             The detected speed
     */
    void newSpeed(Pace speed);
}
