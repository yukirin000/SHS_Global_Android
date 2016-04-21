package com.shs.global.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.ui.activity.MemberTreatmentActivity;
import com.shs.global.ui.activity.MyLoveCarActivity;

/**
 * Created by wenhai on 2016/3/24.
 */
public class MenbersFragment extends BaseFragment implements View.OnClickListener{
    @ViewInject(R.id.my_car)
    RelativeLayout myCarRoot;
    @ViewInject(R.id.treatment)
    RelativeLayout treatmentRoot;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_menbers;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {
        myCarRoot.setOnClickListener(this);
        treatmentRoot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_car:
                Intent intent=new Intent(getActivity(), MyLoveCarActivity.class);
                startActivity(intent);
                break;
            case R.id.treatment:
                Intent intent1=new Intent(getActivity(), MemberTreatmentActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
