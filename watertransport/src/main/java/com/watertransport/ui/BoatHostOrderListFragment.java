package com.watertransport.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.watertransport.R;
import com.watertransport.entity.BoatHostOrderObj;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;
import com.watertransport.support.event.UpdateListEvent;
import com.watertransport.ui.adapter.BoatHostOrderAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.timeface.timekit.support.IEventBus;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.support.listener.OnItemClickListener;
import cn.timeface.timekit.support.net.NetResponse;
import cn.timeface.timekit.ui.dialog.DialogTip;
import cn.timeface.timekit.ui.dialog.OnDialogListenerAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by zhangsheng on 2017/9/3.
 */

public class BoatHostOrderListFragment extends BaseListFragment implements OnItemClickListener<View, BoatHostOrderObj>, IEventBus {

    private BoatHostOrderAdapter hostOrderAdapter;
    private int orderState = -1;

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
            orderState = -1;//全部
        } else if (WtConstant.BOAT_ORDER_NO.equals(type)) {
            orderState = 0;//未结算
        } else {
            orderState = 1;//已结算
        }
        refreshLayout.autoRefresh();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        hostOrderAdapter = new BoatHostOrderAdapter();
        hostOrderAdapter.setOnItemClickListener(this);
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
        Disposable disposable = apiStores.boatOrderList(FastData.getUserId(), orderState == -1 ? "" : String.valueOf(orderState), currentPageNo, 20)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(pageInfoNetResponse -> {
                    if (pageInfoNetResponse.isResult()) {
                        List<BoatHostOrderObj> hostOrderObjs = pageInfoNetResponse.getData().getList();
                        if (refresh) {
                            refreshLayout.finishRefresh();
                            if (hostOrderObjs == null || hostOrderObjs.size() == 0) {
                                tvNoContent.setVisibility(View.VISIBLE);
                            } else {
                                tvNoContent.setVisibility(View.GONE);
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

    @Override
    public void onItemClick(View view, BoatHostOrderObj boatHostOrderObj, int position, @Nullable Bundle bundle) {
        if (view.getId() == R.id.btn_go_edit) {//去结算
            DialogTip.newInstance()
                    .setTipMsg("确定结算？")
                    .setOnTouchOutside(false)
                    .setOnDialogListener(new OnDialogListenerAdapter() {
                        @Override
                        public void onPositiveSelect() {
                            endOrder(boatHostOrderObj);
                        }
                    })
                    .show(getChildFragmentManager());
        } else if (view.getId() == R.id.btn_delete_has || view.getId() == R.id.btn_delete_no) {//去结算
            DialogTip.newInstance()
                    .setTipMsg("确定删除？")
                    .setOnTouchOutside(false)
                    .setOnDialogListener(new OnDialogListenerAdapter() {
                        @Override
                        public void onPositiveSelect() {
                            delete(boatHostOrderObj);
                        }
                    })
                    .show(getChildFragmentManager());
        } else {
            AddNewOrderActivity.start(getContext(), boatHostOrderObj);
        }
    }

    private void delete(BoatHostOrderObj boatHostOrderObj) {
        apiStores.shipDelete(FastData.getUserId(), boatHostOrderObj.getId())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new Consumer<NetResponse>() {
                    @Override
                    public void accept(NetResponse netResponse) throws Exception {
                        showToast(netResponse.getMessage());
                        if (netResponse.isResult()) {
                            hostOrderAdapter.remove(boatHostOrderObj);
                            EventBus.getDefault().post(new UpdateListEvent(orderState));
                        }
                    }
                }, Timber::d);
    }

    private void endOrder(BoatHostOrderObj boatHostOrderObj) {
        Disposable disposable = apiStores.updateShipOderStatue(FastData.getUserId(), boatHostOrderObj.getId())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                    if (netResponse.isResult()) {
//                        hostOrderAdapter.remove(boatHostOrderObj);
                        EventBus.getDefault().post(new UpdateListEvent(orderState));
                    }
                }, Timber::d);
        addSubscription(disposable);
    }


    @Subscribe
    public void onEvent(UpdateListEvent event) {
        if (event.getPageState() != orderState || orderState == -1) {
            refreshLayout.autoRefresh();
        }
    }
}
