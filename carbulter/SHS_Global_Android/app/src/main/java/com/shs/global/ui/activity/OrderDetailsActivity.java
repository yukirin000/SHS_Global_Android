package com.shs.global.ui.activity;

import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.model.OrderDetailsModel;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

/**
 * Created by wenhai on 2016/4/19.
 */
public class OrderDetailsActivity extends BaseActivityWithTopBar{
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

    @Override
    public int setLayoutId() {
        return R.layout.activity_order_details;
    }
    @Override
    protected void setUpView() {
     String  orderID = getIntent().getStringExtra("order_id");
        setBarText("订单详情");
        getData(orderID);
        initView();
    }
    private void initView() {

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
                        String result = jsonResponse.getString(SHSConst.HTTP_RESULT);
                        OrderDetailsModel model = (OrderDetailsModel) JSONArray.parse(result);
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
