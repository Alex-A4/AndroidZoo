package com.alexa4.pseudozoo.presenter;

import com.alexa4.pseudozoo.user_data.NightMode;


/**
 * Top hierarchy interface which contains nightMode
 * Later nightMode must be moved to model (when mode could be use to all fragments)
 */
public interface ViewInterfaceParent {
    NightMode nightMode = new NightMode();
}
