package com.shs.global.control;

import android.content.Context;
import android.content.SharedPreferences;

import com.shs.global.model.InformModel;
import com.shs.global.utils.SHSConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/20.
 */
public class InformManager {
    private static volatile InformManager informManager;
    private static Context context;
    private static SharedPreferences share;
    private final  static  String COUNT= "informcount";
    private final  static  String CARID= "carid";
    private final  static  String USERID= "userid";
    private final  static  String PLATENUM= "platenum";
    private final  static  String READSTATE= "readstate";
    private final  static  String CARSTATE= "carstate";

    private InformManager() {

    }
    public static InformManager getInstance(Context context1) {
        if (informManager == null) {
            synchronized (InformManager.class) {
                if (informManager == null) {
                    context = context1;
                    share = context.getSharedPreferences(SHSConst.TAG, context.MODE_PRIVATE);
                    return informManager = new InformManager();
                }
            }
        }
        return informManager;
    }

    /**
     * 把未读通知保存到本地
     * @param informList
     */
    public void saveInform(List<InformModel> informList) {

        SharedPreferences.Editor editor= share.edit();
        editor.putInt(COUNT, informList.size());
        int size=informList.size();
         for (int i=(size-1);i>0;i--){
             editor.putString(CARID+(size-1-i),informList.get(i).getCarID());
             editor.putString(USERID+(size-1-i),informList.get(i).getUserID());
             editor.putString(PLATENUM+(size-1-i),informList.get(i).getPlateNum());
             editor.putInt(READSTATE +(size-1-i), informList.get(i).getReadState());
             editor.putInt(CARSTATE+(size-1-i),informList.get(i).getCarState());
         }
        editor.commit();
    }

    /**
     * 拉去本地保存的未读通知
     * @return
     */
    public List readInform(){
         List<InformModel> informData=new ArrayList<>();
        int size=share.getInt(COUNT, -1);
        if (size==-1) {
            return informData;
        }else {
            for (int i=size;i>0;i--){
               InformModel model=new InformModel();
                model.setCarID(share.getString(CARID+(i-1),""));
                model.setUserID(share.getString(USERID + (i-1), ""));
                model.setPlateNum(share.getString(PLATENUM + (i-1), ""));
                model.setReadState(share.getInt(READSTATE + (i-1), -1));
                model.setCarState(share.getInt(CARSTATE + (i-1), -1));
                informData.add(model);
            }
            return informData;
        }
    }
    public void addInform(InformModel model){
        List<InformModel> list= readInform();
        SharedPreferences.Editor editor= share.edit();
        editor.putString(CARID+list.size(),model.getCarID());
        editor.putString(USERID+list.size(),model.getUserID());
        editor.putString(PLATENUM + list.size(), model.getPlateNum());
        editor.putInt(READSTATE + list.size(), model.getReadState());
        editor.putInt(CARSTATE+list.size(),model.getCarState());
        editor.putInt(COUNT, list.size()+1);
        editor.commit();
    }

}
