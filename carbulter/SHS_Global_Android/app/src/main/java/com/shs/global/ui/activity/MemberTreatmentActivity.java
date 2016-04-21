package com.shs.global.ui.activity;

import com.shs.global.R;

/**
 * Created by wenhai on 2016/4/20.
 */
public class MemberTreatmentActivity extends BaseActivityWithTopBar {
    @Override
    public int setLayoutId() {
        return R.layout.activity_member_treatment;
    }

    @Override
    protected void setUpView() {
        setBarText("礼遇");
    }
}
