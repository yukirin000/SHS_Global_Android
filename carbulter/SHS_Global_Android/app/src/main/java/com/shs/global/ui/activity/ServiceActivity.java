package com.shs.global.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;

/**
 * Created by wenhai on 2016/4/7.
 */
public class ServiceActivity extends BaseActivityWithTopBar{
    @ViewInject(R.id.call_telephone)
    private LinearLayout button;

    @Override
    public int setLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void setUpView() {
            setBarText("服务");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008693911"));
                startActivity(intent);
            }
        });
    }
}
