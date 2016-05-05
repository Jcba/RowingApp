package app.rowing.jobakker.rowingapp.views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import app.rowing.jobakker.rowingapp.R;
import app.rowing.jobakker.rowingapp.sensors.api.DistanceSensor;
import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;

public class MainFragment extends Fragment implements StrokerateSensor, HeartrateSensor, SpeedSensor, DistanceSensor {

    private TextView heartrate;

    private TextView strokerate;

    private TextView pace;

    private TextView avepace;

    private TextView distance;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.heartrate = (TextView) rootView.findViewById(R.id.heartrate);
        this.avepace = (TextView) rootView.findViewById(R.id.avepace);
        this.pace = (TextView) rootView.findViewById(R.id.pace);
        this.strokerate = (TextView) rootView.findViewById(R.id.strokerate);
        this.distance = (TextView) rootView.findViewById(R.id.distance);
        Log.v("MainFragment", "fragment created");
        return rootView;
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "main_section";

    /**
     * Returns a new instance of this fragment for the given section
     * number.static
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void stroke(final int strokerate) {
        Log.v("MainFragment", "stroke event received");
        this.strokerate.setText(String.format(Locale.getDefault(), "%d", strokerate));
    }

    @Override
    public void heartbeat(final int heartbeat) {
        Log.v("MainFragment", "heartbeat event received");
    }

    @Override
    public void newSpeed(float speed) {
        Log.v("MainFragment", "speed event received");
        this.pace.setText(String.format(Locale.getDefault(), "%.0f", speed));
        this.avepace.setText(String.format(Locale.getDefault(), "%.0f", speed));
    }

    @Override
    public void updatedDistance(float distance) {
        this.distance.setText(String.format(Locale.getDefault(), "%.2f", distance));
    }
}
