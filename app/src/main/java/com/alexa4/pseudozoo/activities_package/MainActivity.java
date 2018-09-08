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


/**
 * MainActivity is a container to all fragments which contains UI
 * In a bottom of activity is bottomNavigation, by which we can change fragments
 * In a top part is Constraint layout which is container where loads fragments
 */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;

    private PresenterParent presenter;

    private ModelNews modelNews;

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
                            MainFragment mf = new MainFragment();
                            presenter.setView(mf);
                            mf.setPresenter((PresenterNews) presenter);

                            ft.replace(R.id.container, mf, "fragment_main");
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
        final MainFragment mainFragment = new MainFragment();

        final FragmentTransaction ft = manager.beginTransaction();

        presenter = new PresenterNews(modelNews);
        presenter.setView(mainFragment);
        mainFragment.setPresenter((PresenterNews) presenter);


        ft.add(R.id.container, mainFragment,"fragment_main");
        ft.commit();


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(bnl);
        navigation.setSelectedItemId(R.id.navigation_home);
    }
}
