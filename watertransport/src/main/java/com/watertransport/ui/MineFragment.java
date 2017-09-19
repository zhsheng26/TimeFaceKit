package com.watertransport.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;
import com.watertransport.ui.mine.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.timeface.timekit.fragment.TfBaseFragment;
import cn.timeface.timekit.util.UiUtil;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangsheng on 2017/8/5.
 */

public class MineFragment extends TfBaseFragment {
    @BindView(R.id.iv_profile_image)
    CircleImageView ivProfileImage;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_send_ordered_count)
    TextView tvSendOrderedCount;
    @BindView(R.id.tv_send_ordering_count)
    TextView tvSendOrderingCount;
    @BindView(R.id.layout_order_info)
    LinearLayout layoutOrderInfo;
    @BindView(R.id.iv_m1)
    ImageView ivM1;
    @BindView(R.id.layout_mine_order)
    RelativeLayout layoutMineOrder;
    @BindView(R.id.iv_m2)
    ImageView ivM2;
    @BindView(R.id.layout_base_info)
    RelativeLayout layoutBaseInfo;
    @BindView(R.id.iv_m3)
    ImageView ivM3;
    @BindView(R.id.layout_setting)
    RelativeLayout layoutSetting;
    Unbinder unbinder;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUserName.setText(FastData.getRealName());
        int userRole = FastData.getUserRole();
        UiUtil.showView(layoutOrderInfo, false);
        layoutBaseInfo.setOnClickListener(v -> UserInfoActivity.start(getActivity()));
        layoutSetting.setOnClickListener(v -> SettingActivity.start(getActivity()));
        layoutMineOrder.setOnClickListener(v -> {
            if (userRole == WtConstant.USER_ROLE_CARGO) {
                CargoOwnerOrderActivity.start(getActivity());
            } else {
                BoatHostOrderActivity.start(getActivity());
            }
        });

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
