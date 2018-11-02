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


    private ManualItemStore() {
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
}
