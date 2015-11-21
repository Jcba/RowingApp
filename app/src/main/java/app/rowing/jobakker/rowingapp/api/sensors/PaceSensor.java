package app.rowing.jobakker.rowingapp.api.sensors;

import app.rowing.jobakker.rowingapp.models.Pace;

public interface PaceSensor {
    /**
     * Called when a change in speed has been detected
     *
     * @param speed:
     *             The detected speed
     */
    void newSpeed(Pace speed);
}
