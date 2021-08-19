package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.ServiceManList;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class ServiceManAdpater extends ArrayAdapter<ServiceManList> {

    private ArrayList<ServiceManList> userListArrayList;
    private ArrayList<ServiceManList> arraylist;
    Context context;

    public ServiceManAdpater(Context context, int textViewResourceId, ArrayList<ServiceManList> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.userListArrayList = new ArrayList<ServiceManList>();
        this.userListArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<ServiceManList>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView IdUserIdTv, IdUserNameTv;
    }

    @Override
    public int getCount() {
        return userListArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ServiceManAdpater.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ServiceManAdpater.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.serviceman_row_item, parent, false);
                holder.IdUserIdTv = (TextView) convertView.findViewById(R.id.IdUserIdTv);
                holder.IdUserNameTv = (TextView) convertView.findViewById(R.id.IdUserNameTv);

                convertView.setTag(holder);
            } else {
                holder = (ServiceManAdpater.ViewHolder) convertView.getTag();
            }
            ServiceManList supplierData = userListArrayList.get(position);

            holder.IdUserIdTv.setText(supplierData.getId());
            holder.IdUserNameTv.setText(supplierData.getName());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<ServiceManList> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            userListArrayList.clear();
            if (charText.length() == 0) {
                userListArrayList.addAll(arraylist);
            } else {
                for (ServiceManList supplierData : arraylist) {
                    if (supplierData.getName().toLowerCase(Locale.getDefault()).contains(charText) || supplierData.getId().toLowerCase(Locale.getDefault()).contains(charText)) {
                        userListArrayList.add(supplierData);
                    }
                }
            }
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userListArrayList;
    }
}
