package com.shs.global.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/3/24.
 */
public class HistoryFragment extends BaseFragment {
    @ViewInject(R.id.history_pager)
    private ViewPager vpager;
    @ViewInject(R.id.tablayout_title)
    private TabLayout tabLayout;
    private String[] title= {"服务中","已服务"};
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
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
        tabLayout.setupWithViewPager(vpager);
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

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
