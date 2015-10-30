package app.rowing.jobakker.rowingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by JOBAKKER on 29-6-2015.
 */
public class MainFragment extends Fragment implements StrokerateSensor, HeartrateSensor, PaceSensor {
    private TextView heartrate;
    private TextView strokerate;
    private TextView pace;
    private TextView avepace;
    private List<Long> laststrokes;

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
        laststrokes = new ArrayList<Long>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.heartrate = (TextView) rootView.findViewById(R.id.heartrate);
        this.avepace = (TextView) rootView.findViewById(R.id.avepace);
        this.pace = (TextView) rootView.findViewById(R.id.pace);
        this.strokerate = (TextView) rootView.findViewById(R.id.strokerate);
        return rootView;
    }

    @Override
    public void stroke() {
        laststrokes.add(currentTimeMillis());

        if (laststrokes.size() > 10) {
            laststrokes.remove(0);
        }

        long delta = (laststrokes.get(laststrokes.size()) - laststrokes.get(0)) / laststrokes.size();

        strokerate.setText(String.format("%.d", 60000 / delta));
    }

    @Override
    public void heartbeat() {

    }

    @Override
    public void newSpeed(Pace speed) {
        this.pace.setText(speed.getPace());
        this.avepace.setText(speed.getPace());
    }
}
