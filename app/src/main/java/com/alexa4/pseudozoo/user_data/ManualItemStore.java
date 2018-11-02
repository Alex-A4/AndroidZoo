package com.alexa4.pseudozoo.user_data;


import java.util.ArrayList;

/**
 * Singleton store which contains all manual items
 */
public class ManualItemStore {
    private static ManualItemStore sStore;
    private ArrayList<ManualItem> mItems;


    private ManualItemStore() { }

    public void setItems(ArrayList<ManualItem> items) {
        mItems = items;
    }

    public ArrayList<ManualItem> getItems() {
        return mItems;
    }

    public static ManualItemStore getStore() {
        if (sStore == null)
            sStore = new ManualItemStore();
        return sStore;
    }
}
