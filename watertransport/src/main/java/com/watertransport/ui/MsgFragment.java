package com.watertransport.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.api.ApiStores;
import com.watertransport.entity.MsgObj;
import com.watertransport.entity.PageInfo;
import com.watertransport.support.FastData;
import com.watertransport.ui.adapter.MsgAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.timeface.timekit.fragment.TfBaseFragment;
import cn.timeface.timekit.support.net.NetResponse;
import cn.timeface.timekit.support.SchedulersCompat;
import cn.timeface.timekit.util.UiUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zhangsheng on 2017/8/5.
 */

public class MsgFragment extends TfBaseFragment {
    public int page_no = 1;
    @BindView(R.id.tv_menu)
    ImageView tvMenu;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_no_content)
    TextView tvNoContent;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private ApiStores apiStores;
    private ArrayList<MsgObj> msgObjs;
    private MsgAdapter msgAdapter;

    public static MsgFragment newInstance() {

        Bundle args = new Bundle();

        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiStores = ApiService.getInstance().getApi();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext())).setEnableHeaderTranslationContent(false);
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMsg(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getMsg(true);
            }
        });
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.TRANSPARENT)
                .sizeResId(R.dimen.normal_margin)
                .build());
        msgObjs = new ArrayList<>();
        msgAdapter = new MsgAdapter(msgObjs);
        rvContent.setAdapter(msgAdapter);
        refreshLayout.autoRefresh();
    }

    private void getMsg(boolean refresh) {
        if (refresh) {
            page_no = 1;
        }
        Disposable disposable = apiStores.userMsgList(page_no, 20, FastData.getUserId())
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new Consumer<NetResponse<PageInfo<MsgObj>>>() {
                    @Override
                    public void accept(NetResponse<PageInfo<MsgObj>> pageInfoNetResponse) throws Exception {
                        if (pageInfoNetResponse.isResult()) {
                            page_no++;
                            PageInfo<MsgObj> objPageInfo = pageInfoNetResponse.getData();
                            List<MsgObj> msgs = objPageInfo.getList();
                            tvNoContent.setVisibility(View.GONE);
                            if (refresh) {
                                msgObjs.clear();
                                UiUtil.showView(tvNoContent, msgs.size() == 0);
                                refreshLayout.finishRefresh();
                            } else {
                                refreshLayout.finishLoadmore();
                            }
                            msgObjs.addAll(msgs);
                            msgAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        addSubscription(disposable);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
