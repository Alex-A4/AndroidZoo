package com.alexa4.pseudozoo.user_data.manual_data;


import java.util.ArrayList;

/**
 * Singleton class which contains list of ManualAnimalItem
 */
public class ManualAnimalsStore {
    //The singleton instance
    private static ManualAnimalsStore sStore;
    //List of items
    private ArrayList<ManualAnimalItem> mItems;

    private ManualAnimalsStore() {
    }

    public static ManualAnimalsStore getStore() {
        if (sStore == null)
            sStore = new ManualAnimalsStore();

        return sStore;
    }

    public ArrayList<ManualAnimalItem> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<ManualAnimalItem> items) {
        mItems = items;
    }
}
