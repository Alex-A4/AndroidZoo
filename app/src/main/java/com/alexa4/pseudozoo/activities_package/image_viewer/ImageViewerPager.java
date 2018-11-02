package com.alexa4.pseudozoo.activities_package.image_viewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.user_data.news_data.ImagesStore;

import java.util.List;

public class ImageViewerPager extends AppCompatActivity {
    private ViewPager mPager;
    private TextView toolbarTitle;
    private List<String> mUrls;
    private int count;
    private static final String CURRENT_URL = "CURRENT_URL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_pager);

        String currentUrl = getIntent().getStringExtra(CURRENT_URL);

        mUrls = ImagesStore.getStore().getUrls();

        mPager = (ViewPager) findViewById(R.id.image_viewer_pager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
                toolbarTitle.setText((position+1)+ " " +getString(R.string.image_viewer_toolbar_of)
                        + " " + count);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        FragmentManager manager = getSupportFragmentManager();
        mPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return ImageViewerFragment.instanceViewer(ImageViewerPager.this,
                        mUrls.get(position));
            }

            @Override
            public int getCount() {
                return mUrls.size();
            }
        });

        count = ImagesStore.getStore().getUrls().size();
        int position = 0;
        for (int i = 0; i < mUrls.size(); i++)
            if (mUrls.get(i).equals(currentUrl)) {
                mPager.setCurrentItem(i);
                position = i;
                break;
            }

        toolbarTitle = (TextView) findViewById(R.id.image_viewer_toolbar_text);
        toolbarTitle.setText((position+1)+ " " +getString(R.string.image_viewer_toolbar_of)
                + " " + count);

        ImageView backArrow = findViewById(R.id.image_viewer_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, ImageViewerPager.class);
        intent.putExtra(CURRENT_URL, url);
        return intent;
    }


}
