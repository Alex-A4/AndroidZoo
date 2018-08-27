package com.alexa4.pseudozoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.alexa4.pseudozoo.viewElements.BottomNavigation;

public class MainActivity extends AppCompatActivity {

    private  TextView newsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsTV = (TextView) findViewById(R.id.message);

        System.out.println("Main opened");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(BottomNavigation.getNavigation(this));
        navigation.setSelectedItemId(R.id.navigation_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        Intent intent;
        intent = new Intent("com.alexa4.pseudozoo.SettingsActivity");
        System.out.println("Settings opened");
        startActivity(intent);
        return super.onOptionsItemSelected(menuItem);
    }
}
