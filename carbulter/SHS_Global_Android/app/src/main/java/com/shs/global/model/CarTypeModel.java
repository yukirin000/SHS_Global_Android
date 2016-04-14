package com.shs.global.model;


import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by wenhai on 2016/4/14.
 */
public class CarTypeModel {
    private String name;
    private JSONArray sub;
    private String iconName;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getName() {
        return name;
    }

    public JSONArray getSub() {
        return sub;
    }

    public void setSub(JSONArray sub) {
        this.sub = sub;
    }

    public void setName(String name) {
        this.name = name;
    }


}
