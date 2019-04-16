package com.stone.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ImageDownloadTask extends AsyncTask<Void, Integer, Bitmap> {
    public static OkHttpClient okHttpClient;
    private int index;
    private String url;
    private String path;
    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;

    public ImageDownloadTask(int index, String url, String path, ImageView imageView) {
        this.index = index;
        this.url = url;
        this.path = path;
        this.imageView = imageView;
    }

//    @Override
//    protected void onPreExecute() {
//        //imageView.setImageResource(R.drawable.ic_empty_photo);
//    }

    //private OkHttpClient okHttpClient;

    @Override
    protected Bitmap doInBackground(Void... voids) {
        File file = downloadImage(url, path);
        if (file != null && !isCancelled()) {
            return ImageManager.toBitmap(file, imageView.getLayoutParams().width);
        }
        return null;
    }
    private File downloadImage(String url, String path) {
        if (okHttpClient == null) okHttpClient = new OkHttpClient();
        InputStream inputStream = null;
        BufferedOutputStream bufferedStream = null;
        try {
            File file = new File(path + "_temp");
            if (file.exists() && !file.delete()) {
                return null;
            } else if (!file.createNewFile()) {
                return null;
            }
            Request request = new Request.Builder().url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null || responseBody.contentLength() == 0) {
                return null;
            }
            inputStream = responseBody.byteStream();
            bufferedStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[4096];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                bufferedStream.write(bytes, 0, len);
            }
            File realFile = new File(path);
            if (!realFile.exists() || realFile.delete()) {
                if (file.renameTo(new File(path))) {
                    return realFile;
                } else {
                    return file;
                }
            }
            return null;
        } catch (IOException e) {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedStream != null) {
                    bufferedStream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && imageView.getTag() == this) {
            ImageManager.putThumbnailCache(index, bitmap);
            imageView.setImageBitmap(bitmap);
        }
        imageView = null;
    }

}
