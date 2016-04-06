package com.shs.global.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
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
import com.shs.global.control.UserManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenhai on 2016/3/24.
 */
public class BindNumActivity extends BaseActivity {
    public final static String INTENT_KEY = "userName";
    private boolean isFindPwd=false;
    @ViewInject(R.id.edit_phone)
    private EditText editText;
    @ViewInject(R.id.next)
    private Button nextButton;
    @ViewInject(R.id.find_pwd)
    private TextView findPwdText;
    @ViewInject(R.id.wechat_login)
    private TextView wxLoginText;
    @OnClick(value = {R.id.next,R.id.wechat_login,R.id.find_pwd})
    public void viewCickListener(View view) {
        switch (view.getId()) {
            case R.id.next:
                loginClick();
                break;
            case R.id.wechat_login:
                wechatLoginClick();
                break;
            case R.id.find_pwd:
                findPwd();
                break;
            default:
                break;
        }
    }

    private void findPwd() {
        isFindPwd=true;
        findPwdText.setVisibility(View.GONE);
        wxLoginText.setVisibility(View.GONE);
//        String userphone = editText.getText().toString().trim();
//        Intent intent = new Intent(BindNumActivity.this, RegisterActivity.class);
//        intent.putExtra(INTENT_KEY, userphone);
//        intent.putExtra("isFindPwd", isFindPwd);
//        startActivity(intent);
    }

    private void wechatLoginClick() {

    }

    private void loginClick() {
        String userphone = editText.getText().toString().trim();
        Pattern p=Pattern.compile(SHSConst.PHONENUMBER_PATTERN);
        Matcher m=p.matcher(userphone);
        if (m.matches()) {
            judgeRegister(userphone);
        }else {
            ToastUtil.show(this, "请输入合法的手机号码");
        }
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_bind_number;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void setUpView() {

    }
    private void judgeRegister(final String userphone) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", userphone);
        Log.i("wx", SHSConst.ISUSER + "?username=" + userphone);
        HttpManager.post(SHSConst.ISUSER, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                // TODO Auto-generated method stub
                super.onSuccess(jsonResponse, flag);
                Log.i("wx", jsonResponse.toJSONString() + "");
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONObject result = jsonResponse.getJSONObject(SHSConst.HTTP_RESULT);
                        String direction = result.getString("direction");
                        Log.i("wx",direction);
                        if ("2".equals(direction)) {
                            Intent intent = new Intent(BindNumActivity.this, RegisterActivity.class);
                            intent.putExtra(INTENT_KEY, userphone);
                            startActivity(intent);
                        } else {
                            if(UserManager.getInstance().beforeSaveContacts()){
                                Intent intent = new Intent(BindNumActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else  if (isFindPwd){
                                Intent intent = new Intent(BindNumActivity.this, RegisterActivity.class);
                                intent.putExtra(INTENT_KEY, userphone);
                                intent.putExtra("isFindPwd", isFindPwd);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(BindNumActivity.this, SecondLoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(INTENT_KEY, userphone);
                                startActivity(intent);
                            }
                        }
                        break;
                    case SHSConst.STATUS_FAIL:
                        hideLoading();
                        Toast.makeText(BindNumActivity.this, "网络错误",
                                Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                // TODO Auto-generated method stub
                super.onFailure(e, arg1, flag);
                //hideLoading();
                Toast.makeText(BindNumActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }, null));
    }
}
