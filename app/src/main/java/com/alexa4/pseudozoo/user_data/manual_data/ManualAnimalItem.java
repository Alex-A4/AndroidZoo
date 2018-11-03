package com.alexa4.pseudozoo.user_data.manual_data;

import android.graphics.Bitmap;


/**
 * Class which contains data about one item on animals page
 */
public class ManualAnimalItem {
    //The url of item
    private final String mUrl;
    //The image src of item
    private final String mImageSrc;
    //The title of item
    private final String mTitle;
    //The image
    private Bitmap mImage;

    public ManualAnimalItem(String title, String imageSrc, String url) {
        mUrl = url;
        mImageSrc = imageSrc;
        mTitle = title;
        mImage = null;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getImageSrc() {
        return mImageSrc;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getImage() {
        return mImage;
    }
}
