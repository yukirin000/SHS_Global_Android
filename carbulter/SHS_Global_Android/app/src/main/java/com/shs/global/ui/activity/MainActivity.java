package com.shs.global.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    //fragment的容器
    @ViewInject(R.id.contain_layout)
    private FrameLayout containLayout;
    //管家
    @ViewInject(R.id.butler)
    private TextView bulerTextview;
    //会员
    @ViewInject(R.id.menbers)
    private TextView menbersTextview;
    //记录
    @ViewInject(R.id.history)
    private TextView historyTextview;
    //特权
    @ViewInject(R.id.privilege)
    private TextView privilegeTextview;
    // private  TextView impTextview;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private ButlerFragment butlerFragment;
    private HistoryFragment historyFragment;
    private MenbersFragment menbersFragment;
    private PrivilegeFragment privilegeFragment;
    private List<TextView> viewList;
    private int[][] drawablelist = {{R.drawable.libertyn, R.drawable.recordn, R.drawable.vipn, R.drawable.managern},
            {R.drawable.liberty, R.drawable.record, R.drawable.vip, R.drawable.manager}};

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void setUpView() {
        viewList = new ArrayList<TextView>();
        viewList.add(privilegeTextview);
        viewList.add(historyTextview);
        viewList.add(menbersTextview);
        viewList.add(bulerTextview);
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

    @OnClick(value = {R.id.butler, R.id.menbers, R.id.history, R.id.privilege})
    private void clickEvent(View view) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.butler:
                changeView(R.id.butler);
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
                changeView(R.id.menbers);
                break;
            case R.id.history:
                transaction.show(historyFragment);
                transaction.hide(butlerFragment);
                transaction.hide(privilegeFragment);
                transaction.hide(menbersFragment);
                changeView(R.id.history);
                break;
            case R.id.privilege:
                transaction.show(privilegeFragment);
                transaction.hide(butlerFragment);
                transaction.hide(historyFragment);
                transaction.hide(menbersFragment);
                changeView(R.id.privilege);
                break;
        }
        transaction.commit();
    }

    private void changeView(int id) {
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).getId() == id) {
                viewList.get(i).setTextColor(getResources().getColor(R.color.main_gold));
                Drawable drawable = getResources().getDrawable(drawablelist[1][i]);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewList.get(i).setCompoundDrawables(null, drawable, null, null);
            } else {
                viewList.get(i).setTextColor(getResources().getColor(R.color.main_black));
                Drawable drawable = getResources().getDrawable(drawablelist[0][i]);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewList.get(i).setCompoundDrawables(null, drawable, null, null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }
}
