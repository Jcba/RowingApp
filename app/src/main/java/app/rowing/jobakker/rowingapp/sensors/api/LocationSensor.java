package app.rowing.jobakker.rowingapp.sensors.api;

import android.location.Location;

public interface LocationSensor extends Sensor {

    void updateLocation(Location location);
}
