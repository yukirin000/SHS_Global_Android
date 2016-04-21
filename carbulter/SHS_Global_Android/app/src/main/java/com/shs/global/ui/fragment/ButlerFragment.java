package com.shs.global.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.control.UserManager;
import com.shs.global.ui.activity.AboutUsActivity;
import com.shs.global.ui.activity.InformActivity;
import com.shs.global.ui.view.PromptAlertDialog;

/**
 * Created by wenhai on 2016/3/24.
 */
public class ButlerFragment extends BaseFragment {
    @ViewInject(R.id.call_butler)
    private RelativeLayout callButler;
    @ViewInject(R.id.call_number)
    private TextView callnumber;
    @ViewInject(R.id.notices)
    private ImageView noticesImage;
    @OnClick(value = {R.id.call_butler, R.id.about,R.id.notices})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.call_butler:
                String phoneNum = callnumber.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                startActivity(intent);
                break;
            case R.id.about:
                jumpAboutUs();
                break;
            case R.id.notices:
                jumpNotices();
                break;
        }
    }

    private void jumpNotices() {
        if(UserManager.getInstance().isUser()) {
             Intent intent=new Intent(getActivity(), InformActivity.class);
            startActivity(intent);
        }else {
            PromptAlertDialog dialog=new PromptAlertDialog(getActivity(),"提示");
            dialog.show();
        }

    }

    private void jumpAboutUs() {
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);
        startActivity(intent);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_butler;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {

    }
}
