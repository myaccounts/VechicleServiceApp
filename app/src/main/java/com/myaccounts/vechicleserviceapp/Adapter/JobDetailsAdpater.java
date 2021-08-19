package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.DocumentTypes;
import com.myaccounts.vechicleserviceapp.Pojo.GetJobDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceManList;
import com.myaccounts.vechicleserviceapp.Pojo.UserServiceList;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class JobDetailsAdpater extends RecyclerView.Adapter<JobDetailsAdpater.ItemViewholder> {
    public static ArrayList<GetJobDetails> getJobDetailsArrayList;
    public static ArrayList<DocumentTypes> documentTypesArrayList;
    public static ArrayList<ServiceManList> userServiceListArrayList;

    Context context;
    private static String ServName = "", StatusSelection = "", UserSelection = "";

    public JobDetailsAdpater(Context context, ArrayList<GetJobDetails> getJobDetailsArrayList, ArrayList<DocumentTypes> documentTypesArrayList, ArrayList<ServiceManList> userServiceListArrayList, int jobcarddetails_row_item) {
        this.context = context;
        this.getJobDetailsArrayList = getJobDetailsArrayList;
        this.documentTypesArrayList = documentTypesArrayList;
        this.userServiceListArrayList = userServiceListArrayList;
    }

    @NonNull
    @Override
    public JobDetailsAdpater.ItemViewholder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.jobcarddetails_row_item, parent, false);
        return new ItemViewholder(view1, this);
    }

    @Override
    public void onBindViewHolder(final JobDetailsAdpater.ItemViewholder holder, final int position) {
        try {

            final GetJobDetails salesMen = getJobDetailsArrayList.get(position);
            if (salesMen.getmServiceName() != null) {
                String str = salesMen.getmServiceName();
                String[] arrOfStr = str.split("\\^");
                holder.ServiceNameTv.setText(arrOfStr[0]);
                holder.ServiceNameTv.setTag(arrOfStr[1]);
                ServName = arrOfStr[1];
            } else {
                holder.ServiceNameTv.setVisibility(View.GONE);
            }
            if (salesMen.getmSubServiceName() != null) {
                String str = salesMen.getmSubServiceName();
                String[] arrOfStr = str.split("\\^");
                holder.SubServiceTv.setText(arrOfStr[0]);
                holder.SubServiceTv.setTag(salesMen.getmSubServiceName());
            } else {
                holder.SubServiceTv.setVisibility(View.GONE);
            }
            String str = salesMen.getmSubServiceValue();
            String[] arrOfStr = str.split("\\!", -1);

            holder.SubserviceheaderTv.setText(arrOfStr[0]);
            holder.SubserviceheaderTv.setTag(salesMen.getmSubServiceValueStr());
            try {
                if (arrOfStr[1] != "") {
                    holder.Remarksedittext.setText(arrOfStr[1]);
                } else {
                    holder.Remarksedittext.setText("");
                }
                StatusSelection = arrOfStr[2];
                UserSelection = arrOfStr[3];
            }catch (Exception e){
                e.printStackTrace();
            }


            int Index = 0;
            String[] us = new String[documentTypesArrayList.size()];
            String[] us1 = new String[documentTypesArrayList.size()];
            for (int i = 0; i < documentTypesArrayList.size(); i++) {
                us[i] = documentTypesArrayList.get(i).getName();
                us1[i] = documentTypesArrayList.get(i).getId();
                if (StatusSelection.equalsIgnoreCase(us1[i])) {
                    Index = i;
                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, us);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.SpnStatusSelection.setAdapter(spinnerArrayAdapter);
            holder.SpnStatusSelection.setSelection(Index);


            String[] userList = new String[userServiceListArrayList.size()];
            try {
                // String ServName = holder.ServiceNameTv.getTag().toString();
                for (int i = 0; i < userServiceListArrayList.size(); i++) {
                    userList[i] = userServiceListArrayList.get(i).getName();
                   /* if (ServName.equalsIgnoreCase(userServiceListArrayList.get(i).getServiceId())) {
                        userList[i] = userServiceListArrayList.get(i).getUserName();
                    }*/
                }
                //cmt
                ArrayList<ServiceManList> arrayList = new ArrayList<>();
                for (int i = 0; i < userServiceListArrayList.size(); i++) {
                  //  if (ServName.equalsIgnoreCase(userServiceListArrayList.get(i).getServiceId())) {//related to user wise assign service names list data getting now all servicemanlist getting.
                        ServiceManList userServiceList = new ServiceManList();
                        userServiceList.setName(userServiceListArrayList.get(i).getName());
                        userServiceList.setId(userServiceListArrayList.get(i).getId());
                        arrayList.add(userServiceList);
                   // }
                }
                int IndexValue = 0;
                String[] userList1 = new String[arrayList.size()];
                String[] userList2 = new String[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    userList1[i] = arrayList.get(i).getName();
                    userList2[i] = arrayList.get(i).getId();
                    if (UserSelection.equalsIgnoreCase(userList2[i])) {
                        IndexValue = i;
                    }
                }
                //cmt
                ArrayAdapter<String> SpnUserSelectionAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, userList1);
                SpnUserSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.SpnUserSelection.setAdapter(SpnUserSelectionAdapter);
                holder.SpnUserSelection.setSelection(IndexValue);

                holder.SpnUserSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectUser = holder.SpnUserSelection.getSelectedItem().toString();

                        String taskToId = userServiceListArrayList.get(position).getId();
                        salesMen.setmSelectedUserId(taskToId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                salesMen.setmSelectedUserId(holder.SpnUserSelection.getSelectedItem().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.Remarksedittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int pos, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int pos, int i1, int i2) {
                    try {
                        getJobDetailsArrayList.get(position).setmRemarks(holder.Remarksedittext.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            /*getJobDetailsArrayList.get(position).setmSelectedServiceId(holder.SpnStatusSelection.getSelectedItem().toString());
            getJobDetailsArrayList.get(position).setmSelectedUserId(holder.SpnUserSelection.getSelectedItem().toString());*/
            salesMen.setmSelectedServiceId(holder.SpnStatusSelection.getSelectedItem().toString());

            holder.SpnStatusSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectUser = holder.SpnStatusSelection.getSelectedItem().toString();

                    String taskToId = documentTypesArrayList.get(position).getId();
                    salesMen.setmSelectedServiceId(taskToId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return getJobDetailsArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ItemViewholder extends RecyclerView.ViewHolder {


        private TextView ServiceNameTv, SubServiceTv, SubserviceheaderTv;
        private EditText Remarksedittext;
        private Spinner SpnStatusSelection, SpnUserSelection;

        public ItemViewholder(View view1, JobDetailsAdpater jobDetailsAdpater) {
            super(view1);
            SubServiceTv = (TextView) view1.findViewById(R.id.SubServiceTv);
            ServiceNameTv = (TextView) view1.findViewById(R.id.IdServiceNameTv);
            SubserviceheaderTv = (TextView) view1.findViewById(R.id.SubserviceheaderTv);
            Remarksedittext = (EditText) view1.findViewById(R.id.Remarksedittext);
            SpnStatusSelection = (Spinner) view1.findViewById(R.id.SpnStatusSelection);
            SpnUserSelection = (Spinner) view1.findViewById(R.id.SpnUserSelection);
        }
    }
}


