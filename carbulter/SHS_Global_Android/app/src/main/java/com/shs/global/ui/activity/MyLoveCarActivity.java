package com.shs.global.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;

/**
 * Created by wenhai on 2016/3/25.
 */
public class MyLoveCarActivity extends BaseActivityWithTopBar implements View.OnClickListener {
    @ViewInject(R.id.call_telephone)
    private LinearLayout button;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_love_car;
    }

    @Override
    protected void setUpView() {
        setBarText("我的爱车");
        // 右上角按钮
        ImageView rightBtn = addRightImgBtn(R.layout.right_image_button, R.id.layout_top_btn_root_view,
                R.id.img_btn_right_top);
        rightBtn.setImageResource(R.drawable.add);
        button.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_telephone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008693911"));
                startActivity(intent);
                break;
            case R.id.img_btn_right_top:
                Log.i("wx","click");
                Intent intent1 = new Intent(this, AddMyCarActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
