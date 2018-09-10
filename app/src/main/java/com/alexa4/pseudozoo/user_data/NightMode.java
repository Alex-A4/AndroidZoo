package com.alexa4.pseudozoo.user_data;


/**
 * Data class which contains information does night mode activate or not
 */
public class NightMode {
    private boolean mode;

    public NightMode(){
        this.mode = false;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean getMode(){
        return this.mode;
    }
}
