package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.EstimationHistory;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class EstimationAdapter extends RecyclerView.Adapter<EstimationAdapter.Itemholder> {
    private ArrayList<EstimationHistory> estimationHistoryArrayList;
    private Context context;
    public EstimationAdapter(Context context, int estimation_row_item, ArrayList<EstimationHistory> estimationHistoryArrayList) {
        this.context=context;
        this.estimationHistoryArrayList=estimationHistoryArrayList;


    }

    public void setFilter(ArrayList<EstimationHistory> countryLists) {

    }


    @NonNull
    @Override
    public Itemholder onCreateViewHolder(ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.estimation_row_item,parent,false);
        Itemholder itemViewHolder=new Itemholder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(Itemholder holder, int position) {
        EstimationHistory estimationHistory=estimationHistoryArrayList.get(position);
        estimationHistory.setRows(position+1);
        holder.IdSnoTv.setText(String.valueOf(estimationHistory.getRows()));
        holder.IdTranNoTv.setText(String.valueOf(estimationHistory.getTranNo()));
//        holder.IdJobCardNoTv.setText(String.valueOf(estimationHistory.getJobCardNo()));
        holder.IdCustomerNameTv.setText(String.valueOf(estimationHistory.getCustomerName()));
        holder.IdMobileNoTv.setText(String.valueOf(estimationHistory.getMobile()));
        holder.SparePartTv.setText(String.valueOf(estimationHistory.getSpareParts()));
        holder.IdServicesTv.setText(String.valueOf(estimationHistory.getServices()));
        holder.IdAmountTv.setText(String.valueOf(estimationHistory.getAmount()));
    }

    @Override
    public int getItemCount() {
        return estimationHistoryArrayList.size();
    }

    public class Itemholder extends RecyclerView.ViewHolder {
        private TextView IdSnoTv,IdTranNoTv,IdJobCardNoTv,IdCustomerNameTv,IdMobileNoTv,SparePartTv,IdServicesTv,IdAmountTv;
        public Itemholder(View itemView)
        {
            super(itemView);
            IdSnoTv=(TextView)itemView.findViewById(R.id.IdSnoTv);
            IdTranNoTv=(TextView)itemView.findViewById(R.id.IdTranNoTv);
            IdJobCardNoTv=(TextView)itemView.findViewById(R.id.IdJobCardNoTv);
            IdCustomerNameTv=(TextView)itemView.findViewById(R.id.IdCustomerNameTv);
            IdMobileNoTv=(TextView)itemView.findViewById(R.id.IdMobileNoTv);
            SparePartTv=(TextView)itemView.findViewById(R.id.SparePartTv);
            IdServicesTv=(TextView)itemView.findViewById(R.id.IdServicesTv);
            IdAmountTv=(TextView)itemView.findViewById(R.id.IdAmountTv);
        }
    }
}
