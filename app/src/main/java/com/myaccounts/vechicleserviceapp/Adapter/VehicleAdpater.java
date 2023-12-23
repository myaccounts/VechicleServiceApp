package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.VehicleTypes;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class VehicleAdpater extends ArrayAdapter<VehicleTypes> {

    private ArrayList<VehicleTypes> VehicleTypesArrayList;
    private ArrayList<VehicleTypes> arraylist;
    Context context;

    public VehicleAdpater(Context context, int textViewResourceId, ArrayList<VehicleTypes> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.VehicleTypesArrayList = new ArrayList<VehicleTypes>();
        this.VehicleTypesArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<VehicleTypes>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView Id_MakeTv,Id_MakeIDTv;
    }

    @Override
    public int getCount() {
        return VehicleTypesArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            VehicleAdpater.ViewHolder holder = null;
            if (convertView == null) {
                holder = new VehicleAdpater.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.make_row_item, parent, false);
                holder.Id_MakeTv = (TextView) convertView.findViewById(R.id.Id_MakeTv);
                holder.Id_MakeIDTv = (TextView) convertView.findViewById(R.id.Id_MakeIDTv);

                convertView.setTag(holder);
            } else {
                holder = (VehicleAdpater.ViewHolder) convertView.getTag();
            }
            VehicleTypes supplierData = VehicleTypesArrayList.get(position);

            holder.Id_MakeTv.setText(supplierData.getName());
            holder.Id_MakeIDTv.setText(supplierData.getId());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<VehicleTypes> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            VehicleTypesArrayList.clear();
            if (charText.length() == 0) {
                VehicleTypesArrayList.addAll(arraylist);
            } else {
                for (VehicleTypes supplierData : arraylist) {
                    if (supplierData.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                        VehicleTypesArrayList.add(supplierData);
                    }
                }
            }
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return VehicleTypesArrayList;
    }
}
