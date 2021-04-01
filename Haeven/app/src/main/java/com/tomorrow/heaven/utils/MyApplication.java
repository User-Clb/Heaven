package com.tomorrow.heaven.utils;


import android.content.Context;

import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置日志级别
        LogUtil.level=LogUtil.DEBUG;
    }

}
