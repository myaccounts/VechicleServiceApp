package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.myaccounts.vechicleserviceapp.Pojo.ModelDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class ModelAdpater extends ArrayAdapter<ModelDetails> {

    private ArrayList<ModelDetails> modelDetailsArrayList;
    private ArrayList<ModelDetails> arraylist;
    Context context;

    public ModelAdpater(Context context, int textViewResourceId, ArrayList<ModelDetails> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.modelDetailsArrayList = new ArrayList<ModelDetails>();
        this.modelDetailsArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<ModelDetails>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView Id_ModelTv,Id_ModelIDTv;
    }

    @Override
    public int getCount() {
        return modelDetailsArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ModelAdpater.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.model_row_item, parent, false);
                holder.Id_ModelTv = (TextView) convertView.findViewById(R.id.Id_ModelTv);
                holder.Id_ModelIDTv = (TextView) convertView.findViewById(R.id.Id_ModelIDTv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ModelDetails supplierData = modelDetailsArrayList.get(position);

            holder.Id_ModelTv.setText(supplierData.getModelName());
            holder.Id_ModelIDTv.setText(supplierData.getModelId());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<ModelDetails> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            modelDetailsArrayList.clear();
            if (charText.length() == 0) {
                modelDetailsArrayList.addAll(arraylist);
            } else {
                for (ModelDetails supplierData : arraylist) {
                    if (supplierData.getModelName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        modelDetailsArrayList.add(supplierData);
                    }
                }
            }
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelDetailsArrayList;
    }
}
