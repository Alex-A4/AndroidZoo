package com.alexa4.pseudozoo.user_data;

public class ManualItem {
    private final String mTitle;
    private final String mImageSrc;
    private final String mUrl;

    public ManualItem(String title, String imageSrc, String url) {
        mTitle = title;
        mImageSrc = imageSrc;
        mUrl = url;
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
