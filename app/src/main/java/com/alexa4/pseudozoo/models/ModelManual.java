package com.alexa4.pseudozoo.models;

import android.content.Context;
import android.os.AsyncTask;


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
}
