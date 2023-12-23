package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class JonCardNoAdpater extends ArrayAdapter<JobCardDetails> {

    private ArrayList<JobCardDetails> supplierDataArrayList;
    private ArrayList<JobCardDetails> arraylist;
    Context context;

    public JonCardNoAdpater(Context context, int textViewResourceId, ArrayList<JobCardDetails> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.supplierDataArrayList = new ArrayList<JobCardDetails>();
        this.supplierDataArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<JobCardDetails>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView Id_jobcardNo,IdCustomerTv,IdMobileTv,IdVehicleNoTv;
    }

    @Override
    public int getCount() {
        return supplierDataArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            JonCardNoAdpater.ViewHolder holder = null;
            if (convertView == null) {
                holder = new JonCardNoAdpater.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.jobcard_row_item, parent, false);
                holder.Id_jobcardNo = (TextView) convertView.findViewById(R.id.Id_jobcardNo);
                holder.IdCustomerTv = (TextView) convertView.findViewById(R.id.IdCustomerTv);
                holder.IdMobileTv = (TextView) convertView.findViewById(R.id.IdMobileTv);
                holder.IdVehicleNoTv = (TextView) convertView.findViewById(R.id.IdVehicleNoTv);
                convertView.setTag(holder);
            } else {
                holder = (JonCardNoAdpater.ViewHolder) convertView.getTag();
            }
            JobCardDetails supplierData = supplierDataArrayList.get(position);
            holder.Id_jobcardNo.setText(supplierData.getModel());
            holder.IdCustomerTv.setText(supplierData.getCustomerName());
            holder.IdMobileTv.setText(supplierData.getMobileNo());
            holder.IdVehicleNoTv.setText(supplierData.getVehicleNo());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<JobCardDetails> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            supplierDataArrayList.clear();
            if (charText.length() == 0) {
                supplierDataArrayList.addAll(arraylist);
            } else {
                for (JobCardDetails supplierData : arraylist) {
                    if (supplierData.getJobCardNo().toLowerCase(Locale.getDefault()).contains(charText)||supplierData.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText)||supplierData.getMobileNo().toLowerCase(Locale.getDefault()).contains(charText)||supplierData.getVehicleNo().toLowerCase(Locale.getDefault()).contains(charText)) {
                        supplierDataArrayList.add(supplierData);
                    }
                }
            }
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierDataArrayList;
    }
}
