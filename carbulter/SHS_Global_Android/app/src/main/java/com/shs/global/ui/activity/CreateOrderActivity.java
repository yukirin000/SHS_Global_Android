package com.shs.global.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.control.UserManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.model.LoveCarModel;
import com.shs.global.model.ShopDetailModel;
import com.shs.global.model.ShopServicesModel;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by wenhai on 2016/4/26.
 */
public class CreateOrderActivity  extends BaseActivityWithTopBar{
    @ViewInject(R.id.tv_shop_name)
    private TextView shopNameText;//商店名
    @ViewInject(R.id.tv_shop_phonenum)
    private TextView phoneNumText;//商店号码
    @ViewInject(R.id.tv_services)
    private TextView servicesText;//服务项目
    @ViewInject(R.id.tv_pay_count)
    private TextView payCountText;//消费金额
    @ViewInject(R.id.tv_servie_car)
    private TextView serviceCarText;//服务车辆
    @ViewInject(R.id.submit_order)
    private TextView submitText;
    private ShopDetailModel shop;//商店
    private ShopServicesModel good;//商品
    private String carID;
    private String carType;
    private IWXAPI api;
    @OnClick(value = {R.id.call_shop_root})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.call_shop_root:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumText.getText().toString()));
                startActivity(intent);
                break;
        }
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_create_order;
    }

    @Override
    public void onResume() {
        submitText.setEnabled(true);
        super.onResume();
    }

    @Override
    protected void setUpView() {
       setBarText("提交订单");
        api = WXAPIFactory.createWXAPI(this, SHSConst.WXPAYAPPID);
        api.registerApp(SHSConst.WXPAYAPPID);
        init();
        submitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitText.setEnabled(false);
                submitOrder();
            }
        });
    }

    private void submitOrder() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", UserManager.getInstance().getUserID() + "");
        params.addBodyParameter("shop_id",shop.getId());
        params.addBodyParameter("goods_id",good.getServiceID());
        params.addBodyParameter("car_id", carID);
      //  Log.i("order", carID + ":" + good.getServiceID() + ":" + shop.getId() + ":" + UserManager.getInstance().getUserID() + "");
        HttpManager.post(SHSConst.CREATEORDER, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONObject result = jsonResponse.getJSONObject(SHSConst.HTTP_RESULT);
                        Log.i("pay",result.toString());
                        sendpay(result);
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(CreateOrderActivity.this, "订单提交失败");
                        break;
                }
            }
            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(CreateOrderActivity.this, getString(R.string.net_error));
            }
        }, null));

    }

    private void init() {
        good= (ShopServicesModel) getIntent().getSerializableExtra("good");
        shop= (ShopDetailModel) getIntent().getSerializableExtra("shop");
        carID=getIntent().getStringExtra("carid");
        carType=getIntent().getStringExtra("cartype");
        shopNameText.setText(shop.getShopName());
        phoneNumText.setText(shop.getShopPhone());
        servicesText.setText(good.getServieceName());
        payCountText.setText(good.getDiscountPrice());
        serviceCarText.setText(carType);
    }
     private void sendpay(JSONObject result){
         PayReq req = new PayReq();
         req.appId			= result.getString("appid");
         req.partnerId		= result.getString("partnerid");
         req.prepayId		= result.getString("prepayid");
         req.nonceStr		= result.getString("noncestr");
         req.timeStamp		= result.getString("timestamp");
         req.packageValue	= result.getString("package");
         req.sign			= result.getString("sign");
         req.extData	    = "app data"; // optional
         boolean b= api.sendReq(req);
         Log.i("pay",b+"");
         submitText.setEnabled(false);
     }
}
