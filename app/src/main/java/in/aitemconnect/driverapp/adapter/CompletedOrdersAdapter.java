package in.aitemconnect.driverapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.aitemconnect.driverapp.R;

public class CompletedOrdersAdapter extends RecyclerView.Adapter<CompletedOrdersAdapter.CompletedOrdersViewHolder> {

    ArrayList<String> arrayList;
    Context mContext;

    public CompletedOrdersAdapter(ArrayList<String> arrayList, Context mContext) {
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

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CompletedOrdersViewHolder extends RecyclerView.ViewHolder {
        public CompletedOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
