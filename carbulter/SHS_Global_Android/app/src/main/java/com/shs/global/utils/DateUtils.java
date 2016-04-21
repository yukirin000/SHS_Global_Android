package com.shs.global.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenhai on 2016/4/19.
 */
public class DateUtils {
    public static String dateStreamToCalendar(String dataStream){
        Date date;
        try {
            date= DateFormat.getInstance().parse(dataStream);
        } catch (ParseException e) {
            e.printStackTrace();
            return "获取时间出错";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t=format.format(date);
        return t;
    }
}
