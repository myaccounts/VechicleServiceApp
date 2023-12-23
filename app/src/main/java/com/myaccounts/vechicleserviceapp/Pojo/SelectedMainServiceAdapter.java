package com.myaccounts.vechicleserviceapp.Pojo;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Adapter.SelectedServiceAdpater;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class SelectedMainServiceAdapter extends RecyclerView.Adapter<SelectedMainServiceAdapter.ItemViewHolder> {
    public static ArrayList<ServiceMaster> serviceMasterArrayList;
    public static ArrayList<ServiceMaster> serviceArrayList;
    private Context context;
    private LayoutInflater inflater;
    private SelectedServiceAdpater.OnItemClickListener mItemClickListener;
    public static SparseBooleanArray mSelectedItemsIds;
    private String serviceListData;

    public SelectedMainServiceAdapter(Context context, int selectedservice_row_item, ArrayList<ServiceMaster> serviceMasterArrayList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.serviceArrayList = serviceMasterArrayList;
        this.serviceMasterArrayList = serviceMasterArrayList;
        //  mSelectedItemsIds = new SparseBooleanArray();
    }

    public SelectedMainServiceAdapter(Context context, String serviceListData) {
        this.context = context;
        this.serviceListData = serviceListData;
    }

    @Override
    public SelectedMainServiceAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = inflater.inflate(R.layout.selectedmainservice_row_item, parent, false);
        return new ItemViewHolder(view1, this);


    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        try {
            final ServiceMaster dashBoard = serviceArrayList.get(position);
            holder.ServiceNameCheckBox.setText(dashBoard.getServiceName());
            holder.ServiceNameCheckBox.setTag(dashBoard.getServiceId());
        /*    if(dashBoard.getActiveStatus().equalsIgnoreCase("1")){
                holder.ServiceNameCheckBox.setChecked(true);
                serviceMasterArrayList.get(position).setSelected(true);
                holder.selectedSericeListTv.setText(dashBoard.getWheeltyreDetails());
            }*/
            if (dashBoard.getSelected() == true) {
                holder.ServiceNameCheckBox.setChecked(true);
                holder.selectedSericeListTv.setText(dashBoard.getWheeltyreDetails());
            } else {
                holder.ServiceNameCheckBox.setChecked(false);
                holder.selectedSericeListTv.setText("");
            }

            if (holder.selectedSericeListTv.getText().toString().equalsIgnoreCase("")) {
                holder.ServiceNameCheckBox.setChecked(false);
            }
            // holder.ServiceNameCheckBox.setTag(position);
           /* holder.ServiceNameCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // checkCheckBox(position, !mSelectedItemsIds.get(position));
                    if (serviceMasterArrayList.get(position).getSelected()) {
                        serviceMasterArrayList.get(position).setSelected(false);
                    } else {
                        serviceMasterArrayList.get(position).setSelected(true);
                    }
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/*public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    public static SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }*/

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }


    public void setOnItemClickListner(final SelectedServiceAdpater.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox ServiceNameCheckBox;
        private TextView selectedSericeListTv;

        public ItemViewHolder(View itemView, SelectedMainServiceAdapter selectedServiceAdpater) {
            super(itemView);
            ServiceNameCheckBox = (CheckBox) itemView.findViewById(R.id.ServiceNameCheckBox);
            selectedSericeListTv = (TextView) itemView.findViewById(R.id.selectedSericeListTv);
            ServiceNameCheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    Log.d("clickeddagain", "clicked");
                    mItemClickListener.onItemClick(v, getPosition(), serviceArrayList.get(getPosition()).getServiceId());
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName);
    }

}
