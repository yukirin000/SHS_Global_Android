package com.shs.global.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wenhai on 2016/4/18.
 */
public class MyCarDetailsModel {
    //车辆ID
    private String id;
    //用户ID
    private String user_id;
    //车主名
    private String name;
    //车主手机号码
    private String mobile;
    // 车牌号
    private String plate_number;
    //车型
    private String car_type;
    //行驶证
    private String driving_image;
    //审核状态
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getDriving_image() {
        return driving_image;
    }

    public void setDriving_image(String driving_image) {
        this.driving_image = driving_image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setContentWithJson(JSONObject object) {
        if (object.containsKey("id")) {
            setId(object.getString("id"));
        }
        if (object.containsKey("user_id")) {
            setUser_id(object.getString("user_id"));
        }
        if (object.containsKey("car_type")) {
            setCar_type(object.getString("car_type"));
        }
        if (object.containsKey("plate_number")) {
            setPlate_number(object.getString("plate_number"));
        }
        if (object.containsKey("state")) {
            setState(object.getInteger("state"));
        }
        if (object.containsKey("driving_license_url")) {
            setDriving_image(object.getString("driving_license_url"));
        }
        if (object.containsKey("mobile")) {
            setMobile(object.getString("mobile"));
        }
        if (object.containsKey("name")) {
            setName(object.getString("name"));
        }
    }
}
