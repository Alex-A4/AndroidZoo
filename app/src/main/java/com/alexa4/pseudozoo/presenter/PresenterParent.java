package com.alexa4.pseudozoo.presenter;

public class PresenterParent {
    private ViewInterfaceParent view;

    public void setView(ViewInterfaceParent view){
        this.view = view;
    }

    public ViewInterfaceParent getview(){
        return this.view;
    }

    public void detachView() {
        this.view = null;
    }
}
