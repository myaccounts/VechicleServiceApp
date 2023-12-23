package com.myaccounts.vechicleserviceapp.Adapter;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.myaccounts.vechicleserviceapp.Activity.ServiceStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewServiceUpdateAdapter extends RecyclerView.Adapter<NewServiceUpdateAdapter.ItemViewHolder> {

    private final Context context;
    private final ArrayList<NewServiceMasterDetails> sparePartDetailsArrayList;
    private NewServiceUpdateAdapter.OnItemClickListener onItemClickListener;
    private String ValueStr = "";
    String jc_id="";

    String storeSpinner;
    List<String> categories;

    public NewServiceUpdateAdapter(Context context, int new_service_issue_row_item, ArrayList<NewServiceMasterDetails> sparePartDetailsArrayList, String jobcard, String jobcard_id) {
        this.context = context;
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
        ValueStr = jobcard;
        this.jc_id = jobcard_id;

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
            final NewServiceMasterDetails serviceDetails = sparePartDetailsArrayList.get(holder.getAdapterPosition());
            categories = new ArrayList<String>();
            categories.add(serviceDetails.getmIssueType());
            serviceDetails.setRowNo(holder.getAdapterPosition() + 1);
            storeSpinner = serviceDetails.getmIssueType();

            holder.SnoTv.setText(String.valueOf(serviceDetails.getRowNo()));
            holder.ServicetNameTv.setText(String.valueOf(serviceDetails.getmServiceName()));
            holder.JobItemTypeTv.setText(String.valueOf(serviceDetails.getmSubServiceName()));
            holder.ServiceQtyTv.setText(String.valueOf(serviceDetails.getmQty()));
            holder.ServiceIssueTv.setText(String.valueOf(serviceDetails.getmIssueType()));
            String status = String.valueOf(serviceDetails.getmSetSerStatus());

            holder.chk_status.setChecked(status.equals("true"));

            holder.chk_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("onClick", "onClick: " + holder.chk_status.isChecked() );

                    String value = "";

                    if (holder.chk_status.isChecked())
                    {
                        value = "true";
                    }
                    else
                    {
                        value = "false";
                    }

                    final String finalValue = value;
                    Runnable runnable = new Runnable(){
                        public void run()
                        {
                            UpdateServiceStatus(jc_id,serviceDetails.getmServiceId(),serviceDetails.getmSubServiceId(), finalValue);
                        }
                    };

                    Thread thread = new Thread(runnable);
                    thread.start();


                }
            });

            /*holder.chk_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.chk_status.isChecked())
                    {
                        serviceDetails.setSelected(true);
                        ServiceStatusUpdateActivity.checkBoxStatus=true;
                    }
                    else
                    {
                        serviceDetails.setSelected(false);
                        ServiceStatusUpdateActivity.checkBoxStatus=false;
                    }
                }
            });*/

            if(ServiceStatusUpdateActivity.isSelectAll)
            {
                holder.chk_status.setChecked(true);
                serviceDetails.setSelected(true);
                ServiceStatusUpdateActivity.checkBoxStatus=true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception in adapter", String.valueOf(e));
        }
    }

    private void UpdateServiceStatus(String jc_id, String service_id, String subServiceId, String value)
    {

        Log.e("service_update", "Adapter " + jc_id + " " + service_id + " " + subServiceId+ " " + value);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("JobCardId",jc_id);
            jsonObject.put("ServiceId",service_id);
            jsonObject.put("SubServiceId",subServiceId);
            jsonObject.put("Status",value);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ProjectVariables.BASE_URL + ProjectVariables.UpdateServiceStatus, jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                Log.e("checkbox", "onResponse: " + jsonArray );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                Log.e("checkbox", "volleyError: " + volleyError.getLocalizedMessage() );
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public int getItemCount() {
        return sparePartDetailsArrayList.size();
    }


    public void SetOnItemClickListener(NewServiceUpdateAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView SnoTv;
        private final TextView ServicetNameTv;
        private final TextView JobItemTypeTv;
        private TextView RemarksTv;
        private final TextView ServiceIssueTv;
        CheckBox chk_status;
        TextView ServiceQtyTv;
        private final ImageButton IdEditIconImg;
        private final ImageButton IdDeleteIconImg;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            SnoTv = (TextView) itemView.findViewById(R.id.SnoTv);
            ServicetNameTv = (TextView) itemView.findViewById(R.id.ServicetNameTv);
            JobItemTypeTv = (TextView) itemView.findViewById(R.id.JobItemTypeTv);
            ServiceQtyTv = (TextView) itemView.findViewById(R.id.ServiceQtyTv);
            ServiceIssueTv = (TextView) itemView.findViewById(R.id.ServiceIssueTv);
            IdEditIconImg = (ImageButton) itemView.findViewById(R.id.IdEditIconImg);
            IdDeleteIconImg = (ImageButton) itemView.findViewById(R.id.IdDeleteIconImg);
            chk_status = (CheckBox) itemView.findViewById(R.id.chk_service_update);
            chk_status.setOnClickListener(this);
            // IdDeleteIconImg.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            try {
                if (onItemClickListener != null)
                {
                    onItemClickListener.onItemClick(v, getAdapterPosition(), sparePartDetailsArrayList.get(getAdapterPosition()).getmServiceName());
                }

            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position, String itemName);
    }
}

