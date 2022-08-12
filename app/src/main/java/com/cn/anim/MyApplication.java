package com.cn.anim;

import android.app.Application;

import com.zz.route.Router;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
    }
}
