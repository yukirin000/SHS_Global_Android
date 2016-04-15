package com.shs.global.model;


import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by wenhai on 2016/4/14.
 */
public class CarTypeModel implements Serializable{
    //品牌编号
    private String first_code;
    //车型
    private String second_code;
    //品牌名
    private String name1;
    //车型
    private String name2;
    //车标
    private String iconName;
    //是否有二级分类
    private String hassub;


    public String getFirst_code() {
        return first_code;
    }

    public void setFirst_code(String first_code) {
        this.first_code = first_code;
    }

    public String getSecond_code() {
        return second_code;
    }

    public void setSecond_code(String second_code) {
        this.second_code = second_code;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getHassub() {
        return hassub;
    }

    public void setHassub(String hassub) {
        this.hassub = hassub;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
