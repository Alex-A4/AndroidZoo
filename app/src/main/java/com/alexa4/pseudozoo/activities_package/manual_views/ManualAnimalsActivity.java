package com.alexa4.pseudozoo.activities_package.manual_views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.alexa4.pseudozoo.R;


/**
 * Activity class which contains list of animals by type
 */
public class ManualAnimalsActivity extends AppCompatActivity {
    private static final String URL_ANIMAL = "URL_ANIMAL";
    //Url which contains on animals type
    private String mUrl;

    private ManualAnimalsFragment mAnimalsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_animals);

        mUrl = getIntent().getStringExtra(URL_ANIMAL);

        mAnimalsFragment = ManualAnimalsFragment.initFragment(mUrl);

        loadFragment(mAnimalsFragment);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.manual_animals_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Initializing new intent which return instance of the class
     * @param context the context of app
     * @param url the url of animals type which need open
     * @return
     */
    public static Intent newIntent(Context context, @NonNull String url) {
        Intent intent = new Intent(context, ManualAnimalsActivity.class);
        intent.putExtra(URL_ANIMAL, url);

        return intent;
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() <= 1)
            finish();
        else manager.popBackStack();
    }
}
