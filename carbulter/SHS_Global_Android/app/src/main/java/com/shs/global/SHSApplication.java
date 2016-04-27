package com.shs.global;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.shs.global.control.UserManager;
import com.shs.global.utils.SHSConst;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by wenhai on 2016/2/19.
 */
public class SHSApplication extends Application {
    public static Context applicationContext;

    private static SHSApplication application;

    public static boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        application = (SHSApplication) getApplicationContext();
        UserManager.init(application);
        //获取保存的本地数据
        UserManager.getInstance().find();
        initJpush();
    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Log.i("wea", JPushInterface.getRegistrationID(getApplicationContext()));
    }

    public static SHSApplication getInstance() {
        return application;
    }


}
