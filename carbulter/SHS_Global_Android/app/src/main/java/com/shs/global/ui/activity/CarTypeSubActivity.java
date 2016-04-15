package com.shs.global.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.model.CarTypeModel;
import com.shs.global.utils.InitCarTypeUtils;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/14.
 */
public class CarTypeSubActivity extends BaseActivityWithTopBar {
    private List<CarTypeModel> list;
    @ViewInject(R.id.type_list_sub)
    private ListView subListView;
    private CarTypeModel model;
    private List<String> data = new ArrayList<>();
    private SHSGlobalAdapter subAdapter;
    private String category;
    private String CARTYPE = "cartype";
    private int typenum;

    @Override
    public int setLayoutId() {
        return R.layout.activity_car_type_sub;
    }

    @Override
    protected void setUpView() {
        setBarText("车型选择");
        list = new ArrayList<>();
        model = (CarTypeModel) getIntent().getSerializableExtra("data");
        getListData();
        initList();
    }

    private void initList() {
        subAdapter = new SHSGlobalAdapter<CarTypeModel>(this, R.layout.car_sub_list_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, CarTypeModel item) {
                helper.setText(R.id.sub_name, item.getName2());
            }
        };
        subAdapter.replaceAll(data);
        subListView.setAdapter(subAdapter);
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarTypeModel sub = (CarTypeModel) subAdapter.getItem(position);
                model.setName2(sub.getName2());
                model.setSecond_code(sub.getSecond_code());
                Intent resultIntent = new Intent();
                resultIntent.setAction(CARTYPE);
                resultIntent.putExtra("data",model);
                sendBroadcast(resultIntent);
                finish();
            }
        });
    }

    private void getListData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("first_code", model.getFirst_code());
        HttpManager.post(SHSConst.CARCLASSIFY, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONArray result = jsonResponse.getJSONArray(SHSConst.HTTP_RESULT);
                        parseData(result);
                        subAdapter.replaceAll(list);
                        subListView.setAdapter(subAdapter);
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(CarTypeSubActivity.this, "获取爱车列表失败");
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(CarTypeSubActivity.this, getString(R.string.net_error));
            }
        }, null));
    }

    private void parseData(JSONArray result) {
        for (int i = 0; i < result.size(); i++) {
            CarTypeModel model = new CarTypeModel();
            //品牌名
            model.setName2(result.getJSONObject(i).getString("name"));
            //品牌编号
            model.setSecond_code(result.getJSONObject(i).getString("second_code"));
            list.add(model);
        }
    }
}
