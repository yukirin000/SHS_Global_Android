package com.shs.global.ui.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/3/24.
 */
public class HistoryFragment extends BaseFragment {
    private int pageIndex;
    @ViewInject(R.id.history_pager)
    private ViewPager vpager;
    //    @ViewInject(R.id.tablayout_title)
//    private TabLayout tabLayout;
    // private String[] title= {"服务中","已服务"};
    @ViewInject(R.id.tab_text1)
    private TextView tabText1;
    @ViewInject(R.id.tab_text2)
    private TextView tabText2;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    @OnClick(value = {R.id.tab_text1, R.id.tab_text2})
    public void choiceTab(View view) {
        switch (view.getId()) {
            case R.id.tab_text1:
               vpager.setCurrentItem(0);
                break;
            case R.id.tab_text2:
                vpager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {
        mFragmentList.add(new ServingFragment());
        mFragmentList.add(new BeforeOrderFragment());
        vpager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), mFragmentList));
        //  tabLayout.setupWithViewPager(vpager);
        vpagerchoice();
    }

    private void vpagerchoice() {
        vpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                settingTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void settingTab(int position) {
        switch (position) {
            case 0:
                tabText1.setTextColor(getResources().getColor(R.color.main_white));
                tabText1.setBackgroundResource(R.drawable.round_select_left_pre);
                tabText2.setTextColor(Color.parseColor("#3a3a3a"));
                tabText2.setBackgroundResource(R.drawable.round_select_right_normal);
                break;
            case 1:
                tabText2.setTextColor(getResources().getColor(R.color.main_white));
                tabText2.setBackgroundResource(R.drawable.round_select_right_pre);
                tabText1.setTextColor(Color.parseColor("#3a3a3a"));
                tabText1.setBackgroundResource(R.drawable.round_select_left_normal);
                break;
        }

    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragmentList = list;
        }

        // 得到每个item
        @Override
        public Fragment getItem(int index) {
            return fragmentList.get(index);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            //
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int position) {
            // 初始化每个页卡选项
            return super.instantiateItem(arg0, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //
            super.destroyItem(container, position, object);
        }

//        @Override   没有再使用tablayout
//        public CharSequence getPageTitle(int position) {
//            return title[position];
//        }
    }
}
