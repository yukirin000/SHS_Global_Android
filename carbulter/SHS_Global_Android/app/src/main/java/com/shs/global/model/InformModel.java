package com.shs.global.model;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wenhai on 2016/4/21.
 */
public class InformModel {
    private String carID;
    private String userID;
    private String plateNum;
    private int carState;
    private int readState=1;//0为已读，1为未读

    public int getCarState() {
        return carState;
    }

    public void setCarState(int carState) {
        this.carState = carState;
    }

    public String getCarID() {

        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

    public void parseJson(JSONObject jsonObject) {
        try {
            setCarState(jsonObject.getInt("type"));
            JSONObject jsonsubObject = jsonObject.getJSONObject("content");
            setUserID(jsonsubObject.getString("user_id"));
            setCarID(jsonsubObject.getString("id"));
            setPlateNum(jsonsubObject.getString("plate_number"));
            setReadState(1);
            Log.i("inform",jsonsubObject.getString("plate_number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
