package com.watertransport.ui.boatowner;

import android.os.Bundle;
import android.view.View;

import cn.timeface.timekit.fragment.TfBaseFragment;

/**
 * Created by zhangsheng on 2017/8/2.
 */

public class BoatOwnerHomeFragment extends TfBaseFragment {
    public static BoatOwnerHomeFragment newInstance() {

        Bundle args = new Bundle();

        BoatOwnerHomeFragment fragment = new BoatOwnerHomeFragment();
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
