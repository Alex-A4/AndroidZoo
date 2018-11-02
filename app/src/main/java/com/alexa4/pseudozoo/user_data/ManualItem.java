package com.alexa4.pseudozoo.user_data;

public class ManualItem {
    private final String mText;
    private final String mImageSrc;
    private final String mUrl;

    public ManualItem(String text, String imageSrc, String url) {
        mText = text;
        mImageSrc = imageSrc;
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getText() {
        return mText;
    }

    public String getImageSrc() {
        return mImageSrc;
    }
}
