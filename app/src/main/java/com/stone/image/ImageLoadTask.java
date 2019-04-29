package com.stone.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.stone.model.Stone;

public class ImageLoadTask extends AsyncTask<Void, Integer, Bitmap> {
    private Stone stone;
    private int type;
    @SuppressLint("StaticFieldLeak")
    private volatile ImageView imageView;

    public ImageLoadTask(Stone stone, int type, ImageView imageView) {
        this.stone = stone;
        this.type = type;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        //imageView.setImageResource(R.drawable.ic_empty_photo);
    }

    //private OkHttpClient okHttpClient;

    @Override
    protected Bitmap doInBackground(Void... voids) {
        return ImageManager.loadLocalImage(ImageManager.getImagePath(stone.chaName, type), imageView);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //Log.i("task", "onPostExecute" + stone.id);
        if (bitmap != null && imageView.getTag() == this) {
            if (type == ImageManager.TYPE_THUMBNAIL) {
                ImageManager.putThumbnailCache(stone.id, bitmap);
            } else if (type == ImageManager.TYPE_BIGIMAGE) {
                ImageManager.putBigImageCache(stone.chaName, bitmap);
            }

        }
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
            imageView.setTag(null);
            imageView = null;
        }
    }

    @Override
    protected void onCancelled(Bitmap bitmap) {
        imageView = null;
        if (bitmap != null) {
            if (type == ImageManager.TYPE_THUMBNAIL) {
                ImageManager.putThumbnailCache(stone.id, bitmap);
            } else if (type == ImageManager.TYPE_BIGIMAGE) {
                ImageManager.putBigImageCache(stone.chaName, bitmap);
            }
        }
    }
}
