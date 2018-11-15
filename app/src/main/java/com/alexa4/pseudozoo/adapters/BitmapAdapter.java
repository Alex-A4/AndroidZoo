package com.alexa4.pseudozoo.adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
    public static void decodeBitmapFromUrl(String url, Resources res, boolean isRoundCorners,
                                           DownloadImageCallback callback) {
        AsyncDownloadBitmap downloadBitmap = new AsyncDownloadBitmap(url, res, isRoundCorners, callback);
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

        private String url;
        private Resources res;
        private DownloadImageCallback callback;
        private boolean mIsRoundCorners;

        AsyncDownloadBitmap(String url, Resources res, boolean isRoundCorners,
                            DownloadImageCallback callback){
            this.url = url;
            this.res = res;
            this.callback = callback;
            mIsRoundCorners = isRoundCorners;
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
            if (mIsRoundCorners)
                bitmap = getRoundedCornerBitmap(bitmap);

            callback.onDownloadFinished(bitmap);
        }
    }


    /**
     * Method to get image with round corners
     * @param bitmap the image which need to round
     * @return the round bitmap
     */
    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 25;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
