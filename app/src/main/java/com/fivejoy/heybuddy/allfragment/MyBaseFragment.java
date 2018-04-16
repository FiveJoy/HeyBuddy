package com.fivejoy.heybuddy.allfragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.fivejoy.heybuddy.MyApplication;

/**
 * Created by Administrator on 2018/4/14.
 */

public class MyBaseFragment extends Fragment{
        private Activity activity;

        public Context getContext(){
            if(activity == null){
                return MyApplication.getInstance();
            }
            return activity;
        }

}
