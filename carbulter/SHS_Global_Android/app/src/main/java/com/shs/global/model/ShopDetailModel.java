package com.shs.global.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.shs.global.utils.DistanceUtil;

/**
 * Created by wenhai on 2016/4/5.
 */
public class ShopDetailModel {
    private String id;
    private String shopName;
    private String address;
    private String shopCover;
    private String  shopPhone;
    //经度
    private double longitude;
    //维度
    private double latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopCover() {
        return shopCover;
    }

    public void setShopCover(String shopCover) {
        this.shopCover = shopCover;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setContentWithJson(JSONObject object) {
        if (object.containsKey("id")) {
            setId(object.getString("id"));
        }
        if (object.containsKey("shop_name")) {
            setShopName(object.getString("shop_name"));
        }
        if (object.containsKey("address")) {
            setAddress(object.getString("address"));
        }
        if (object.containsKey("shop_image")) {
            setShopCover(object.getString("shop_image"));
        }
        if (object.containsKey("latitude")) {
            setLatitude(object.getDouble("latitude"));
        }
        if (object.containsKey("longitude")) {
            setLongitude(object.getDouble("longitude"));
        }
        if (object.containsKey("shop_phone")){
           setShopPhone(object.getString("shop_phone"));
        }
    }

}
