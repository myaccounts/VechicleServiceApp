package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceList;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class GeneralServiceAdapter extends ArrayAdapter<ServiceList> {

    private ArrayList<ServiceList> serviceListArrayList;
    private ArrayList<ServiceList> arraylist;
    Context context;

    public GeneralServiceAdapter(Context context, int textViewResourceId, ArrayList<ServiceList> serviceListArrayList) {
        super(context, textViewResourceId, serviceListArrayList);
        this.serviceListArrayList = new ArrayList<ServiceList>();
        this.serviceListArrayList.addAll(serviceListArrayList);
        arraylist = new ArrayList<ServiceList>();
        arraylist.addAll(serviceListArrayList);
    }



    private class ViewHolder {
        TextView mServiceName,mServiceCharage;
    }

    @Override
    public int getCount() {

        return serviceListArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            GeneralServiceAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new GeneralServiceAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.generalservicelist, parent, false);
                holder.mServiceName = (TextView) convertView.findViewById(R.id.IdSerNameTv);
                holder.mServiceCharage = (TextView) convertView.findViewById(R.id.IdSerChargeTv);
                holder.mServiceCharage.setVisibility(View.VISIBLE);

                convertView.setTag(holder);
            } else {
                holder = (GeneralServiceAdapter.ViewHolder) convertView.getTag();
            }
            ServiceList serviceList = serviceListArrayList.get(position);
            holder.mServiceName.setText(String.valueOf(serviceList.getSubServiceName()));
            holder.mServiceName.setTag(String.valueOf(serviceList.getSubServiceId()));
            holder.mServiceCharage.setText(String.valueOf(serviceList.getServiceCharge()));



        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }



    public ArrayList<ServiceList> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            serviceListArrayList.clear();
            if (charText.length() == 0) {
                serviceListArrayList.addAll(arraylist);
            } else {
                for (ServiceList spareParts : arraylist) {
                    if (spareParts.getServiceId().toLowerCase(Locale.getDefault()).contains(charText) || spareParts.getServiceName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        serviceListArrayList.add(spareParts);
                    }
                }
            }
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceListArrayList;
    }
}
