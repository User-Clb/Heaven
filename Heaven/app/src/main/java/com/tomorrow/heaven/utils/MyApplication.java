package com.tomorrow.heaven.utils;


import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;


public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //设置日志级别
        LogUtil.level=LogUtil.INFO;
        LitePal.initialize(this);
    }

    public static Context getContext(){
        return context;
    }

}
