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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.timeface.timekit.fragment.TfBaseFragment;

/**
 * Created by zhangsheng on 2017/9/3.
 */

public abstract class BaseListFragment extends TfBaseFragment implements OnRefreshLoadmoreListener {
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_no_content)
    TextView tvNoContent;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    protected int currentPageNo;
    protected ApiStores apiStores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.layout_list_content, container, false);
        unbinder = ButterKnife.bind(this, listView);
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext())).setEnableHeaderTranslationContent(false);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        rvContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.TRANSPARENT)
                .sizeResId(R.dimen.normal_margin)
                .build());
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.setAdapter(getAdapter());
        apiStores = ApiService.getInstance().getApi();
        return listView;
    }

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public abstract void onLoadmore(RefreshLayout refreshlayout);

    @Override
    public abstract void onRefresh(RefreshLayout refreshlayout);
}
