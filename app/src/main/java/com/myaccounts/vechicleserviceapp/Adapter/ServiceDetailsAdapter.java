package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.myaccounts.vechicleserviceapp.Pojo.SubService;
import com.myaccounts.vechicleserviceapp.Pojo.SubServiceList;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class ServiceDetailsAdapter extends RecyclerView.Adapter<ServiceDetailsAdapter.ItemHolder> {
    private Context context;
    public static ArrayList<SubServiceList> tyredetailsArraylist;

    public ServiceDetailsAdapter(Context context, int subservice_row_item, ArrayList<SubServiceList> tyredetailsArraylist) {
        this.context=context;
        this.tyredetailsArraylist=tyredetailsArraylist;
    }

    @Override
    public ServiceDetailsAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.mainsubservice_row_item, parent, false);
        return new ItemHolder(view1);
    }

    @Override
    public void onBindViewHolder(final ServiceDetailsAdapter.ItemHolder holder, final int position) {
      final SubServiceList serviceList=tyredetailsArraylist.get(position);
//      holder.PaidorFreeServiceCheckBox.setText("Paid");
      holder.ServiceHeaderCheckBox.setText(serviceList.getServiceheader());

      //holder.ServiceHeaderCheckBox.setTag(serviceList.getServiceheaderId());

        holder.ServiceHeaderCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (tyredetailsArraylist.get(position).getSelected()) {
                        tyredetailsArraylist.get(position).setSelected(false);

                    } else {
                        tyredetailsArraylist.get(position).setSelected(true);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        holder.PaidorFreeServiceCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (tyredetailsArraylist.get(position).getPaidSelected()) {
                        tyredetailsArraylist.get(position).setPaidSelected(false);
                        holder.PaidorFreeServiceCheckBox.setText("Paid");
                    } else {
                        tyredetailsArraylist.get(position).setPaidSelected(true);
                        holder.PaidorFreeServiceCheckBox.setText("Free");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return tyredetailsArraylist.size();
    }
    @Override
    public long getItemId(int i) {
        return i;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
       public CheckBox ServiceHeaderCheckBox,PaidorFreeServiceCheckBox;
        public ItemHolder(View itemView) {
            super(itemView);
            PaidorFreeServiceCheckBox=(CheckBox)itemView.findViewById(R.id.PaidorFreeServiceCheckBox);
            ServiceHeaderCheckBox=(CheckBox)itemView.findViewById(R.id.ServiceHeaderCheckBox);

        }
    }
}
