package com.alexa4.pseudozoo.activities_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.presenter.PresenterNews;
import com.alexa4.pseudozoo.presenter.PresenterParent;
import com.alexa4.pseudozoo.presenter.ViewInterfaceParent;

/**
 * MainActivity is a container to all fragments which contains UI
 * In a bottom of activity is bottomNavigation, by which we can change fragments
 * In a top part is Constraint layout which is container where loads fragments
 */
public class MainActivity extends FragmentActivity implements ViewInterfaceParent{
    private BottomNavigationView navigation;

    public PresenterParent presenter;

    private ModelNews modelNews;

    private NewsFragment newsFragment;
    private ZooMapFragment zooMapFragment;
    private AboutFragment aboutFragment;


    /**
     * Bottom navigation bar which open fragments
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener bnl = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    presenter.detachView();

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            presenter = new PresenterNews(modelNews);
                            presenter.setView(newsFragment);
                            newsFragment.setPresenter((PresenterNews) presenter);
                            loadFragment(newsFragment);
                            return true;

                        case R.id.navigation_map:
                            loadFragment(zooMapFragment);
                            return true;

                        case R.id.navigation_about:
                            loadFragment(aboutFragment);
                            return true;
                    }
                    return false;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelNews = new ModelNews();


        //Basic fragments
        FragmentManager fm = getSupportFragmentManager();
        newsFragment = (NewsFragment) fm.findFragmentByTag(String.valueOf(NewsFragment.class));
        zooMapFragment = (ZooMapFragment) fm.findFragmentByTag(String.valueOf(ZooMapFragment.class));
        aboutFragment = (AboutFragment) fm.findFragmentByTag(String.valueOf(AboutFragment.class));

        if (newsFragment == null){
            newsFragment = new NewsFragment();
            fm.beginTransaction().add(newsFragment, String.valueOf(NewsFragment.class)).commit();
        }

        zooMapFragment = new ZooMapFragment();
        aboutFragment = new AboutFragment();


        presenter = new PresenterNews(modelNews);
        presenter.setView(newsFragment);
        newsFragment.setPresenter((PresenterNews) presenter);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, newsFragment, String.valueOf(newsFragment.getClass()));
        ft.addToBackStack(null);
        ft.commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(bnl);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //Delete settings from backstack
        if (getSupportFragmentManager().getFragments().get(0).getClass() == SettingsFragment.class)
            getSupportFragmentManager().popBackStack();

        ft.replace(R.id.container, fragment, String.valueOf(fragment.getClass()));

        //If current fragment is not a fragment which we want to open
        if (fragment.getClass() != getSupportFragmentManager().getFragments().get(0).getClass()) {
            ft.addToBackStack(null);
        }

        ft.setCustomAnimations(
                android.R.animator.fade_in, android.R.animator.fade_out);
        ft.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        setColors();
    }

    public void setColors(){
        if (nightMode.getMode())
            navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
        else navigation.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
    }
}

