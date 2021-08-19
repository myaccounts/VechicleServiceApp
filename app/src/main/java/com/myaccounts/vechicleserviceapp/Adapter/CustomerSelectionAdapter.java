package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Activity.CustomerSelectionActivity;
import com.myaccounts.vechicleserviceapp.Pojo.CustomerList;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;
import java.util.ArrayList;
import java.util.Locale;

public class CustomerSelectionAdapter extends ArrayAdapter<CustomerList> {

    private ArrayList<CustomerList> userListArrayList;
    private ArrayList<CustomerList> arraylist;
    Context context;

    public CustomerSelectionAdapter(Context context, int customer_row_item, ArrayList<CustomerList> userListArrayList) {
        super(context, customer_row_item, userListArrayList);
        this.userListArrayList = new ArrayList<CustomerList>();
        this.userListArrayList.addAll(userListArrayList);
        arraylist = new ArrayList<CustomerList>();
        arraylist.addAll(userListArrayList);

    }

    private class ViewHolder {
        TextView Id_CustomerIdTv, IdCustomerNameTv,IdCustomerMobileTv;
    }

    @Override
    public int getCount() {
        return userListArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            CustomerSelectionAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.customer_row_item, parent, false);
                holder.Id_CustomerIdTv = (TextView) convertView.findViewById(R.id.Id_CustomerIdTv);
                holder.IdCustomerNameTv = (TextView) convertView.findViewById(R.id.IdCustomerNameTv);
                holder.IdCustomerMobileTv = (TextView) convertView.findViewById(R.id.IdCustomerMobileTv);
                convertView.setTag(holder);
            } else {
                holder = (CustomerSelectionAdapter.ViewHolder) convertView.getTag();
            }
            CustomerList spareParts = userListArrayList.get(position);

            holder.Id_CustomerIdTv.setText(spareParts.getCustomerId());
            holder.IdCustomerNameTv.setText(spareParts.getCustomerName());
            holder.IdCustomerMobileTv.setText(spareParts.getMobileNo());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<CustomerList> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            userListArrayList.clear();
            if (charText.length() == 0) {
                userListArrayList.addAll(arraylist);
            } else {
                for (CustomerList spareParts : arraylist) {
                    if (spareParts.getCustomerId().toLowerCase(Locale.getDefault()).contains(charText) || spareParts.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText)|| spareParts.getMobileNo().toLowerCase(Locale.getDefault()).contains(charText)) {
                        userListArrayList.add(spareParts);
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
