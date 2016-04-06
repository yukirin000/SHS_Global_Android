package com.shs.global.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wenhai on 2016/4/5.
 */
public class ShopServicesModel {
    private String serviceID;
    private String servieceName;
    //原价
    private String originalPrice;
    //会员价
    private String discountPrice;

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServieceName() {
        return servieceName;
    }

    public void setServieceName(String servieceName) {
        this.servieceName = servieceName;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setContentWithJson(JSONObject object) {
        if (object.containsKey("id")) {
            setServiceID(object.getString("id"));
        }
        if (object.containsKey("goods_name")) {
            setServieceName(object.getString("goods_name"));
        }
        if (object.containsKey("original_price")) {
            setOriginalPrice(object.getString("original_price"));
        }
        if (object.containsKey("discount_price")) {
            setDiscountPrice(object.getString("discount_price"));
        }
    }
}
