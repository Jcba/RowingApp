package app.rowing.jobakker.rowingapp;

import java.util.List;

import app.rowing.jobakker.rowingapp.models.Coordinate;
import app.rowing.jobakker.rowingapp.models.Workout;

public interface LocationService {

    List<Coordinate> getAllCurrentLocationData();

    List<Coordinate> getAllLocationData(Workout workout);

    void startLocationRecording();

    void pauseLocationRecording();

    void stopLocationRecording();
}
