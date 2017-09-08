package com.watertransport.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.api.ApiService;
import com.watertransport.api.ApiStores;
import com.watertransport.entity.CargoOrderObj;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;
import com.watertransport.support.event.UpdateListEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;
import cn.timeface.timekit.support.NetResponse;
import cn.timeface.timekit.support.SchedulersCompat;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

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
    @BindView(R.id.ll_boat_time)
    LinearLayout llBoatTime;
    @BindView(R.id.ll_cargo_contacts)
    LinearLayout llCargoContacts;
    private ApiStores apiStores;
    private int userRole;
    private CargoOrderObj cargoOrderObj;
    private boolean detail;

    public static void startForDetail(Context context, CargoOrderObj cargoOrderObj) {
        Intent starter = new Intent(context, AddNewOrderActivity.class);
        starter.putExtra("detail", true);
        starter.putExtra("cargoOrderObj", cargoOrderObj);
        context.startActivity(starter);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AddNewOrderActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, CargoOrderObj cargoOrderObj) {
        Intent starter = new Intent(context, AddNewOrderActivity.class);
        starter.putExtra("cargoOrderObj", cargoOrderObj);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);
        ButterKnife.bind(this);
        detail = getIntent().getBooleanExtra("detail", false);
        apiStores = ApiService.getInstance().getApi();
        llCargoArriveTime.setOnClickListener(v -> selectDate(etCargoArriveTime));
        llCargoStartTime.setOnClickListener(v -> selectDate(etCargoStartTime));
        userRole = FastData.getUserRole();
        if (userRole == WtConstant.USER_ROLE_CARGO) {
            llBoatTime.setVisibility(View.GONE);
        } else {
            llCargoContacts.setVisibility(View.GONE);
        }
        etContactPhone.setText(FastData.getPhone());
        etContactUser.setText(FastData.getRealName());
        cargoOrderObj = getIntent().getParcelableExtra("cargoOrderObj");
        if (cargoOrderObj == null) {
            getSupportActionBar().setTitle("新增运单信息");
            return;
        } else {
            getSupportActionBar().setTitle("编辑运单");
            if (detail) getSupportActionBar().setTitle("订单详情");
        }
        etCargoKind.setText(cargoOrderObj.getCargoName());
        etCargoWeight.setText(cargoOrderObj.getTonnage());
        etCargoPrice.setText(cargoOrderObj.getTonnageCost());
        etCargoStartAddress.setText(cargoOrderObj.getLoadTerminal());
        etCargoDestination.setText(cargoOrderObj.getUnloadTerminal());

        etCargoKind.setEnabled(!detail);
        etCargoWeight.setEnabled(!detail);
        etCargoPrice.setEnabled(!detail);
        etCargoStartAddress.setEnabled(!detail);
        etCargoDestination.setEnabled(!detail);
        etContactUser.setEnabled(!detail);
        etContactPhone.setEnabled(!detail);
        etCargoStartTime.setEnabled(!detail);
        etCargoArriveTime.setEnabled(!detail);
        etExtraInfo.setEnabled(!detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        etCargoKind.requestFocus();
    }

    private void selectDate(TextView showDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            String strDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
            calendar.set(year1, monthOfYear, dayOfMonth);
            showDate.setText(strDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!detail) getMenuInflater().inflate(R.menu.save, menu);
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
        String cargoKind = etCargoKind.getText().toString();
        String cargoWeight = etCargoWeight.getText().toString();
        String price = etCargoPrice.getText().toString();
        String startAddress = etCargoStartAddress.getText().toString();
        String destination = etCargoDestination.getText().toString();
        String startDate = etCargoStartTime.getText().toString();
        String arriveTime = etCargoArriveTime.getText().toString();
        String extraInfo = etExtraInfo.getText().toString();
        String phone = etContactPhone.getText().toString();
        String contactUse = etContactUser.getText().toString();
        if (TextUtils.isEmpty(cargoKind)) {
            showToast("请输入货物种类");
            return;
        }
        if (TextUtils.isEmpty(cargoWeight)) {
            showToast("请输入货物重量");
            return;
        }
        if (TextUtils.isEmpty(price)) {
            showToast("请输入单价");
            return;
        }
        if (TextUtils.isEmpty(startAddress)) {
            showToast("请输入装载起始地");
            return;
        }
        if (TextUtils.isEmpty(destination)) {
            showToast("请输入装载目的地");
            return;
        }
        userRole = FastData.getUserRole();
        if (userRole == WtConstant.USER_ROLE_CARGO) {
            if (TextUtils.isEmpty(phone)) {
                showToast("请输入联系人手机号码");
                return;
            }
            if (TextUtils.isEmpty(contactUse)) {
                showToast("请输入联系人姓名");
                return;
            }
        } else {
            if (TextUtils.isEmpty(startDate)) {
                showToast("起运时间");
                return;
            }
            if (TextUtils.isEmpty(arriveTime)) {
                showToast("运达时间");
                return;
            }
        }

        showLoading();

        if (cargoOrderObj == null) {

            if (userRole == WtConstant.USER_ROLE_BOAT) {
                Disposable subscribe1 = apiStores.boatAddOrder(FastData.getUserId(),
                        "",
                        cargoKind,
                        etContactUser.getText().toString(),
                        phone,
                        startDate,
                        arriveTime,
                        "",
                        "",
                        startAddress,
                        destination,
                        cargoWeight,
                        "",//结算时间
                        price,
                        "",//结算金额
                        extraInfo,
                        0,
                        FastData.getUserId())
                        .compose(SchedulersCompat.applyIoSchedulers())
                        .subscribe(netResponseConsumer);
                addSubscription(subscribe1);
                return;
            }

            Disposable subscribe = apiStores.cargoAdd("", cargoKind, etContactUser.getText().toString(), startAddress, destination, cargoWeight, price, 0, extraInfo, FastData.getUserId())
                    .compose(SchedulersCompat.applyIoSchedulers())
                    .doAfterTerminate(this::hideLoading)
                    .subscribe(netResponseConsumer, Timber::e);
            addSubscription(subscribe);
        } else {
            if (userRole == WtConstant.USER_ROLE_BOAT) {
                apiStores.boatUpdateOrder(cargoOrderObj.getId(),
                        "",
                        cargoKind,
                        etContactUser.getText().toString(),
                        phone,
                        startDate,
                        arriveTime,
                        "",
                        "",
                        startAddress,
                        destination,
                        cargoWeight,
                        "",//结算时间
                        price,
                        "",//结算金额
                        extraInfo)
                        .compose(SchedulersCompat.applyIoSchedulers())
                        .subscribe(netResponseConsumer);
                return;
            }
            Disposable subscribe = apiStores.cargoUpdate(cargoOrderObj.getId(), "", cargoKind, etContactUser.getText().toString(), startAddress, destination, cargoWeight, price, 0, extraInfo, FastData.getUserId())
                    .compose(SchedulersCompat.applyIoSchedulers())
                    .doAfterTerminate(this::hideLoading)
                    .subscribe(netResponseConsumer, Timber::e);
            addSubscription(subscribe);
        }
    }

    Consumer<NetResponse> netResponseConsumer = netResponse -> {
        if (netResponse.isResult()) {
            showToast(netResponse.getMessage());
            finish();
        }
    };
}
