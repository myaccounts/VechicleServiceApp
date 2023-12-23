package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.SparePartEst;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class SparePartEstAdapter extends RecyclerView.Adapter<SparePartEstAdapter.Itemholder> {
    private Context context;
    private ArrayList<SparePartEst> jobCardHistoryArrayList;
    public SparePartEstAdapter(Context context, int sparepartest_history_row_item, ArrayList<SparePartEst> jobCardHistoryArrayList) {
        this.context=context;
        this.jobCardHistoryArrayList=jobCardHistoryArrayList;

    }

    public void setFilter(ArrayList<SparePartEst> countryLists) {

    }


    @NonNull
    @Override
    public SparePartEstAdapter.Itemholder onCreateViewHolder(ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.sparepartest_history_row_item,parent,false);
        SparePartEstAdapter.Itemholder itemViewHolder=new SparePartEstAdapter.Itemholder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(SparePartEstAdapter.Itemholder holder, int position) {
        SparePartEst jobCardHistory=jobCardHistoryArrayList.get(position);
        jobCardHistory.setRows(position + 1);
        holder.IdSNoTv.setText(String.valueOf(jobCardHistory.getRows()));
        holder.IdJobCardNoTv.setText(String.valueOf(jobCardHistory.getJobCard()));
        holder.IdTranNoTv.setText(String.valueOf(jobCardHistory.getTranNo()));
        holder.IdCustomerNameTv.setText(String.valueOf(jobCardHistory.getCustomer()));
        holder.IdMobileNoTv.setText(String.valueOf(jobCardHistory.getMobileNo()));
        holder.IdSparePartCountTv.setText(String.valueOf(jobCardHistory.getSparePartCount()));
        holder.IdAmountTv.setText(String.valueOf(jobCardHistory.getAmount()));
        holder.IdServiceCountTv.setText(String.valueOf(jobCardHistory.getServiceCount()));
    }

    @Override
    public int getItemCount() {
        return jobCardHistoryArrayList.size();
    }

    public class Itemholder extends RecyclerView.ViewHolder {
        private TextView IdSNoTv,IdTranNoTv,IdJobCardNoTv,IdCustomerNameTv,IdMobileNoTv,IdSparePartCountTv,IdAmountTv,IdServiceCountTv;
        public Itemholder(View itemView) {
            super(itemView);
            IdSNoTv=(TextView)itemView.findViewById(R.id.IdSNoTv);
            IdTranNoTv=(TextView)itemView.findViewById(R.id.IdTranNoTv);
            IdJobCardNoTv=(TextView)itemView.findViewById(R.id.IdJobCardNoTv);
            IdCustomerNameTv=(TextView)itemView.findViewById(R.id.IdCustomerNameTv);
            IdMobileNoTv=(TextView)itemView.findViewById(R.id.IdMobileNoTv);
            IdSparePartCountTv=(TextView)itemView.findViewById(R.id.IdSparePartCountTv);
            IdServiceCountTv=(TextView)itemView.findViewById(R.id.IdServiceCountTv);
            IdAmountTv=(TextView)itemView.findViewById(R.id.IdAmountTv);

        }
    }
}
