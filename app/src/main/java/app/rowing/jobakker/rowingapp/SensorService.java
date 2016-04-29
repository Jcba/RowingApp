package app.rowing.jobakker.rowingapp;

import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;

public interface SensorService {

    void addHeartrateListener(HeartrateSensor listener);

    void addPaceListener(SpeedSensor listener);

    void addStrokerateListener(StrokerateSensor listener);

    void onResume();

    void onDestroy();
}
