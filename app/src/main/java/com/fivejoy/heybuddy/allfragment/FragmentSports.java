package com.fivejoy.heybuddy.allfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivejoy.heybuddy.MyApplication;
import com.fivejoy.heybuddy.R;
import com.fivejoy.heybuddy.db.StepDataDao;
import com.fivejoy.heybuddy.stepcounter.StepCountCheckUtil;
import com.fivejoy.heybuddy.stepcounter.StepEntity;
import com.fivejoy.heybuddy.stepcounter.TimeUtils;
import com.fivejoy.heybuddy.stepcounter.calendar.MyCalendarView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/25.
 */

public class FragmentSports extends MyBaseFragment {
    private Unbinder unbinder;
    View parentView;
    @BindView(R.id.movement_records_calender_ll)
    LinearLayout movementRecordsCalenderLl;
    @BindView(R.id.movement_total_km_tv)
    TextView movementTotalKmTv;
    @BindView(R.id.movement_total_km_time_tv)
    TextView movementTotalKmTimeTv;
    @BindView(R.id.movement_total_steps_tv)
    TextView movementTotalStepsTv;
    @BindView(R.id.movement_total_steps_time_tv)
    TextView movementTotalStepsTimeTv;
    @BindView(R.id.tv_step_goal)
    TextView tvStepGoal;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.is_support_tv)
    TextView isSupportTv;

    /**
     * 屏幕长度和宽度
     */
    public static int screenWidth, screenHeight;

    private MyCalendarView calenderView;

    private String curSelDate;
    private DecimalFormat df = new DecimalFormat("#.##");
    private List<StepEntity> stepEntityList = new ArrayList<>();
    private StepDataDao stepDataDao;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_sports, container, false);
        unbinder=  ButterKnife.bind(this, parentView);
        initStepData();
        return parentView;
    }


    private void initStepData() {
        curSelDate = TimeUtils.getCurrentDate();
        calenderView=new MyCalendarView(MyApplication.getInstance());
       movementRecordsCalenderLl.addView(calenderView);
        /**
         * 这里判断当前设备是否支持计步
         */
        if (StepCountCheckUtil.isSupportStepCountSensor(getContext())) {
            getRecordList();
            isSupportTv.setVisibility(View.GONE);
            setDatas();
            //setupService();
        } else {
            movementTotalStepsTv.setText("0");
            isSupportTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置记录数据
     *
     */
    private void setDatas() {
        StepEntity stepEntity = stepDataDao.getCurDataByDate(curSelDate);

        if (stepEntity != null) {
            int steps = Integer.parseInt(stepEntity.getSteps());

            //获取全局的步数
            movementTotalStepsTv.setText(String.valueOf(steps));
            //计算总公里数
            movementTotalKmTv.setText(countTotalKM(steps));
        } else {
            //获取全局的步数
            movementTotalStepsTv.setText("0");
            //计算总公里数
            movementTotalKmTv.setText("0");
        }
        //设置时间
        String time = TimeUtils.getWeekStr(curSelDate);
        movementTotalKmTimeTv.setText(time);
        movementTotalStepsTimeTv.setText(time);
    }

    /**
     * 简易计算公里数，假设一步大约有0.7米-----后续根据用户信息调整
     *
     * @param steps 用户当前步数
     * @return
     */
    private String countTotalKM(int steps) {
        double totalMeters = steps * 0.7;
        //保留两位有效数字
        return df.format(totalMeters / 1000);
    }


    /**
     * 获取全部运动历史纪录
     */
    private void getRecordList() {
        //获取数据库
        stepDataDao = new StepDataDao(getContext());
        stepEntityList.clear();
        stepEntityList.addAll(stepDataDao.getAllDatas());
        if (stepEntityList.size() >= 7) {
            // TODO: 在这里获取历史记录条数，当条数达到7条或以上时，就开始删除第七天之前的数据,暂未实现
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
