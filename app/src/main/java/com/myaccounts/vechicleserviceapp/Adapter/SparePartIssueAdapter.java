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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.List;

public class SparePartIssueAdapter extends RecyclerView.Adapter<SparePartIssueAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SparePartDetails> sparePartDetailsArrayList;
    private SparePartIssueAdapter.OnItemClickListener onItemClickListener;
    private String ValueStr = "";


    String storeSpinner;
    List<String> categories;

    public SparePartIssueAdapter(Context context, int sparepart_issue_row_item, ArrayList<SparePartDetails> sparePartDetailsArrayList, String jobcard) {
        this.context = context;
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
        ValueStr = jobcard;

    }

    @NonNull
    @Override
    public SparePartIssueAdapter.ItemViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sparepart_issue_row_item, holder, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(SparePartIssueAdapter.ItemViewHolder holder, int position) {
        try {
            SparePartDetails sparePartDetails = sparePartDetailsArrayList.get(position);

            categories = new ArrayList<String>();

            categories.add(sparePartDetails.getmIssueType());
            // categories.add("Paid");
            //  categories.add("Free");

           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.ServiceIssueTypeTv.setAdapter(adapter);

*/
            Log.d("SparePartDetailsadpater", "" + sparePartDetails.getmAVLQTY());
            sparePartDetails.setRowNo(position + 1);

            storeSpinner = sparePartDetails.getmIssueType();

            // sparePartDetails.setRowNo(position + 1);
//            if(sparePartDetails.getRowNo())
            holder.SnoTv.setText(String.valueOf(sparePartDetails.getRowNo()));
            Log.e("setting",String.valueOf(sparePartDetails.getRowNo()));
            holder.SparePartNameTv.setText(String.valueOf(sparePartDetails.getmSparePartName()));
            Log.e("setting",String.valueOf(sparePartDetails.getmSparePartName()));
            holder.SparePartUomTv.setText(String.valueOf(sparePartDetails.getmUOMName()));
            Log.e("setting",String.valueOf(sparePartDetails.getmUOMName()));
            holder.SparePartUomTv.setTag(String.valueOf(sparePartDetails.getmUOMID()));
            Log.e("setting",String.valueOf(sparePartDetails.getmUOMID()));
            holder.SparePartRateTv.setText(String.valueOf(sparePartDetails.getmMRP()));
            Log.e("setting",String.valueOf(sparePartDetails.getmMRP()));
            holder.SparePartQtyTv.setText(String.valueOf(sparePartDetails.getmQTY()));
            Log.e("setting",String.valueOf(sparePartDetails.getmQTY()));
//            holder.SparePartAvlQtyTv.setText(String.valueOf(sparePartDetails.getmAVLQTY()));
            holder.SparePartAvlQtyTv.setText(String.valueOf(sparePartDetails.getmRate()));
            Log.d("SparePartDetailsadpater","+"+String.valueOf(sparePartDetails.getmAVLQTY()));
            holder.SparePartIssueTv.setText(sparePartDetails.getmIssueType());
            storeSpinner = sparePartDetails.getmIssueType();

            Log.d("stoooooo", storeSpinner);

//            holder.ActionTv.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        Log.e("size pp",String.valueOf(sparePartDetailsArrayList.size()));
        return sparePartDetailsArrayList.size();

    }


    public void SetOnItemClickListener(SparePartIssueAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView SnoTv, SparePartNameTv, SparePartUomTv, SparePartMRPTv, SparePartAvlQtyTv, SparePartRateTv, SparePartQtyTv, ActionTv,SparePartIssueTv;
        private ImageButton IdEditIconImg, IdDeleteIconImg;

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

            if (ValueStr.equalsIgnoreCase("jobcard")) {
                SparePartRateTv.setVisibility(View.GONE);

            } else {
                SparePartRateTv.setVisibility(View.VISIBLE);

            }
            IdEditIconImg.setOnClickListener(this);
            IdDeleteIconImg.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null)
                {
                    onItemClickListener.onItemClick(v, getAdapterPosition(), sparePartDetailsArrayList.get(getAdapterPosition()).getmSparePartName());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public interface OnItemClickListener extends SparePartUpdateAdapter.OnItemClickListener {
        public void onItemClick(View view, int position, String itemName);
    }
}
