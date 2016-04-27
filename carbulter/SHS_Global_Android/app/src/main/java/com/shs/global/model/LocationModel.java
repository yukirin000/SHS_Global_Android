package com.shs.global.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by wenhai on 2016/4/27.
 */
public class LocationModel {
    //当前定位纬度
    private double currentlat;
    //当前定位经度
    private double currentlong;

    public double getCurrentlat() {
        return currentlat;
    }

    public void setCurrentlat(double currentlat) {
        this.currentlat = currentlat;
    }

    public double getCurrentlong() {
        return currentlong;
    }

    public void setCurrentlong(double currentlong) {
        this.currentlong = currentlong;
    }

    private static volatile LocationModel locationModel;

    private LocationModel() {

    }

    public static LocationModel getInstance() {
        if (locationModel == null) {
            synchronized (LocationModel.class) {
                if (locationModel == null) {
                    return locationModel = new LocationModel();
                }
            }
        }
        return locationModel;
    }

    public class AddressBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("locationAction")) {
                currentlat = intent.getDoubleExtra("lat", 0.00);
                currentlong = intent.getDoubleExtra("long", 0.00);
            }
        }
    }
}
