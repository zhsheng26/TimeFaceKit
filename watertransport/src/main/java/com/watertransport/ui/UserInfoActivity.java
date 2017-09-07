package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.util.UiUtil;

public class UserInfoActivity extends TfBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.ll_user_avatar)
    LinearLayout llUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_user_name)
    LinearLayout llUserName;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.ll_user_mobile)
    LinearLayout llUserMobile;
    @BindView(R.id.tv_user_realName)
    TextView tvUserRealName;
    @BindView(R.id.ll_user_real_name)
    LinearLayout llUserRealName;
    @BindView(R.id.tv_boat_num)
    TextView tvBoatNum;
    @BindView(R.id.ll_boat_num)
    LinearLayout llBoatNum;
    @BindView(R.id.tv_boat_host_verify)
    TextView tvBoatHostVerify;
    @BindView(R.id.ll_boat_host_verify)
    LinearLayout llBoatHostVerify;
    @BindView(R.id.tv_boat_ton)
    TextView tvBoatTon;
    @BindView(R.id.ll_boat_ton)
    LinearLayout llBoatTon;
    @BindView(R.id.tv_boat_address)
    TextView tvBoatAddress;
    @BindView(R.id.ll_boat_address)
    LinearLayout llBoatAddress;
    @BindView(R.id.tv_boat_company)
    TextView tvBoatCompany;
    @BindView(R.id.ll_boat_company)
    LinearLayout llBoatCompany;
    @BindView(R.id.ll_boat_info)
    LinearLayout llBoatInfo;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setTitle("基本信息");
        tvUserName.setText(FastData.getLoginName());
        tvUserMobile.setText(FastData.getPhone());
        tvUserRealName.setText(FastData.getRealName());
        int userRole = FastData.getUserRole();
        UiUtil.showView(llBoatInfo, userRole == WtConstant.USER_ROLE_BOAT);

    }
}
