package com.stone.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.stone.R;

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

    @Override
    protected void onPreExecute() {
        imageView.setImageResource(R.drawable.ic_empty_photo);
    }

    //private OkHttpClient okHttpClient;

    @Override
    protected Bitmap doInBackground(Void... voids) {
        File file = downloadImage(url, path);
        if (file != null) {
            return ImageManager.toBitmap(file, imageView.getLayoutParams().width);
        } else {
            Log.i("666", "下载失败");
        }
        return null;
    }

    private File downloadImage(String url, String path) {
        if (okHttpClient == null) okHttpClient = new OkHttpClient();
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
            InputStream inputStream = responseBody.byteStream();
            BufferedOutputStream bufferedStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[4096];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                bufferedStream.write(bytes, 0, len);
            }
            inputStream.close();
            bufferedStream.close();
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            ImageManager.putThumbnailCache(index, bitmap);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ic_empty_photo);
        }
        imageView = null;
    }
}
