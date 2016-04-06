package com.shs.global.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;

/**
 * Created by wenhai on 2016/3/24.
 */
public class ButlerFragment extends BaseFragment {
    @ViewInject(R.id.call_butler)
    RelativeLayout callButler;
    @ViewInject(R.id.call_number)
    TextView callnumber;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_butler;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {
        callButler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = callnumber.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
                startActivity(intent);
            }
        });
    }

}
