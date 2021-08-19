package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.MainActivity;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class SelectedServiceAdpater extends RecyclerView.Adapter<SelectedServiceAdpater.ItemViewHolder> {

    public static ArrayList<ServiceMaster> serviceMasterArrayList;
    public static ArrayList<ServiceMaster> serviceArrayList;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mItemClickListener;
    public static SparseBooleanArray mSelectedItemsIds;
    private  String serviceListData;

    public SelectedServiceAdpater(Context context, int selectedservice_row_item, ArrayList<ServiceMaster> serviceMasterArrayList) {
        inflater = LayoutInflater.from(context);
        this.context=context;
        this.serviceArrayList=serviceMasterArrayList;
       this.serviceMasterArrayList=serviceMasterArrayList;
      //  mSelectedItemsIds = new SparseBooleanArray();
    }

    public SelectedServiceAdpater(Context context, String serviceListData) {
        this.context=context;
        this.serviceListData=serviceListData;
    }

    @Override
    public SelectedServiceAdpater.ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 =  inflater.inflate(R.layout.selectedservice_row_item, parent, false);
        return new ItemViewHolder(view1, this);


    }

    @Override
    public void onBindViewHolder(final SelectedServiceAdpater.ItemViewHolder holder, final int position) {
        try {
            final ServiceMaster dashBoard = serviceArrayList.get(position);
            holder.ServiceNameCheckBox.setText(dashBoard.getServiceName());
            holder.ServiceNameCheckBox.setTag(dashBoard.getServiceId());
            if(dashBoard.getActiveStatus().equalsIgnoreCase("1")){
                holder.ServiceNameCheckBox.setChecked(true);
                serviceMasterArrayList.get(position).setSelected(true);
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

        }catch (Exception e){
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
    public int getItemCount()
    {
        return serviceArrayList.size();
    }



    public void setOnItemClickListner(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox ServiceNameCheckBox;
        private TextView selectedSericeListTv;
        public ItemViewHolder(View itemView, SelectedServiceAdpater selectedServiceAdpater) {
            super(itemView);
            ServiceNameCheckBox=(CheckBox)itemView.findViewById(R.id.ServiceNameCheckBox);
            selectedSericeListTv=(TextView) itemView.findViewById(R.id.selectedSericeListTv);
            ServiceNameCheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v,getPosition(),serviceArrayList.get(getPosition()).getServiceName());
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
