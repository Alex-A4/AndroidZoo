package com.alexa4.pseudozoo.models;

import android.os.AsyncTask;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.user_data.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelNews {
    private ArrayList<News> newsArrayList = new ArrayList<News>(Arrays.asList(
            new News("1 и 2 сентября - День знаний в Ярославском зоопарке!","02 августа 2018", R.drawable.firstseptember,"1 и 2 сентября 2018 года мы приглашаем мальчишек и девчонок, а так же их родителей на День знаний в Ярославский зоопарк! Для Вас - интерактивная экскурсия, лекторий, а так же возможность познаокмиться с нашим учебным центром!"),
            new News("Выездная выставка зоопарка в Левцово!", "02 августа 2018", R.drawable.levcovo,"3 августа 2018 года Ярославский зоопарк представил свою экспозицию на \"Дне ярославского поля\", а 4 августа мы ждем всех в \"Левцово\" на фестивале \"ТехноTravel\", где Вы сможете покататься верхом на верблюде!"),
            new News("Вкусное лакомство для обитаталей зоопарка", "01 июня 2018",R.drawable.lakomstvo,"Во время жаркой погоды сотрудники Ярославского зоопарка балуют обитаталей вкусным мороженым. В пятницу, 27.07.2018, все желающие наблюдали, как лакомятся вкусняшками наши Ума и Топа")
    ));


    /**
     * Method which calls from PresenterNews and returns callback
     * @param loadNewsCallback
     */
    public void loadNews(LoadNewsCallback loadNewsCallback){

        LoadNewsTask loadTask = new LoadNewsTask(loadNewsCallback);
        loadTask.execute();
    }


    /**
     * Method which inform about complete downloading of news
     */
    public interface LoadNewsCallback{
        void onLoad(ArrayList<News> list);
    }




    /**
     * Class to async download news from internet
     */
    class LoadNewsTask {

        LoadNewsCallback loadNewsCallback;

        LoadNewsTask(LoadNewsCallback loadNewsCallback){
            this.loadNewsCallback = loadNewsCallback;
        }

        /**
         * Later it will download news from internet
         */
        protected void execute(){
            ArrayList<News> list = newsArrayList;
            if (loadNewsCallback != null)
                loadNewsCallback.onLoad(list);
        }
    }


}
