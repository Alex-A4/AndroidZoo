package com.alexa4.pseudozoo.user_data.news_data;

import android.graphics.Bitmap;

import java.util.ArrayList;


public class FullNews {
    private String title;
    private String fullText;
    private String imgUrl;
    private ArrayList<String> listUrlOfImages;
    private ArrayList<Bitmap> listOfBitmap;

    public void setListOfBitmap(ArrayList<Bitmap> listOfBitmap) {
        this.listOfBitmap = listOfBitmap;
    }

    public void setListUrlOfImages(ArrayList<String> listUrlOfImages) {
        this.listUrlOfImages = listUrlOfImages;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public FullNews(String title, String fullText, String imgUrl) {
        this.title = title;
        this.fullText = fullText;
        this.imgUrl = imgUrl;
        this.listUrlOfImages = new ArrayList<>();
        this.listOfBitmap = new ArrayList<>();
    }

    public ArrayList<Bitmap> getListOfBitmap() {
        return listOfBitmap;
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

    public ArrayList<String> getListUrlOfImages() {
        return listUrlOfImages;
    }
}

