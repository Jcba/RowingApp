package app.rowing.jobakker.rowingapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.List;

import app.rowing.jobakker.rowingapp.models.Coordinate;
import app.rowing.jobakker.rowingapp.models.Workout;

public class LocationServiceImpl implements LocationListener, LocationService {

    private final static LocationService locationService = new LocationServiceImpl();

    private LocationServiceImpl() {
        // make this class a singleton
    }

    public static LocationService getInstance() {
        return locationService;
    }

    @Override
    public List<Coordinate> getAllCurrentLocationData() {
        return null;
    }

    @Override
    public List<Coordinate> getAllLocationData(Workout workout) {
        return null;
    }

    @Override
    public void startLocationRecording() {

    }

    @Override
    public void pauseLocationRecording() {

    }

    @Override
    public void stopLocationRecording() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
