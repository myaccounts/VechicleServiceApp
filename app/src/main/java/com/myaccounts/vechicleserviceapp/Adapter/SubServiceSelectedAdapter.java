package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.myaccounts.vechicleserviceapp.Pojo.SubService;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class SubServiceSelectedAdapter extends RecyclerView.Adapter<SubServiceSelectedAdapter.ItemViewHolder> {
    private Context context;
    public ArrayList<SubService> subServiceArrayList;

    private LayoutInflater inflater;
    private OnItemClickListener mItemClickListener;

    public SubServiceSelectedAdapter(Context context, int selectedservice_row_item, ArrayList<SubService> subServiceArrayList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.subServiceArrayList = subServiceArrayList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = inflater.inflate(R.layout.selectedsubservice_row_item, parent, false);
        return new ItemViewHolder(view1);


    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        try {
            if (subServiceArrayList != null) {

                final SubService dashBoard = subServiceArrayList.get(position);
                if (position == 0) {
                    if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                        holder.HeaderLinerLayout.setVisibility(View.GONE);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.HeaderLinerLayout.setLayoutParams(layoutParams);
                } else if (dashBoard.getValue().startsWith("SSID")) {
                    if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                        holder.HeaderLinerLayout.setVisibility(View.GONE);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.HeaderLinerLayout.setLayoutParams(layoutParams);
                      /*  subServiceArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,getItemCount());*/
                    //holder.HeaderLinerLayout.setVisibility(View.GONE);

                    // holder.HeaderLinerLayout.setGravity(View.FOCUS_LEFT);

                } else if (dashBoard.getValue().startsWith("SRT")) {

                    if (holder.HeaderLinerLayout.getVisibility() != View.GONE)
                        holder.HeaderLinerLayout.setVisibility(View.GONE);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.HeaderLinerLayout.setLayoutParams(layoutParams);
                      /*  subServiceArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,getItemCount());*/
                    //  holder.HeaderLinerLayout.setVisibility(View.GONE);
                    // holder.HeaderLinerLayout.setGravity(View.FOCUS_LEFT);
                } else {
                    holder.HeaderLinerLayout.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.HeaderLinerLayout.getLayoutParams();
                    holder.HeaderLinerLayout.setGravity(View.FOCUS_LEFT);
                    holder.HeaderLinerLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);
                    layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;


                    holder.SubServiceValue.setText(dashBoard.getValue());
                }
                // holder.SubServiceValue.setText(dashBoard.getValue());
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
                       // CalltheMethodBody();
                    }
                });

              /*  if(serviceHeaderArrayList.size()>0){
                    for(int i=0;i<serviceHeaderArrayList.size();i++){
                        headerValue= serviceHeaderArrayList.get(i).getValue();
                        if(holder.SubServiceValue.getText().toString().equalsIgnoreCase(headerValue)){
                            holder.SubServiceValue.setVisibility(View.GONE);
                        }else{
                            holder.SubServiceValue.setVisibility(View.VISIBLE);
                        }
                    }
                }*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return subServiceArrayList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setOnItemClickListner(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText SubServiceValue;
        private LinearLayout LinerLayout, HeaderLinerLayout;

        public ItemViewHolder(View view1) {
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
