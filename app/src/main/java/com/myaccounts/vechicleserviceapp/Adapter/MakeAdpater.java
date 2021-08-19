package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.myaccounts.vechicleserviceapp.Pojo.MakeDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class MakeAdpater extends ArrayAdapter<MakeDetails> {

    private ArrayList<MakeDetails> makeDetailsArrayList;
    private ArrayList<MakeDetails> arraylist;
    Context context;

    public MakeAdpater(Context context, int textViewResourceId, ArrayList<MakeDetails> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.makeDetailsArrayList = new ArrayList<MakeDetails>();
        this.makeDetailsArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<MakeDetails>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView Id_MakeTv,Id_MakeIDTv;
    }

    @Override
    public int getCount() {
        return makeDetailsArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            MakeAdpater.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.make_row_item, parent, false);
                holder.Id_MakeTv = (TextView) convertView.findViewById(R.id.Id_MakeTv);
                holder.Id_MakeIDTv = (TextView) convertView.findViewById(R.id.Id_MakeIDTv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            MakeDetails supplierData = makeDetailsArrayList.get(position);

            holder.Id_MakeTv.setText(supplierData.getMakeName());
            holder.Id_MakeIDTv.setText(supplierData.getMakeId());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<MakeDetails> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            makeDetailsArrayList.clear();
            if (charText.length() == 0) {
                makeDetailsArrayList.addAll(arraylist);
            } else {
                for (MakeDetails supplierData : arraylist) {
                    if (supplierData.getMakeName().toLowerCase(Locale.getDefault()).contains(charText)){
                        makeDetailsArrayList.add(supplierData);
                    }
                }
            }
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return makeDetailsArrayList;
    }
}
