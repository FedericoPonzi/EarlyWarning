package ium.earlywarning.sapienzaapps;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import ium.earlywarning.sapienzaapps.widget.GIFView;

public class SecondActivity extends ActionBarActivity
{
    private Camera mCamera = Camera.open();
    private Camera.Parameters mCameraParameters = mCamera.getParameters();
    private boolean mCameraToggle = false;

    public static String APERTO = "allaperto";
    @SuppressWarnings("unused")
    private static String LOG_TAG = SecondActivity.class.getName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this
     * becomes too memory intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final ActionBar actionBar = getSupportActionBar();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener()
        {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
            {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab,
                                        FragmentTransaction ft)
            {
                // hide the given tab
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab,
                                        FragmentTransaction ft)
            {
                // probably ignore this event
            }
        };

        /**
         * Aggiunge i tabs:
         */
        actionBar.addTab(actionBar.newTab().setText("al Chiuso")
                                 .setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("all'Aperto")
                                 .setTabListener(tabListener));
        if (getIntent().getBooleanExtra(APERTO, false))
        {
            getSupportActionBar().setSelectedNavigationItem(1);

        }

        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
                {
                    @Override
                    public void onPageSelected(int position)
                    {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getSupportActionBar().setSelectedNavigationItem(
                                position);
                    }
                });
    }

    @Override
    protected void onPause()
    {
        if (mCamera != null)
        {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        super.onPause();
    }

    public void onToggleClicked(View v)
    {
        // Is the toggle on?
        boolean mCameraToggle = ((ToggleButton) v).isChecked();

        if (mCameraToggle)
        {
            Log.i("info", "torch is turn on!");
            mCameraParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mCameraParameters);
            mCamera.startPreview();
        }
        else
        {
            Log.i("info", "torch is turn off!");
            mCameraParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mCameraParameters);
            mCamera.stopPreview();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.fine_action_bar:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean("flash", mCameraToggle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean("flash"))
        {
            mCameraToggle = true;
            Log.i("info", "torch is turn on!");
            mCameraParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mCameraParameters);
            mCamera.startPreview();
        }

    }

    ;

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 2;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        private TextView oldTextViewClicked;
        private static int section = 0;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            final View rootView = inflater.inflate(R.layout.fragment_second,
                                                   container, false);

            Bundle args = getArguments();
            final Integer[] gifIDs;
            section = args.getInt(ARG_SECTION_NUMBER);
            switch (args.getInt(ARG_SECTION_NUMBER))
            {
                case 1:
                    gifIDs = new Integer[]{
                            R.drawable.lol1, R.drawable.lol2, R.drawable.lol3,
                            R.drawable.lol4};
                    break;
                case 2:
                    gifIDs = new Integer[]{
                            R.drawable.lol1};
                    break;
                default:
                    gifIDs = new Integer[]{R.drawable.pic1, R.drawable.pic2};
            }

            TextView suggestion = (TextView) rootView
                    .findViewById(R.id.consiglio_tv);
            suggestion.setText(getSuggestion(0));

            Button sosBtn = (Button) rootView.findViewById(R.id.sos_btn);
            sosBtn.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), SOSActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout linearLayout = (LinearLayout) rootView
                    .findViewById(R.id.thumb_linearlayout);

            OnClickListener textViewOnClickListener = new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // display the images selected
                    oldTextViewClicked.setTextColor(getResources().getColor(R.color.white));
                    oldTextViewClicked = (TextView) v;
                    oldTextViewClicked.setTextColor(
                            getResources().getColor(R.color.danger_6_gradient_start));
                    GIFView gifView = (GIFView) rootView
                            .findViewById(R.id.main_gif);
                    int tvValue = Integer.parseInt((String) ((TextView) v)
                            .getText());
                    TextView suggestion = (TextView) rootView
                            .findViewById(R.id.consiglio_tv);
                    suggestion.setText(getSuggestion(tvValue));
                    gifView.setMovieResource(gifIDs[tvValue]);
                }
            };

            int i = 0;
            for (int id : gifIDs)
            {
                TextView tv = (TextView) inflater.inflate(
                        R.layout.list_numb_tv, null);
                if (i == 0)
                {
                    oldTextViewClicked = tv;
                    oldTextViewClicked.setTextColor(
                            getResources().getColor(R.color.danger_6_gradient_start));
                }
                tv.setText(i + "");
                i++;
                // GIFView gv = (GIFView) inflater.inflate(R.layout.thumb_gif,
                // null);
                // gv.setMovieResource(id);
                // gv.setOnClickListener(gifOnClickListener);
                tv.setOnClickListener(textViewOnClickListener);
                linearLayout.addView(tv);

                TextView spacer = new TextView(getActivity());
                spacer.setLayoutParams(new LayoutParams(4, 2));
                spacer.setText(" ");
                linearLayout.addView(spacer);

            }
            return rootView;
        }

        private String getSuggestion(int suggestion)
        {
            if (section == 1)
            {
                switch (suggestion)
                {
                    case 0:
                        return "Durante un terremoto potrebbe essere utile addestrare il proprio merlo a imboccare i vostri animali domestici fino all' arrivo dei soccorsi.";
                    case 1:
                        return "qualcosa!";
                    case 2:
                        return "altro";
                    default:
                        return "SCAPPAAAAAAAA";
                }
            }
            else
            {
                switch (suggestion)
                {
                    case 0:
                        return "Durante un terremoto potrebbe essere utile addestrare il proprio merlo a imboccare i vostri animali domestici fino all' arrivo dei soccorsi.";
                    case 1:
                        return "qualcosa!";
                    case 2:
                        return "altro";
                    default:
                        return "SCAPPAAAAAAAA";
                }
            }
        }
    }

}
