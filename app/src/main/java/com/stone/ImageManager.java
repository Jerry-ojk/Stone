package com.stone;

import android.util.Log;

import com.stone.model.Stone;
import com.stone.model.StonePicture;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ImageManager {
    public static String[] ImageID = {"ktzeHHHT"};

    public static void loadImage(Stone stone) {
        loadImage(stone.id);
    }

    public static void loadImage(int index) {
        if (index >= ImageID.length) return;
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<StonePicture> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(ImageID[index], new QueryListener<StonePicture>() {
            @Override
            public void done(StonePicture stonePicture, BmobException e) {
                if (e == null) {
                    //File file = stonePicture.getImage().getLocalFile();
                    //Log.i("666", file.getAbsolutePath());
                    Log.i("666", stonePicture.getImage().getFileUrl());
                } else {
                    Log.i("666", "失败");
                    e.printStackTrace();
                }
            }
        });
    }
}
