package com.alexa4.pseudozoo.viewElements;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.alexa4.pseudozoo.R;

public class BottomNavigation {

    public static BottomNavigationView.OnNavigationItemSelectedListener getNavigation(final Context context) {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                System.out.println(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent("com.alexa4.pseudozoo.MainActivity");
                        System.out.println("Home opened");
                        startActivity(intent);
                        return true;
                    case R.id.navigation_map:
                        intent = new Intent("com.alexa4.pseudozoo.MapActivity");
                        System.out.println("Map opened");
                        startActivity(intent);
                        return true;
                    case R.id.navigation_about:
                        intent = new Intent("com.alexa4.pseudozoo.AboutActivity");
                        System.out.println("About opened");
                        startActivity(intent);
                        return true;
                }
                return false;
            }

            private void startActivity(Intent intent){
                context.startActivity(intent);
            }
        };
    }
}
