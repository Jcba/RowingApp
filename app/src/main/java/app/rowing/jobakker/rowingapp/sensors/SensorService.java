package app.rowing.jobakker.rowingapp.sensors;

import app.rowing.jobakker.rowingapp.sensors.api.DistanceSensor;
import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.LocationSensor;
import app.rowing.jobakker.rowingapp.sensors.api.Sensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;

public interface SensorService {

    void addHeartrateListener(HeartrateSensor listener);

    void addPaceListener(SpeedSensor listener);

    void addStrokerateListener(StrokerateSensor listener);

    void addDistanceSensorListener(DistanceSensor sensor);

    void addLocationSensorListener(LocationSensor sensor);

    void registerSensors(Sensor sensor);

    void onResume();

    void onDestroy();
}
