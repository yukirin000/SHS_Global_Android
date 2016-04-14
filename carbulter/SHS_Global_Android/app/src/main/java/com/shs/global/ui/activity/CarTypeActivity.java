package com.shs.global.ui.activity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.model.CarTypeModel;
import com.shs.global.utils.InitCarTypeUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/14.
 */
public class CarTypeActivity extends BaseActivityWithTopBar {
    List<CarTypeModel> list = new ArrayList<>();
    @ViewInject(R.id.type_list)
    private ListView listView;
    //车图片名字
    private static String[] carIcon = {"benz", "bmw", "audi", "porsche", "land", "bentley", "rolls", "maserati", "lamborghini", "ferrari", "lincoln"};

    @Override
    public int setLayoutId() {
        return R.layout.activity_car_type;
    }

    @Override
    protected void setUpView() {
        setBarText("品牌选择");
        initList();
    }

    private void initList() {
        SHSGlobalAdapter adapter = new SHSGlobalAdapter<CarTypeModel>(this, R.layout.car_type_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, CarTypeModel item) {
                helper.setText(R.id.car_name, item.getName());
                ImageView imageView = helper.getView(R.id.car_icon);
                try {
                    Glide.with(CarTypeActivity.this).load(getDrawableId(item.getIconName())).into(imageView);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        };
        adapter.replaceAll(getTypeList());
        listView.setAdapter(adapter);
    }

    private int getDrawableId(String name) throws NoSuchFieldException {
        int id = 0;
        Class cls = R.drawable.class;
        try {
            id = cls.getField(name).getInt(name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<CarTypeModel> getTypeList() {
        String jsony = InitCarTypeUtils.getJson(this);
        JSONArray   jsonArray= (JSONArray) JSONArray.parse(jsony);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CarTypeModel model = new CarTypeModel();
            model.setName(jsonObject.getString("name"));
            model.setSub(jsonObject.getJSONArray("sub"));
            model.setIconName(carIcon[i]);
            list.add(model);
        }
        return list;
    }
}
