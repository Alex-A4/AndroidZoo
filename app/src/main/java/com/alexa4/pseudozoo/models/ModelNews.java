package com.alexa4.pseudozoo.models;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.alexa4.pseudozoo.user_data.FullNews;
import com.alexa4.pseudozoo.user_data.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Model class which contains list of News and methods to exchange information with Presenter
 */
public class ModelNews {

    public interface Progress{
        int CONNECTING_ERROR = 1;
        int DOWNLOADING_ERROR = 2;
    }

    //List of news
    private final ArrayList<News> newsList = new ArrayList<>();


    /**
     * Method which calls from PresenterNews and returns callback
     * @param loadNewsCallback
     */
    public void loadNews(LoadNewsCallback loadNewsCallback){
        LoadNewsTask loadTask = new LoadNewsTask(loadNewsCallback);
        loadTask.execute();
    }

    public void loadFullNews(LoadFullNewsCallback loadFullNewsCallback, String newsUrl){
        LoadFullNewsTask loadFullNewsTask = new LoadFullNewsTask(loadFullNewsCallback, newsUrl);
        loadFullNewsTask.execute();
    }

    /**
     * Getting basic list of news
     * @return
     */
    public ArrayList<News> getNewsList(){
        return this.newsList;
    }


    /**
     * Interface which inform about complete downloading of news
     */
    public interface LoadNewsCallback {
        void retrieveResult(ArrayList<News> list);
        NetworkInfo getInstanceNetworkInfo();
        void startDownloading();
        void stopDownloading();
        void errorWhileDownloading(int progress);
    }

    public interface LoadFullNewsCallback {
        void retrieveResult(FullNews fullNews);
        NetworkInfo getInstanceNetworkInfo();
        void errorWhileDownloading(int progress);
    }

    /**
     * Class to async download news from link http://yar-zoo.ru/home/news.html
     */
    private class LoadNewsTask extends AsyncTask<String, Void, ArrayList<News>> {
        LoadNewsCallback loadNewsCallback;

        LoadNewsTask(LoadNewsCallback loadNewsCallback){
            this.loadNewsCallback = loadNewsCallback;
        }

        /**
         * Cancel background network operation if we do not have network connectivity.
         */
        @Override
        protected void onPreExecute() {
            if (loadNewsCallback != null) {
                NetworkInfo networkInfo = loadNewsCallback.getInstanceNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    loadNewsCallback.errorWhileDownloading(Progress.CONNECTING_ERROR);
                    cancel(true);
                }
            }
        }

        /**
         * Defines work to perform on the background thread
         * Downloading html page, parse it due to JSOUP then create list of news
         */
        @Override
        protected ArrayList<News> doInBackground(String... urls) {
            loadNewsCallback.startDownloading();

            boolean wasExist = newsList.size() == 0 ? false : true;

            Document doc = null;
            try {
                //Downloading html page
                doc = Jsoup.connect("http://yar-zoo.ru/home/news.html").get();

                //Parsing page
                Elements timeElement = doc.select(   ".element-itempublish_up");
                Elements headerElement = doc.select(".jbimage-link");
                Elements descriptionElement = doc.select("div.element");

                //Creating list of news
                for (int i = 0; i < headerElement.size(); i++){
                    String title = headerElement.get(i).attr("title");
                    String link = headerElement.get(i).attr("href");
                    String imageUrl = headerElement.get(i).select("img[src$=.jpg]")
                            .attr("src");
                    String time = timeElement.get(i).text();
                    String description = descriptionElement.get(i).text();
                    if (wasExist) {
                        if (!isContains(time))
                            newsList.add(0, new News(title, time, imageUrl, description, link));
                    } else newsList.add(new News(title, time, imageUrl, description, link));
                }
            } catch (IOException e) {
                //If reading error appear then call to view about it and return empty list
                e.printStackTrace();
                cancel(true);
                loadNewsCallback.errorWhileDownloading(Progress.DOWNLOADING_ERROR);
                return null;
            }

            return newsList;
        }


        private boolean isContains(String time){
            for (News news: newsList){
                if (news.getTime().equals(time))
                    return true;
            }
            return false;
        }

        /**
         * Updates the LoadNewsCallback with the result.
         */
        @Override
        protected void onPostExecute(ArrayList<News> result) {
            if (result != null && loadNewsCallback != null) {
                loadNewsCallback.stopDownloading();
                loadNewsCallback.retrieveResult(result);
            }
        }
    }





    /**
     * Defines work to perform on the background thread
     * Downloading full news information which can contains on the web page
     */
    private class LoadFullNewsTask extends AsyncTask<Void, Void, FullNews> {

        String url;
        LoadFullNewsCallback callback;

        LoadFullNewsTask(LoadFullNewsCallback callback, String url){
            this.callback = callback;
            this.url = url;
        }


        /**
         * Cancel background network operation if we do not have network connectivity.
         */
        @Override
        protected void onPreExecute() {
            if (callback != null) {
                NetworkInfo networkInfo = callback.getInstanceNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    callback.errorWhileDownloading(Progress.CONNECTING_ERROR);
                    cancel(true);
                }
            }
        }

        @Override
        protected FullNews doInBackground(Void... voids) {

            FullNews fullNews;

            Document doc = null;

            try {
                //Downloading html page
                doc = Jsoup.connect(this.url).get();

                //Parsing page
                Elements titleElement = doc.select(".jbimage-link");
                Elements fullTextElement = doc.select(".element-textarea").select("p");
                Elements gallery = doc.select(".element-jbgallery").select("a[href$=.jpg]");
                Elements galleryLowQuality = gallery.select("img[src$=.jpg]");

                String title = titleElement.get(0).attr("title");
                String imageUrl = titleElement.get(0).select("a[href$=.jpg]")
                        .attr("href");

                String fullText = "";
                for (int i = 0; i < fullTextElement.size(); i++)
                    fullText = fullText.concat(fullTextElement.get(i).text() + "\n\n");

                System.out.println(fullText);

                fullNews = new FullNews(title, fullText, imageUrl);

                if (gallery.size() > 0) {
                    ArrayList<String> galleryListHighQuality = new ArrayList<>();
                    ArrayList<String> galleryListLowQuality = new ArrayList<>();

                    for (Element element : gallery)
                        galleryListHighQuality.add(element.attr("href"));
                    for (Element element: galleryLowQuality)
                        galleryListLowQuality.add(element.attr("src"));
                    fullNews.setListUrlOfImagesLowQuality(galleryListLowQuality);
                    fullNews.setListUrlOfImagesHighQuality(galleryListHighQuality);
                }

            } catch (IOException e) {
                //If reading error appear then call to view about it and return empty list
                e.printStackTrace();
                cancel(true);
                callback.errorWhileDownloading(Progress.DOWNLOADING_ERROR);
                return null;
            }


            return fullNews;
        }

        /**
         * Update the LoadFullNewsCallback with the result
         * @param result
         */
        @Override
        protected void onPostExecute(FullNews result) {
            if (result != null && callback != null) {
                callback.retrieveResult(result);
            }
        }
    }


}
