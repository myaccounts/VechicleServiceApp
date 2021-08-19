package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleHistory;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class VehicleNoAdpater extends ArrayAdapter<VehicleDetails> {

    private ArrayList<VehicleDetails> supplierDataArrayList;
    private ArrayList<VehicleDetails> arraylist;
    Context context;

    public VehicleNoAdpater(Context context, int textViewResourceId, ArrayList<VehicleDetails> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.supplierDataArrayList = new ArrayList<VehicleDetails>();
        this.supplierDataArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<VehicleDetails>();
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
            VehicleNoAdpater.ViewHolder holder = null;
            if (convertView == null) {
                holder = new VehicleNoAdpater.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.vehicleno_row_item, parent, false);

              /*  holder.IdCustomerTv = (TextView) convertView.findViewById(R.id.IdCustomerTv);
                holder.IdMobileTv = (TextView) convertView.findViewById(R.id.IdMobileTv);*/
                holder.IdVehicleNoTv = (TextView) convertView.findViewById(R.id.IdVehicleNoTv);
                convertView.setTag(holder);
            } else {
                holder = (VehicleNoAdpater.ViewHolder) convertView.getTag();
            }
            VehicleDetails supplierData = supplierDataArrayList.get(position);


          /*  holder.IdCustomerTv.setText(supplierData.getCustomerName());
            holder.IdMobileTv.setText(supplierData.getMobileNo());*/
            holder.IdVehicleNoTv.setText(supplierData.getVehicleNo());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<VehicleDetails> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            supplierDataArrayList.clear();
            if (charText.length() == 0) {
                supplierDataArrayList.addAll(arraylist);
            } else {
                for (VehicleDetails supplierData : arraylist) {
                    if (supplierData.getVehicleNo().toLowerCase(Locale.getDefault()).contains(charText)) {
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
