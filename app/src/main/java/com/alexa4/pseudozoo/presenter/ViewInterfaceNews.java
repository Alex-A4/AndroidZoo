package com.alexa4.pseudozoo.presenter;


/**
 * View interface for NewsFragment. Contains methods which needs to initializing of fragment
 */
public interface ViewInterfaceNews extends ViewInterfaceParent {

    /**
     * Initializing list's of news
     */
    void getNewsList();

    /**
     * Adding presenter to view
     * @param presenterNews
     */
    void setPresenter(PresenterNews presenterNews);
}
