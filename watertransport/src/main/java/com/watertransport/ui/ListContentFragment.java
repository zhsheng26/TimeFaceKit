package com.watertransport.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.api.ApiStores;
import com.watertransport.entity.CargoOrderObj;
import com.watertransport.entity.PageInfo;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;
import com.watertransport.support.event.UpdateListEvent;
import com.watertransport.ui.adapter.CargoHostOrderAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.timeface.timekit.fragment.TfBaseFragment;
import cn.timeface.timekit.support.IEventBus;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.util.UiUtil;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


/**
 * Created by zhangsheng on 2017/8/6.
 */

public class ListContentFragment extends TfBaseFragment implements IEventBus {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_no_content)
    TextView tvNoContent;
    private ApiStores apiStores;
    private int pageState = WtConstant.PAGE_STATE_NO_PUBLISH;
    private int currentPageNo = 1;
    private CargoHostOrderAdapter cargoHostOrderAdapter;

    public static ListContentFragment newInstance(int pageType) {

        Bundle args = new Bundle();
        args.putInt("pageType", pageType);

        ListContentFragment fragment = new ListContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.layout_list_content, container, false);
        unbinder = ButterKnife.bind(this, content);
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext())).setEnableHeaderTranslationContent(false);
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getCargoOrder(pageState, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPageNo = 1;
                getCargoOrder(pageState, true);
            }
        });
        rvContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.TRANSPARENT)
                .sizeResId(R.dimen.normal_margin)
                .build());
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        cargoHostOrderAdapter = new CargoHostOrderAdapter(pageState);
        rvContent.setAdapter(cargoHostOrderAdapter);
        apiStores = ApiService.getInstance().getApi();
        cargoHostOrderAdapter.setOnItemClickListener((view, cargoOrderObj, position, bundle) -> {
            int id = view.getId();
            if (id == R.id.btn_edit || id == R.id.btn_edit_publishing) {
                AddNewOrderActivity.start(getContext(), cargoOrderObj);
            } else if (id == R.id.btn_publish) {
                publishOrder(cargoOrderObj);
            } else if (id == R.id.btn_to_close) {
                closeOrder(cargoOrderObj);
            } else {
                AddNewOrderActivity.startForDetail(getContext(), cargoOrderObj);
            }
        });
        return content;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentPageNo = 1;

        int pageType = getArguments().getInt("pageType");
        switch (pageType) {
            case WtConstant.BOAT_PAGE_1:

                break;
            case WtConstant.BOAT_PAGE_2:
                break;
            case WtConstant.CARGO_PAGE_1:
                pageState = WtConstant.PAGE_STATE_PUBLISHING;
                break;
            case WtConstant.CARGO_PAGE_2:
                pageState = WtConstant.PAGE_STATE_NO_PUBLISH;
                break;
            case WtConstant.CARGO_PAGE_3:
                pageState = WtConstant.PAGE_STATE_CLOSED;
                break;
        }
        cargoHostOrderAdapter.setPageState(pageState);
        refreshLayout.autoRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getCargoOrder(int state, boolean refresh) {
        Disposable disposable = apiStores.list(currentPageNo, 20, state, FastData.getUserId())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(pageInfoNetResponse -> {
                    boolean result = pageInfoNetResponse.isResult();
                    if (result) {
                        currentPageNo++;
                        PageInfo<CargoOrderObj> data = pageInfoNetResponse.getData();
                        List<CargoOrderObj> orderObjs = data.getList();
                        if (orderObjs == null) {
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadmore();
                            return;
                        }
                        if (refresh) {
                            UiUtil.showView(tvNoContent, orderObjs.size() == 0);
                            cargoHostOrderAdapter.setNewData(orderObjs);
                            refreshLayout.finishRefresh();
                        } else {
                            tvNoContent.setVisibility(View.GONE);
                            cargoHostOrderAdapter.addData(orderObjs);
                            refreshLayout.finishLoadmore();
                        }

                    } else {
                        showToast(pageInfoNetResponse.getMessage());
                    }
                }, throwable -> {
                    Timber.e(throwable);
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadmore();
                    showToast("网络不通");
                });
        addSubscription(disposable);

    }

    private void publishOrder(CargoOrderObj cargoOrderObj) {
        Disposable disposable = apiStores.updateStatue(cargoOrderObj.getId(), FastData.getUserId(), 1)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                    showToast(netResponse.getMessage());
                    if (netResponse.isResult()) {
                        refreshLayout.autoRefresh();
                        EventBus.getDefault().post(new UpdateListEvent(pageState));
                    }
                }, Timber::e);
        addSubscription(disposable);
    }

    private void closeOrder(CargoOrderObj cargoOrderObj) {
        Disposable disposable = apiStores.updateStatue(cargoOrderObj.getId(), FastData.getUserId(), 2)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(netResponse -> {
                    showToast(netResponse.getMessage());
                    if (netResponse.isResult()) {
                        refreshLayout.autoRefresh();
                        EventBus.getDefault().post(new UpdateListEvent(pageState));
                    }
                }, Timber::e);
        addSubscription(disposable);
    }

    @Subscribe
    public void onEvent(UpdateListEvent event) {
        if (event.getPageState() != pageState) {
            currentPageNo = 1;
            getCargoOrder(pageState, true);
        }
    }

}
