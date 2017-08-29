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
import cn.timeface.timekit.support.SchedulersCompat;
import io.reactivex.disposables.Disposable;
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
        }
        etCargoKind.setText(cargoOrderObj.getCargoName());
        etCargoWeight.setText(cargoOrderObj.getTonnage());
        etCargoPrice.setText(cargoOrderObj.getTonnageCost());
        etCargoStartAddress.setText(cargoOrderObj.getLoadTerminal());
        etCargoDestination.setText(cargoOrderObj.getUnloadTerminal());
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
            Disposable subscribe = apiStores.cargoAdd("", cargoKind, etContactUser.getText().toString(), startAddress, destination, cargoWeight, price, 0, extraInfo, FastData.getUserId())
                    .compose(SchedulersCompat.applyIoSchedulers())
                    .doAfterTerminate(this::hideLoading)
                    .subscribe(netResponse -> {
                        if (netResponse.isResult()) {
                            showToast(netResponse.getMessage());
                            EventBus.getDefault().post(new UpdateListEvent(-1));
                            finish();
                        }
                    }, Timber::e);
            addSubscription(subscribe);
        } else {
            Disposable subscribe = apiStores.cargoUpdate(cargoOrderObj.getId(), "", cargoKind, etContactUser.getText().toString(), startAddress, destination, cargoWeight, price, 0, extraInfo, FastData.getUserId())
                    .compose(SchedulersCompat.applyIoSchedulers())
                    .doAfterTerminate(this::hideLoading)
                    .subscribe(netResponse -> {
                        if (netResponse.isResult()) {
                            showToast(netResponse.getMessage());
                            EventBus.getDefault().post(new UpdateListEvent(-1));
                            finish();
                        }
                    }, Timber::e);
            addSubscription(subscribe);
        }
    }

}
