package com.fivejoy.heybuddy;

import android.app.Application;

/**
 * Created by Administrator on 2018/4/14.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static Application getInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}