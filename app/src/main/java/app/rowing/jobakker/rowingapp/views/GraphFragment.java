package app.rowing.jobakker.rowingapp.views;

import android.app.Fragment;
import android.os.Bundle;

import org.androidannotations.annotations.EFragment;

import app.rowing.jobakker.rowingapp.R;

@EFragment(R.layout.fragment_graph)
public class GraphFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "graph_section";

    /**
     * Returns a new instance of this fragment for the given section
     * number.static
     */
    public static GraphFragment newInstance(int sectionNumber) {
        GraphFragment fragment = new GraphFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
