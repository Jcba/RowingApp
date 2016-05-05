package app.rowing.jobakker.rowingapp.sensors;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

public class PaceSensorServiceImpl implements PaceSensorService{
    private final List<Long> laststrokes;
    private final FixedSizeContainer<SensorValue> sensorValues;

    public PaceSensorServiceImpl() {
        laststrokes = new ArrayList<Long>();
        sensorValues = new FixedSizeContainer<>();
    }

    @Override
    public int addSensorValueAndDetermineStrokeRate(float x, float y, float z) {
        sensorValues.add(new SensorValue(x, y, z));

        if (sensorValues.lastIsPeak())
            return getStrokeRateOnStroke();
        return -1;
    }

    private int getStrokeRateOnStroke() {
        laststrokes.add(currentTimeMillis());
        if (laststrokes.size() > 10) {
            laststrokes.remove(0);
        }
        if (laststrokes.size() > 1) {
            final long delta = (laststrokes.get(laststrokes.size() - 1) - laststrokes.get(0)) / laststrokes.size();
            final int strokerate = delta == 0 ? 0 : (int) (60000 / delta);
            return strokerate;
        }
        return 0;
    }

    private class SensorValue {
        private final float x;
        private final float y;
        private final float z;

        private final float xyz;

        SensorValue(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;

            this.xyz = (x * x) + (y * y) + (z * z);
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

        public float getXyz() {
            return xyz;
        }
    }

    private class FixedSizeContainer<T extends SensorValue> {
        private static final int DEFAULT_SIZE = 200;
        private final Object[] values;
        private int maxNumberOfValues;
        private int head;

        public FixedSizeContainer() {
            maxNumberOfValues = DEFAULT_SIZE;
            values = new Object[DEFAULT_SIZE];
            head = 0;
        }

        public FixedSizeContainer(int size) {
            maxNumberOfValues = size;
            values = new Object[size];
            head = 0;
        }

        public void add(T element) {
            if (head+1 >= maxNumberOfValues) {
                head = 0;
                values[head] = element;
            }
            values[++head] = element;
        }

        public T get() {
            return (T) values[head];
        }

        public T[] getAll() {
            return (T[]) values;
        }

        public List<T> getMaximums() {
            List<T> result = new ArrayList<T>();

            T valueLast = ((T) values[0]);
            if(valueLast == null) {
                return result;
            }

            float last = valueLast.getXyz();

            for (int i = 0; i < maxNumberOfValues; i++) {
                T value = (T) values[(i + head) % maxNumberOfValues];
                T next = (T) values[(i + head + 1) % maxNumberOfValues];

                if(value == null && next == null) {
                    continue;
                }

                if (value.getXyz() > last && value.getXyz() > next.getXyz()) {
                    result.add(value);
                }

                last = value.getXyz();
            }

            Log.v("PaceSensorServiceImpl", "calculated peaks"+result.toString());
            return result;
        }

        public boolean lastIsPeak() {
            List<SensorValue> maximums = sensorValues.getMaximums();
            float mean = 0f;
            for (SensorValue val : maximums) {
                mean += val.getXyz();
            }
            mean = mean / maximums.size();

            SensorValue twoAgo = (T) values[((head) % maxNumberOfValues)];
            SensorValue last = (T) values[((head+1) % maxNumberOfValues)];
            SensorValue current = (T) values[((head+2) % maxNumberOfValues)];

            if(twoAgo == null || last == null || current == null) {
                return false;
            }

            if (last.getXyz() > twoAgo.getXyz() && last.getXyz() > current.getXyz() && last.getXyz() > mean) {
                return true;
            }

            return false;
        }
    }
}
