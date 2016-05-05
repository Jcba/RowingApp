package app.rowing.jobakker.rowingapp.sensors;

public interface PaceSensorService {

    //do things with these listeners (update them for instance)
    int addSensorValueAndDetermineStrokeRate(float x, float y, float z);
}
