package com.alexa4.pseudozoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.alexa4.pseudozoo.interfaces.OnSetToolBarListener;

public class MainActivity extends AppCompatActivity /*implements OnSetToolBarListener*/ {
    BottomNavigationView navigation;


    BottomNavigationView.OnNavigationItemSelectedListener bnl = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            MainFragment mf = new MainFragment();
                            ft.replace(R.id.container, mf, "fragment_main");
                            ft.addToBackStack(null);
                            ft.setCustomAnimations(
                                    android.R.animator.fade_in, android.R.animator.fade_out);
                            ft.commit();
                            System.out.println("Home opened");
                            return true;

                        case R.id.navigation_map:
                            MapFragment mapf= new MapFragment();
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

        FragmentManager manager = getSupportFragmentManager();
        MainFragment mainFragment = new MainFragment();

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.container, mainFragment,"fragment_main");
        ft.commit();


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(bnl);
        navigation.setSelectedItemId(R.id.navigation_home);
    }


    /*@Override
    public void onToolbarSet(Toolbar toolbar) {
        setSupportActionBar(toolbar);

    }*/
}
