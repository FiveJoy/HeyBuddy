package com.fivejoy.heybuddy.allfragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivejoy.heybuddy.MyApplication;
import com.fivejoy.heybuddy.R;
import com.fivejoy.heybuddy.db.StepDataDao;
import com.fivejoy.heybuddy.stepcounter.StepCountCheckUtil;
import com.fivejoy.heybuddy.stepcounter.StepEntity;
import com.fivejoy.heybuddy.stepcounter.StepService;
import com.fivejoy.heybuddy.stepcounter.TimeUtils;
import com.fivejoy.heybuddy.stepcounter.calendar.MyCalendarView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/25.
 */

public class FragmentSports extends MyBaseFragment  implements android.os.Handler.Callback{
    private static final String TAG= MyApplication.fivejoy+"FragmentSports";

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
        Log.d(TAG,"Create **********************************");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_sports, container, false);
        unbinder=  ButterKnife.bind(this, parentView);
        initStepData();
        initListener();
        Log.d(TAG,"Create Views**********************************");
        return parentView;
    }


    private void initStepData() {
        curSelDate = TimeUtils.getCurrentDate();
        calenderView=new MyCalendarView(getActivity());
       movementRecordsCalenderLl.addView(calenderView);
        /**
         * 这里判断当前设备是否支持计步
         */
        if (StepCountCheckUtil.isSupportStepCountSensor(getActivity())) {
            getRecordList();
            isSupportTv.setVisibility(View.GONE);
            setDatas();
            setupService();
        } else {
            movementTotalStepsTv.setText("0");
            isSupportTv.setVisibility(View.VISIBLE);
        }
    }
    private void initListener() {
        calenderView.setOnBoaCalenderClickListener(new MyCalendarView.BoaCalenderClickListener() {
            @Override
            public void onClickToRefresh(int position, String curDate) {
                //获取当前选中的时间
                curSelDate = curDate;
                //根据日期去取数据
                setDatas();
            }
        });
    }

    /**
     * 设置记录数据
     *
     */
    private void setDatas() {
        StepEntity stepEntity = stepDataDao.getCurDataByDate(curSelDate);

        if (stepEntity != null) {
            Log.d(TAG,"stepEntity !=null ----->设置view");
            int steps = Integer.parseInt(stepEntity.getSteps());

            //获取全局的步数
            movementTotalStepsTv.setText(String.valueOf(steps));
            //计算总公里数
            movementTotalKmTv.setText(countTotalKM(steps));
        } else {
            Log.d(TAG,"stepEntity ====null ----->设置view = 0");
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
        stepDataDao = new StepDataDao(getActivity());
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
    private boolean isBind = false;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Messenger messenger;
    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(getActivity(), StepService.class);
        isBind = getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
        getActivity().startService(intent);
    }


    @Override
    public boolean handleMessage(Message msg) {
        {
            switch (msg.what) {
                //这里用来获取到Service发来的数据
                case MyApplication.MSG_FROM_SERVER:

                    //如果是今天则更新数据
                    if (curSelDate.equals(TimeUtils.getCurrentDate())) {
                        //记录运动步数
                        int steps = msg.getData().getInt("steps");
                        //设置的步数
                        movementTotalStepsTv.setText(String.valueOf(steps));
                        //计算总公里数
                        movementTotalKmTv.setText(countTotalKM(steps));
                    }
                    break;
            }
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //记得解绑Service，不然多次绑定Service会异常
        if (isBind) getActivity().unbindService(conn);
    }
    /**
     * 定时任务
     */
    private TimerTask timerTask;
    private Timer timer;
    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    private ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
            /**
             * 设置定时器，每个三秒钟去更新一次运动步数
             */
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        messenger = new Messenger(service);
                        Message msg = Message.obtain(null, MyApplication.MSG_FROM_CLIENT);
                        msg.replyTo = mGetReplyMessenger;
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 0, 3000);
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
