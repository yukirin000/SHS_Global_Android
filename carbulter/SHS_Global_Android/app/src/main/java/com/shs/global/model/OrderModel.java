package com.shs.global.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wenhai on 2016/4/18.
 */
public class OrderModel {
    private String orderID;
    private String payMoney;
    private String shopName;
    private String shopSubImage;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopSubImage() {
        return shopSubImage;
    }

    public void setShopSubImage(String shopSubImage) {
        this.shopSubImage = shopSubImage;
    }
    public void setContentWithJson(JSONObject object) {
        if (object.containsKey("id")) {
            setOrderID(object.getString("id"));
        }
        if (object.containsKey("total_fee")) {
            setPayMoney(object.getString("total_fee"));
        }
        if (object.containsKey("shop_name")) {
            setShopName(object.getString("shop_name"));
        }
        if (object.containsKey("pay_date")) {
            setDate(object.getString("pay_date"));
        }
        if (object.containsKey("use_date")) {
            setDate(object.getString("use_date"));
        }
        if (object.containsKey("shop_image_thumb")) {
            setShopSubImage(object.getString("shop_image_thumb"));
        }
    }
}
