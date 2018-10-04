package com.alexa4.pseudozoo.custom_views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;
import com.alexa4.pseudozoo.adapters.ImageCompressor;

import java.util.List;

/**
 * Custom image view which support embedded click listener to open ImageViewer
 * to open full screen image by url
 */
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private String photoUrl;

    public CustomImageView(Context context) {
        super(context);
        setClickListener();
        setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickListener();
        setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickListener();
        setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
    }

    /**
     * Embedded click listener
     */
    private void setClickListener() {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.alexa4.pseudozoo.ImageViewerActivity");
                intent.putExtra("HighNewsUrl", CustomImageView.this.photoUrl);
                getContext().startActivity(intent);
            }
        });
    }

    /**
     * Download img by url and set the downloaded bitmap to fullNews list
     * @param url the url of img
     * @param listOfBmp the list where need insert new bitmap
     * @param position the position at which need to insert
     */
    public void downloadImageByUrl(String url, List<Bitmap> listOfBmp, int position) {
        this.photoUrl = url;
        BitmapAdapter.decodeBitmapFromUrl(url, getResources(), new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                setImageBitmap(bitmap);
                listOfBmp.add(position, bitmap);
            }
        });
    }

    /**
     * Download img and set it
     * @param url the url of img
     */
    public void downloadImageByUrl(String url) {
        this.photoUrl = url;
        BitmapAdapter.decodeBitmapFromUrl(url, getResources(), new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                setImageBitmap(bitmap);
            }
        });
    }

    public void downloadCompressedImageByUrl(String url) {
        this.photoUrl = url;
        ImageCompressor.getCompressedImage(getContext(), url, new ImageCompressor.BitmapCompressorCallback() {
            @Override
            public void sendCompressedBmp(Bitmap bmp) {
                setImageBitmap(bmp);
            }
        });
    }
    public void downloadCompressedImageByUrl(String url, List<Bitmap> listOfBmp, int position) {
        this.photoUrl = url;
        ImageCompressor.getCompressedImage(getContext(), url, new ImageCompressor.BitmapCompressorCallback() {
            @Override
            public void sendCompressedBmp(Bitmap bmp) {
                setImageBitmap(bmp);
                listOfBmp.add(position, bmp);
            }
        });
    }
}
