package app.rowing.jobakker.rowingapp.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.rowing.jobakker.rowingapp.exceptions.RowingAppRuntimeException;
import app.rowing.jobakker.rowingapp.sensors.api.DistanceSensor;
import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.LocationSensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;

public class SensorServiceImpl implements SensorEventListener, SensorService, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    /**
     * Source: https://github.com/googlesamples/android-play-location/blob/master/LocationUpdates/app/src/main/java/com/google/android/gms/location/sample/locationupdates/MainActivity.java
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private final List<HeartrateSensor> heartrateListeners;
    private final List<SpeedSensor> paceListeners;
    private final List<StrokerateSensor> strokerateListeners;
    private final List<DistanceSensor> distanceSensorListeners;
    private final List<LocationSensor> locationSensorListeners;

    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    private final PaceSensorService paceSensorServiceImpl;
    private final LocationService locationService;
    private final GoogleApiClient mGoogleApiClient;

    public SensorServiceImpl(final Context context, final SensorManager sensorManager) {
        heartrateListeners = new ArrayList<>();
        paceListeners = new ArrayList<>();
        strokerateListeners = new ArrayList<>();
        distanceSensorListeners = new ArrayList<>();
        locationSensorListeners = new ArrayList<>();

        mSensorManager = sensorManager;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        paceSensorServiceImpl = new PaceSensorServiceImpl();
        locationService = new LocationServiceImpl();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    private void connectLocationService() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, createLocationRequest(), this);
    }

    /**
     * Source: https://github.com/googlesamples/android-play-location/blob/master/LocationUpdates/app/src/main/java/com/google/android/gms/location/sample/locationupdates/MainActivity.java
     * <p/>
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return mLocationRequest;
    }

    @Override
    public void addHeartrateListener(HeartrateSensor listener) {
        this.heartrateListeners.add(listener);
    }

    @Override
    public void addPaceListener(SpeedSensor listener) {
        this.paceListeners.add(listener);
    }

    @Override
    public void addStrokerateListener(StrokerateSensor listener) {
        this.strokerateListeners.add(listener);
    }

    @Override
    public void addDistanceSensorListener(DistanceSensor sensor) {
        this.distanceSensorListeners.add(sensor);
    }

    @Override
    public void addLocationSensorListener(LocationSensor sensor) {
        this.locationSensorListeners.add(sensor);
    }

    @Override
    public void addSensor(app.rowing.jobakker.rowingapp.sensors.api.Sensor sensor) {
        if(sensor instanceof DistanceSensor) {
            addDistanceSensorListener((DistanceSensor) sensor);
        }
        if(sensor instanceof HeartrateSensor) {
            addHeartrateListener((HeartrateSensor) sensor);
        }
        if(sensor instanceof SpeedSensor) {
            addPaceListener((SpeedSensor) sensor);
        }
        if(sensor instanceof StrokerateSensor) {
            addStrokerateListener((StrokerateSensor) sensor);
        }
    }

    public void onResume() {
        mGoogleApiClient.connect();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onDestroy() {
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            determineStrokeRate(x, y, z);
        }
    }

    private void determineStrokeRate(float x, float y, float z) {
        final int strokerate = paceSensorServiceImpl.addSensorValueAndDetermineStrokeRate(x, y, z);
        if (strokerate != -1) {
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

    private void determineDistance(float distance) {
        for (final DistanceSensor sensor : distanceSensorListeners) {
            sensor.updatedDistance(distance);
        }
    }

    private void determineLocation(Location location) {
        for (final LocationSensor sensor : locationSensorListeners) {
            sensor.updateLocation(location);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("SensorServiceImpl", "sensor accuracy changed, accuracy: " + accuracy);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationService.addLocation(location);
        determineDistance(locationService.getTotalDistance());
        determineSpeed(locationService.getSpeed());
        determineLocation(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        connectLocationService();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        throw new RowingAppRuntimeException("Could not connect to location service: " + connectionResult.getErrorMessage());
    }
}
