package com.alexa4.pseudozoo.presenter;


/**
 * View interface for MainFragment. Contains methods which needs to initializing of fragment
 */
public interface ViewInterfaceNews extends ViewInterfaceParent {

    /**
     * Initializing list's of news
     */
    void createNewsList();

    /**
     * Adding presenter to view
     * @param presenterNews
     */
    void setPresenter(PresenterNews presenterNews);
}
