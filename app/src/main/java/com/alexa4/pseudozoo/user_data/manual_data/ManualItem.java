package com.alexa4.pseudozoo.user_data.manual_data;

import android.graphics.Bitmap;

public class ManualItem {
    //The title of manual item
    private final String mTitle;
    //The image source of manual item
    private final String mImageSrc;
    //The url of manual item
    private final String mUrl;
    //The image of manual item
    private Bitmap mImage;

    public ManualItem(String title, String imageSrc, String url) {
        mTitle = title;
        mImageSrc = imageSrc;
        mUrl = url;
        mImage = null;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageSrc() {
        return mImageSrc;
    }
}
