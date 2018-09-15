package com.alexa4.pseudozoo.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alexa4.pseudozoo.activities_package.FullNewsActivity;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.user_data.FullNews;

public class PresenterFullNews extends PresenterParent {
    private final ModelNews modelNews;

    /**
     * Setting model to presenter
     * @param model
     */
    public PresenterFullNews(ModelNews model) {
        this.modelNews = model;
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
     * Downloading news content
     * @param imageUrl
     */
    public void updateFullNews(String imageUrl){
        modelNews.loadFullNews(new ModelNews.LoadFullNewsCallback(){

            @Override
            public void retrieveResult(FullNews fullNews) {
                if (getView() != null)
                    ((FullNewsActivity) getView()).updateNews(fullNews);
            }

            @Override
            public NetworkInfo getInstanceNetworkInfo() {
                ConnectivityManager cm = (ConnectivityManager) ((FullNewsActivity) getView())
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                return networkInfo;
            }

            @Override
            public void startDownloading() {
                if (getView() != null)
                    ((FullNewsActivity) getView()).startDownloading();
            }

            @Override
            public void stopDownloading() {
                if (getView() != null)
                    ((FullNewsActivity) getView()).stopDownloading();
            }

            @Override
            public void errorWhileDownloading(int progress) {
                if (getView() != null)
                    ((FullNewsActivity) getView()).errorWhileDownloading(progress);
            }
        }, imageUrl);
    }

}
