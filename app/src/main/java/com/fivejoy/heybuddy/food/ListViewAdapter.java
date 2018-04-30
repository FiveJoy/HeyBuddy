package com.fivejoy.heybuddy.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivejoy.heybuddy.Constant;
import com.fivejoy.heybuddy.MyApplication;
import com.fivejoy.heybuddy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liulu on 2018/4/29.
 */

public class ListViewAdapter extends BaseAdapter {
    private int FOOD_NUMBER;
    private Context mContext;
    private LayoutInflater mInflater;
    public ArrayList<Map<String, Object>> list;

    protected ListViewAdapter(Context mContext, int food_number) {
        //this.fragment = fragment;
        this.mContext = mContext;
        this.FOOD_NUMBER=food_number;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<Map<String, Object>>();
        int i;
        switch(FOOD_NUMBER)
        {
            case 1:
                for(i=0; i< MyApplication.vegetable_image.length; i++)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("food_image", MyApplication.vegetable_image[i]);
                    map.put("food_name", MyApplication.vegetable_name[i]);
                    map.put("food_calorie", MyApplication.vegetable_calerie[i]);
                    list.add(map);
                }
                break;
            case 2:
                for(i=0;i<MyApplication.fruit_image.length;i++)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("food_image",MyApplication.fruit_image[i]);
                    map.put("food_name", MyApplication.fruit_name[i]);
                    map.put("food_calorie",MyApplication.fruit_calerie[i]);
                    list.add(map);
                }
                break;
            case 3:
                for(i=0;i<MyApplication.meat_image.length;i++)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("food_image", MyApplication.meat_image[i]);
                    map.put("food_name", MyApplication.meat_name[i]);
                    map.put("food_calorie", MyApplication.meat_calerie[i]);
                    list.add(map);
                }
                break;
            case 4:
                for(i=0;i<MyApplication.staple_image.length;i++)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("food_image", MyApplication.staple_image[i]);
                    map.put("food_name", MyApplication.staple_name[i]);
                    map.put("food_calorie", MyApplication.staple_calerie[i]);
                    list.add(map);
                }
                break;
        }

    }


    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Map<String,Object> getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.food_list_raw, null);
            holder.name_textView = (TextView)convertView.findViewById(R.id.food_name);
            holder.calorie_textView= (TextView) convertView.findViewById(R.id.food_calorie);
            holder.imageView=(ImageView)convertView.findViewById(R.id.food_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name_textView.setText( list.get(position).get("food_name").toString());
        holder.calorie_textView.setText( list.get(position).get("food_calorie").toString());
        holder.imageView.setImageResource((Integer) list.get(position).get("food_image"));
        return convertView;
    }



    public static  class ViewHolder {
        public TextView name_textView;
        public TextView calorie_textView;
        public ImageView imageView;
    }

}
