package com.fivejoy.heybuddy;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.fivejoy.heybuddy.widget.BottomSelectView;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements  BottomSelectView.OnItemClickListener {
    private static final String TAG=MyApplication.fivejoy+"MainActivity";
    public static int screenWidth=0;
    @BindArray(R.array.bottom_item_name) String[] itemNames;
    @BindArray(R.array.itemBeforeRes) int[] itemBeforeRes;
    @BindArray(R.array.itemAfterRes) int[] itemAfterRes;
    @BindColor(R.color.bottom_before_click) int beforeClickColor;
    @BindColor(R.color.bottom_after_click) int afterClickColor;
    @BindViews({R.id.fragment_sports,R.id.fragment_food,R.id.fragment_me})
    List<FrameLayout> frameLayouts;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.bottomSelectView)
    com.fivejoy.heybuddy.widget.BottomSelectView bottomSelectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        computeWidth();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG,"onCreate()");

        Log.d(TAG,"screenWidth="+screenWidth);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_sports);
        initBottomView();//底部三个模块


    }

    private void computeWidth() {
        Log.d(TAG,"begin to compute  screenWidth="+screenWidth);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
     //   screenHeight = display.getHeight();
    }


    private void initBottomView() {
        bottomSelectView.addChildViews(itemBeforeRes,itemAfterRes,
                beforeClickColor,afterClickColor,itemNames);
        bottomSelectView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        for (FrameLayout layout : frameLayouts){
            layout.setVisibility(View.GONE);
        }
        frameLayouts.get(position).setVisibility(View.VISIBLE);
    }
}
