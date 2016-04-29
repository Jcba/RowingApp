package app.rowing.jobakker.rowingapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import app.rowing.jobakker.rowingapp.exceptions.FragmentNotFoundException;
import app.rowing.jobakker.rowingapp.exceptions.TranslatableNotFoundException;
import app.rowing.jobakker.rowingapp.views.GraphFragment;
import app.rowing.jobakker.rowingapp.views.MainFragment;
import app.rowing.jobakker.rowingapp.views.MapsFragment;


public class Main extends Activity {

    private final static int NUMBER_OF_FRAGMENTS = 3;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private SensorService sensorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorService = new SensorServiceImpl(sensorManager);

        sensorService.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorService.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    final MainFragment mainFragment = MainFragment.newInstance(position + 1);
                    sensorService.addHeartrateListener(mainFragment);
                    sensorService.addPaceListener(mainFragment);
                    sensorService.addStrokerateListener(mainFragment);
                    return mainFragment;
                case 1:
                    final MapsFragment mapsFragment = MapsFragment.newInstance(position + 1);
                    return mapsFragment;
                case 2:
                    final GraphFragment graphFragment = GraphFragment.newInstance(position + 1);
                    return graphFragment;
                default:
                    Log.e("Main", "unable to determine fragment to use");
                    throw new FragmentNotFoundException("Unbale to determine fragment to use");
            }
        }

        @Override
        public int getCount() {
            return NUMBER_OF_FRAGMENTS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            throw new TranslatableNotFoundException(String.format("Could not find page title for locale %s", l.getCountry()));
        }
    }
}
