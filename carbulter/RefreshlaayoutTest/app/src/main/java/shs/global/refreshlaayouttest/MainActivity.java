package shs.global.refreshlaayouttest;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 模拟一些数据
        final List<String> datas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            datas.add("item - " + i);
        }

        // 构造适配器
        final BaseAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                datas);
        // 获取listview实例
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        // 获取RefreshLayout实例
        final RefreshLayout myRefreshListView = (RefreshLayout)
                findViewById(R.id.swipe_layout);

        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        myRefreshListView
                .setColorScheme(R.color.umeng_comm_text_topic_light,R.color.umeng_comm_yellow, R.color.umeng_comm_green,
                        R.color.umeng_comm_linked_text);
        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        datas.add(new Date().toGMTString());
                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        // 加载监听器
        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {

                Toast.makeText(MainActivity.this, "load", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        datas.add(new Date().toGMTString());
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        myRefreshListView.setLoading(false);
                    }
                }, 1500);

            }
        });
    }

}
