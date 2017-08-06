package com.watertransport.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watertransport.support.WtConstant;

import cn.timeface.timekit.fragment.TfBaseFragment;

/**
 * Created by zhangsheng on 2017/8/6.
 */

public class ListContentFragment extends TfBaseFragment {

    private RecyclerView recyclerView;

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
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int pageType = getArguments().getInt("pageType");
        switch (pageType) {
            case WtConstant.BOAT_PAGE_1:
                recyclerView.setAdapter(new CargoHostOrderAdapter());
                break;
        }
    }
}
