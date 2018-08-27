package com.alexa4.pseudozoo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.alexa4.pseudozoo.viewElements.BottomNavigation;

public class AboutActivity extends AppCompatActivity {

    //@SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(BottomNavigation.getNavigation(this));
        navigation.setSelectedItemId(R.id.navigation_about);
    }
}
