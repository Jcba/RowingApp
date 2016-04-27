package app.rowing.jobakker.rowingapp.views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import app.rowing.jobakker.rowingapp.R;
import app.rowing.jobakker.rowingapp.models.Pace;
import app.rowing.jobakker.rowingapp.sensors.api.HeartrateSensor;
import app.rowing.jobakker.rowingapp.sensors.api.PaceSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements StrokerateSensor, HeartrateSensor, PaceSensor {

    @ViewById
    private TextView heartrate;

    @ViewById
    private TextView strokerate;

    @ViewById
    private TextView pace;

    @ViewById
    private TextView avepace;

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

    public MainFragment() {
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
