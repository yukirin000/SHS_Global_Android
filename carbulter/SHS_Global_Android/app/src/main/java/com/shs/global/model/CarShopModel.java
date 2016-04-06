package com.shs.global.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wenhai on 2016/3/25.
 */
public class CarShopModel {
    private String shopName;
    private String shopAddress;
    private String shopID;
    private String shopImage;
    //经度
    private double longitude;
    //维度
    private double latitude;
    private  String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setContentWithJson(JSONObject object) {
        if (object.containsKey("id")) {
            setShopID(object.getString("id"));
        }
        if (object.containsKey("shop_name")) {
            setShopName(object.getString("shop_name"));
        }
        if (object.containsKey("address")) {
            setShopAddress(object.getString("address"));
        }
        if (object.containsKey("shop_image_thumb")) {
            setShopImage(object.getString("shop_image_thumb"));
        }
        if (object.containsKey("latitude")) {
            setLatitude(object.getDouble("latitude"));
        }
        if (object.containsKey("longitude")) {
            setLongitude(object.getDouble("longitude"));
        }
    }
}
