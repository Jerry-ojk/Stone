package com.stone.image;

import android.os.AsyncTask;

import com.stone.Data;
import com.stone.model.Stone;

import okhttp3.OkHttpClient;

public class ImageDownloadTask extends AsyncTask<Void, Integer, Void> {

    //private OkHttpClient okHttpClient;

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient okHttpClient = new OkHttpClient();
        for (Stone stone : Data.STONE_LIST) {
            if (stone.bigImageUrl != null) {
                ImageManager.downloadImage(okHttpClient, stone.bigImageUrl, ImageManager.getBigImagePath(stone));
            }
            if (stone.thumbnailUrl != null) {
                ImageManager.downloadImage(okHttpClient, stone.thumbnailUrl, ImageManager.getThumbnailPath(stone));
            }
        }
        return null;
    }
}
