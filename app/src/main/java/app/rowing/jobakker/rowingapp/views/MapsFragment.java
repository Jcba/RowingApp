package app.rowing.jobakker.rowingapp.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EFragment;

import app.rowing.jobakker.rowingapp.LocationService;
import app.rowing.jobakker.rowingapp.LocationServiceImpl;
import app.rowing.jobakker.rowingapp.R;

@EFragment
public class MapsFragment extends Fragment {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView mMapView;
    private LocationService locationService;

    public MapsFragment() {
        locationService = LocationServiceImpl.getInstance();
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "maps_section";

    /**
     * Returns a new instance of this fragment for the given section
     * number.static
     */
    public static MapsFragment newInstance(int sectionNumber) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap = mMapView.getMap();

        setUpMap();

        return rootView;
    }

    private void setUpMap() {
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        locationService.getAllCurrentLocationData();
    }
}
