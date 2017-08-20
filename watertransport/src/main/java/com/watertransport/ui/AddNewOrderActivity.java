package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.api.ApiStores;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;

public class AddNewOrderActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_cargo_kind)
    EditText etCargoKind;
    @BindView(R.id.et_cargo_weight)
    EditText etCargoWeight;
    @BindView(R.id.et_cargo_price)
    EditText etCargoPrice;
    @BindView(R.id.et_cargo_start_address)
    EditText etCargoStartAddress;
    @BindView(R.id.et_cargo_destination)
    EditText etCargoDestination;
    @BindView(R.id.et_cargo_start_time)
    TextView etCargoStartTime;
    @BindView(R.id.ll_cargo_start_time)
    LinearLayout llCargoStartTime;
    @BindView(R.id.et_cargo_arrive_time)
    TextView etCargoArriveTime;
    @BindView(R.id.ll_cargo_arrive_time)
    LinearLayout llCargoArriveTime;
    @BindView(R.id.et_extra_info)
    EditText etExtraInfo;
    @BindView(R.id.et_contact_user)
    EditText etContactUser;
    @BindView(R.id.et_contact_phone)
    EditText etContactPhone;
    private ApiStores apiStores;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddNewOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("新增运单信息");
        apiStores = ApiService.getInstance().getApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            publishOrder();
        }
        return super.onOptionsItemSelected(item);
    }

    private void publishOrder() {

    }
}
