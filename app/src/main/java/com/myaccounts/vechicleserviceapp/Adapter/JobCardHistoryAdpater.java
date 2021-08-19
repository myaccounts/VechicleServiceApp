package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class JobCardHistoryAdpater extends RecyclerView.Adapter<JobCardHistoryAdpater.Itemholder> {
    private Context context;
    private ArrayList<JobCardHistory> jobCardHistoryArrayList;
    public JobCardHistoryAdpater(Context context, int jobcard_history_row_item, ArrayList<JobCardHistory> jobCardHistoryArrayList) {
        this.context=context;
        this.jobCardHistoryArrayList=jobCardHistoryArrayList;

    }

    public void setFilter(ArrayList<JobCardHistory> countryLists) {

    }


    @NonNull
    @Override
    public Itemholder onCreateViewHolder(ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.jobcard_history_row_item,parent,false);
        Itemholder itemViewHolder=new Itemholder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(JobCardHistoryAdpater.Itemholder holder, int position) {
        JobCardHistory jobCardHistory=jobCardHistoryArrayList.get(position);
        jobCardHistory.setSno(position + 1);
        holder.IdSNoTv.setText(String.valueOf(jobCardHistory.getSno()));
        holder.IdJobCardNoTv.setText(String.valueOf(jobCardHistory.getmJobCardNo()));
        holder.IdDateTv.setText(String.valueOf(jobCardHistory.getmDate()));
        holder.IdVehicleNoTv.setText(String.valueOf(jobCardHistory.getmVehicleNo()));
        holder.IdTimeInTv.setText(String.valueOf(jobCardHistory.getmTimeIn()));
        holder.IdTimeOutTv.setText(String.valueOf(jobCardHistory.getmTineOut()));
        holder.IdSparePartsTv.setText(String.valueOf(jobCardHistory.getMspareParts()));
        holder.IdServChargeTv.setText(String.valueOf(jobCardHistory.getMserviceCharge()));
        holder.IdStatusTv.setText(String.valueOf(jobCardHistory.getStatus()));
    }

    @Override
    public int getItemCount() {
        return jobCardHistoryArrayList.size();
    }

    public class Itemholder extends RecyclerView.ViewHolder {
        private TextView IdSNoTv,IdJobCardNoTv,IdDateTv,IdVehicleNoTv,IdTimeInTv,IdTimeOutTv,IdSparePartsTv,IdServChargeTv,IdStatusTv;
        public Itemholder(View itemView) {
            super(itemView);
            IdSNoTv=(TextView)itemView.findViewById(R.id.IdSNoTv);
            IdJobCardNoTv=(TextView)itemView.findViewById(R.id.IdJobCardNoTv);
            IdDateTv=(TextView)itemView.findViewById(R.id.IdDateTv);
            IdVehicleNoTv=(TextView)itemView.findViewById(R.id.IdVehicleNoTv);
            IdTimeInTv=(TextView)itemView.findViewById(R.id.IdTimeInTv);
            IdTimeOutTv=(TextView)itemView.findViewById(R.id.IdTimeOutTv);
            IdSparePartsTv=(TextView)itemView.findViewById(R.id.IdSparePartsTv);
            IdServChargeTv=(TextView)itemView.findViewById(R.id.IdServChargeTv);
            IdStatusTv=(TextView)itemView.findViewById(R.id.IdStatusTv);
            //IdVehicleNoTv.setVisibility(View.GONE);
        }
    }
}
