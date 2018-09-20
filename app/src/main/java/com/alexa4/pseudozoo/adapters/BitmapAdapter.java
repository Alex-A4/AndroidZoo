package com.alexa4.pseudozoo.adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.alexa4.pseudozoo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Class to convert image url to Bitmap
 */
public class BitmapAdapter extends BitmapFactory{

    /**
     * Calling download a bitmap
     * @param url of news image
     * @param res link to project Resources
     * @param callback callback interface which notify that download finished
     */
    public static void decodeBitmapFromUrl(String url, Resources res, DownloadImageCallback callback) {
        AsyncDownloadBitmap downloadBitmap = new AsyncDownloadBitmap(url, res, callback);
        downloadBitmap.execute();
    }


    /**
     * Notify that download finished and send the downloaded bitmap
     */
    public interface DownloadImageCallback{
        void onDownloadFinished(Bitmap bitmap);
    }


    /**
     * Asynchronous downloading the bitmap with specified url
     */
    static class AsyncDownloadBitmap extends AsyncTask<Void, Void, Bitmap> {

        String url;
        Resources res;
        DownloadImageCallback callback;

        AsyncDownloadBitmap(String url, Resources res, DownloadImageCallback callback){
            this.url = url;
            this.res = res;
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                return decodeStream((InputStream) new URL(url).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BitmapFactory.decodeResource(res, R.drawable.wrong_url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            callback.onDownloadFinished(bitmap);
        }
    }
}
