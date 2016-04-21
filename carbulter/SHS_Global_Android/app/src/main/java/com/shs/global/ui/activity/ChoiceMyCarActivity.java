package com.shs.global.ui.activity;

import android.util.Log;
import android.view.View;
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
 * Created by wenhai on 2016/4/18.
 */
public class ChoiceMyCarActivity extends BaseActivityWithTopBar {
    @ViewInject(R.id.choice_car_list)
    private ListView choiceListView;
    @ViewInject(R.id.none_content)
    private TextView noneText;
    private List<LoveCarModel> data;
    private SHSGlobalAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_choice_my_car;
    }

    @Override
    protected void setUpView() {
        setBarText("选择我的爱车");
        data=new ArrayList<>();
        getData();
        initListView();
    }

    private void initListView() {
        adapter = new SHSGlobalAdapter<LoveCarModel>(this, R.layout.my_love_car_list_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, LoveCarModel item) {
                helper.setText(R.id.car_type, item.getCarType());
                helper.setText(R.id.car_plate, item.getPlateNum());
            }
        };
    }
    private void getData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", UserManager.getInstance().getUserID() + "");
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
                            noneText.setVisibility(View.VISIBLE);
                            choiceListView.setVisibility(View.GONE);
                        } else {
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
