package app.rowing.jobakker.rowingapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;
import app.rowing.jobakker.rowingapp.sensors.impl.LocationServiceImpl;
import app.rowing.jobakker.rowingapp.sensors.impl.PaceSensorServiceImpl;

public class SensorServiceImpl implements SensorEventListener, SensorService {
    private final List<HeartrateSensor> heartrateListeners;
    private final List<SpeedSensor> paceListeners;
    private final List<StrokerateSensor> strokerateListeners;
    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    private final PaceSensorServiceImpl paceSensorServiceImpl;
    private final LocationServiceImpl locationService;

    SensorServiceImpl(final SensorManager sensorManager) {
        heartrateListeners = new ArrayList<>();
        paceListeners = new ArrayList<>();
        strokerateListeners = new ArrayList<>();

        mSensorManager = sensorManager;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        paceSensorServiceImpl = new PaceSensorServiceImpl();
        locationService = new LocationServiceImpl();
    }

    public void addHeartrateListener(HeartrateSensor listener) {
        this.heartrateListeners.add(listener);
    }

    public void addPaceListener(SpeedSensor listener) {
        this.paceListeners.add(listener);
    }

    public void addStrokerateListener(StrokerateSensor listener) {
        this.strokerateListeners.add(listener);
    }

    public void onResume() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onDestroy() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v("SensorServiceImpl", "sensor change received");

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            determineStrokeRate(x, y, z);
        }
    }

    private void determineStrokeRate(float x, float y, float z) {
        final int strokerate = paceSensorServiceImpl.addSensorValueAndDetermineStrokeRate(x,y,z);
        if(strokerate != -1) {
            for (final StrokerateSensor sensor : strokerateListeners) {
                sensor.stroke(strokerate);
            }
        }
    }

    private void determineHeartBeat() {
        for (final HeartrateSensor sensor : heartrateListeners) {
            sensor.heartbeat(60);
        }
    }

    private void determineSpeed(float speed) {
        for (final SpeedSensor sensor : paceListeners) {
            sensor.newSpeed(speed);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("SensorServiceImpl", "sensor accuracy changed, accuracy: " + accuracy);
    }
}
