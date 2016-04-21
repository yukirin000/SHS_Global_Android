package com.shs.global.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wenhai on 2016/4/15.
 */
public class LoveCarModel {
    //车辆ID
    private String carID;
    //用户ID
    private String userID;
    //车牌号
    private String plateNum;
    //车类型
    private String carType;
    //操作状态
    private int state;

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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public void setContentWithJson(JSONObject object) {
        if (object.containsKey("id")) {
            setCarID(object.getString("id"));
        }
        if (object.containsKey("user_id")) {
            setUserID(object.getString("user_id"));
        }
        if (object.containsKey("car_type")) {
            setCarType(object.getString("car_type"));
        }
        if (object.containsKey("plate_number")) {
            setPlateNum(object.getString("plate_number"));
        }
        if (object.containsKey("state")) {
            setState(object.getInteger("state"));
        }
    }
}
