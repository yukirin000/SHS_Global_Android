package com.shs.global.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import com.shs.global.R;
import com.shs.global.control.UserManager;

/**
 * Created by wenhai on 2016/4/11.
 */
public class LaunchActivity extends BaseActivity {
    private static final int sleepTime = 1000;

    @Override
    public int setLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void setUpView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                if (UserManager.getInstance().beforeLogin()) {
                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(LaunchActivity.this, BindNumActivity.class);
//                    startActivity(intent);
//                }
                finish();
            }
        }).start();
    }
}
