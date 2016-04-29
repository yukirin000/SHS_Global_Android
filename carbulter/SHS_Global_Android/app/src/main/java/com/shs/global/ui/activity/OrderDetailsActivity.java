package com.shs.global.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.model.OrderDetailsModel;
import com.shs.global.ui.view.ShowQrcodePopupWindow;
import com.shs.global.utils.QRCodeUtil;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

/**
 * Created by wenhai on 2016/4/19.
 */
public class OrderDetailsActivity extends BaseActivityWithTopBar {
    @ViewInject(R.id.order_num)
    private TextView textOrderNum;
    @ViewInject(R.id.service_shop)
    private TextView textServiceShop;
    @ViewInject(R.id.phone_num)
    private TextView textPhoneNum;
    @ViewInject(R.id.services)
    private TextView textServices;
    @ViewInject(R.id.pay_num)
    private TextView textPay;
    @ViewInject(R.id.car_type)
    private TextView textCarType;
    @ViewInject(R.id.use_state)
    private TextView textState;
    @ViewInject(R.id.order_qrcode)
    private ImageView qrcodeImage;
    private OrderDetailsModel model;
    private Bitmap qrcodeBitmap;
    @OnClick(value = {R.id.call_shop_root})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.call_shop_root:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textPhoneNum.getText().toString()));
                startActivity(intent);
                break;
        }
    }
    @Override
    public int setLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void setUpView() {
        String orderID = getIntent().getStringExtra("order_id");
        setBarText("订单详情");
        getData(orderID);

    }

    private void initView() {
        textOrderNum.setText(model.getOut_trade_no());
        textServiceShop.setText(model.getShop_name());
        textPhoneNum.setText(model.getShop_phone());
        textServices.setText(model.getGoods_name());
        textPay.setText(model.getTotal_fee());
        textCarType.setText(model.getCar_type());
        if ("1".equals(model.getState())) {
            textState.setText("服务中");
        } else if ("2".equals(model.getState())) {
            textState.setText("已使用");
        } else {
            textState.setText("已失效");
        }
        qrcodeBitmap = QRCodeUtil.createQRImage(SHSConst.QRCODEPREFIX + model.getOut_trade_no(), 400, 400, null);
                qrcodeImage.setImageBitmap(qrcodeBitmap);
        qrcodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowQrcodePopupWindow popupWindow=new ShowQrcodePopupWindow(OrderDetailsActivity.this);
                popupWindow.setGroupBitmap(qrcodeBitmap);
                popupWindow.showPopupWindow(v,false);
            }
        });
    }

    private void getData(String orderID) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("order_id", orderID);
        HttpManager.post(SHSConst.SERVICEDETAILS, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONArray result = jsonResponse.getJSONArray(SHSConst.HTTP_RESULT);
                        JSONObject details = result.getJSONObject(0);
                        model = new OrderDetailsModel();
                        model.jsonParse(details);
                        initView();
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(OrderDetailsActivity.this, "获取订单详情失败");
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(OrderDetailsActivity.this, getString(R.string.net_error));
            }
        }, null));

    }
}
