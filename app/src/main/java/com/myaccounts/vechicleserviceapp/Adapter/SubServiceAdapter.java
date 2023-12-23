package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.ArrayList;

public class SubServiceAdapter extends RecyclerView.Adapter<SubServiceAdapter.ItemViewHolder> {
    private Context context;
    public ArrayList<SubService> subServiceArrayList;
  /*  public ArrayList<SubService> subServiceArrayList2;
    public ArrayList<SubService> subServiceArrayList3;
    public ArrayList<SubService> subServiceArrayList4;
    public ArrayList<SubService> subServiceArrayList5;
    public ArrayList<SubService> subServiceArrayList6;
    public ArrayList<SubService> subServiceArrayList7;
    public ArrayList<SubService> subServiceArrayList8;
    public ArrayList<SubService> subServiceArrayList9;
    public ArrayList<SubService> subServiceArrayList10;
    public ArrayList<SubService> subServiceArrayList11;
    public ArrayList<SubService> subServiceArrayList12;
    public ArrayList<SubService> subServiceArrayList13;
    public ArrayList<SubService> subServiceArrayList14;
    public ArrayList<SubService> subServiceArrayList15;*/
    private ArrayList<SubService> serviceHeaderArrayList;
    private ArrayList<SubServiceHeader> subServiceHeaderArrayList;
    private OnItemClickListener mItemClickListener;
    private String serviceName;
    private  int value = 0;
    private LayoutInflater inflater;

    public SubServiceAdapter(Context context, int selectedsubservice_row_item, ArrayList<SubService> subServiceArrayList, ArrayList<SubService> serviceHeaderArrayList, String serviceName) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.subServiceArrayList = subServiceArrayList;
       this.serviceHeaderArrayList = serviceHeaderArrayList;
        this.serviceName = serviceName;
        setHasStableIds(true);
        //CalltheMethodBody(subServiceArrayList);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectedsubservice_row_item, parent, false);
        // pass MyCustomEditTextListener to viewholder in onCreateViewHolder
        // so that we don't have to do this expensive allocation in onBindViewHolder
        ItemViewHolder vh = new ItemViewHolder(v);


        return vh;
    /*   View converview=inflater.inflate(R.layout.selectedsubservice_row_item, parent, false);
      *//*  parent = ((Activity)context).getCurrentFocus();
        if (parent != null) {
            parent.clearFocus();
        }*//*


        return new ItemViewHolder(converview,this);*/
       /* if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selectedsubservice_row_item, parent, false);
            return new ItemViewHolder(view1,this);
        }
        else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
            View  view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_two_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_three_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_four_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
            View  view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_five_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_six_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_seven_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_eight_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_night_layout, parent, false);
            return new ItemViewHolder(view1,this);
        } else {
            View view1 = LayoutInflater.from(context).inflate(R.layout.selected_service_row_item_ten_layout, parent, false);
            return new ItemViewHolder(view1,this);
        }*/



    }

    public void setOnItemClickListner(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        try {
            String headerValue;
            if (subServiceArrayList != null) {

                final SubService dashBoard = subServiceArrayList.get(position);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();
               /* layoutParams.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);*/
              /*  if (dashBoard.getValue().startsWith("SSID")) {
                    if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                        holder.HeaderLinerLayout.setVisibility(View.GONE);
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.HeaderLinerLayout.setLayoutParams(layoutParams);


                } else if (dashBoard.getValue().startsWith("SRT")) {

                    if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                        holder.HeaderLinerLayout.setVisibility(View.GONE);

                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.HeaderLinerLayout.setLayoutParams(layoutParams);

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

              /*  if(position==0){
               String headerString=dashBoard.getValue().trim();
               String finalstring= headerString.substring(headerString.lastIndexOf("!") + 1);
                    holder.SubServiceValue.setText(finalstring);
               }else {
                    holder.HeaderLinerLayout.setVisibility(View.VISIBLE);
                    holder.HeaderLinerLayout.setGravity(View.FOCUS_LEFT);
                    holder.HeaderLinerLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);
                    layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    holder.SubServiceValue.setText(dashBoard.getValue());

                }
*/
                if (dashBoard.getValue().equalsIgnoreCase("1")) {
                    holder.SubServiceValue.setEnabled(true);
                    holder.SubServiceValue.setClickable(true);
                    holder.SubServiceValue.setCursorVisible(true);
                    holder.SubServiceValue.setText("");
                    holder.SubServiceValue.setHint("");
                } else if(dashBoard.getValue().equalsIgnoreCase("0")) {
                    holder.SubServiceValue.setText("");
                    holder.SubServiceValue.setHint("");
                    holder.SubServiceValue.setEnabled(false);//delte AFTER CHEKING
                    holder.SubServiceValue.setBackgroundColor(Color.parseColor("#dddddf"));
                }
           /* if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList10())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList11())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList12())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList13())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList14())) {
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
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList15())) {
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

*/



                holder.SubServiceValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence Value, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        subServiceArrayList.get(position).setValue(String.valueOf(editable));
                       // CalltheMethodBody(subServiceArrayList);
                    }
                });


            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

   /* private void CalltheMethodBody(ArrayList<SubService> subServiceArrayList) {
        try {
            if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
                this.subServiceArrayList = subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
                subServiceArrayList2 = subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
                subServiceArrayList3 = subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
                subServiceArrayList4 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
                subServiceArrayList5 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
                subServiceArrayList6 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
                subServiceArrayList7 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
                subServiceArrayList8 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
                subServiceArrayList9 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList10())) {
                subServiceArrayList10 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList11())) {
                subServiceArrayList11 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList12())) {
                subServiceArrayList12 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList13())) {
                subServiceArrayList13 = subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList14())) {
                subServiceArrayList14 =subServiceArrayList;
            } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList15())) {
                subServiceArrayList15 =subServiceArrayList;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    @Override
    public int getItemCount() {
     /* int Size = 0;
           *//* if (subServiceHeaderArrayList != null) {
                return subServiceHeaderArrayList.size();
            } else {
                return subServiceArrayList.size();
            }*//*
        if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
            Size= subServiceArrayList.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
            Size= subServiceArrayList2.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
            Size= subServiceArrayList3.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
            Size=  subServiceArrayList4.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
            Size= subServiceArrayList5.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
            Size= subServiceArrayList6.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
            Size=  subServiceArrayList7.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
            Size=  subServiceArrayList8.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
            Size=  subServiceArrayList9.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList10())) {
            Size= subServiceArrayList10.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList11())) {
            Size= subServiceArrayList11.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList12())) {
            Size=  subServiceArrayList12.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList13())) {
            Size=  subServiceArrayList13.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList14())) {
            Size=  subServiceArrayList14.size();
        } else if (serviceName.equalsIgnoreCase(ProjectMethods.getServiceList15())) {
            Size= subServiceArrayList15.size();
        }

*/
        return subServiceArrayList.size();
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

        public ItemViewHolder(View view1) {
            super(view1);
            SubServiceValue = (EditText)view1.findViewById(R.id.SubServiceValue);
            LinerLayout = (LinearLayout) view1.findViewById(R.id.LinerLayout);
            HeaderLinerLayout = (LinearLayout) view1.findViewById(R.id.HeaderLinerLayout);
            SubServiceValue.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition(), subServiceArrayList.get(getPosition()).getValue());
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
