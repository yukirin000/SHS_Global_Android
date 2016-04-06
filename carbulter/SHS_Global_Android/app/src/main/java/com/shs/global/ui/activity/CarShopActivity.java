package com.shs.global.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.model.CarShopModel;
import com.shs.global.utils.DistanceUtil;
import com.shs.global.utils.SHSConst;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/3/25.
 */
public class CarShopActivity extends BaseActivityWithTopBar {
    //是否是下拉刷新
    private boolean isFresh = true;
    //是否是下拉加载
    private boolean isMore = false;
    //是否定位成功
    private boolean isLocation = false;
    // 当前数据的页
    private int pageIndex = 1;
    // 是否是最后一页数据
    private int listSize;
    private boolean isLoad = false;
    private boolean lastPage = false;
    private int lastItem;
    @ViewInject(R.id.car_shop_listview)
    private ListView shopListview;
    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private IntentFilter intentFilter;
    //当前定位纬度
    private double currentlat;
    //当前定位经度
    private double currentlong;
    private AddressBroadcastReceiver broadcastReceiver;
    private List<CarShopModel> list;
    private SHSGlobalAdapter shopAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_car_shop;
    }

    @Override
    protected void setUpView() {
        intentFilter = new IntentFilter("locationAction");
        broadcastReceiver = new AddressBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter);
        setBarText("车店");
        list = new ArrayList<CarShopModel>();
        getListdata();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                isFresh = true;
                lastPage = false;
                list.clear();
                shopAdapter.clear();
                getListdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initlistview() {
        shopAdapter = new SHSGlobalAdapter<CarShopModel>(CarShopActivity.this, R.layout.activity_car_shop_listview_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, CarShopModel item) {
                helper.setText(R.id.shop_name, item.getShopName());
                ImageView image = helper.getView(R.id.shop_cover_image);
                //加载图片
                Glide.with(CarShopActivity.this).load(item.getShopImage()).into(image);
                helper.setText(R.id.shop_address, item.getShopAddress());
                if (isLocation) {
                    if (item.getDistance() != null) {
                        helper.setText(R.id.distance, item.getDistance() + "m");
                    } else if (currentlong != 0) {
                        helper.setText(R.id.distance, DistanceUtil.gps2m(item.getLatitude(), item.getLongitude(), currentlat, currentlong) + "");
                    }else {
                        helper.setText(R.id.distance, "正在定位...");
                    }

                } else {
                    helper.setText(R.id.distance, "正在定位...");
                }
            }
        };
        if (isFresh) {
            shopAdapter.addAll(list);
            isFresh = false;
        }
        if (isMore) {
            shopAdapter.addAll(list);
            isMore = false;
            shopListview.setSelection(lastItem);
        }

        shopListview.setAdapter(shopAdapter);
        shopListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CarShopActivity.this, ShopHomePageActivity.class);
                intent.putExtra("shopID", ((CarShopModel) shopAdapter.getItem(position)).getShopID());
                intent.putExtra("distance", ((CarShopModel) shopAdapter.getItem(position)).getDistance());
                startActivity(intent);
            }
        });
        shopListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (lastItem == listSize && scrollState == this.SCROLL_STATE_IDLE) {
                    isMore = true;
                    if (isLoad) {
                        Log.i("wx", lastPage + "");
                        if (!lastPage) {
                            getListdata();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItem = totalItemCount;
                listSize = firstVisibleItem + visibleItemCount;

//                if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
//                    isMore=true;
//                    if (!lastPage) {
//                        getListdata();
//                    }
//                }
            }
        });
    }

    private void getListdata() {

        String path = SHSConst.GETSHOPLIST + "?page=" + pageIndex + "&size=7";
        HttpManager.get(path, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                // TODO Auto-generated method stub
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONObject jsonObject = jsonResponse.getJSONObject(SHSConst.HTTP_RESULT);
                        JSONArray jsonList = jsonObject.getJSONArray(SHSConst.HTTP_LSIT);
                        for (int i = 0; i < jsonList.size(); i++) {
                            CarShopModel model = new CarShopModel();
                            JSONObject object = jsonList.getJSONObject(i);
                            model.setContentWithJson(object);
                            list.add(model);
                        }

                        initlistview();
                        if (jsonObject.getString("is_last").equals("0")) {
                            lastPage = false;
                            pageIndex++;
                        } else {
                            lastPage = true;
                        }
                        isLoad = false;
                        break;
                    case SHSConst.STATUS_FAIL:
                        hideLoading();
                        Toast.makeText(CarShopActivity.this, "访问失败",
                                Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                // TODO Auto-generated method stub
                super.onFailure(e, arg1, flag);
                //hideLoading();
                Toast.makeText(CarShopActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }, null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public class AddressBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("locationAction")) {
                currentlat = intent.getDoubleExtra("lat", 0.00);
                currentlong = intent.getDoubleExtra("long", 0.00);
                isLocation = true;
                for (CarShopModel model : list) {
                    model.setDistance(DistanceUtil.gps2m(model.getLatitude(), model.getLongitude(), currentlat, currentlong) + "");
                }
                shopAdapter.notifyDataSetChanged();
                //  initlistview();

            }
        }
    }
}
