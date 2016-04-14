package com.shs.global.ui.activity;

import com.shs.global.R;

/**
 * Created by wenhai on 2016/4/14.
 */
public class CarTypeSubActivity extends BaseActivityWithTopBar {
    @Override
    public int setLayoutId() {
        return R.layout.activity_car_type_sub;
    }
    @Override
    protected void setUpView() {
        setBarText("车型选择");
    }
}
