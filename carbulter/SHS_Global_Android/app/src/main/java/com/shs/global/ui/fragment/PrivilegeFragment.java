package com.shs.global.ui.fragment;

import android.content.Intent;
import android.location.LocationListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.ui.activity.BaseActivity;
import com.shs.global.ui.activity.CarShopActivity;
import com.shs.global.ui.service.LocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/3/24.
 */
public class PrivilegeFragment extends BaseFragment {
    @ViewInject(R.id.serviece_listview)
    private ListView listview;
    private List<String> data;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_privilege;
    }

    @Override
    public void loadLayout(View rootView) {

    }

    @Override
    public void setUpViews(View rootView) {
        data = new ArrayList<String>();
        data.add("豪车精洗");
        data.add("豪车美容");
        data.add("在线问诊");
        data.add("保险咨询");
        data.add("道路救援");
        data.add("车辆维修");
        initListView();
    }

    private void initListView() {
        SHSGlobalAdapter adapter = new SHSGlobalAdapter<String>(getActivity(),
                R.layout.privileg_listview_item) {

            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, String item) {
                helper.setText(R.id.service_name, item);
            }
        };
        adapter.addAll(data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent serviceIntent=new Intent(getActivity(), LocationService.class);
                getActivity().startService(serviceIntent);
                Intent shopIntent=new Intent(getActivity(), CarShopActivity.class);
                getActivity().startActivity(shopIntent);
                Log.i("zwea","启动服务");
            }
        });
    }
}
