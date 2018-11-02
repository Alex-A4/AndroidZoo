package com.alexa4.pseudozoo.user_data;

public class ManualItem {
    private final String mText;
    private final String mImageSrc;

    public ManualItem(String text, String imageSrc) {
        mText = text;
        mImageSrc = imageSrc;
    }

    public String getText() {
        return mText;
    }

    public String getImageSrc() {
        return mImageSrc;
    }
}
