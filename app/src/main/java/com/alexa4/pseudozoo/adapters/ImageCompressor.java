package com.alexa4.pseudozoo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public static void getCompressedImage(Context context, String url,
                                          BitmapCompressorCallback callback) {
        BitmapAdapter.decodeBitmapFromUrl(url, context.getResources(),
                new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                AsyncImageCompressor compressor = new AsyncImageCompressor(context, bitmap, callback);
                compressor.execute();
            }
        });
    }

    private static class AsyncImageCompressor extends AsyncTask<Void, Void, Bitmap> {

        private BitmapCompressorCallback callback;
        private Context context;
        private Bitmap bmp;

        public AsyncImageCompressor(Context context, Bitmap bmp, BitmapCompressorCallback callback) {
            this.callback = callback;
            this.context = context;
            this.bmp = bmp;
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
}
