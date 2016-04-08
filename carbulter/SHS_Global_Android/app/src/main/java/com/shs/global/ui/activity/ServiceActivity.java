package com.shs.global.ui.activity;

import com.shs.global.R;

/**
 * Created by wenhai on 2016/4/7.
 */
public class ServiceActivity extends BaseActivityWithTopBar{
    @Override
    public int setLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void setUpView() {
            setBarText("服务");
    }
}
