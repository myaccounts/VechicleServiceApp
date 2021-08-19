package com.myaccounts.vechicleserviceapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewServiceMasterIssueAdapter extends RecyclerView.Adapter<NewServiceMasterIssueAdapter.ItemViewHolder> {
    public static boolean spinnerTouched=false;
    String[] issueType = {"Paid", "Free"};
    String issueTypeString,issueTypeSelectedString,JJJJJ;
    private Context context;
    private ArrayList<NewServiceMasterDetails> sparePartDetailsArrayList;
    private NewServiceMasterIssueAdapter.OnItemClickListener onItemClickListener;
    private NewServiceMasterIssueAdapter.OnItemSelected jRecyclarObj;
//    private NewServiceMasterIssueAdapter.OnItemSelected onItemSelected;
    public static boolean clickQty=false;
    //    private NewServiceMasterIssueAdapter.AfterTextChanged afterTextChanged;
    private String ValueStr = "";
    // String[] issueType = {"Paid", "Free"};
    // String qty = "1";

    String storeSpinner;
    List<String> categories;
    JListner listner;

    public NewServiceMasterIssueAdapter(Context context, int new_service_issue_row_item, ArrayList<NewServiceMasterDetails> sparePartDetailsArrayList, String jobcard) {
        this.context = context;
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
        ValueStr = jobcard;

    }

    @NonNull
    @Override
    public NewServiceMasterIssueAdapter.ItemViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_service_issue_row_item, holder, false);
        NewServiceMasterIssueAdapter.ItemViewHolder itemViewHolder = new NewServiceMasterIssueAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewServiceMasterIssueAdapter.ItemViewHolder holder, int position) {
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
//            holder.ServicetNameTv.setText(String.valueOf(serviceDetails.getmServiceName()));
            holder.JobItemTypeTv.setText(String.valueOf(serviceDetails.getmSubServiceName()));
            Log.d("Sssssss", String.valueOf(serviceDetails.getmSubServiceName()));
            holder.ServiceQtyTv.setText(String.valueOf(serviceDetails.getmQty()));
            holder.ServiceRateTv.setText(String.valueOf(serviceDetails.getmRate()));
//            holder.ServicetNameTv.setText(String.valueOf(serviceDetails.getmServiceName()));
//            holder.ServiceAvlQtyTv.setText(String.valueOf(serviceDetails.getmAvlQty()));
//            holder.ServiceIssueTypeTv.setText(String.valueOf(serviceDetails.getmIssueType()));
            issueTypeSelectedString=String.valueOf(serviceDetails.getmIssueType());
            Log.d("ANUSHA ", "+++++ "+issueTypeSelectedString);
            Log.d("ANUSHA ", "+++++ "+serviceDetails.getmIssueType());
            for(int i=0;i<issueType.length;i++)
            {
                Log.d("ANUSHA ", "+++++ string "+issueType[i]);
                Log.d("ANUSHA ", "+++++ pos "+i);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, issueType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              holder.ServiceIssueTypeSpinner.setAdapter(adapter);
              if(issueTypeSelectedString.equalsIgnoreCase("Paid"))
                  holder.ServiceIssueTypeSpinner.setSelection(0,true);
              else
                  holder.ServiceIssueTypeSpinner.setSelection(1,true);
              /*holder.ServiceIssueTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                      if(view!=null) {//added null check
                          view.setSelected(true);
                          issueTypeString = issueType[position];
                          JJJJJ=parent.getItemAtPosition(position).toString();
//                          if(sparePartDetailsArrayList.size()>0)
                          Log.d("ANUSHA ", "+++++"+JJJJJ+" "+sparePartDetailsArrayList.get(position).getmIssueType());
//                                  +" "+serviceDetails.getRowNo()+" "+sparePartDetailsArrayList.get(serviceDetails.getRowNo()).getmIssueType());
                          holder.ServiceIssueTypeTv.setText(JJJJJ);
                          serviceDetails.setmIssueType(JJJJJ);
                          jRecyclarObj.getDetails(String.valueOf(serviceDetails.getRowNo()),JJJJJ);
//                          listner.getJdata("1");
                      }
//                Toast.makeText(getActivity(), "service Selected User: " + issueType[position], Toast.LENGTH_SHORT).show();

                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {

                  }
              });*/


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception in adapter", String.valueOf(e));
        }

    }

    @Override
    public int getItemCount() {
        return sparePartDetailsArrayList.size();
    }


    public void SetOnItemClickListener(NewServiceMasterIssueAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void SetJRecyclar(NewServiceMasterIssueAdapter.OnItemSelected jrecyclar) {
        this.jRecyclarObj = jrecyclar;
    }
    public void SetOnItemSelected(NewServiceMasterIssueAdapter.OnItemSelected onItemSelected) {
        this.jRecyclarObj = onItemSelected;
    }

    /*public void SetAfterTextChanged(NewServiceMasterIssueAdapter.AfterTextChanged afterTextChanged) {
        this.afterTextChanged = afterTextChanged;
    }*/




    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener{
        private TextView SnoTv, JobItemTypeTv, RemarksTv, ServiceRateTv;// ServiceIssueTypeTv;//ServicetNameTv
        public Spinner ServiceIssueTypeSpinner;
        ImageButton plus;
        TextView ServiceQtyTv;
        private ImageButton IdEditIconImg, IdDeleteIconImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            SnoTv = (TextView) itemView.findViewById(R.id.SnoTv);
//            ServicetNameTv = (TextView) itemView.findViewById(R.id.ServicetNameTv);
            JobItemTypeTv = (TextView) itemView.findViewById(R.id.JobItemTypeTv);

            ServiceQtyTv = (TextView) itemView.findViewById(R.id.ServiceQtyTv);
            ServiceRateTv = (TextView) itemView.findViewById(R.id.ServiceRateTv);
            plus = (ImageButton) itemView.findViewById(R.id.plus);
            ServiceIssueTypeSpinner = (Spinner) itemView.findViewById(R.id.ServiceIssueTypeSpinner);
            ServiceIssueTypeSpinner.setSelection(0,false);
//            ServiceIssueTypeSpinner.setSelection(0,false);
//            RemarksTv = (TextView) itemView.findViewById(R.id.RemarksTv);
//            ServiceIssueTypeTv = (TextView) itemView.findViewById(R.id.ServiceIssueTv);
            IdEditIconImg = (ImageButton) itemView.findViewById(R.id.IdEditIconImg);
            IdDeleteIconImg = (ImageButton) itemView.findViewById(R.id.IdDeleteIconImg);


            if (ValueStr.equalsIgnoreCase("jobcard")) {
//                RemarksTv.setVisibility(View.GONE);

            } else {
//                RemarksTv.setVisibility(View.VISIBLE);

            }
            IdEditIconImg.setOnClickListener(this);
            IdDeleteIconImg.setOnClickListener(this);
            plus.setOnClickListener(this);
            ServiceIssueTypeSpinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Real touch felt.");
                    spinnerTouched = true;
                    return false;
                }
            });
            ServiceIssueTypeSpinner.setOnItemSelectedListener(this);
//            ServiceQtyTv.addTextChangedListener(this);

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

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try{
                JJJJJ=adapterView.getItemAtPosition(i).toString();
//                          if(sparePartDetailsArrayList.size()>0)
                Log.d("ANUSHA ", "+++++"+JJJJJ);
                Log.d("ANUSHA ", "+++++"+getAdapterPosition());
//                Log.d("ANUSHA ", "+++++"+JJJJJ+" "+sparePartDetailsArrayList.get(i).getmIssueType());
//                                  +" "+serviceDetails.getRowNo()+" "+sparePartDetailsArrayList.get(serviceDetails.getRowNo()).getmIssueType());

                if(jRecyclarObj!=null){
                    jRecyclarObj.onItemSelected(adapterView,getAdapterPosition(),view,spinnerTouched,JJJJJ);
//                    jRecyclarObj.getDetails(adapterView,getAdapterPosition(),view,"JESUS",JJJJJ);
                }

            }catch (Exception e){
                Log.d("ANUSHA "," "+e.toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }


        /*@Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                if (onItemSelected != null) {
                    onItemSelected.onItemSelected(adapterView, getPosition(), adapterView.getItemAtPosition(i).toString());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }*/
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName);

    }
    public interface OnItemSelected {//adapterView, getPosition(), adapterView.getItemAtPosition(i).toString()
        public void onItemSelected(AdapterView adapter,int position,View view,boolean result, String status);

    }
    /*public interface JRecyclar{
        public void getDetails(AdapterView adapter,int position,View view,String result, String status);
    }*/

}
