package com.shs.global.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;

/**
 * Created by wenhai on 2016/3/25.
 */
public class MyLoveCarActivity extends BaseActivityWithTopBar {
    @ViewInject(R.id.call_telephone)
    private TextView button;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_love_car;
    }

    @Override
    protected void setUpView() {
        setBarText("我的爱车");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008693911"));
                startActivity(intent);
            }
        });
    }


}
