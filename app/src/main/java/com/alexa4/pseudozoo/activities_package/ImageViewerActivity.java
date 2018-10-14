package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;

public class ImageViewerActivity extends FragmentActivity {
    private static final String IMAGE_URL = "IMAGE_URL";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);

        //Get image url from intent
        String url = getIntent().getStringExtra(IMAGE_URL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Color.argb(255, 0, 0, 0));

        ImageView image = findViewById(R.id.image_viewer);
        BitmapAdapter.decodeBitmapFromUrl(url, getResources(), new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
            }
        });

        ImageView backArrow = findViewById(R.id.image_viewer_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * Initializing intent for ImageViewer with the url
     * @param context the context of app
     * @param url the url of image
     * @return intent for ImageViewer
     */
    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(IMAGE_URL, url);
        return intent;
    }
}
