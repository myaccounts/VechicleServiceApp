package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.SpareParts;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class NewServiceMasterAdapter extends ArrayAdapter<ServiceMaster> {

    private ArrayList<ServiceMaster> supplierDataArrayList;
    private ArrayList<ServiceMaster> arraylist;
    Context context;

    public NewServiceMasterAdapter(Context context, int textViewResourceId, ArrayList<ServiceMaster> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.supplierDataArrayList = new ArrayList<ServiceMaster>();
        this.supplierDataArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<ServiceMaster>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView IdServiceNameTv, IdSubServiceNameTv, IdServiceChargeTv, IdSprMRPTv;
    }

    @Override
    public int getCount() {
        return supplierDataArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            NewServiceMasterAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new NewServiceMasterAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.new_service_search_row_item, parent, false);
                holder.IdServiceNameTv = (TextView) convertView.findViewById(R.id.IdServiceNameTv);
                holder.IdSubServiceNameTv = (TextView) convertView.findViewById(R.id.IdSubServiceNameTv);
                holder.IdServiceChargeTv = (TextView) convertView.findViewById(R.id.IdServiceChargeTv);
//                holder.IdSprMRPTv = (TextView) convertView.findViewById(R.id.IdSprMRPTv);
                convertView.setTag(holder);
            } else {
                holder = (NewServiceMasterAdapter.ViewHolder) convertView.getTag();
            }
            ServiceMaster spareParts = supplierDataArrayList.get(position);

            holder.IdServiceNameTv.setText(spareParts.getServiceName());
            holder.IdSubServiceNameTv.setText(spareParts.getSubServiceName());
            holder.IdServiceChargeTv.setText(spareParts.getRate());
//            holder.IdSprMRPTv.setText(spareParts.getRate());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<ServiceMaster> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            supplierDataArrayList.clear();
            if (charText.length() == 0) {
                supplierDataArrayList.addAll(arraylist);
            } else {
                for (ServiceMaster spareParts : arraylist) {
                    if (spareParts.getServiceId().toLowerCase(Locale.getDefault()).contains(charText) || spareParts.getServiceName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        supplierDataArrayList.add(spareParts);
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
