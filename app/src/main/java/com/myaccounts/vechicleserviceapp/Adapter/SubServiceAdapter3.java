package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.myaccounts.vechicleserviceapp.Pojo.SubService;
import com.myaccounts.vechicleserviceapp.Pojo.SubServiceHeader;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;

import java.util.ArrayList;

public class SubServiceAdapter3 extends RecyclerView.Adapter<SubServiceAdapter3.ItemViewHolder> {
    private Context context;

    public ArrayList<SubService> subServiceArrayList3;
    private ArrayList<SubService> serviceHeaderArrayList;
    private ArrayList<SubServiceHeader> subServiceHeaderArrayList;
    private SubServiceAdapter3.OnItemClickListener mItemClickListener;
    private String serviceName;
    private  int value = 0;
    public SubServiceAdapter3(Context context, int selectedsubservice_row_item, ArrayList<SubService> subServiceArrayList, ArrayList<SubService> serviceHeaderArrayList, String serviceName) {
        this.context = context;
        this.subServiceArrayList3 = subServiceArrayList;
        this.serviceHeaderArrayList = serviceHeaderArrayList;
        this.serviceName = serviceName;

    }


    @Override
    public SubServiceAdapter3.ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_three_layout, parent, false);
        return new SubServiceAdapter3.ItemViewHolder(view1, this);


    }

    public void setOnItemClickListner(final SubServiceAdapter3.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(final SubServiceAdapter3.ItemViewHolder holder, final int position) {
        try {
            String headerValue;
            if (subServiceArrayList3 != null) {

                final SubService dashBoard = subServiceArrayList3.get(position);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();
              /*  if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
                    if (dashBoard.getValue().startsWith("SSID")) {
                        if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                            holder.HeaderLinerLayout.setVisibility(View.GONE);
                        layoutParams.setMargins(0, 0, 0, 0);
                        holder.HeaderLinerLayout.setLayoutParams(layoutParams);


                    } else if (dashBoard.getValue().startsWith("SRT")) {

                        if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                            holder.HeaderLinerLayout.setVisibility(View.GONE);

                        layoutParams.setMargins(0, 0, 0, 0);
                        holder.HeaderLinerLayout.setLayoutParams(layoutParams);

                    } else {
                        holder.HeaderLinerLayout.setVisibility(View.VISIBLE);
                        holder.HeaderLinerLayout.setGravity(View.FOCUS_LEFT);
                        holder.HeaderLinerLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);
                        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                        holder.SubServiceValue.setText(dashBoard.getValue());

                    }*/
                if (position == value) {
                    String headerString=dashBoard.getValue().trim();
                    String finalstring= headerString.substring(headerString.lastIndexOf("!") + 1);
                    holder.SubServiceValue.setText(finalstring);
                    value = value + serviceHeaderArrayList.size();

                } else {
                    holder.HeaderLinerLayout.setVisibility(View.VISIBLE);
                    holder.HeaderLinerLayout.setGravity(View.FOCUS_LEFT);
                    holder.HeaderLinerLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);
                    layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    holder.SubServiceValue.setText(dashBoard.getValue());
                }
                    if (dashBoard.getValue().equalsIgnoreCase("1")) {
                        holder.SubServiceValue.setEnabled(true);
                        holder.SubServiceValue.setText("");
                        holder.SubServiceValue.setHint("");
                    } else if (dashBoard.getValue().equalsIgnoreCase("0")) {
                        holder.SubServiceValue.setText("");
                        holder.SubServiceValue.setHint("");
                        holder.SubServiceValue.setEnabled(false);//delte AFTER CHEKING
                        holder.SubServiceValue.setBackgroundColor(Color.parseColor("#dddddf"));
                    }
                }

                holder.SubServiceValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence Value, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        subServiceArrayList3.get(position).setValue(String.valueOf(editable));

                    }
                });




        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }




    @Override
    public int getItemCount() {
        return subServiceArrayList3.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private EditText SubServiceValue;
        private LinearLayout LinerLayout, HeaderLinerLayout;

        public ItemViewHolder(View view1, SubServiceAdapter3 subServiceAdapter) {
            super(view1);
            SubServiceValue = (EditText) view1.findViewById(R.id.SubServiceValue);
            LinerLayout = (LinearLayout) view1.findViewById(R.id.LinerLayout);
            HeaderLinerLayout = (LinearLayout) view1.findViewById(R.id.HeaderLinerLayout);
            SubServiceValue.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition(), subServiceArrayList3.get(getPosition()).getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String value);
    }
}
