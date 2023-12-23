package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.SparePartReports;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class SparePartConsumptionAdpater extends RecyclerView.Adapter<SparePartConsumptionAdpater.Itemholder> {
    private Context context;
    private ArrayList<SparePartReports> sparePartReportsArrayList;
    public SparePartConsumptionAdpater(Context context, int jobcard_history_row_item, ArrayList<SparePartReports> sparePartReportsArrayList) {
       this.context=context;
       this.sparePartReportsArrayList=sparePartReportsArrayList;
    }

    public void setFilter(ArrayList<SparePartReports> countryLists) {

    }


    @NonNull
    @Override
    public SparePartConsumptionAdpater.Itemholder onCreateViewHolder(ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.sp_consumption_row_item,parent,false);
        SparePartConsumptionAdpater.Itemholder itemViewHolder=new SparePartConsumptionAdpater.Itemholder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(Itemholder itemholder, int position) {
        SparePartReports sparePartReports=sparePartReportsArrayList.get(position);
        sparePartReports.setRows(position+1);
        itemholder.IdSnoTv.setText(String.valueOf(sparePartReports.getRows()));
        itemholder.IdTransDatetv.setText(String.valueOf(sparePartReports.getTransDate()));
        itemholder.IdTransTypetv.setText(String.valueOf(sparePartReports.getTransType()));
        itemholder.IdItemDesctv.setText(String.valueOf(sparePartReports.getItemDesc()));
        itemholder.IdUomtv.setText(String.valueOf(sparePartReports.getUOM()));
        itemholder.IdOpenQtytv.setText(String.valueOf(sparePartReports.getOpenQty()));
        itemholder.IdReceivedtv.setText(String.valueOf(sparePartReports.getReceivedQty()));
        itemholder.IdIssuedtv.setText(String.valueOf(sparePartReports.getIssuedQty()));
        itemholder.IdBalQtytv.setText(String.valueOf(sparePartReports.getBalanceQty()));
    }

    @Override
    public int getItemCount() {
        return sparePartReportsArrayList.size();
    }

    public class Itemholder extends RecyclerView.ViewHolder {
        private TextView IdSnoTv,IdTransDatetv,IdTransTypetv,IdItemDesctv,IdUomtv,IdOpenQtytv,IdReceivedtv,IdIssuedtv,IdBalQtytv;
        public Itemholder(View itemView) {
            super(itemView);
            IdSnoTv=(TextView)itemView.findViewById(R.id.IdSnoTv);
            IdTransDatetv=(TextView)itemView.findViewById(R.id.IdTransDatetv);
            IdTransTypetv=(TextView)itemView.findViewById(R.id.IdTransTypetv);
            IdItemDesctv=(TextView)itemView.findViewById(R.id.IdItemDesctv);
            IdUomtv=(TextView)itemView.findViewById(R.id.IdUomtv);
            IdOpenQtytv=(TextView)itemView.findViewById(R.id.IdOpenQtytv);
            IdReceivedtv=(TextView)itemView.findViewById(R.id.IdReceivedtv);
            IdIssuedtv=(TextView)itemView.findViewById(R.id.IdIssuedtv);
            IdBalQtytv=(TextView)itemView.findViewById(R.id.IdBalQtytv);
        }
    }

}
