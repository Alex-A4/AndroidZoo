package com.alexa4.pseudozoo.user_data;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton container which contains list of images url
 * @author alexa4
 */
public class ImagesStore {
    private static ImagesStore sStore;
    private List<String> mUrls;

    private ImagesStore(){
    }
    
    public void setUrls(List<String> urls) {
        mUrls = new ArrayList<>(urls);
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public static ImagesStore getStore() {
        if (sStore == null)
            sStore = new ImagesStore();

        return sStore;
    }
}
