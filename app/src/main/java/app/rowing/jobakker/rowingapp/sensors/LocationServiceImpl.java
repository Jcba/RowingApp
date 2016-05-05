package app.rowing.jobakker.rowingapp.sensors;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationServiceImpl implements LocationService {
    private final List<Location> locations;
    private float totalDistance;

    public LocationServiceImpl() {
        this.locations = new ArrayList<>();
    }

    public LocationServiceImpl(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public void addLocation(Location location) {
        if (locations.size() > 0) {
            totalDistance += locations.get(locations.size() - 1).distanceTo(location);
            Location l1 = locations.get(locations.size() - 1);
            float distance = l1.distanceTo(location);
            long timeDelta = location.getTime()-l1.getTime();
            location.setSpeed((distance / timeDelta)*3600f);
        }
        locations.add(location);
    }

    @Override
    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public float getTotalDistance() {
        return totalDistance;
    }

    @Override
    public float getSpeed() {
        if (locations.size() > 1) {
            return locations.get(locations.size() -1).getSpeed();
        }
        return 0f;
    }

    private float getDistance(Location lastLocation, Location location) {
        return lastLocation.distanceTo(location);
    }
}
