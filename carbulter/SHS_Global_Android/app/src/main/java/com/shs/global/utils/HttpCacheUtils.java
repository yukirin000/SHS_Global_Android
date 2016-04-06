package com.shs.global.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shs.global.SHSApplication;



//http缓存
public class HttpCacheUtils {
	
	//缓存到preference
	public static void saveHttpCache(String path,String jsonStr){
		try {
			SharedPreferences httpPreferences = SHSApplication.getInstance().getSharedPreferences("http", Activity.MODE_PRIVATE);
			Editor editor = httpPreferences.edit();
			editor.putString(path, jsonStr);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	//缓存到preference
	public static JSONObject getHttpCache(String path){
		try {
			SharedPreferences httpPreferences = SHSApplication.getInstance().getSharedPreferences("http", Activity.MODE_PRIVATE);
			String jsonValue = httpPreferences.getString(path, "");
			return JSON.parseObject(jsonValue);
		} catch (Exception e) {
			return null;
		}
	}
	
	//清空缓存
	public static void clearHttpCache(){
		try {
			SharedPreferences httpPreferences = SHSApplication.getInstance().getSharedPreferences("http", Activity.MODE_PRIVATE);
			httpPreferences.edit().clear();
		} catch (Exception e) {
		}
	}
	
}
