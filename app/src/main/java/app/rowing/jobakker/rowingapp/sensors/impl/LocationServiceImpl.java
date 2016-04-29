package app.rowing.jobakker.rowingapp.sensors.impl;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationServiceImpl {
    private final List<Location> locations;
    private float totalDistance;

    public LocationServiceImpl() {
        this.locations = new ArrayList<>();
    }

    public LocationServiceImpl(List<Location> locations) {
        this.locations = locations;
    }

    public void addLocation(Location location) {
        if (locations.size() > 0) {
            totalDistance += locations.get(locations.size()).distanceTo(location);
        }
        locations.add(location);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public float getSpeed() {
        if(locations.size() > 0) {
            return locations.get(locations.size()).getSpeed();
        }
        return 0f;
    }

    private float getDistance(Location lastLocation, Location location) {
        return lastLocation.distanceTo(location);
    }

}
