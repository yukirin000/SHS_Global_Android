package com.shs.global.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.control.UserManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.model.ShopDetailModel;
import com.shs.global.model.ShopServicesModel;
import com.shs.global.ui.view.PromptAlertDialog;
import com.shs.global.utils.DistanceUtil;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

/**
 * Created by wenhai on 2016/3/28.
 */
public class ShopHomePageActivity extends BaseActivityWithTopBar {
    //是否定位成功
    private boolean isLocation = false;
    //当前定位纬度
    private double currentlat;
    //当前定位经度
    private double currentlong;
    private String id;
    //商店详情
    private ShopDetailModel model;
    //服务项目
    private ShopServicesModel servicesModel;
    //距离
    private String distance;
    @ViewInject(R.id.shop_cover)
    private ImageView coverImage;

    @ViewInject(R.id.shop_name)
    private TextView shopNameText;

    @ViewInject(R.id.distance)
    private TextView distanceText;

    @ViewInject(R.id.shop_address)
    private TextView addressText;
    @ViewInject(R.id.shop_telephone_num)
    private TextView telephoneText;
    //    @ViewInject(R.id.services)
//    private TextView servicesText;
    @ViewInject(R.id.original_price)
    private TextView originalpriceText;
    @ViewInject(R.id.discount_price)
    private TextView discountpriceText;
    private IntentFilter intentFilter;
    private ImageView rightBtn;
    private AddressBroadcastReceiver broadcastReceiver;

    @OnClick({R.id.buy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.buy:
                jumpChoice();
                break;
        }
    }

    private void jumpChoice() {
        if (UserManager.getInstance().isUser()) {
            Intent intent = new Intent(this, ChoiceMyCarActivity.class);
            intent.putExtra("shop", model);
            intent.putExtra("good", servicesModel);
            startActivity(intent);
        } else {
            PromptAlertDialog dialog = new PromptAlertDialog(this, "提示");
            dialog.show();
        }
    }

//    private void callButler() {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008693911"));
//        startActivity(intent);
//    }

  private  void addRightBtn() {
        rightBtn = addRightImgBtn(R.layout.right_image_button, R.id.layout_top_btn_root_view,
                R.id.img_btn_right_top);
        rightBtn.setImageResource(R.drawable.btn_telephone);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008693911"));
                startActivity(intent);
            }
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_shop_homepage;
    }

    @Override
    protected void setUpView() {
        setBarText(getString(R.string.shop_home));
        distance = getIntent().getStringExtra("distance");
        id = getIntent().getStringExtra("shopID");
        addRightBtn();
        intentFilter = new IntentFilter("locationAction");
        broadcastReceiver = new AddressBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter);
        getData();
    }

    private void init() {
        Glide.with(this).load(model.getShopCover()).into(coverImage);
        shopNameText.setText(model.getShopName());
        addressText.setText(model.getAddress());
        telephoneText.setText(model.getShopPhone());
        if (servicesModel != null) {
            //   servicesText.setText(servicesModel.getServieceName());
            discountpriceText.setText("￥" + servicesModel.getDiscountPrice());
            originalpriceText.setText("原价：￥" + servicesModel.getOriginalPrice());
        }
        if (!isLocation) {
            if (distance != null) {
                distanceText.setText(distance + "km");
            } else {
                distanceText.setText("正在定位...");
            }
        }
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("shop_id", id);
        HttpManager.post(SHSConst.GETSHOPDETAIL, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);

                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        //商店详情
                        JSONObject result = jsonResponse.getJSONObject(SHSConst.HTTP_RESULT);
                        JSONObject shopDetail = result.getJSONObject("shop");
                        model = new ShopDetailModel();
                        model.setContentWithJson(shopDetail);
                        //服务项目
                        if (result.containsKey("good")) {
                            JSONObject services = result.getJSONObject("good");
                            servicesModel = new ShopServicesModel();
                            servicesModel.setContentWithJson(services);
                        }
                        init();
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(ShopHomePageActivity.this, "登陆失败");
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(ShopHomePageActivity.this, getString(R.string.net_error));
            }
        }, null));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    public class AddressBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("locationAction")) {
                currentlat = intent.getDoubleExtra("lat", 0.00);
                currentlong = intent.getDoubleExtra("long", 0.00);
                //  initlistview();
                isLocation = true;
                if (isLocation) {
                    distanceText.setText(DistanceUtil.gps2m(model.getLatitude(), model.getLongitude(), currentlat, currentlong) + "km");
                }
            }
        }
    }
}
