package app.rowing.jobakker.rowingapp.views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.rowing.jobakker.rowingapp.R;
import app.rowing.jobakker.rowingapp.models.Pace;
import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;

public class MainFragment extends Fragment implements StrokerateSensor, HeartrateSensor, SpeedSensor {

    private TextView heartrate;

    private TextView strokerate;

    private TextView pace;

    private TextView avepace;

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
        this.strokerate.setText(String.format("%d", strokerate));
    }

    @Override
    public void heartbeat(final int heartbeat) {
        Log.v("MainFragment", "heartbeat event received");
    }

    @Override
    public void newSpeed(Pace speed) {
        Log.v("MainFragment", "speed event received");
        this.pace.setText(speed.getPace());
        this.avepace.setText(speed.getPace());
    }
}
