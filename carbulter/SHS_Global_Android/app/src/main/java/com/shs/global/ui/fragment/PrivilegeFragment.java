package com.shs.global.ui.fragment;

import android.content.Intent;
import android.location.LocationListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.model.PrivilegeModel;
import com.shs.global.ui.activity.BaseActivity;
import com.shs.global.ui.activity.CarShopActivity;
import com.shs.global.ui.activity.OnlineConsultantActivity;
import com.shs.global.ui.activity.ServiceActivity;
import com.shs.global.ui.service.LocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/3/24.
 */
public class PrivilegeFragment extends BaseFragment {
//    @ViewInject(R.id.serviece_listview)
//    private ListView listview;
    @ViewInject(R.id.car_repair)
    private TextView repairText;//保养维修
    @ViewInject(R.id.car_decoration)
    private TextView decorationText;//豪车美容
    @ViewInject(R.id.online_consult)
    private TextView consultText;//在线问诊
    @ViewInject(R.id.online_insurance)
    private TextView insuranceText;//保险咨询
    private List<PrivilegeModel> data;

    @OnClick(value = {R.id.car_repair, R.id.car_decoration, R.id.online_consult, R.id.online_insurance})
    public void touch(View view) {
        switch (view.getId()) {

            case R.id.car_decoration:
                Intent shopIntent = new Intent(getActivity(), CarShopActivity.class);
                startActivity(shopIntent);
                break;
            case R.id.online_consult:
                Intent onlineIntent = new Intent(getActivity(), OnlineConsultantActivity.class);
                startActivity(onlineIntent);
                break;
            case R.id.car_repair:
            case  R.id.online_insurance:
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_privilege;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {
        PrivilegeModel model;
        data = new ArrayList<PrivilegeModel>();
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                model = new PrivilegeModel();
                model.setPrivilegeName("豪车美容");
                model.setHeadImage("cosmetology");
            } else if (i == 1) {
                model = new PrivilegeModel();
                model.setPrivilegeName("在线问诊");
                model.setHeadImage("maintain");
            } else if (i == 2) {
                model = new PrivilegeModel();
                model.setPrivilegeName("保险咨询");
                model.setHeadImage("consult");
            } else {
                model = new PrivilegeModel();
                model.setPrivilegeName("保养维修");
                model.setHeadImage("maintain");
            }
            data.add(model);
        }
        initListView();
    }

    private void initListView() {
        SHSGlobalAdapter adapter = new SHSGlobalAdapter<PrivilegeModel>(getActivity(),
                R.layout.privileg_listview_item) {

            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, PrivilegeModel item) {
                helper.setText(R.id.service_name, item.getPrivilegeName());
//                if (helper.getPosition() < 1) {
//                    helper.setVisible(R.id.specification, true);
//                } else {
//                    helper.setVisible(R.id.specification, false);
//                }
                ImageView image = helper.getView(R.id.service_head_image);
                try {
                    Glide.with(PrivilegeFragment.this).load(getDrawableId(item.getHeadImage())).into(image);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        };
        adapter.addAll(data);
//        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    Intent shopIntent = new Intent(getActivity(), CarShopActivity.class);
//                    startActivity(shopIntent);
//
//                } else if (position == 1) {
//                    Intent shopIntent = new Intent(getActivity(), OnlineConsultantActivity.class);
//                    startActivity(shopIntent);
//                } else {
//                    Intent intent = new Intent(getActivity(), ServiceActivity.class);
//                    startActivity(intent);
//                }
//                Intent serviceIntent = new Intent(getActivity(), LocationService.class);
//                getActivity().startService(serviceIntent);
//                Log.i("zwea", "启动服务");
//            }
//        });
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
}
