package com.alexa4.pseudozoo.user_data.news_data;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Custom class for using it list of news
 */
public final class News {
    private String caption;
    private String time;
    private String imageUrl;
    private String description;
    private String fullNewsLink;
    private Bitmap bitmap;

    public News(String caption, String time, String image, String description, String fullNewsLink){
        this.caption = caption;
        this.time = time;
        this.imageUrl = image;
        this.description = description;
        this.fullNewsLink = fullNewsLink;
        this.bitmap = null;
    }

    public String getCaption() {
        return caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public String getFullNewsLink(){
        return this.fullNewsLink;
    }

    @Override
    public String toString(){
        return caption + " " + time + "\n" + description + "\n" +  imageUrl + "\n" +
               fullNewsLink + "\n---------------------\n";
    }
}

