package com.shs.global.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.control.UserManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.model.LoveCarModel;
import com.shs.global.model.ShopDetailModel;
import com.shs.global.model.ShopServicesModel;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/18.
 */
public class ChoiceMyCarActivity extends BaseActivityWithTopBar {
    private ShopDetailModel shop;//商店
    private ShopServicesModel good;//商品
    @ViewInject(R.id.choice_car_list)
    private ListView choiceListView;
    @ViewInject(R.id.none_car_root)
    private RelativeLayout noneText;
    private List<LoveCarModel> data;
    private SHSGlobalAdapter adapter;
    private ImageView rightBtn;

    @Override
    public int setLayoutId() {
        return R.layout.activity_choice_my_car;
    }
    @Override
    protected void setUpView() {
        setBarText("选择我的爱车");
        shop= (ShopDetailModel) getIntent().getSerializableExtra("shop");
        good= (ShopServicesModel) getIntent().getSerializableExtra("good");
        data=new ArrayList<>();
        initListView();
    }

    @Override
    public void onResume() {
        getData();
        super.onResume();
    }

    private void initListView() {
        adapter = new SHSGlobalAdapter<LoveCarModel>(this, R.layout.my_love_car_list_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, LoveCarModel item) {
                helper.setText(R.id.car_type, item.getCarType());
                helper.setText(R.id.car_plate, item.getPlateNum());
            }
        };
        choiceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpOrderView(position);
            }
        });
    }
    private void addRightBtn(){
        rightBtn = addRightImgBtn(R.layout.right_image_button, R.id.layout_top_btn_root_view,
                R.id.img_btn_right_top);
        rightBtn.setImageResource(R.drawable.add);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChoiceMyCarActivity.this, AddMyCarActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void jumpOrderView(int position) {
        LoveCarModel model = (LoveCarModel) adapter.getItem(position);
        Intent orderIntent = new Intent(ChoiceMyCarActivity.this, CreateOrderActivity.class);
        orderIntent.putExtra("shop", shop);
        orderIntent.putExtra("good", good);
        orderIntent.putExtra("cartype", model.getCarType());
        orderIntent.putExtra("carid", model.getCarID());
        startActivity(orderIntent);
    }
    private void getData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", UserManager.getInstance().getUserID() + "");
        data.clear();
        HttpManager.post(SHSConst.CHOICEMYCAR, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONArray result = jsonResponse.getJSONArray(SHSConst.HTTP_RESULT);
                        Log.i("wx", result.toString());
                        for (int i = 0; i < result.size(); i++) {
                            LoveCarModel model = new LoveCarModel();
                            model.setContentWithJson(result.getJSONObject(i));
                            data.add(model);
                        }
                        if (data.size() == 0) {
                            addRightBtn();
                            noneText.setVisibility(View.VISIBLE);
                            choiceListView.setVisibility(View.GONE);
                        } else {
                            if (rightBtn!=null){
                                rightBtn.setVisibility(View.GONE);
                            }
                            noneText.setVisibility(View.GONE);
                            choiceListView.setVisibility(View.VISIBLE);
                            adapter.replaceAll(data);
                            choiceListView.setAdapter(adapter);
                        }
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(ChoiceMyCarActivity.this, "获取爱车列表失败");
                        break;
                }
            }
            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(ChoiceMyCarActivity.this, getString(R.string.net_error));
            }
        }, null));

    }

}
