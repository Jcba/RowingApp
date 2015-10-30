package app.rowing.jobakker.rowingapp;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOBAKKER on 30-6-2015.
 */
public class SensorService extends Service implements SensorEventListener {
    private final List<HeartrateSensor> heartrateListeners;
    private final List<PaceSensor> paceListeners;
    private final List<StrokerateSensor> strokerateListeners;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    SensorService() {
        heartrateListeners = new ArrayList<>();
        paceListeners = new ArrayList<>();
        strokerateListeners = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void addHeartrateListener(HeartrateSensor listener){
        this.heartrateListeners.add(listener);
    }

    void addPaceListener(PaceSensor listener) {
        this.paceListeners.add(listener);
    }

    void addStrokerateListener(StrokerateSensor listener) {
        this.strokerateListeners.add(listener);
    }

    //do things with these listeners (update them for instance)
    private void determineStroke(){
        for(StrokerateSensor sensor: strokerateListeners){
            sensor.stroke();
        }
    }

    private void determineHeartBeat(){
        for(HeartrateSensor sensor: heartrateListeners){
            sensor.heartbeat();
        }
    }

    private void determinePace(Pace pace){
        for(PaceSensor sensor: paceListeners){
            sensor.newSpeed(pace);
        }
    }

    protected void onResume() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
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
