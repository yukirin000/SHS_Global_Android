package com.shs.global.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by wenhai on 2016/3/28.
 */
public class DistanceUtil {


    private final static double EARTH_RADIUS = 6378137.0;

    /**
     * @param lat_a A地点的纬度
     * @param lng_a a地点的经度
     * @param lat_b b地点的纬度
     * @param lng_b b地点的经度
     * @return
     */
    public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
      //  DecimalFormat df = new DecimalFormat("#.00");
       // return  df.format(s/1000);
//        double reserveBit = 3.0;//保留位数
//        double tempD = s * Math.pow(10.0,reserveBit);
//        return( (Math.round(tempD))/(Math.pow(10.0,reserveBit)) );
        double d=new BigDecimal(s/1000).setScale(2, 1).doubleValue();
        return d ;
    }
}
