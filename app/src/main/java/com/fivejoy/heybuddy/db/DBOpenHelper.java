package com.fivejoy.heybuddy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fivejoy.heybuddy.MyApplication;

/**
 * Created by fySpring
 * Date : 2017/1/16
 * To do :
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG= MyApplication.fivejoy+"DBOpenHelper";

    private static final String DB_NAME = "my_db"; //数据库名称
    private static final int DB_VERSION = 1;//数据库版本,大于0
    private static volatile DBOpenHelper dbHelper;

    /* private String food_name;
     private String food_sum;//食物总克数
     private String food_calorie_sum;//总卡路里数*/
    private static final String CREATE_WALK_STEP = "create table step ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "curDate TEXT, "
            + "totalSteps TEXT)";
    public static final String CREATE_RUN_DISTANCE="create table if not exists run_everyday_data("
            + "date Integer primary key,"
            + "run_distance Integer)";
    public static final String CREATE_BREAKFAST_INFO="create table if not exists breakfast_info("
            + "food_name text primary key,"
            + "food_calorie_sum Integer,"
            + "food_sum Integer)";
    public static final String CREATE_LUNCH_INFO="create table if not exists lunch_info("
            + "food_name text primary key,"
            + "food_calorie_sum Integer,"
            + "food_sum Integer)";
    public static final String CREATE_DINNER_INFO="create table if not exists dinner_info("
            + "food_name text primary key,"
            + "food_calorie_sum Integer,"
            + "food_sum Integer)";


    public DBOpenHelper(Context context)
    {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(TAG,"create SQLiteDatabase");
        db.execSQL(CREATE_WALK_STEP);
        db.execSQL(CREATE_RUN_DISTANCE);

        db.execSQL(CREATE_BREAKFAST_INFO);
        db.execSQL(CREATE_LUNCH_INFO);
        db.execSQL(CREATE_DINNER_INFO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("drop table if exists step");
        onCreate(db);
    }
}
