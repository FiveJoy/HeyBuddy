package com.fivejoy.heybuddy.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fivejoy.heybuddy.R;
import com.fivejoy.heybuddy.myview.CleanEditText;
import com.fivejoy.heybuddy.utils.RegexUtils;
import com.fivejoy.heybuddy.utils.ToastUtils;
import com.fivejoy.heybuddy.utils.VerifyCodeManager;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liulu on 2018/4/10.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.et_phone)
    CleanEditText etPhone;//编辑电话号
    @BindView(R.id.et_password)
    CleanEditText etPassword;//密码
    @BindView(R.id.et_verifiCode)
    CleanEditText etVerifiCode;//验证码
    @BindView(R.id.et_nickname)
    CleanEditText etNickname;//昵称

    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;//创建账户
    @BindView(R.id.btn_send_verifi_code)
    Button btnSendVerifiCode;//发送验证码

    private VerifyCodeManager codeManager;
    String result = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        initViews();
        codeManager = new VerifyCodeManager(this, etPhone, btnSendVerifiCode);
    }

    private void initViews() {
        btnSendVerifiCode.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
        etPhone.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        etVerifiCode.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        etNickname.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etPassword.setImeOptions(EditorInfo.IME_ACTION_GO);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                // 点击虚拟键盘的done
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    try {
                        commit();
                    } catch (IOException | JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verifi_code:
                // TODO 请求接口发送验证码
                codeManager.getVerifyCode(VerifyCodeManager.REGISTER);
                break;
            case R.id.btn_create_account:
                try {
                    commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

    private void commit() throws IOException, JSONException {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (checkInput(phone, password)) {
            // TODO:请求服务端注册账号
            btnCreateAccount.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //android4.0后的新的特性，网络数据请求时不能放在主线程中。
                    //使用线程执行访问服务器，获取返回信息后通知主线程更新UI或者提示信息。
                    final Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == 1) {
                                //提示读取结果
                                Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
                                if (result.contains("成")){
                                    Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
                                    ToastUtils.showShort(SignUpActivity.this,
                                            "注册成功......");
                                }
                                else{
                                    final Intent it = new Intent(SignUpActivity.this, LoginActivity.class); //你要转向的Activity
                                    Timer timer = new Timer();
                                    TimerTask task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            startActivity(it); //执行
                                        }
                                    };
                                    timer.schedule(task, 1000); //1秒后
                                }
                            }
                        }
                    };
                    // 启动线程来执行任务
                    new Thread() {
                        public void run() {
                            //请求网络
                            try {
                                Register(etPhone.getText().toString(),etPassword.getText().toString(),etNickname.getText().toString());
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                            Message m = new Message();
                            m.what = 1;
                            // 发送消息到Handler
                            handler.sendMessage(m);
                        }
                    }.start();
                }
            });
        }
    }

    private boolean checkInput(String phone, String password) {
        if (TextUtils.isEmpty(phone)) { // 电话号码为空
            ToastUtils.showShort(this, R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtils.checkMobile(phone)) { // 电话号码格式有误
                ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            }  else if (password == null || password.trim().equals("")) {
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
            }else if (password.length() < 6 || password.length() > 32
                    || TextUtils.isEmpty(password)) { // 密码格式
                ToastUtils.showShort(this,
                        R.string.tip_please_input_6_32_password);
            } else {
                return true;
            }
        }
        return false;
    }

    public Boolean Register(String account, String passWord, String niceName) throws IOException, JSONException {
        try {
            String httpUrl="http://cdz.ittun.cn/cdz/user_register.php";
            URL url = new URL(httpUrl);//创建一个URL
            HttpURLConnection connection  = (HttpURLConnection)url.openConnection();//通过该url获得与服务器的连接
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");//设置请求方式为post
            connection.setConnectTimeout(3000);//设置超时为3秒
            //设置传送类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            //提交数据
            String data = "&name=" + URLEncoder.encode(niceName, "UTF-8")+"&cardid="
                    + "&passwd=" +passWord+ "&money=0"+ "&number=" + account;//传递的数据
            connection.setRequestProperty("Content-Length",String.valueOf(data.getBytes().length));
            ToastUtils.showShort(this,
                    "数据提交成功......");

            //获取输出流
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            //获取响应输入流对象
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(is);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            //读取服务器返回信息
            while ((line = bufferedReader.readLine()) != null){
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            is.close();
            connection.disconnect();
        } catch (Exception e) {
            return true;
        }
        return false;
    }


}
