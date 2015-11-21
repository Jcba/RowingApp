package app.rowing.jobakker.rowingapp;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class SensorServiceTest {

    @Mock
    SensorManager sensorManagerMock;

    @Mock
    Sensor accelerometerMock;

    SensorService testable;

    @BeforeClass
    public void beforeClass() {
        testable = new SensorService(sensorManagerMock);
        when(sensorManagerMock.getDefaultSensor(eq(Sensor.TYPE_LINEAR_ACCELERATION))).thenReturn(accelerometerMock);
    }

    @Before
    public void beforeMethod() {

    }

    @Test
    public void testDetermineStroke() {

    }
}
