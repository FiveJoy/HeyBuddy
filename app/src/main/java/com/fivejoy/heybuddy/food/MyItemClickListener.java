package com.fivejoy.heybuddy.food;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivejoy.heybuddy.MyApplication;
import com.fivejoy.heybuddy.R;
import com.fivejoy.heybuddy.db.DBOpenHelper;
import com.fivejoy.heybuddy.db.FoodDataDAO;
import com.fivejoy.heybuddy.stepcounter.StepEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Liulu on 2018/4/29.
 */


public class MyItemClickListener implements AdapterView.OnItemClickListener {
    private static final String TAG = MyApplication.fivejoy + "MyItemClickListener";
    @BindView(R.id.food_image)
    ImageView foodImage;
    @BindView(R.id.food_calorieTv)
    TextView foodCalorieTv;
    @BindView(R.id.food_100_hanliangTv)
    TextView food100HanliangTv;
    @BindView(R.id.dialog_inputEt)
    EditText dialogInputEt;
    @BindView(R.id.food_sum_calorieTv)
    TextView foodSumCalorieTv;
    @BindView(R.id.foodSureBn)
    Button foodSureBn;
    @BindView(R.id.foodCancleBn)
    Button foodCancleBn;

    private int FOOD_NUMBER;
    private Context mContext;
    private String whichOne;

    private String  tableName;


    public MyItemClickListener(int FOOD_NUMBER, Context mContext, String whichOne) {
        this.FOOD_NUMBER = FOOD_NUMBER;
        this.mContext = mContext;
        this.whichOne = whichOne;
        tableName=whichOne+"_info";

        Log.d(TAG, "确定table=" + tableName);

    }


    private int position;
    DBOpenHelper mySQLiteHelper;

     AlertDialog finalDialog=null;//本来是加上final关键字的。。。


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position0, long id) {
        position = position0;
        initSelectedDialog();
    }








    private void initSelectedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View v = View.inflate(mContext, R.layout.food_selected_dialog, null);
        dialogInputEt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        AlertDialog dialog = null;

        switch(FOOD_NUMBER)
        {
            case 1:
                foodImage.setBackground(mContext.getResources().getDrawable(MyApplication.vegetable_image[position]));
                food100HanliangTv.setText(MyApplication.vegetable_calerie[position]);
                break;
            case 2:
                foodImage.setBackground(mContext.getResources().getDrawable(MyApplication.fruit_image[position]));
                food100HanliangTv.setText(MyApplication.fruit_calerie[position]);
                break;
            case 3:
                foodImage.setBackground(mContext.getResources().getDrawable(MyApplication.meat_image[position]));
                food100HanliangTv.setText(MyApplication.meat_calerie[position]);

                break;
            case 4:
                foodImage.setBackground(mContext.getResources().getDrawable(MyApplication.staple_image[position]));
                food100HanliangTv.setText(MyApplication.staple_calerie[position]);
                break;
        }
        builder.setView(v);
        //input.setHint("输入你的卡路里消耗目标");
        dialog = builder.create();
        dialog.show();
        finalDialog= dialog;
    }

    @OnClick({R.id.foodSureBn, R.id.foodCancleBn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.foodSureBn:{
                String dialog_Ed_input=dialogInputEt.getText().toString();
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(dialog_Ed_input);
                if(m.matches() ) {
                    String food_sum_string=dialog_Ed_input;
                    String food_calorie_string_single100="";
                    String food_name="";
                    switch (FOOD_NUMBER){
                        case 1:
                            food_calorie_string_single100=MyApplication.vegetable_calerie[position];
                            food_name=MyApplication.vegetable_name[position];
                            break;
                        case 2:
                            food_calorie_string_single100=MyApplication.fruit_calerie[position];
                            food_name=MyApplication.fruit_name[position];
                            break;
                        case  3:
                            food_calorie_string_single100=MyApplication.meat_calerie[position];
                            food_name=MyApplication.meat_name[position];
                            break;
                        case 4:
                            food_calorie_string_single100=MyApplication.staple_calerie[position];
                            food_name=MyApplication.staple_name[position];
                            break;
                    }
                }
                break;

            }

            case R.id.foodCancleBn:
                finalDialog.dismiss();
                break;
        }
    }

    private void saveFoodData(String tableName,FoodDataDAO foodDataDAO) {

    }

}

