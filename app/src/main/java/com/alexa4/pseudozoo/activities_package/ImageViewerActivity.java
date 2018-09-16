package com.alexa4.pseudozoo.activities_package;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;

public class ImageViewerActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);

        String url = getIntent().getStringExtra("HighNewsUrl");

        ImageView image = findViewById(R.id.image_viewer);
        BitmapAdapter.decodeBitmapFromUrl(url, getResources(), new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
            }
        });
    }
}
