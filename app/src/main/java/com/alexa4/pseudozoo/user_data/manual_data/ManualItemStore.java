package com.alexa4.pseudozoo.user_data.manual_data;


import java.util.ArrayList;

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
     * Getting item from list by title of item
     * @param url the url which need find into the list
     * @return the item or null
     */
    public ManualItem getItemByUrl(String url) {
        for (ManualItem item: mItems)
            if (item.getUrl().equals(url))
                return item;

        return null;
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
