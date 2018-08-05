package com.ls.qdaliy.base;

import android.app.Application;

/**
 * Created by asus on 2018/5/26.
 * Describe:
 */
public class AppContext extends Application {
    private static AppContext instance;
    public static AppContext getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }
}
