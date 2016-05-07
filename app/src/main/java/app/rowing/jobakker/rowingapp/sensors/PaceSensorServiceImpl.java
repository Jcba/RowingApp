package app.rowing.jobakker.rowingapp.sensors;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

public class PaceSensorServiceImpl implements PaceSensorService {
    private final List<Long> laststrokes;
    private float previousValue;
    private int numberOfStrokeSamples;
    private int numberOfFilteredPastStrokeSamples;
    private long lastStroke;

    public PaceSensorServiceImpl() {
        laststrokes = new ArrayList<>();
        previousValue = 0f;
        numberOfStrokeSamples = 0;
        numberOfFilteredPastStrokeSamples = 0;
        lastStroke = currentTimeMillis();
    }

    @Override
    public int addSensorValueAndDetermineStrokeRate(float x, float y, float z) {
        float value = (x * x) + (y * y) + (z * z);

        if ((value < previousValue / 2) && (numberOfStrokeSamples > numberOfFilteredPastStrokeSamples / 2)) {
            numberOfFilteredPastStrokeSamples = (numberOfFilteredPastStrokeSamples + numberOfStrokeSamples) / 2;
            numberOfStrokeSamples = 0;
            return getStrokeRateOnStroke();
        }

        previousValue = value;
        numberOfStrokeSamples += 1;
        return -1;
    }

    private int getStrokeRateOnStroke() {
        Log.i("PaceSensorServiceImpl", "stroke detected");
        laststrokes.add(currentTimeMillis());
        if (laststrokes.size() > 10) {
            laststrokes.remove(0);
        }
        if (laststrokes.size() > 1) {
            final long delta = (laststrokes.get(laststrokes.size() - 1) - laststrokes.get(0)) / laststrokes.size();
            return delta == 0 ? 0 : (int) (60000 / delta);
        }
        return 0;
    }
}
