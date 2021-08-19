package com.myaccounts.vechicleserviceapp.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class WheelServiceAdapter extends RecyclerView.Adapter<WheelServiceAdapter.ItemViewHolder> {
    private Context context;

    public WheelServiceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public WheelServiceAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(WheelServiceAdapter.ItemViewHolder itemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
