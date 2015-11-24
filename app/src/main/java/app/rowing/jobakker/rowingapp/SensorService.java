package app.rowing.jobakker.rowingapp;

import app.rowing.jobakker.rowingapp.api.sensors.HeartrateSensor;
import app.rowing.jobakker.rowingapp.api.sensors.PaceSensor;
import app.rowing.jobakker.rowingapp.api.sensors.StrokerateSensor;

public interface SensorService {

    void addHeartrateListener(HeartrateSensor listener);

    void addPaceListener(PaceSensor listener);

    void addStrokerateListener(StrokerateSensor listener);

    void onResume();

    void onDestroy();
}
