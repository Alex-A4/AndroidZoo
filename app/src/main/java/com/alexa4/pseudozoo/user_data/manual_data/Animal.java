package com.alexa4.pseudozoo.user_data.manual_data;

import java.util.HashMap;


/**
 * Class which contains information about one animal from the web page
 */
public class Animal {
    private String mName;
    private String mDescription;
    private String mImageUrl;
    //Contains pairs like: Parameter name - parameter description
    private HashMap<String, String> mAnimalInfo;

    public Animal(String name, String description, String imageUrl, HashMap<String, String> animalInfo) {
        mName = name;
        mDescription = description;
        mImageUrl = imageUrl;
        mAnimalInfo = animalInfo;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public HashMap<String, String> getAnimalInfo() {
        return mAnimalInfo;
    }
}
