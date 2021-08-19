package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.SpareParts;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class SparePartsAdapter extends ArrayAdapter<SpareParts> {

    private ArrayList<SpareParts> supplierDataArrayList;
    private ArrayList<SpareParts> arraylist;
    Context context;

    public SparePartsAdapter(Context context, int textViewResourceId, ArrayList<SpareParts> supplierDataArrayList) {
        super(context, textViewResourceId, supplierDataArrayList);
        this.supplierDataArrayList = new ArrayList<SpareParts>();
        this.supplierDataArrayList.addAll(supplierDataArrayList);
        arraylist = new ArrayList<SpareParts>();
        arraylist.addAll(supplierDataArrayList);
    }

    private class ViewHolder {
        TextView Id_SparePartId,IdSparePartNameTv,IdSprCurQtyTv,IdSprMRPTv;
    }

    @Override
    public int getCount() {
        return supplierDataArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            SparePartsAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new SparePartsAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.spareparts_row_item, parent, false);
                holder.Id_SparePartId = (TextView) convertView.findViewById(R.id.Id_SparePartId);
                holder.IdSparePartNameTv = (TextView) convertView.findViewById(R.id.IdSparePartNameTv);
                holder.IdSprCurQtyTv = (TextView) convertView.findViewById(R.id.IdSprCurQtyTv);
                holder.IdSprMRPTv = (TextView) convertView.findViewById(R.id.IdSprMRPTv);
                convertView.setTag(holder);
            } else {
                holder = (SparePartsAdapter.ViewHolder) convertView.getTag();
            }
            SpareParts spareParts = supplierDataArrayList.get(position);

            holder.Id_SparePartId.setText(spareParts.getId());
            holder.IdSparePartNameTv.setText(spareParts.getName());
            holder.IdSprCurQtyTv.setText(spareParts.getBalQty());
            holder.IdSprMRPTv.setText(spareParts.getRate());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public ArrayList<SpareParts> filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            supplierDataArrayList.clear();
            if (charText.length() == 0) {
                supplierDataArrayList.addAll(arraylist);
            } else {
                for (SpareParts spareParts : arraylist) {
                    if (spareParts.getId().toLowerCase(Locale.getDefault()).contains(charText)||spareParts.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
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
