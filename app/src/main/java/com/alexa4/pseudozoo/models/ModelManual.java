package com.alexa4.pseudozoo.models;

import android.content.Context;
import android.os.AsyncTask;

import com.alexa4.pseudozoo.user_data.manual_data.ManualAnimalItem;
import com.alexa4.pseudozoo.user_data.manual_data.ManualAnimalsStore;
import com.alexa4.pseudozoo.user_data.manual_data.ManualItem;
import com.alexa4.pseudozoo.user_data.manual_data.ManualItemStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Singleton model to work with Zoo manuals
 */
public class ModelManual {
    private static ModelManual sManual;
    private final Context mContext;


    private ModelManual(Context context) {
        mContext = context;
    }

    public static ModelManual getManual(Context context) {
        if (sManual == null)
            sManual = new ModelManual(context);

        return sManual;
    }


    /**
     * Callback which notify about result of downloading
     */
    public interface DownloadManualCallback {
        void sendResult(boolean result);
    }

    /**
     * Method which call async downloading of manual from Zoo-web
     * @param callback the callback
     */
    public void downloadManual(DownloadManualCallback callback) {
        AsyncManualDownloading async = new AsyncManualDownloading(callback);
        async.execute();
    }


    /**
     * Class which implements async downloading of manuals
     * All downloaded items will add to ManualItemStore
     */
    private static class AsyncManualDownloading extends AsyncTask<Void, Void, Boolean> {

        private DownloadManualCallback mCallback;

        public AsyncManualDownloading(DownloadManualCallback callback) {
            mCallback = callback;
        }

        /**
         * Download information about manual from http://yar-zoo.ru/animals.html
         * @return the result of downloading
         */
        @Override
        protected Boolean doInBackground(Void... voids) {
            Document doc = null;

            try {
                //Downloading html page
                doc = Jsoup.connect("http://yar-zoo.ru/animals.html").get();

                ArrayList<ManualItem> list = new ArrayList<>();

                //Parsing page
                Elements item = doc.select(".subcategory-image");

                for (int i = 0; i < item.size(); i++) {
                    String title = item.get(i).select("a").attr("title");
                    title = title.replace("<br/>", "\n");
                    String imgSrc = item.get(i).select("img[src]")
                            .attr("src");;
                    String url = "http://yar-zoo.ru"
                            + item.get(i).select("a").attr("href");
                    list.add(new ManualItem(title, imgSrc, url));
                }

                //Add the list to store
                ManualItemStore.getStore().setItems(list);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        /**
         * Notify callback about downloading finish
         * @param aBoolean the result of downloading
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mCallback.sendResult(aBoolean);
        }
    }


    /**
     * Callback which notify about result of downloading
     */
    public interface DownloadAnimalsCallback {
        void sendResult(boolean response);
    }

    /**
     * Method to download page about selected animals group
     * @param url the url of selected animals group
     * @param callback the callback
     */
    public void downloadAnimalsPage(String url, DownloadAnimalsCallback callback) {
        AsyncAnimalsPageDownloading async = new AsyncAnimalsPageDownloading(callback, url);
        async.execute();
    }

    /**
     * Async class to download information about group of animals by input url
     */
    public static class AsyncAnimalsPageDownloading extends AsyncTask<Void, Void, Boolean> {
        private DownloadAnimalsCallback mCallback;
        private String mUrl;

        public AsyncAnimalsPageDownloading(DownloadAnimalsCallback callback, String url) {
            mCallback = callback;
            mUrl = url;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Document doc = null;

            try {
                //Downloading html page
                doc = Jsoup.connect("http://yar-zoo.ru/animals.html").get();

                ArrayList<ManualAnimalItem> list = new ArrayList<>();

                //Parsing page
                Elements item = doc.select(".item-image");

                for (int i = 0; i < item.size(); i++) {
                    String title = item.get(i).select("a").attr("title");
                    String imgSrc = item.get(i).select("img[src]")
                            .attr("src");;
                    String url = item.get(i).select("a").attr("href");
                    
                    list.add(new ManualAnimalItem(title, imgSrc, url));
                }

                //Add the list to store
                ManualAnimalsStore.getStore().setItems(list);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mCallback.sendResult(aBoolean);
        }
    }
}
