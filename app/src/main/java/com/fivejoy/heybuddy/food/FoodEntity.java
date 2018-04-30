package com.fivejoy.heybuddy.food;

/**
 * Created by Liulu on 2018/4/30.
 */

public class FoodEntity {
    private String food_name;
    private int food_sum;//食物总克数
    private int food_calorie_sum;//总卡路里数

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_sum() {
        return food_sum;
    }

    public void setFood_sum(int food_sum) {
        this.food_sum = food_sum;
    }

    public int getFood_calorie_sum() {
        return food_calorie_sum;
    }

    public void setFood_calorie_sum(int food_calorie_sum) {
        this.food_calorie_sum = food_calorie_sum;
    }

    public FoodEntity() {
    }



    @Override
    public String toString() {
        return "FoodEntity{" +
                "food_name='" + food_name +
                ", food_sum=" + food_sum +
                ", food_calorie_sum=" + food_calorie_sum +
                '}';
    }
}
