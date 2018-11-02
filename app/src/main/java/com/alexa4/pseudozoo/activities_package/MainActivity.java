package com.alexa4.pseudozoo.activities_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.presenter.PresenterNews;
import com.alexa4.pseudozoo.presenter.PresenterParent;
import com.alexa4.pseudozoo.presenter.ViewInterfaceParent;
import com.alexa4.pseudozoo.user_data.NightMode;

/**
 * MainActivity is a container to all fragments which contains UI
 * In a bottom of activity is bottomNavigation, by which we can change fragments
 * In a top part is Constraint layout which is container where loads fragments
 */
public class MainActivity extends FragmentActivity implements ViewInterfaceParent {
    private BottomNavigationView navigation;
    private static final String NEWS_FRAGMENT_TAG = "NEWS_FRAGMENT";
    private static final String MAP_FRAGMENT_TAG = "MAP_FRAGMENT";
    private static final String ABOUT_FRAGMENT_TAG = "ABOUT_FRAGMENT";
    private static final String MANUAL_FRAGMENT_TAG = "MANUAL_FRAGMENT";

    public PresenterParent presenter;

    private NewsFragment newsFragment;
    private ZooMapFragment zooMapFragment;
    private AboutFragment aboutFragment;
    private ManualFragment mManualFragment;


    /**
     * Bottom navigation bar which open fragments
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener bnl = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    presenter.detachView();

                    switch (item.getItemId()) {
                        case R.id.navigation_news:
                            loadFragment(newsFragment);
                            return true;

                        case R.id.navigation_map:
                            loadFragment(zooMapFragment);
                            return true;

                        case R.id.navigation_about:
                            loadFragment(aboutFragment);
                            return true;
                        case R.id.navigation_manual:
                            loadFragment(mManualFragment);
                            return true;
                    }
                    return false;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(bnl);
        //Basic fragments
        initFragments();

        presenter = new PresenterNews();
        presenter.setView(newsFragment);
        newsFragment.setPresenter((PresenterNews) presenter);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, newsFragment, newsFragment.getTag())
                .addToBackStack(null)
                .show(newsFragment)
                .setCustomAnimations(
                    android.R.animator.fade_in, android.R.animator.fade_out)
                .commit();

    }

    /**
     * Initializing fragments
     */
    private void initFragments() {
        FragmentManager fm = getSupportFragmentManager();
        newsFragment = (NewsFragment) fm.findFragmentByTag(NEWS_FRAGMENT_TAG);
        zooMapFragment = (ZooMapFragment) fm.findFragmentByTag(MAP_FRAGMENT_TAG);
        aboutFragment = (AboutFragment) fm.findFragmentByTag(ABOUT_FRAGMENT_TAG);
        mManualFragment = (ManualFragment) fm.findFragmentByTag(MANUAL_FRAGMENT_TAG);

        if (newsFragment == null){
            newsFragment = new NewsFragment();
            fm.beginTransaction().add(R.id.container, newsFragment, NEWS_FRAGMENT_TAG).commit();
        }

        if (zooMapFragment == null) {
            zooMapFragment = new ZooMapFragment();
            fm.beginTransaction().add(R.id.container, zooMapFragment, MAP_FRAGMENT_TAG).commit();
        }
        if (aboutFragment == null) {
            aboutFragment = new AboutFragment();
            fm.beginTransaction().add(R.id.container, aboutFragment, ABOUT_FRAGMENT_TAG).commit();
        }
        if (mManualFragment == null) {
            mManualFragment = new ManualFragment();
            fm.beginTransaction().add(R.id.container, mManualFragment, MANUAL_FRAGMENT_TAG).commit();
        }
    }

    /**
     * Loading fragment to container and clearing settings fragment
     * @param fragment the fragment which need to set to container
     */
    private void loadFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        //Delete settings from backstack
        if (manager.getFragments().get(0).getClass() == SettingsFragment.class)
            manager.popBackStack();

        //If current fragment is not a fragment which we want to open
        if (!fragment.getTag().equals(manager.getFragments().get(0).getTag())) {
            ft.replace(R.id.container, fragment, fragment.getTag());
            ft.addToBackStack(null);
        }

        ft.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        setColors();
    }

    public void setColors(){
        if (NightMode.getNightMode().getMode())
            navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
        else navigation.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        if (manager.getBackStackEntryCount() == 1 || manager.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}

