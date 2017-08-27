package com.watertransport.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.entity.CargoOrderObj;
import com.watertransport.support.WtConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangsheng on 2017/8/6.
 */

public class CargoHostOrderAdapter extends RecyclerView.Adapter {


    private final int pageState;
    private List<CargoOrderObj> listData = new ArrayList<>(5);

    public CargoHostOrderAdapter(int pageState) {
        this.pageState = pageState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == WtConstant.PAGE_STATE_NO_PUBLISH) {
            return new CargoHostViewHolder(View.inflate(parent.getContext(), R.layout.item_cargo_order, null));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CargoOrderObj cargoOrderObj = listData.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case WtConstant.PAGE_STATE_NO_PUBLISH:
                CargoHostViewHolder cargoHostViewHolder = (CargoHostViewHolder) holder;
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
                break;
        }


    }

    class CargoHostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_route)
        TextView tvRoute;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_cargo_weight)
        TextView tvCargoWeight;
        @BindView(R.id.tv_cargo_price)
        TextView tvCargoPrice;

        public CargoHostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
