package com.shs.global.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.model.MyCarDetailsModel;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

/**
 * Created by wenhai on 2016/4/18.
 */
public class MyCarDetailsActivity extends BaseActivityWithTopBar {

    private MyCarDetailsModel detailsModel;
    @ViewInject(R.id.text_name)
    private TextView nameTextView;
    @ViewInject(R.id.text_phone_num)
    private TextView phoneNumTextView;
    @ViewInject(R.id.text_car_num)
    private TextView carNumTextView;
    @ViewInject(R.id.text_car_type)
    private TextView carTypeTextView;
    @ViewInject(R.id.dividing_image)
    private ImageView drivingImageView;
   @ViewInject(R.id.car_state_root)
   private RelativeLayout relativeLayout;
    @ViewInject(R.id.car_state)
    private TextView carText;
    //不需要审核了所以没有了重新提交
//    @ViewInject(R.id.car_resubmit_root)
//    private RelativeLayout resubmitrelativeLayout;
//    @ViewInject(R.id.car_resubmit)
//    private TextView resubmit;
    @Override
    public int setLayoutId() {
        return R.layout.activity_my_car_details;
    }

    @Override
    protected void setUpView() {
        setBarText("我的爱车");
        String carID = getIntent().getStringExtra(MyLoveCarActivity.CARID);
        Log.i("wx", carID);
        getData(carID);
      /*  resubmitrelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 resubmitCar();
            }
        });*/
    }
    private void resubmitCar() {
        Intent intent=new Intent(MyCarDetailsActivity.this,AddMyCarActivity.class);
        intent.putExtra("data",detailsModel);
        intent.putExtra("isReSubmit",true);
        startActivity(intent);
    }
    private void initView() {
        nameTextView.setText(detailsModel.getName());
        phoneNumTextView.setText(detailsModel.getMobile());
        carNumTextView.setText(detailsModel.getPlate_number());
        carTypeTextView.setText(detailsModel.getCar_type());
        Glide.with(MyCarDetailsActivity.this).load(detailsModel.getDriving_image()).into(drivingImageView);
//        if (detailsModel.getState()==1) {
//            relativeLayout.setVisibility(View.VISIBLE);
//            resubmitrelativeLayout.setVisibility(View.GONE);
//            carText.setText("正在审核");
//        }else if(detailsModel.getState()==3){
//            relativeLayout.setVisibility(View.VISIBLE);
//            carText.setText("审核未通过");
//            resubmitrelativeLayout.setVisibility(View.VISIBLE);
//        }else {
            relativeLayout.setVisibility(View.GONE);
            //resubmitrelativeLayout.setVisibility(View.GONE);
//        }
    }

    private void getData(String carID) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("car_id", carID);
        HttpManager.post(SHSConst.CARINFO, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONObject result = jsonResponse.getJSONObject(SHSConst.HTTP_RESULT);
                        detailsModel = new MyCarDetailsModel();
                        detailsModel.setContentWithJson(result);
                        initView();
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(MyCarDetailsActivity.this, "获取爱车详情失败");
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(MyCarDetailsActivity.this, getString(R.string.net_error));
            }
        }, null));
    }


}
