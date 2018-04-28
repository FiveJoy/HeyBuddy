package com.fivejoy.heybuddy;

import android.app.Application;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/14.
 */
public  class MyApplication extends Application {
    private static final String TAG=MyApplication.fivejoy+"MyApplication";
    private static MyApplication mInstance;
    public static final String fivejoy="fj";

    public static Application getInstance(){
        Log.d(TAG,"getInstance()的函数就是不执行么？？？？");
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}