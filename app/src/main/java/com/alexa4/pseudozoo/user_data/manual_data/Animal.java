package com.alexa4.pseudozoo.user_data.manual_data;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * Class which contains information about one animal from the web page
 */
public class Animal {
    private String mName;
    private String mDescription;
    private String mImageUrl;
    //Contains pairs like: Parameter name - parameter description
    private ArrayList<String> mParamNames;
    private ArrayList<String> mParamText;

    public Animal(String name, String description, String imageUrl,
                  ArrayList<String> paramNames, ArrayList<String> paramText) {
        mName = name;
        mDescription = description;
        mImageUrl = imageUrl;
        mParamNames = paramNames;
        mParamText = paramText;
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

    public ArrayList<String> getParamNames() {
        return mParamNames;
    }

    public ArrayList<String> getParamText() {
        return mParamText;
    }
}
