package com.stone;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "47777f618d8d04b7d352703bb7944bb0");
    }
}
