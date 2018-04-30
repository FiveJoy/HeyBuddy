package com.fivejoy.heybuddy.food;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fivejoy.heybuddy.MyApplication;
import com.fivejoy.heybuddy.R;

/**
 * Created by Liulu on 2018/4/29.
 */

@SuppressLint("ValidFragment")
public class FragmentMeat extends Fragment {
    private static final int FOOD_NUMBER=1;
    private static final String TAG= MyApplication.fivejoy+"FragmentMeat";

    ListView listView;
    ListViewAdapter adapter;
    private String whichOne;//表示具体是早餐、午餐、还是晚餐
    private MyItemClickListener myItemClickListener;
    public FragmentMeat(){}
    @SuppressLint("ValidFragment")
    public FragmentMeat(String whichOne){
        this.whichOne=whichOne;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myItemClickListener=new MyItemClickListener(FOOD_NUMBER,getContext(),whichOne);
        View v=inflater.inflate(R.layout.food_meat,container,false);
        listView=(ListView)v.findViewById(R.id.meat_list);
        adapter=new ListViewAdapter(getContext(),FOOD_NUMBER);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(myItemClickListener);
        return v;
    }
}

