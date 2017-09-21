package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.entity.UserObj;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;

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
    @BindView(R.id.tv_cargo_address)
    TextView tvCargoAddress;
    @BindView(R.id.ll_cargo_extra_ino)
    LinearLayout llCargoExtraIno;
    @BindView(R.id.tv_cargo_company_name)
    TextView tvCargoCompanyName;
    @BindView(R.id.ll_cargo_company_name)
    LinearLayout llCargoCompanyName;
    @BindView(R.id.tv_cargo_register_address)
    TextView tvCargoRegisterAddress;
    @BindView(R.id.ll_cargo_register_address)
    LinearLayout llCargoRegisterAddress;
    @BindView(R.id.ll_cargo_info)
    LinearLayout llCargoInfo;

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
        UserObj userInfo = FastData.getUserInfo();
        /*
        * 船主：
船号(shipCode)、船主证(shipLicense)、船载吨位(tonnage)、所在地(belongs)、所属公司(belongsCompany);
货运公司/货主：
 地址(addrees)、公司名称(companyName)、注册地址(registeAddress)
        * */
        if (userRole == WtConstant.USER_ROLE_BOAT) {
            llBoatInfo.setVisibility(View.VISIBLE);
            if (userInfo == null) return;
            tvBoatAddress.setText(userInfo.getBelongs());
            tvBoatCompany.setText(userInfo.getBelongsCompany());
            tvBoatHostVerify.setText(userInfo.getShipLicense());
            tvBoatNum.setText(userInfo.getShipCode());
            tvBoatTon.setText(userInfo.getTonnage());
        } else {
            llCargoInfo.setVisibility(View.VISIBLE);
            if (userInfo == null) return;
            tvCargoAddress.setText(userInfo.getAddrees());
            tvCargoCompanyName.setText(userInfo.getCompanyName());
            tvCargoRegisterAddress.setText(userInfo.getRegisteAddress());
        }


    }
}
