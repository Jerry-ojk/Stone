package com.stone;

import android.app.Application;
import android.util.Log;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("666", "initialize1");
        Bmob.initialize(this, "47777f618d8d04b7d352703bb7944bb0");
        Log.i("666", "initialize");
    }
}
