package app.rowing.jobakker.rowingapp.sensors;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

import java.util.List;

public interface LocationService {

    void addLocation(Location location);

    List<Location> getLocations();

    float getTotalDistance();

    float getSpeed();
}
