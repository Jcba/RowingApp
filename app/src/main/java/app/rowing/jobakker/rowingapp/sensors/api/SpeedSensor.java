package app.rowing.jobakker.rowingapp.sensors.api;

import app.rowing.jobakker.rowingapp.models.Pace;

public interface SpeedSensor extends Sensor {

    /**
     * Called when a change in speed has been detected
     *
     * @param speed:
     *             The detected speed
     */
    void newSpeed(float speed);
}
