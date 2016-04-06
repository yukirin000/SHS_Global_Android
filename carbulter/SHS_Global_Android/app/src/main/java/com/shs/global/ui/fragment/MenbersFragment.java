package com.shs.global.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.ui.activity.MyLoveCarActivity;

/**
 * Created by wenhai on 2016/3/24.
 */
public class MenbersFragment extends BaseFragment{
    @ViewInject(R.id.my_car)
    RelativeLayout myCarRoot;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_menbers;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {
        myCarRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyLoveCarActivity.class);
                startActivity(intent);
            }
        });
    }
}
