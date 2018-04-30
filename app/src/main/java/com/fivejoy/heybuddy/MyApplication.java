package com.fivejoy.heybuddy;

import android.app.Application;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/14.
 */
public  class MyApplication extends Application {

    private static final String TAG=MyApplication.fivejoy+"MyApplication";
    public static final String fivejoy="fj";
    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVER = 1;

    private static MyApplication mInstance;

    //食物 总更新
    public static  int ilunch_calorie=0;
    public static  int ibreakfast_calorie=0;
    public static  int idinner_calorie=0;
    public static  int isum_calorie=0;
    //今日是否已经添加过XXX饭了,true表示未添加
    public static boolean breakfastHas=true;
    public static boolean lunchHas=true;
    public static boolean dinnerHas=true;






    public static Application getInstance(){
        Log.d(TAG,"getInstance()方法执行");
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
    public static int[] vegetable_image={R.drawable.fvshengcai,R.drawable.fvtonghao,R.drawable.fvlajiao,R.drawable.fvonion,R.drawable.fvpotato,R.drawable.fvtomato,R.drawable.fvcaihua,R.drawable.fvhuanggua};
    public static String[] vegetable_name={"生菜","茼蒿","辣椒","洋葱","土豆","西红柿","菜花","黄瓜",};
    public static String[] vegetable_calerie={"54","54","38","40","77","20","26","16"};
    public static int[] fruit_image={R.drawable.ffapple,R.drawable.ffboluo,R.drawable.ffcaomei,R.drawable.ffmihoutao,R.drawable.ffchengzi,R.drawable.ffshiliu,R.drawable.ffxiangjiao,R.drawable.ffwatermalen,R.drawable.ffyingtao};
    public static String[] fruit_name={"苹果","菠萝","草莓","猕猴桃","橙子","石榴","香蕉","西瓜","樱桃"};
    public static String[] fruit_calerie={"54","44","32","61","48","73","93","26","46"};
    public static  int[] meat_image={R.drawable.fmbeefpai,R.drawable.fmchickenpai,R.drawable.fmduck,R.drawable.fmfish,R.drawable.fmyangrou};
    public static String[] meat_name={"牛排","鸡排","鸭肉","鱼肉","羊肉"};
    public static  String[] meat_calerie={"54","54","54","54","54"};
    public static int[] staple_image={R.drawable.fsegg,R.drawable.fshongshu,R.drawable.fsmantou,R.drawable.fsmifan,R.drawable.fsnoodle,R.drawable.fsmilk,R.drawable.fsxiaomizhou,R.drawable.fsyanmai,R.drawable.fsyumi};
    public static String[] staple_name={"鸡蛋","红薯","馒头","米饭","面条","牛奶","小米粥","燕麦","油条","玉米"};
    public static  String[] staple_calerie={"144","102","233","116","112","46","377","388","112"};


}