package com.myaccounts.vechicleserviceapp.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.ServiceStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewServiceUpdateAdapter extends RecyclerView.Adapter<NewServiceUpdateAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<NewServiceMasterDetails> sparePartDetailsArrayList;
    private NewServiceUpdateAdapter.OnItemClickListener onItemClickListener;
    private String ValueStr = "";
    // String[] issueType = {"Paid", "Free"};
    // String qty = "1";

    String storeSpinner;
    List<String> categories;

    public NewServiceUpdateAdapter(Context context, int new_service_issue_row_item, ArrayList<NewServiceMasterDetails> sparePartDetailsArrayList, String jobcard) {
        this.context = context;
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
        ValueStr = jobcard;

    }

    @NonNull
    @Override
    public NewServiceUpdateAdapter.ItemViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_service_update_row_item, holder, false);
        NewServiceUpdateAdapter.ItemViewHolder itemViewHolder = new NewServiceUpdateAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        try {
            final NewServiceMasterDetails serviceDetails = sparePartDetailsArrayList.get(position);
            categories = new ArrayList<String>();

            categories.add(serviceDetails.getmIssueType());


            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.ServiceIssueTypeTv.setAdapter(adapter);*/


            Log.d("fffffff", "" + serviceDetails + "," + position + 1);
            serviceDetails.setRowNo(position + 1);

            storeSpinner = serviceDetails.getmIssueType();


            Log.d("Adapissuetype", storeSpinner);

            holder.SnoTv.setText(String.valueOf(serviceDetails.getRowNo()));
            holder.ServicetNameTv.setText(String.valueOf(serviceDetails.getmServiceName()));
            holder.JobItemTypeTv.setText(String.valueOf(serviceDetails.getmSubServiceName()));
            Log.d("Sssssss", String.valueOf(serviceDetails.getmSubServiceName()));
            holder.ServiceQtyTv.setText(String.valueOf(serviceDetails.getmQty()));
//            holder.ServiceAvlQtyTv.setText(String.valueOf(serviceDetails.getmAvlQty()));
            holder.ServiceIssueTv.setText(String.valueOf(serviceDetails.getmIssueType()));
//            String status = String.valueOf(serviceDetails.getSelected());
            String status = String.valueOf(serviceDetails.getmSetSerStatus());
            Log.e("status",status);
            Log.d(" ANUSHA ","++++++"+status);
            if(status.equals("true")){
                holder.chk_status.setChecked(true);
            }
            else{
                holder.chk_status.setChecked(false);
            }
            holder.chk_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.chk_status.isChecked()){
                        serviceDetails.setSelected(true);
                        Log.e("checked ",sparePartDetailsArrayList.get(position).getmServiceName()+", "+sparePartDetailsArrayList.get(position).getmServiceId()
                         +" , "+sparePartDetailsArrayList.get(position).getmSubServiceId()+", "+sparePartDetailsArrayList.get(position).getSelected());
                        ServiceStatusUpdateActivity.checkBoxStatus=true;
                    }
                    else
                    {
                        serviceDetails.setSelected(false);
                        ServiceStatusUpdateActivity.checkBoxStatus=false;
                        Log.e("not checked ",sparePartDetailsArrayList.get(position).getmServiceName());
                    }


                }
            });
            if(ServiceStatusUpdateActivity.isSelectAll){
                holder.chk_status.setChecked(true);
                serviceDetails.setSelected(true);
                ServiceStatusUpdateActivity.checkBoxStatus=true;
            }/*else if(!ServiceStatusUpdateActivity.isSelectAll && !status.equals("true")){
                holder.chk_status.setChecked(false);
                serviceDetails.setSelected(false);
                ServiceStatusUpdateActivity.checkBoxStatus=false;
            }*/

//            holder.ServiceIssueTypeTv.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception in adapter", String.valueOf(e));
        }

    }




    @Override
    public int getItemCount() {
        return sparePartDetailsArrayList.size();
    }


    public void SetOnItemClickListener(NewServiceUpdateAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView SnoTv, ServicetNameTv, JobItemTypeTv, RemarksTv,ServiceIssueTv;
        CheckBox chk_status;
        // public Spinner ServiceIssueTypeTv;
        TextView ServiceQtyTv;
        private ImageButton IdEditIconImg, IdDeleteIconImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            SnoTv = (TextView) itemView.findViewById(R.id.SnoTv);
            ServicetNameTv = (TextView) itemView.findViewById(R.id.ServicetNameTv);
            JobItemTypeTv = (TextView) itemView.findViewById(R.id.JobItemTypeTv);

            ServiceQtyTv = (TextView) itemView.findViewById(R.id.ServiceQtyTv);
//            ServiceAvlQtyTv = (TextView) itemView.findViewById(R.id.ServiceAvlQtyTv);
//            RemarksTv = (TextView) itemView.findViewById(R.id.RemarksTv);
            ServiceIssueTv = (TextView) itemView.findViewById(R.id.ServiceIssueTv);
            IdEditIconImg = (ImageButton) itemView.findViewById(R.id.IdEditIconImg);
            IdDeleteIconImg = (ImageButton) itemView.findViewById(R.id.IdDeleteIconImg);
            chk_status = (CheckBox) itemView.findViewById(R.id.chk_service_update);



            if (ValueStr.equalsIgnoreCase("jobcard")) {
//                RemarksTv.setVisibility(View.GONE);

            } else {
//                RemarksTv.setVisibility(View.VISIBLE);

            }
            chk_status.setOnClickListener(this);
           // IdDeleteIconImg.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getPosition(), sparePartDetailsArrayList.get(getPosition()).getmServiceName());

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

