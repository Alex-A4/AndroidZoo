package com.alexa4.pseudozoo.user_data;

import java.util.ArrayList;

public class FullNews {
    private String title;
    private String fullText;
    private String imgUrl;
    private ArrayList<String> listUrlOfImagesHighQuality;
    private ArrayList<String> listUrlOfImagesLowQuality;


    public void setListUrlOfImagesHighQuality(ArrayList<String> listUrlOfImagesHighQuality) {

        this.listUrlOfImagesHighQuality = listUrlOfImagesHighQuality;
    }

    public void setListUrlOfImagesLowQuality(ArrayList<String> listUrlOfImagesLowQuality) {
        this.listUrlOfImagesLowQuality = listUrlOfImagesLowQuality;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public FullNews(String title, String fullText, String imgUrl) {
        this.title = title;
        this.fullText = fullText;
        this.imgUrl = imgUrl;
        this.listUrlOfImagesLowQuality = new ArrayList<>();
        this.listUrlOfImagesHighQuality = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getFullText() {
        return fullText;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public ArrayList<String> getListUrlOfImagesHighQuality() {
        return listUrlOfImagesHighQuality;
    }

    public ArrayList<String> getListUrlOfImagesLowQuality() {
        return listUrlOfImagesLowQuality;
    }
}
