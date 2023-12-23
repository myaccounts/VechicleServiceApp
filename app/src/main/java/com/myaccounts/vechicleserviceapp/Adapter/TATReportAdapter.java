package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;

import java.util.ArrayList;

public class TATReportAdapter extends RecyclerView.Adapter<TATReportAdapter.ItemHolder> {
    public TATReportAdapter(Context activity, int jobcard_history_row_item, ArrayList<JobCardHistory> jobCardHistoryArrayList) {
    }

    @NonNull
    @Override
    public TATReportAdapter.ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TATReportAdapter.ItemHolder itemHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setFilter(ArrayList<JobCardHistory> countryLists) {

    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
