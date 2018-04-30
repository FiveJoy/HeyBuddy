package com.fivejoy.heybuddy.food;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Liulu on 2018/4/29.
 */

public class FoodFragmentAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> foodFragment_lists;
    ArrayList<String> tabs;
    public FoodFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setData(ArrayList<Fragment> lists) {
        this.foodFragment_lists = lists;
    }

    public void setTabs(ArrayList<String> tabs) {
        this.tabs = tabs;
    }



    @Override
    public Fragment getItem(int position) {
        return foodFragment_lists == null ? null : foodFragment_lists.get(position);

    }

    @Override
    public int getCount() {
        return tabs == null ? 0 : tabs.size();
    }
    public CharSequence getPageTitle(int position) {
        return tabs == null ? null : tabs.get(position);
    }
}
