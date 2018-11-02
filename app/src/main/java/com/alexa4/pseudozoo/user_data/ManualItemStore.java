package com.alexa4.pseudozoo.user_data;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton store which contains all manual items
 */
public class ManualItemStore {
    //Singleton instance
    private static ManualItemStore sStore;
    //Items of ManualItem class
    private ArrayList<ManualItem> mItems;
    //Collection which contains bitmap list of manuals
    private HashMap<String, Bitmap> mImagesStore;


    private ManualItemStore() {
        mImagesStore = new HashMap<>();
    }


    /**
     * Set items to store
     * @param items
     */
    public void setItems(ArrayList<ManualItem> items) {
        mItems = items;
    }

    /**
     * Get items of store
     * @return
     */
    public ArrayList<ManualItem> getItems() {
        return mItems;
    }


    /**
     * Get singleton instance
     * @return the ManualItemStore
     */
    public static ManualItemStore getStore() {
        if (sStore == null)
            sStore = new ManualItemStore();
        return sStore;
    }


    /**
     * Return the bitmap by url
     * @param url the url of image
     * @return the bitmap
     */
    public Bitmap getBitmapByUrl(String url) {
        if (mImagesStore.containsKey(url))
            return mImagesStore.get(url);

        return null;
    }

    /**
     * Adding pair to collection
     * @param url the specified url of image
     * @param bmp the specified image
     */
    public void addBitmapWithUrl(String url, Bitmap bmp) {
        mImagesStore.put(url, bmp);
    }
}
