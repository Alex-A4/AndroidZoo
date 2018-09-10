package com.alexa4.pseudozoo.presenter;


/**
 * Top hierarchy Presenter class which contains important methods and fields
 */
public class PresenterParent {
    //View field which contains current open fragment
    private ViewInterfaceParent view;

    /**
     * Bind view to presenter
     * @param view current open view
     */
    public void setView(ViewInterfaceParent view){
        this.view = view;
    }


    /**
     * Getting current view
     * @return
     */
    public ViewInterfaceParent getView(){
        return this.view;
    }

    /**
     * Unbind view from presenter
     */
    public void detachView() {
        this.view = null;
    }
}
