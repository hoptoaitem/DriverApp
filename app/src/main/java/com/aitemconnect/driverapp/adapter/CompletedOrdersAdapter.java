package com.aitemconnect.driverapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.aitemconnect.driverapp.R;
import com.aitemconnect.driverapp.pojo.order.OrderPojo;

public class CompletedOrdersAdapter extends RecyclerView.Adapter<CompletedOrdersAdapter.CompletedOrdersViewHolder> {

    ArrayList<OrderPojo> arrayList;
    Context mContext;

    public CompletedOrdersAdapter(ArrayList<OrderPojo> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CompletedOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View vieww = layoutInflater.inflate(R.layout.item_recent_order, parent, false);
        return new CompletedOrdersViewHolder(vieww);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedOrdersViewHolder holder, int position) {
        String createdAt = arrayList.get(position).getCreatedAt();
        String id = arrayList.get(position).getId();

        holder.tvOrderId.setText(id);
        if (createdAt == null || createdAt.isEmpty() || createdAt.equalsIgnoreCase("")) {
            holder.tvDate.setText("-");
        } else {
            holder.tvDate.setText(createdAt);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CompletedOrdersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvOrderId)
        TextView tvOrderId;

        @BindView(R.id.tvDate)
        TextView tvDate;

        public CompletedOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
