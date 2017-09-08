package com.watertransport.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.entity.BoatHostOrderObj;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.support.listener.OnItemClickListener;

/**
 * Created by zhangsheng on 2017/9/3.
 */

public class BoatHostOrderAdapter extends RecyclerView.Adapter<BoatHostOrderAdapter.BoatViewHolder> {

    private List<BoatHostOrderObj> hostOrderObj = new ArrayList<>();
    private OnItemClickListener<View, BoatHostOrderObj> onItemClickListener;

    public void setData(List<BoatHostOrderObj> hostOrderObjs) {
        hostOrderObj.clear();
        hostOrderObj.addAll(hostOrderObjs);
        notifyDataSetChanged();
    }

    public void addData(List<BoatHostOrderObj> boatHostOrderObjs) {
        hostOrderObj.addAll(boatHostOrderObjs);
        notifyDataSetChanged();
    }

    public void remove(BoatHostOrderObj boatHostOrderObj) {
        if (hostOrderObj.contains(boatHostOrderObj)) {
            int indexOf = hostOrderObj.indexOf(boatHostOrderObj);
            hostOrderObj.remove(indexOf);
            notifyItemRemoved(indexOf);
        }
    }

    @Override
    public BoatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoatViewHolder(View.inflate(parent.getContext(), R.layout.item_boat_host_order, null));
    }

    @Override
    public void onBindViewHolder(BoatViewHolder holder, int position) {
        BoatHostOrderObj boatHostOrderObj = hostOrderObj.get(position);
        holder.setData(boatHostOrderObj);
        holder.tvRoute.setText(boatHostOrderObj.getLoadTerminal() + "-" + boatHostOrderObj.getUnloadTerminal());
        holder.tvDate.setText(boatHostOrderObj.getLoadTime());
        holder.tvCargoWeight.setText(String.format("黄沙、%s吨", boatHostOrderObj.getTonnage()));
        String tonnageCost = boatHostOrderObj.getTransportCost();
        String text = tonnageCost + "元/吨";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        int indexOf = text.indexOf(tonnageCost);
        stringBuilder.setSpan(new ForegroundColorSpan(holder.itemView.getContext().getResources().getColor(R.color.text_light)), indexOf, indexOf + tonnageCost.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new RelativeSizeSpan(1.5f), indexOf, indexOf + tonnageCost.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int orderStatue = boatHostOrderObj.getOrderStatue();
        if (orderStatue == 0) {
            holder.rlNoEnd.setVisibility(View.VISIBLE);
            holder.rlHasEnd.setVisibility(View.GONE);
        } else {
            holder.rlHasEnd.setVisibility(View.VISIBLE);
            holder.rlNoEnd.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<View, BoatHostOrderObj> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return hostOrderObj.size();
    }

    class BoatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        TextView btnToClose;
        @BindView(R.id.rl_has_end)
        RelativeLayout rlHasEnd;
        @BindView(R.id.btn_go_edit)
        TextView btnGoEdit;
        @BindView(R.id.btn_edit)
        TextView btnEdit;
        @BindView(R.id.rl_no_end)
        RelativeLayout rlNoEnd;
        BoatHostOrderObj hostOrderObj;

        public BoatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            btnToClose.setOnClickListener(this);
        }

        public void setData(BoatHostOrderObj hostOrderObj) {
            this.hostOrderObj = hostOrderObj;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, hostOrderObj, getAdapterPosition(), null);
            }
        }
    }
}
