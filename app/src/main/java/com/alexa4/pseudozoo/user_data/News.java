package com.alexa4.pseudozoo.user_data;


/**
 * Custom class for using it in Adapter
 */
public final class News{
    private String caption;
    private String time;
    private int imageId;
    private String description;

    public News(String caption, String time,int imageId, String description){
        this.caption = caption;
        this.time = time;
        this.imageId = imageId;
        this.description = description;
    }

    public String getCaption() {
        return caption;
    }

    public int getImage() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString(){
        return caption + " " + time;
    }
}

