package app.rowing.jobakker.rowingapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.rowing.jobakker.rowingapp.api.sensors.HeartrateSensor;
import app.rowing.jobakker.rowingapp.api.sensors.PaceSensor;
import app.rowing.jobakker.rowingapp.api.sensors.StrokerateSensor;
import app.rowing.jobakker.rowingapp.models.Pace;

import static java.lang.System.currentTimeMillis;

public class SensorService implements SensorEventListener {
    private final List<HeartrateSensor> heartrateListeners;
    private final List<PaceSensor> paceListeners;
    private final List<StrokerateSensor> strokerateListeners;

    private final List<Long> laststrokes;

    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;

    SensorService(final SensorManager sensorManager) {
        heartrateListeners = new ArrayList<>();
        paceListeners = new ArrayList<>();
        strokerateListeners = new ArrayList<>();
        laststrokes = new ArrayList<Long>();
        mSensorManager = sensorManager;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    public void addHeartrateListener(HeartrateSensor listener) {
        this.heartrateListeners.add(listener);
    }

    public void addPaceListener(PaceSensor listener) {
        this.paceListeners.add(listener);
    }

    public void addStrokerateListener(StrokerateSensor listener) {
        this.strokerateListeners.add(listener);
    }

    //do things with these listeners (update them for instance)
    private void determineStroke() {
        laststrokes.add(currentTimeMillis());

        if (laststrokes.size() > 10) {
            laststrokes.remove(0);
        }
        if(laststrokes.size() > 1) {
            final long delta = (laststrokes.get(laststrokes.size() - 1) - laststrokes.get(0)) / laststrokes.size();
            final int strokerate = delta==0?0:(int)(60000 / delta);
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

    private void determinePace(Pace pace) {
        for (final PaceSensor sensor : paceListeners) {
            sensor.newSpeed(pace);
        }
    }

    protected void onResume() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onDestroy() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v("SensorService", "sensor change received");

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            determineStroke();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
