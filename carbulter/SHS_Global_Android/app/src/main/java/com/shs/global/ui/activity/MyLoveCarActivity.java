package com.shs.global.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/3/25.
 */
public class MyLoveCarActivity extends BaseActivityWithTopBar implements View.OnClickListener {
    public static final String CARID = "car_id";
    @ViewInject(R.id.my_car_list)
    private ListView myCarListView;
    private List<LoveCarModel> list;
    private SHSGlobalAdapter lcarAdapter;

    @ViewInject(R.id.none_car)
    private TextView noneText;

    @ViewInject(R.id.refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_love_car;
    }

    @Override
    public void onResume() {
        getlistData();
        super.onResume();
    }

    @Override
    protected void setUpView() {

        list = new ArrayList<>();
        setBarText("我的爱车");
        // 右上角按钮
        ImageView rightBtn = addRightImgBtn(R.layout.right_image_button, R.id.layout_top_btn_root_view,
                R.id.img_btn_right_top);
        rightBtn.setImageResource(R.drawable.add);
        rightBtn.setOnClickListener(this);
        initListView();
    }


    private void initListView() {
        lcarAdapter = new SHSGlobalAdapter<LoveCarModel>(this, R.layout.my_love_car_list_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, LoveCarModel item) {
                helper.setText(R.id.car_type, item.getCarType());
                TextView textView = helper.getView(R.id.car_plate);
//                if (item.getState() == 1) {
//                    textView.setText("正在审核");
//                    textView.setTextColor(Color.parseColor("#ff0000"));
//                } else {
                    textView.setText(item.getPlateNum());
//                }
            }
        };
        myCarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoveCarModel model = (LoveCarModel) lcarAdapter.getItem(position);
                Intent intent = new Intent(MyLoveCarActivity.this, MyCarDetailsActivity.class);
                intent.putExtra(CARID, model.getCarID());
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getlistData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getlistData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", UserManager.getInstance().getUserID() + "");
        Log.i("wx", SHSConst.MYCARS + "?user_id=" + UserManager.getInstance().getUserID());
        HttpManager.post(SHSConst.MYCARS, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONArray result = jsonResponse.getJSONArray(SHSConst.HTTP_RESULT);
                        Log.i("wx", result.toString());
                        list.clear();
                        for (int i = 0; i < result.size(); i++) {
                            LoveCarModel model = new LoveCarModel();
                            model.setContentWithJson(result.getJSONObject(i));
                            list.add(model);
                        }
                        if (list.size() == 0) {
                            noneText.setVisibility(View.VISIBLE);
                            myCarListView.setVisibility(View.GONE);
                        } else {
                            noneText.setVisibility(View.GONE);
                            myCarListView.setVisibility(View.VISIBLE);
                            lcarAdapter.replaceAll(list);
                            myCarListView.setAdapter(lcarAdapter);
                        }
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(MyLoveCarActivity.this, "获取爱车列表失败");
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(MyLoveCarActivity.this, getString(R.string.net_error));
            }
        }, null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.call_telephone:
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008693911"));
//                startActivity(intent);
//                break;
            case R.id.img_btn_right_top:
                Intent intent1 = new Intent(this, AddMyCarActivity.class);
                startActivity(intent1);
                break;
        }
    }


}
