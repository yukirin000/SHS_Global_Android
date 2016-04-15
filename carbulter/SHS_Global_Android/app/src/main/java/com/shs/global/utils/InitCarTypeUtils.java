package com.shs.global.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shs.global.model.CarTypeModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/14.
 */
public class InitCarTypeUtils {
    private final static String fileName = "cars.json";
    static List<CarTypeModel> list = new ArrayList<>();
    private static String[] carIcon = {"benz", "bmw", "audi", "porsche", "land", "bentley", "rolls", "maserati", "lamborghini", "ferrari", "lincoln"};
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
    public static List<CarTypeModel> getTypeList(Context context) {
        String jsony = InitCarTypeUtils.getJson(context);
        JSONArray jsonArray = (JSONArray) JSONArray.parse(jsony);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CarTypeModel model = new CarTypeModel();
            model.setIconName(carIcon[i]);
            list.add(model);
        }
        return list;
    }
}
