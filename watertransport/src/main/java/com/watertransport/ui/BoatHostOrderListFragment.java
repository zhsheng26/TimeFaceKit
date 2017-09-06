package com.watertransport.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.watertransport.entity.BoatHostOrderObj;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;
import com.watertransport.ui.adapter.BoatHostOrderAdapter;

import java.util.List;

import cn.timeface.timekit.support.SchedulersCompat;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by zhangsheng on 2017/9/3.
 */

public class BoatHostOrderListFragment extends BaseListFragment {

    private BoatHostOrderAdapter hostOrderAdapter;
    private int orderState = 0;

    public static BoatHostOrderListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        BoatHostOrderListFragment fragment = new BoatHostOrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String type = getArguments().getString("type");
        if (WtConstant.BOAT_ORDER_ALL.equals(type)) {
            orderState = 0;//全部
        } else if (WtConstant.BOAT_ORDER_NO.equals(type)) {
            orderState = 1;//未结算
        } else {
            orderState = 2;//已结算
        }
        refreshLayout.autoRefresh();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        hostOrderAdapter = new BoatHostOrderAdapter();
        return hostOrderAdapter;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        reqData(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        reqData(true);
    }

    private void reqData(boolean refresh) {
        if (refresh) currentPageNo = 1;
        Disposable disposable = apiStores.boatOrderList(FastData.getUserId(), orderState, currentPageNo, 20)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(pageInfoNetResponse -> {
                    if (pageInfoNetResponse.isResult()) {
                        List<BoatHostOrderObj> hostOrderObjs = pageInfoNetResponse.getData().getList();
                        if (refresh) {
                            refreshLayout.finishRefresh();
                            if (hostOrderObjs == null || hostOrderObjs.size() == 0) {
                                tvNoContent.setVisibility(View.VISIBLE);
                            } else {
                                currentPageNo += 1;
                                hostOrderAdapter.setData(hostOrderObjs);
                            }
                        } else {
                            hostOrderAdapter.addData(hostOrderObjs);
                            refreshLayout.finishLoadmore();
                        }
                    }
                }, Timber::d);
        addSubscription(disposable);
    }
}
