package com.shs.global.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.ui.fragment.ButlerFragment;
import com.shs.global.ui.fragment.HistoryFragment;
import com.shs.global.ui.fragment.MenbersFragment;
import com.shs.global.ui.fragment.PrivilegeFragment;
import com.shs.global.ui.service.LocationService;

public class MainActivity extends BaseActivity {
    //fragment的容器
    @ViewInject(R.id.contain_layout)
    FrameLayout containLayout;
    //管家
    @ViewInject(R.id.butler)
    TextView bulerTextview;
    //会员
    @ViewInject(R.id.menbers)
    TextView menbersTextview;
    //记录
    @ViewInject(R.id.history)
    TextView historyTextview;
    //特权
    @ViewInject(R.id.privilege)
    TextView butlerTextview;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private ButlerFragment butlerFragment;
    private HistoryFragment historyFragment;
    private MenbersFragment menbersFragment;
    private PrivilegeFragment privilegeFragment;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void setUpView() {
        initView();
    }

    private void initView() {
      manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        butlerFragment = new ButlerFragment();
        historyFragment = new HistoryFragment();
        menbersFragment = new MenbersFragment();
        privilegeFragment = new PrivilegeFragment();
        transaction.add(R.id.contain_layout, privilegeFragment);
        transaction.add(R.id.contain_layout, butlerFragment);
        transaction.add(R.id.contain_layout, historyFragment);
        transaction.add(R.id.contain_layout, menbersFragment);
        transaction.hide(butlerFragment);
        transaction.hide(historyFragment);
        transaction.hide(menbersFragment);
        transaction.show(privilegeFragment);
        transaction.commit();
    }
    @OnClick(value = {R.id.butler,R.id.menbers,R.id.history,R.id.privilege})
    private void clickEvent(View view) {
        FragmentTransaction  transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.butler:
                Log.i("wea","butlerFragment");
                transaction.show(butlerFragment);
                transaction.hide(privilegeFragment);
                transaction.hide(historyFragment);
                transaction.hide(menbersFragment);
                break;
            case R.id.menbers:
                transaction.show(menbersFragment);
                transaction.hide(butlerFragment);
                transaction.hide(historyFragment);
                transaction.hide(privilegeFragment);
                break;
            case R.id.history:
                Log.i("wea","historyFragment");
                transaction.show(historyFragment);
                transaction.hide(butlerFragment);
                transaction.hide(privilegeFragment);
                transaction.hide(menbersFragment);
                break;
            case R.id.privilege:
                transaction.show(privilegeFragment);
                 transaction.hide(butlerFragment);
                transaction.hide(historyFragment);
                transaction.hide(menbersFragment);
                break;
        }
        transaction.commit();
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(this, LocationService.class);
        stopService(intent);
    }
}
