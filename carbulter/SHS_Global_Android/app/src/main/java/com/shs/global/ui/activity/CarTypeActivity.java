package com.shs.global.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.shs.global.model.CarTypeModel;
import com.shs.global.model.LoveCarModel;
import com.shs.global.utils.InitCarTypeUtils;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/14.
 */
public class CarTypeActivity extends BaseActivityWithTopBar {
    private String CARTYPE="cartype";
    public static final int requestCode = 1;
    private List<CarTypeModel> list;
    @ViewInject(R.id.type_list)
    private ListView listView;
    //车图片名字
    private SHSGlobalAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_car_type;
    }

    @Override
    protected void setUpView() {
        list = new ArrayList<>();
        setBarText("品牌选择");
        getListData();
        initList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarTypeModel model = ((CarTypeModel) adapter.getItem(position));
                if ("1".equals(model.getHassub())) {
                    Intent intent = new Intent(CarTypeActivity.this, CarTypeSubActivity.class);
                    intent.putExtra("data", model);
                    startActivity(intent);
                }else {
                    Intent resultIntent = new Intent( );
                    resultIntent.setAction(CARTYPE);
                    resultIntent.putExtra("data", model);
                    sendBroadcast(resultIntent);
                }
                finish();
            }
        });

    }

    private void getListData() {
        HttpManager.get(SHSConst.CARCATEGORY, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONArray result = jsonResponse.getJSONArray(SHSConst.HTTP_RESULT);
                        parseData(result);
                        adapter.replaceAll(list);
                        listView.setAdapter(adapter);
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(CarTypeActivity.this, "获取车型列表失败");
                        break;
                }
            }
            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(CarTypeActivity.this, getString(R.string.net_error));
            }
        }, null));
    }
    private void parseData(JSONArray result) {
        for (int i = 0; i < result.size(); i++) {
            CarTypeModel model = new CarTypeModel();
            model.setIconName(result.getJSONObject(i).getString("image"));
            //品牌名
            model.setName1(result.getJSONObject(i).getString("name"));
            //品牌编号
            model.setFirst_code(result.getJSONObject(i).getString("first_code"));
            model.setHassub(result.getJSONObject(i).getString("has_second"));
            list.add(model);
        }
    }

    private void initList() {
        adapter = new SHSGlobalAdapter<CarTypeModel>(this, R.layout.car_type_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, CarTypeModel item) {
                helper.setText(R.id.car_name, item.getName1());
                ImageView imageView = helper.getView(R.id.car_icon);
                Glide.with(CarTypeActivity.this).load(item.getIconName()).into(imageView);
            }
        };

    }

//    private int getDrawableId(String name) throws NoSuchFieldException {
//        int id = 0;
//        Class cls = R.drawable.class;
//        try {
//            id = cls.getField(name).getInt(name);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return id;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
