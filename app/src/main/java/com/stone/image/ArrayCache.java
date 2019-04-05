package com.stone.image;

import android.graphics.Bitmap;

/**
 * 用数组实现的缩略图缓存类
 */
public class ArrayCache {
    private final Bitmap[] bitmaps;

    public ArrayCache(int size) {
        bitmaps = new Bitmap[size];
    }

    public void put(int index, Bitmap bitmap) {
        bitmaps[index] = bitmap;
    }

    public Bitmap get(int index) {
        return bitmaps[index];
    }

    public void clear() {
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i].recycle();
            bitmaps[i] = null;
        }
    }
}
