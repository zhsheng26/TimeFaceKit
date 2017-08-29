package com.watertransport.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.entity.MsgObj;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangsheng on 2017/8/29.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {
    private List<MsgObj> msgObjs;


    public MsgAdapter(List<MsgObj> msgObjs) {
        this.msgObjs = msgObjs;
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(View.inflate(parent.getContext(), R.layout.item_msg, null));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        MsgObj msgObj = msgObjs.get(position);
        holder.tvDate.setText(msgObj.getCreateDate());
        holder.tvMsgContent.setText(msgObj.getAnnounceInfo().getAnnounceContext());
    }

    @Override
    public int getItemCount() {
        return msgObjs.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_msg_content)
        TextView tvMsgContent;

        public MsgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
