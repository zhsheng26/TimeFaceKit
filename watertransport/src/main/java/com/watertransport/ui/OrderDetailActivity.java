package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.entity.CargoOrderObj;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;

public class OrderDetailActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_start_address)
    TextView tvStartAddress;
    @BindView(R.id.tv_end_address)
    TextView tvEndAddress;
    @BindView(R.id.tv_publish_date)
    TextView tvPublishDate;
    @BindView(R.id.tv_cargo_name)
    TextView tvCargoName;
    @BindView(R.id.tv_cargo_weight)
    TextView tvCargoWeight;
    @BindView(R.id.tv_cargo_price)
    TextView tvCargoPrice;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.iv_call_phone)
    ImageView ivCallPhone;
    @BindView(R.id.tv_extra)
    TextView tvExtra;

    public static void start(Context context, CargoOrderObj orderObj) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra("orderObj", orderObj);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setTitle("订单详情");
        CargoOrderObj orderObj = getIntent().getParcelableExtra("orderObj");
        tvStartAddress.setText(orderObj.getLoadTerminal());
        tvEndAddress.setText(orderObj.getUnloadTerminal());
        tvPublishDate.setText(String.format("发布时间：%s", orderObj.getCreateDate()));
        tvCargoName.setText(orderObj.getCargoName());
        tvCargoWeight.setText(String.format("%s吨", orderObj.getTonnage()));
        tvCargoPrice.setText(String.format("%s元/吨", orderObj.getTonnageCost()));
        String name = TextUtils.isEmpty(orderObj.getContactor()) ? orderObj.getTransporterName() : orderObj.getContactor();
        tvContacts.setText(String.format("联系人：%s", TextUtils.isEmpty(name) ? orderObj.getTransporter() : name));
        tvPhone.setText(String.format("手机号码：%s", orderObj.getMobile()));
        tvMobile.setText(String.format("固定电话：%s", orderObj.getPhone()));
        tvExtra.setText(String.format("备注：%s", orderObj.getRemarks()));
        ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + orderObj.getMobile()));
                startActivity(intent);
            }
        });


    }
}
