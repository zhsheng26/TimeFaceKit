package com.watertransport.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.entity.CargoOrderObj;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.support.listener.OnItemClickListener;

/**
 * Created by zhangsheng on 2017/8/6.
 */

public class CargoHostOrderAdapter extends RecyclerView.Adapter {


    private int pageState;


    private List<CargoOrderObj> listData = new ArrayList<>(5);
    private OnItemClickListener<View, CargoOrderObj> onItemClickListener;

    public CargoHostOrderAdapter(int pageState) {
        this.pageState = pageState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CargoHostViewHolder(View.inflate(parent.getContext(), R.layout.item_cargo_order, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CargoOrderObj cargoOrderObj = listData.get(position);
        CargoHostViewHolder cargoHostViewHolder = (CargoHostViewHolder) holder;
        cargoHostViewHolder.setData(cargoOrderObj);
        cargoHostViewHolder.tvRoute.setText(cargoOrderObj.getLoadTerminal() + "-" + cargoOrderObj.getUnloadTerminal());
        cargoHostViewHolder.tvDate.setText(cargoOrderObj.getCreateDate());
        cargoHostViewHolder.tvCargoWeight.setText(cargoOrderObj.getCargoName() + "、" + cargoOrderObj.getTonnage());
        String tonnageCost = cargoOrderObj.getTonnageCost();
        String text = tonnageCost + "元/吨";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        int indexOf = text.indexOf(tonnageCost);
        stringBuilder.setSpan(new ForegroundColorSpan(holder.itemView.getContext().getResources().getColor(R.color.text_light)), indexOf, indexOf + tonnageCost.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new RelativeSizeSpan(1.5f), indexOf, indexOf + tonnageCost.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cargoHostViewHolder.tvCargoPrice.setText(stringBuilder);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case WtConstant.PAGE_STATE_PUBLISHING:
                if (FastData.getUserRole() == WtConstant.USER_ROLE_CARGO) {//因为船主也可以查看发布中的，但是不能编辑和关闭
                    cargoHostViewHolder.rlPublishing.setVisibility(View.VISIBLE);
                } else {
                    cargoHostViewHolder.viewSplit.setVisibility(View.GONE);
                }
                break;
            case WtConstant.PAGE_STATE_NO_PUBLISH:
                cargoHostViewHolder.rlNoPublish.setVisibility(View.VISIBLE);
                break;
            case WtConstant.PAGE_STATE_CLOSED:
                cargoHostViewHolder.rlClosed.setVisibility(View.VISIBLE);
                break;
        }


    }

    public void setPageState(int pageState) {
        this.pageState = pageState;
    }

    class CargoHostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_route)
        TextView tvRoute;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_cargo_weight)
        TextView tvCargoWeight;
        @BindView(R.id.tv_cargo_price)
        TextView tvCargoPrice;
        @BindView(R.id.view_split)
        View viewSplit;
        @BindView(R.id.btn_to_close)
        Button btnToClose;
        @BindView(R.id.btn_edit_publishing)
        Button btnEditPublishing;
        @BindView(R.id.rl_publishing)
        RelativeLayout rlPublishing;
        @BindView(R.id.btn_publish)
        Button btnPublish;
        @BindView(R.id.btn_edit)
        Button btnEdit;
        @BindView(R.id.btn_delete1)
        Button btnDelete1;
        @BindView(R.id.btn_delete2)
        Button btnDelete2;
        @BindView(R.id.btn_delete3)
        Button btnDelete3;
        @BindView(R.id.rl_no_publish)
        RelativeLayout rlNoPublish;
        @BindView(R.id.rl_closed)
        RelativeLayout rlClosed;
        private CargoOrderObj cargoOrderObj;

        public CargoHostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnEdit.setOnClickListener(this);
            btnPublish.setOnClickListener(this);
            btnToClose.setOnClickListener(this);
            btnEditPublishing.setOnClickListener(this);
            btnDelete1.setOnClickListener(this);
            btnDelete2.setOnClickListener(this);
            btnDelete3.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setData(CargoOrderObj cargoOrderObj) {
            this.cargoOrderObj = cargoOrderObj;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, cargoOrderObj, getAdapterPosition(), null);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener<View, CargoOrderObj> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return pageState;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setNewData(List<CargoOrderObj> newData) {
        this.listData.clear();
        this.listData.addAll(newData);
        notifyDataSetChanged();
    }

    public void addData(List<CargoOrderObj> orderObjs) {
        this.listData.addAll(orderObjs);
        notifyDataSetChanged();
    }
}
