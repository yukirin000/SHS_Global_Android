package com.shs.global.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.shs.global.R;
import com.shs.global.control.InformManager;
import com.shs.global.helper.SHSGlobalAdapter;
import com.shs.global.helper.SHSGlobalBaseAdapterHelper;
import com.shs.global.model.InformModel;
import com.shs.global.utils.SHSConst;

import java.util.List;

/**
 * Created by wenhai on 2016/4/20.
 */
public class InformActivity extends BaseActivityWithTopBar {
    private BroadcastReceiver messageReceiver;
    @ViewInject(R.id.notify_list_view)
    private ListView listView;
    private List<InformModel> data;
    private SHSGlobalAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_infor;
    }

    @Override
    protected void setUpView() {
        setBarText("通知");
        init();
        initListView();
        getData();
    }

    private void init() {
        messageReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refresh();
            }
        };
        IntentFilter newsfilter = new IntentFilter(
                SHSConst.INFORM);
        registerReceiver(messageReceiver, newsfilter);
    }
    private void initListView() {
        adapter = new SHSGlobalAdapter<InformModel>(this, R.layout.inform_list_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, InformModel item) {
              helper.setText(R.id.car_plate,item.getPlateNum());
                ImageView readview=helper.getView(R.id.read_state);
                if (item.getCarState()==1){
                    helper.setText(R.id.car_state,"审核通过");
                }else {
                    helper.setText(R.id.car_state,"审核未通过");
                }
                if (item.getReadState()==1){
                    readview.setVisibility(View.VISIBLE);
                }else {
                    readview.setVisibility(View.GONE);
                }
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(InformActivity.this,MyCarDetailsActivity.class);
                InformModel  model= (InformModel) adapter.getItem(position);
                model.setReadState(0);
                adapter.notifyDataSetChanged();
                intent.putExtra(MyLoveCarActivity.CARID,model.getCarID());
                startActivity(intent);
            }
        });
    }

    private void getData() {
        refresh();
    }
   private void refresh(){
       data = InformManager.getInstance(getApplicationContext()).readInform();
       adapter.replaceAll(data);
   }
    @Override
    protected void onDestroy() {
        if (messageReceiver != null) {
            unregisterReceiver(messageReceiver);
            messageReceiver = null;
        }
        super.onDestroy();
    }
}
