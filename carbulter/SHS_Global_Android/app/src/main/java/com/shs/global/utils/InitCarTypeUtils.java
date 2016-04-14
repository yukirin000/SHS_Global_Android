package com.shs.global.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wenhai on 2016/4/14.
 */
public class InitCarTypeUtils {
    private final static String fileName = "cars.json";
    //static List<CarTypeModel> list = new ArrayList<>();

    public static String getJson(Context context) {
        Log.i("wx", "1111");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"UTF-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("wx",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
