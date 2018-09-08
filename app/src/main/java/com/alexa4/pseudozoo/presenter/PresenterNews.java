package com.alexa4.pseudozoo.presenter;

import com.alexa4.pseudozoo.activities_package.MainFragment;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.user_data.News;

import java.util.ArrayList;

public class PresenterNews extends PresenterParent{
    private final ModelNews modelNews;

    public PresenterNews(ModelNews model) {
        this.modelNews = model;
    }

    @Override
    public void setView(ViewInterfaceParent view) {
        super.setView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void getNewsList(){
        modelNews.loadNews(new ModelNews.LoadNewsCallback() {
            @Override
            public void onLoad(ArrayList<News> list) {
                ((MainFragment) getview()).updateNewsList(list);
            }
        });
    }
}
