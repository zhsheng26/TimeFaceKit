package com.watertransport.ui.cargohost;

import android.os.Bundle;
import android.view.View;

import cn.timeface.timekit.fragment.TfBaseFragment;

/**
 * Created by zhangsheng on 2017/8/2.
 */

public class CargoHostHomeFragment extends TfBaseFragment {
    public static CargoHostHomeFragment newInstance() {

        Bundle args = new Bundle();

        CargoHostHomeFragment fragment = new CargoHostHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }
}
