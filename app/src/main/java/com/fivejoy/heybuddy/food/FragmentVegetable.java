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
public class FragmentVegetable extends Fragment {
    private static final int FOOD_NUMBER=1;
    private static final String TAG= MyApplication.fivejoy+"FragmentVegetable";

    ListView listView;
    ListViewAdapter adapter;
    private String whichOne;
    private MyItemClickListener myItemClickListener;
    public FragmentVegetable(){}
    @SuppressLint("ValidFragment")
    public FragmentVegetable(String whichOne){
        this.whichOne=whichOne;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myItemClickListener=new MyItemClickListener(FOOD_NUMBER,getContext(),whichOne);
        View v=inflater.inflate(R.layout.food_vegetables,container,false);
        listView=(ListView)v.findViewById(R.id.vegetables_list);
        adapter=new ListViewAdapter(getContext(),FOOD_NUMBER);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(myItemClickListener);
        return v;
    }
}
