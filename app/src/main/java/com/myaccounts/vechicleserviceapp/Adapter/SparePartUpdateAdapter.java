package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.Activity.SpareStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.List;

public class SparePartUpdateAdapter extends RecyclerView.Adapter<SparePartUpdateAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SparePartDetails> sparePartDetailsArrayList;
    private SparePartUpdateAdapter.OnItemClickListener onItemClickListener;
    private String ValueStr = "";


    String storeSpinner;
    List<String> categories;

    public SparePartUpdateAdapter(Context context, int sparepart_issue_row_item, ArrayList<SparePartDetails> sparePartDetailsArrayList, String jobcard) {
        this.context = context;
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
        ValueStr = jobcard;

    }

    @NonNull
    @Override
    public SparePartUpdateAdapter.ItemViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sparepart_update_row_item, holder, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SparePartUpdateAdapter.ItemViewHolder holder,final int position) {
        try {
            final SparePartDetails sparePartDetails = sparePartDetailsArrayList.get(position);

            categories = new ArrayList<String>();

            categories.add(sparePartDetails.getmIssueType());
            // categories.add("Paid");
            //  categories.add("Free");

           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.ServiceIssueTypeTv.setAdapter(adapter);

*/
            Log.d("SparePartDetailsadpater", "" + sparePartDetails + "," + position + 1);
            sparePartDetails.setRowNo(position + 1);

            storeSpinner = sparePartDetails.getmIssueType();

            // sparePartDetails.setRowNo(position + 1);
            holder.SnoTv.setText(String.valueOf(sparePartDetails.getRowNo()));
            Log.e("setting",String.valueOf(sparePartDetails.getRowNo()));
            holder.SparePartNameTv.setText(String.valueOf(sparePartDetails.getmSparePartName()));
            Log.e("setting",String.valueOf(sparePartDetails.getmSparePartName()));
            holder.SparePartUomTv.setText(String.valueOf(sparePartDetails.getmUOMName()));
            Log.e("setting",String.valueOf(sparePartDetails.getmUOMName()));
            holder.SparePartUomTv.setTag(String.valueOf(sparePartDetails.getmUOMID()));
            Log.e("setting",String.valueOf(sparePartDetails.getmUOMID()));
//            holder.SparePartAvlQtyTv.setText(String.valueOf(sparePartDetails.getmAVLQTY()));
            holder.SparePartAvlQtyTv.setText(String.valueOf(sparePartDetails.getmRate()));
            holder.SparePartRateTv.setText(String.valueOf(sparePartDetails.getmMRP()));
            Log.e("setting",String.valueOf(sparePartDetails.getmMRP()));
            holder.SparePartQtyTv.setText(String.valueOf(sparePartDetails.getmQTY()));
            Log.e("setting",String.valueOf(sparePartDetails.getmQTY()));
            holder.SparePartIssueTv.setText(sparePartDetails.getmIssueType());
            storeSpinner = sparePartDetails.getmIssueType();
            String status = String.valueOf(sparePartDetails.getSelected());
            Log.e("status",status);
            if(status.equals("true")){
                holder.chk_status.setChecked(true);

            }
            else{
                holder.chk_status.setChecked(false);
                SpareStatusUpdateActivity.checkBoxStatus=false;
            }


            holder.chk_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.chk_status.isChecked()){
                        sparePartDetails.setSelected(true);
//                        Log.d("ANUSHA "," Adapter "+holder.chk_status.isChecked());
                        Log.e("checked ",sparePartDetailsArrayList.get(position).getmSparePartID()+", "+sparePartDetailsArrayList.get(position).getSelected());
                        SpareStatusUpdateActivity.checkBoxStatus=true;
                        Log.d("ANUSHA "," Adapter "+SpareStatusUpdateActivity.checkBoxStatus);

                    }
                    else
                    {

                        sparePartDetails.setSelected(false);
                        SpareStatusUpdateActivity.checkBoxStatus=false;
                        Log.d("ANUSHA "," Adapter "+SpareStatusUpdateActivity.checkBoxStatus);
                        Log.e("not checked ",sparePartDetailsArrayList.get(position).getmSparePartID());
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        Log.e("size pp",String.valueOf(sparePartDetailsArrayList.size()));
        return sparePartDetailsArrayList.size();

    }


    public void SetOnItemClickListener(SparePartUpdateAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView SnoTv, SparePartNameTv, SparePartUomTv, SparePartMRPTv, SparePartAvlQtyTv, SparePartRateTv, SparePartQtyTv, ActionTv,SparePartIssueTv;
        private ImageButton IdEditIconImg, IdDeleteIconImg;
        CheckBox chk_status;
        //  private Spinner ServiceIssueTypeTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            SnoTv = (TextView) itemView.findViewById(R.id.SnoTv);
            SparePartNameTv = (TextView) itemView.findViewById(R.id.SparePartNameTv);
            SparePartUomTv = (TextView) itemView.findViewById(R.id.SparePartUomTv);

            SparePartAvlQtyTv = (TextView) itemView.findViewById(R.id.SparePartAvlQtyTv);
            SparePartRateTv = (TextView) itemView.findViewById(R.id.SparePartRateTv);
            SparePartQtyTv = (TextView) itemView.findViewById(R.id.SparePartQtyTv);
//            ActionTv = (TextView) itemView.findViewById(R.id.ActionTv);
            IdEditIconImg = (ImageButton) itemView.findViewById(R.id.IdEditIconImg);
            IdDeleteIconImg = (ImageButton) itemView.findViewById(R.id.IdDeleteIconImg);
            //  ServiceIssueTypeTv = (Spinner) itemView.findViewById(R.id.ServiceIssueTypeTv);
            SparePartIssueTv  = (TextView) itemView.findViewById(R.id.SparePartIssueTv);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, issueTy                                    pe);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chk_status = (CheckBox) itemView.findViewById(R.id.chk_spare_update);
//            ServiceIssueTypeTv.setAdapter(adapter);
//
//            int spinnerPosition = adapter.getPosition(storeSpinner);
//            ServiceIssueTypeTv.setSelection(spinnerPosition);



            if (ValueStr.equalsIgnoreCase("jobcard")) {
                SparePartRateTv.setVisibility(View.GONE);

            } else {
                SparePartRateTv.setVisibility(View.VISIBLE);

            }

            chk_status.setOnClickListener(this);
            IdEditIconImg.setOnClickListener(this);
            IdDeleteIconImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getPosition(), sparePartDetailsArrayList.get(getPosition()).getmSparePartName());

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
