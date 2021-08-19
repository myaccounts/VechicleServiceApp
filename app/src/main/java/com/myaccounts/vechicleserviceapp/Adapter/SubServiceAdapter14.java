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

public class SubServiceAdapter14 extends RecyclerView.Adapter<SubServiceAdapter14.ItemViewHolder> {
    private Context context;

    public ArrayList<SubService> subServiceArrayList14;
    private ArrayList<SubService> serviceHeaderArrayList;
    private ArrayList<SubServiceHeader> subServiceHeaderArrayList;
    private OnItemClickListener mItemClickListener;
    private String serviceName;
    private  int value = 0;
    public SubServiceAdapter14(Context context, int selectedsubservice_row_item, ArrayList<SubService> subServiceArrayList, ArrayList<SubService> serviceHeaderArrayList, String serviceName) {
        this.context = context;
        this.subServiceArrayList14 = subServiceArrayList;
        this.serviceHeaderArrayList = serviceHeaderArrayList;
        this.serviceName = serviceName;

    }


    @Override
    public SubServiceAdapter14.ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.selectedsubservice_row_item, parent, false);
        return new SubServiceAdapter14.ItemViewHolder(view1, this);


    }

    public void setOnItemClickListner(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(final SubServiceAdapter14.ItemViewHolder holder, final int position) {
        try {
            String headerValue;
            if (subServiceArrayList14 != null) {

                final SubService dashBoard = subServiceArrayList14.get(position);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();

                    /*if (dashBoard.getValue().startsWith("SSID")) {
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

                    }
*/

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
                        holder.SubServiceValue.setEnabled(true);//delte AFTER CHEKING
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
                        subServiceArrayList14.get(position).setValue(String.valueOf(editable));

                    }
                });




        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }




    @Override
    public int getItemCount() {
        return subServiceArrayList14.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private EditText SubServiceValue;
        private LinearLayout LinerLayout, HeaderLinerLayout;

        public ItemViewHolder(View view1, SubServiceAdapter14 subServiceAdapter) {
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
                    mItemClickListener.onItemClick(v, getPosition(), subServiceArrayList14.get(getPosition()).getValue());
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
