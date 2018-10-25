package com.alexa4.pseudozoo.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alexa4.pseudozoo.activities_package.FullNewsActivity;
import com.alexa4.pseudozoo.activities_package.NewsFragment;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.user_data.FullNews;
import com.alexa4.pseudozoo.user_data.News;

import java.util.ArrayList;


/**
 * Presenter which implements interface to download news from the internet
 */
public class PresenterNews extends PresenterParent{
    private final ModelNews modelNews;
    private boolean downloadingFlag = false;

    /**
     * Setting model to presenter
     */
    public PresenterNews() {
        this.modelNews = ModelNews.getModelNews();
    }

    /**
     * Bind current open view
     * @param view current open view
     */
    @Override
    public void setView(ViewInterfaceParent view) {
        super.setView(view);
    }


    /**
     * Unbind view to avoid memory leaks
     */
    @Override
    public void detachView() {
        super.detachView();
    }

    /**
     * Downloading list of news from Zoo web link
     */
    public void updateNewsList(){
        if (!downloadingFlag) {
            downloadingFlag = true;
            modelNews.loadNews(new ModelNews.LoadNewsCallback() {
                @Override
                public void retrieveResult(ArrayList<News> list) {
                    if (getView() != null)
                        ((NewsFragment) getView()).updateNewsList(list);
                    downloadingFlag = false;
                }

                @Override
                public NetworkInfo getInstanceNetworkInfo() {
                    ConnectivityManager cm = null;
                    if (getView() != null) {
                        cm = (ConnectivityManager) ((NewsFragment) getView())
                                .getContext()
                                .getSystemService(Context.CONNECTIVITY_SERVICE);
                    }
                    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                    return networkInfo;
                }


                @Override
                public void startDownloading() {
                    if (getView() != null)
                        ((NewsFragment) getView()).showConnectingText();
                }

                @Override
                public void stopDownloading() {
                    if (getView() != null)
                        ((NewsFragment) getView()).hideConnectingText();
                    downloadingFlag = false;
                }

                @Override
                public void errorWhileDownloading(int progress) {
                    if (getView() != null)
                        ((NewsFragment) getView()).showErrorConnection(progress);
                    downloadingFlag = false;
                }
            });
        }
    }


    /**
     * Getting basic list of news
     * @return
     */
    public ArrayList<News> getNewsList(){
        return modelNews.getNewsList();
    }
}
