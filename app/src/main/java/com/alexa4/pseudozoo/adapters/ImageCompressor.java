package com.alexa4.pseudozoo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;

import com.alexa4.pseudozoo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import id.zelory.compressor.Compressor;

public class ImageCompressor {
    public interface BitmapCompressorCallback {
        void sendCompressedBmp(Bitmap bmp);
    }

    /**
     * Getting compressed image by the url
     * @param context the context of app
     * @param url the url of image
     * @param isRoundCorners the value which define rounded corners
     * @param callback the callback to return image
     */
    public static void getCompressedImage(Context context, String url, boolean isRoundCorners,
                                          BitmapCompressorCallback callback) {
        BitmapAdapter.decodeBitmapFromUrl(url, context.getResources(), false,
                new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                AsyncImageCompressor compressor = new AsyncImageCompressor(context, bitmap,
                        isRoundCorners, callback);
                compressor.execute();
            }
        });
    }

    private static class AsyncImageCompressor extends AsyncTask<Void, Void, Bitmap> {

        private BitmapCompressorCallback callback;
        private Context context;
        private Bitmap bmp;
        private boolean mIsRoundedCorners;

        public AsyncImageCompressor(Context context, Bitmap bmp, boolean isRoundedCorners,
                                    BitmapCompressorCallback callback) {
            this.callback = callback;
            this.context = context;
            this.bmp = bmp;
            mIsRoundedCorners = isRoundedCorners;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {

                //Creating byteArray of bitmap
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 10, bytes);

                //Creating new temp file
                File file = new File(context.getFilesDir().getAbsolutePath() + File.separator +
                        "temp" + bmp.hashCode() + ".jpg");
                FileOutputStream fOut = null;
                file.createNewFile();

                //write the bytes in file
                fOut = new FileOutputStream(file);
                fOut.write(bytes.toByteArray());

                fOut.close();

                //get compressed bitmap
                Bitmap bitmap = new Compressor(context)
                        .setQuality(10)
                        .compressToBitmap(file);

                if (mIsRoundedCorners)
                    return getRoundedCornerBitmap(bitmap);

                return bitmap;
            } catch (Exception e) {
                //If error appeared
                return BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.wrong_url);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            callback.sendCompressedBmp(bitmap);
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
