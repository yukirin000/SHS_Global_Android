package com.shs.global.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
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
import com.shs.global.model.OrderModel;
import com.shs.global.utils.DateUtils;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhai on 2016/4/18.
 */
public class BeforeOrderFragment extends BaseFragment implements AbsListView.OnScrollListener{
    //是否是下拉刷新
    private boolean isFresh = true;
    //是否是下拉加载
    private boolean isMore = false;
    // 当前数据的页
    private int pageIndex = 1;
    // 是否是最后一页数据
    private int listSize;
    private int lastItem;
    private boolean isLoad = false;
    private boolean lastPage = false;
    @ViewInject(R.id.order_list)
    private ListView listView;
    @ViewInject(R.id.down_fresh)
    private SwipeRefreshLayout swipeRefreshLayout;
    private SHSGlobalAdapter orderedAdapter;
   private List<OrderModel> list;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_before_order;
    }
    @Override
    public void loadLayout(View rootView) {

    }
    @Override
    public void setUpViews(View rootView) {
        list=new ArrayList<>();
        getData();
        initListView();
    }
    private void initListView() {
        orderedAdapter = new SHSGlobalAdapter<OrderModel>(getActivity(), R.layout.order_list_item) {
            @Override
            protected void convert(SHSGlobalBaseAdapterHelper helper, OrderModel item) {
                ImageView orderImage=helper.getView(R.id.shop_image);
                Glide.with(BeforeOrderFragment.this).load(item.getShopSubImage()).into(orderImage);
                helper.setText(R.id.shop_name, item.getShopName());
                helper.setText(R.id.pay_money,item.getPayMoney());
                helper.setText(R.id.order_date, DateUtils.dateStreamToCalendar(item.getDate()));
                helper.setVisible(R.id.is_use,false);
            }
        };
        listView.setOnScrollListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isFresh = true;
                isMore=false;
                lastPage = false;
                Log.i("wea", isLoad + "");
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void getData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", UserManager.getInstance().getUserID() + "");
        HttpManager.post(SHSConst.SERVICELIST, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        JSONObject result = jsonResponse.getJSONObject(SHSConst.HTTP_RESULT);
                        JSONArray JSONlist=result.getJSONArray(SHSConst.HTTP_LSIT);
                        for (int i = 0; i < JSONlist.size(); i++) {
                            OrderModel moder = new OrderModel();
                            moder.setContentWithJson(JSONlist.getJSONObject(i));
                            list.add(moder);
                        }
                        if (result.getString("is_last").equals("0")) {
                            lastPage = false;
                            pageIndex++;
                        } else {
                            lastPage = true;
                        }
                        if (isFresh) {
                            orderedAdapter.replaceAll(list);
                            isFresh = false;
                        }
                        if (isMore) {
                            orderedAdapter.addAll(list);
                            isMore = false;
                        //orderedAdapter.setSelection(lastItem);
                        }
                        listView.setAdapter(orderedAdapter);
                        isLoad = false;
                        break;
                    case SHSConst.STATUS_FAIL:
                        ToastUtil.show(getActivity(), "获取服务中列表失败");
                        break;
                }
            }
            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(getActivity(), getString(R.string.net_error));
            }
        }, null));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastItem == listSize && scrollState == this.SCROLL_STATE_IDLE) {
            isMore = true;
            if (!isLoad) {
                isLoad = true;
                Log.i("wx", lastPage + "");
                if (!lastPage) {
                    getData();
                }
            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = totalItemCount;
        listSize = firstVisibleItem + visibleItemCount;
    }
}
