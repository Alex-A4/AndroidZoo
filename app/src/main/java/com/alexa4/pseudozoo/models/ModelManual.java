package com.alexa4.pseudozoo.models;

import android.content.Context;


/**
 * Singleton model to work with Zoo manuals
 */
public class ModelManual {
    private static ModelManual sManual;
    private final Context mContext;


    private ModelManual(Context context) {
        mContext = context;
    }

    public static ModelManual getManual(Context context) {
        if (sManual == null)
            sManual = new ModelManual(context);

        return sManual;
    }
}
