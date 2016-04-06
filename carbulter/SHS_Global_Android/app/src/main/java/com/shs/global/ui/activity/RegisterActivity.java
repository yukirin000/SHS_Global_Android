package com.shs.global.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.utils.Md5Utils;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

/**
 * Created by wenhai on 2016/2/23.
 */
public class RegisterActivity extends BaseActivityWithTopBar {
    public final static String INTENT_KEY = "userName";
    private int userID;
    private String token;
    //短信验证码是是否发送成功
    private boolean SMSSuccess;
    // 是否是忘记密码
    private boolean isFindPwd;
    // 用户 的电话号码
    private String userPhoneNumber;
    // 用户 区号
    private String areaNumber;
    // 当前倒计时的值
    private int countdownValue = 0;
    // 倒计时对象
    private CountDownTimer verifyCountdownTimer = null;
    // 用户输入的验证码
    private String verifyCodeEditTextValue;
    // 提示电话的textview
    @ViewInject(R.id.phone_prompt_textview)
    private TextView phonePromptTextView;
    // 验证码输入框
    @ViewInject(R.id.verificationcode_edittext)
    private EditText verifycodeEditText;
    // 密码
    private String password = "";
    // 返回按钮
    @ViewInject(R.id.base_tv_back)
    private TextView backTextView;
    // 页面标头
    @ViewInject(R.id.base_tv_title)
    private TextView titletTextView;
    // 完成按钮
    @ViewInject(R.id.next_button)
    private Button nextButton;
    // 密码框
    @ViewInject(R.id.passwd_edittext)
    private EditText passwdeEditText;
    // 重新验证
    @ViewInject(R.id.revalidated_textview)
    private TextView revalidatedTextView;

    // 点击事件绑定
    @OnClick(value = {R.id.next_button,
            R.id.register_activity, R.id.revalidated_textview})
    public void viewCickListener(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                // 点击下一步
                nextClick();
                break;
            case R.id.revalidated_textview:
                //重新发送验证码
                getVerificationCode();
                timer();
                break;
            case R.id.register_activity:
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            0);
                } catch (Exception e) {

                }
                break;
            default:
                break;
        }
    }

    // 初始化数据
    private void init() {
        Intent intent = getIntent();
        userPhoneNumber = intent.getStringExtra(INTENT_KEY);
        isFindPwd = intent.getBooleanExtra("isFindPwd", false);
    }

    // 点击返回
    private void backClick() {
        finish();
    }

    // 点击下一步按钮
    private void nextClick() {
        password = passwdeEditText.getText().toString();
        // 判断输入值是否正确
        verifyCodeEditTextValue = verifycodeEditText.getText().toString().trim();
        // 判断输入值是否正确
        if (verifyCodeEditTextValue.length() == 0) {
            ToastUtil.show(RegisterActivity.this, getString(R.string.login_verification_not_null));
        } else if (password.length() < 6) {
            ToastUtil.show(RegisterActivity.this, getString(R.string.login_password_length_six));
        } else {
            // 忘记密码
            if (isFindPwd) {
                findPwd();
            } else {
                // 注册
                startRegister();
            }
        }
    }

    // 找回密码
    private void findPwd() {
        showLoading(getString(R.string.loading), false);
        finishPwd();
        hideLoading();


    }
        // 找回密码
        private void finishPwd() {
            RequestParams params = new RequestParams();
            params.addBodyParameter("username", userPhoneNumber);
            params.addBodyParameter("password", Md5Utils.encode(password));
            HttpManager.post(SHSConst.FINDPWDUSER, params,
                    new JsonRequestCallBack<String>(new LoadDataHandler<String>() {

                        @Override
                        public void onSuccess(JSONObject jsonResponse, String flag) {
                            super.onSuccess(jsonResponse, flag);
                            int status = jsonResponse
                                    .getInteger(SHSConst.HTTP_STATUS);
                            if (status == SHSConst.STATUS_SUCCESS) {
                                hideLoading();
                                JSONObject result = jsonResponse
                                        .getJSONObject(SHSConst.HTTP_RESULT);
                                // 设置用户实例
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                // 数据持久化

                            }
                            if (status == SHSConst.STATUS_FAIL) {
                                hideLoading();
                                ToastUtil.show(RegisterActivity.this, getString(R.string.net_error));
                            }
                        }

                        @Override
                        public void onFailure(HttpException arg0, String arg1,
                                              String flag) {
                            super.onFailure(arg0, arg1, flag);
                            hideLoading();
                            ToastUtil.show(RegisterActivity.this, getString(R.string.net_error));
                        }
                    }, null));
        }

    // 开始注册
    private void startRegister() {
        showLoading(getString(R.string.loading), false);
        finishRegister();
        hideLoading();
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_register_layout;
    }

    @Override
    protected void setUpView() {

        init();
        if (isFindPwd) {
            titletTextView.setText("修改密码");
        } else {
            titletTextView.setText("注册");
        }
        getVerificationCode();
        // RelativeLayout rlBar = (RelativeLayout)
        // findViewById(R.id.layout_base_title);
        // rlBar.setBackgroundResource(R.color.main_clear);
        timer();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (verifycodeEditText.getText().toString().length() > 0 && passwdeEditText.getText().toString().length() > 0) {
                    nextButton.setClickable(true);
                    nextButton.setTextColor(Color.WHITE);
                } else {
                    nextButton.setClickable(false);
                    nextButton.setTextColor(Color.GRAY);
                }
            }
        };

        verifycodeEditText.addTextChangedListener(textWatcher);
        passwdeEditText.addTextChangedListener(textWatcher);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void timer() {
        phonePromptTextView.setText("短信已发送至：" + userPhoneNumber);
        revalidatedTextView.setEnabled(false);
        verifyCountdownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownValue = (int) millisUntilFinished / 1000;
                revalidatedTextView.setText(countdownValue + "s " + "重发");
            }

            @Override
            public void onFinish() {
                countdownValue = 0;
                revalidatedTextView.setEnabled(true);
                revalidatedTextView.setText(getString(R.string.resend));
            }
        };
        // 开始倒计时
        verifyCountdownTimer.start();
        //处理按钮点击
        nextButton.setClickable(false);
        nextButton.setTextColor(Color.GRAY);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    // 获取验证码
    private void getVerificationCode() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone_num", userPhoneNumber);
        String path;
        if (isFindPwd) {
            path = SHSConst.FINDPWDSMS;
        } else {
            path = SHSConst.REGISTERSMS;
        }
        HttpManager.post(path, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        int result = jsonResponse.getInteger(SHSConst.HTTP_RESULT);
                        if (userPhoneNumber.equals(String.valueOf(result))) {
                            SMSSuccess = true;
                        } else {
                            SMSSuccess = false;
                        }
                        break;
                    case SHSConst.STATUS_FAIL:
                        SMSSuccess = false;
                        break;
                }

            }

            @Override
            public void onFailure(HttpException arg0, String arg1, String flag) {
                super.onFailure(arg0, arg1, flag);
                SMSSuccess = false;
            }
        }, null));


    }

    //验证成功 完成注册
    private void finishRegister() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", userPhoneNumber);
        params.addBodyParameter("password", Md5Utils.encode(password));
        params.addBodyParameter("code", verifyCodeEditTextValue);
        HttpManager.post(SHSConst.REGISTERUSER, params,
                new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
                    @Override
                    public void onSuccess(JSONObject jsonResponse, String flag) {
                        super.onSuccess(jsonResponse, flag);
                        int status = jsonResponse
                                .getInteger(SHSConst.HTTP_STATUS);
                        if (status == SHSConst.STATUS_SUCCESS) {
                            hideLoading();
                            JSONObject result = jsonResponse
                                    .getJSONObject(SHSConst.HTTP_RESULT);
                            // 设置用户实例
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        if (status == SHSConst.STATUS_FAIL) {
                            hideLoading();
                            ToastUtil.show(RegisterActivity.this, "未知错误");
                        }
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1,
                                          String flag) {
                        super.onFailure(arg0, arg1, flag);
                        hideLoading();
                        ToastUtil.show(RegisterActivity.this, "网络错误");
                    }

                }, null));
    }

    /**
     * 重写返回操作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            backClick();
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }


}
