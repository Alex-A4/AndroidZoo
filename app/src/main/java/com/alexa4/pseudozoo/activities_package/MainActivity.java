package com.alexa4.pseudozoo.activities_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
public class MainActivity extends AppCompatActivity implements ViewInterfaceParent{
    private BottomNavigationView navigation;

    private PresenterParent presenter;

    private ModelNews modelNews;


    /**
     * Bottom navigation bar which open fragments
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener bnl = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();

                    presenter.detachView();

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            presenter = new PresenterNews(modelNews);
                            NewsFragment mf = new NewsFragment();
                            presenter.setView(mf);
                            mf.setPresenter((PresenterNews) presenter);

                            ft.replace(R.id.container, mf, "fragment_news");
                            ft.addToBackStack(null);
                            ft.setCustomAnimations(
                                    android.R.animator.fade_in, android.R.animator.fade_out);
                            ft.commit();
                            System.out.println("Home opened");
                            return true;

                        case R.id.navigation_map:
                            ZooMapFragment mapf= new ZooMapFragment();
                            ft.replace(R.id.container, mapf, "fragment_map");
                            ft.addToBackStack(null);
                            ft.setCustomAnimations(
                                    android.R.animator.fade_in, android.R.animator.fade_out);
                            ft.commit();
                            System.out.println("Map opened");
                            return true;

                        case R.id.navigation_about:
                            AboutFragment af = new AboutFragment();
                            ft.replace(R.id.container, af, "fragment_about");
                            ft.addToBackStack(null);
                            ft.setCustomAnimations(
                                    android.R.animator.fade_in, android.R.animator.fade_out);
                            ft.commit();
                            System.out.println("About opened");
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

        final FragmentManager manager = getSupportFragmentManager();

        final FragmentTransaction ft = manager.beginTransaction();

        final NewsFragment newsFragment = new NewsFragment();

        presenter = new PresenterNews(modelNews);
        presenter.setView(newsFragment);
        newsFragment.setPresenter((PresenterNews) presenter);


        ft.add(R.id.container, newsFragment,"fragment_news");
        ft.commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(bnl);
        navigation.setSelectedItemId(R.id.navigation_home);
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
}

