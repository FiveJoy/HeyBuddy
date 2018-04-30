package com.fivejoy.heybuddy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fivejoy.heybuddy.food.FoodEntity;

/**
 * Created by Liulu on 2018/4/30.
 */

public class FoodDataDAO {
    private DBOpenHelper foodSqliteHelper;
    private SQLiteDatabase foodDb;
    public FoodDataDAO(Context context)
    {
        foodSqliteHelper = new DBOpenHelper(context);
    }
   /* private String food_name;
    private String food_sum;//食物总克数
    private String food_calorie_sum;//总卡路里数*/
    public  void addNewData(FoodEntity foodEntity,String tableName){
        foodDb = foodSqliteHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("food_name", foodEntity.getFood_name());
        values.put("food_sum", foodEntity.getFood_sum());
        values.put("food_calorie_sum", foodEntity.getFood_calorie_sum());
        foodDb.insert(tableName, null, values);
        foodDb.close();
    }

}
