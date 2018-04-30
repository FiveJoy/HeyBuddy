package com.fivejoy.heybuddy.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.fivejoy.heybuddy.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Liulu on 2018/4/28.
 */

public class FoodActivity extends AppCompatActivity {
    @BindView(R.id.tabLayout)
    android.support.design.widget.TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.saveFood)
    Button saveFood;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_container);
        ButterKnife.bind(this);
        String whichOne=getWhichOne();
        initAdapter(whichOne);//通过选择的whichOne决定是添加到哪个 餐的数据中，与后期保存相关
    }

    private void initAdapter(        String whichOne) {
             FoodFragmentAdapter myAdapter = new FoodFragmentAdapter(getSupportFragmentManager());
        ArrayList<Fragment> lists = new ArrayList<Fragment>();
        lists.add(new FragmentVegetable(whichOne));
        lists.add(new FragmentFruit(whichOne));
        lists.add(new FragmentMeat(whichOne));
        lists.add(new FragmentStaple(whichOne));
        myAdapter.setData(lists);

        ArrayList<String> tabs = new ArrayList<String>();
        tabs.add("蔬菜");
        tabs.add("水果");
        tabs.add("肉类");
        tabs.add("主食");
        myAdapter.setTabs(tabs);
        //适配器和数据都有了，就可以放入viewpager并且和Tablayout进行关联了：
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);
        //关联viewPager和TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.saveFood)
    public void onViewClicked() {
        //保存数据的广播
    }

    public String getWhichOne() {
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        final String whichOne=bundle.getString("whichOne");//getString()返回指定key的值
        return whichOne;
    }
}
