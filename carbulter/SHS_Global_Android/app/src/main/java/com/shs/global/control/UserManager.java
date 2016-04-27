package com.shs.global.control;

import android.content.Context;
import android.content.SharedPreferences;

import com.shs.global.utils.SHSConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by wenhai on 2016/2/29.
 */
public class UserManager {
    private static Context context;
    private String myPhone;
    private String userName;
    private String token;
    private int userID;
    private String passoword;


    public static void init(Context context1) {
        context = context1;
    }

    public String getPassoword() {
        return passoword;
    }


    public void setPassoword(String passoword) {
        this.passoword = passoword;
    }

    private static volatile UserManager userManager;

    private UserManager() {

    }


    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    return userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    public String getMyPhone() {
        return myPhone;
    }

    public void setMyPhone(String myPhone) {
        this.myPhone = myPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * 初始化
     */
    public void init() {
        if (userName == null && token == null) {
            find();
        }
    }

    /**
     * 持久话保存
     */
    public void saveInfo() {
        SharedPreferences share = context.getSharedPreferences(SHSConst.TAG, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("username", userName);
        edit.putString("token", token);
        edit.putString("myphone", myPhone);
        edit.putInt("userid", userID);
        edit.putString("password", passoword);
        edit.commit();  //保存数据信息
        //测试用的
        JPushInterface.setAliasAndTags(context, "globalTest" + userID, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        //正式用的
//        JPushInterface.setAliasAndTags(context,"global"+userID, null, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//            }
//        });
    }

    /**
     * 清楚保存信息
     */
    public void clearInfo() {
        SharedPreferences share = context.getSharedPreferences(SHSConst.TAG, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取保存数据
     */
    public void find() {
        SharedPreferences share = context.getSharedPreferences(SHSConst.TAG, context.MODE_PRIVATE);
        setToken(share.getString("token", ""));
        setMyPhone(share.getString("myphone", "暂无信息"));
        setUserName(share.getString("username", ""));
        setUserID(share.getInt("userid", -1));
        setPassoword(share.getString("password", ""));
    }
   /**
    *判断是否是用户
    */
    public boolean isUser() {
        SharedPreferences share = context.getSharedPreferences(SHSConst.TAG, context.MODE_PRIVATE);
        if (share.getInt("userid", -1) == -1)
            return false;
        return true;
    }

    public boolean beforeLogin() {
        SharedPreferences share = context.getSharedPreferences(SHSConst.TAG, context.MODE_PRIVATE);
        if ("".equals(share.getString("token", "")))
            return false;
        return true;
    }
}
