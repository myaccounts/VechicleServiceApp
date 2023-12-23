package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.ServiceList;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class GeneralServiceEstimationAdapter extends RecyclerView.Adapter<GeneralServiceEstimationAdapter.ItemHolder> {
    public static ArrayList<ServiceList> generailServiceDetailsArrayList;
    private Context context;
    private OnItemClickListener mItemClickListener;
    float value=0.0f;
    public GeneralServiceEstimationAdapter(Context context, int generalservice_row_item, ArrayList<ServiceList> generailServiceDetailsArrayList) {
        this.context=context;
        this.generailServiceDetailsArrayList=generailServiceDetailsArrayList;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View view1 = LayoutInflater.from(context).inflate(R.layout.generalservice_row_item, parent, false);
        return new ItemHolder(view1);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        ServiceList serviceList = generailServiceDetailsArrayList.get(position);

        serviceList.setRowNo(position + 1);
        holder.mSnotv.setText(String.valueOf(serviceList.getRowNo()));
//        holder.mSerDescptv.setText(String.valueOf(serviceList.getServiceName()));
//        holder.mSerDescptv.setTag(String.valueOf(serviceList.getServiceId()));
        holder.mSerDescptv.setText(String.valueOf(serviceList.getSubServiceName()));
        holder.mSerDescptv.setTag(String.valueOf(serviceList.getSubServiceId()));
        holder.mSerQtytv.setText(String.valueOf(serviceList.getmQty()));
        holder.mSerRateTv.setText(String.valueOf(serviceList.getServiceCharge()));
        holder.mSerTotalValtv.setText(String.valueOf(String.format("%.2f",CalculateToTalValue(serviceList.getmQty(),serviceList.getServiceCharge()))));
        holder.mSerRemarkstv.setText(String.valueOf(serviceList.getmRemarks()));
        holder.mSerQtytv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String  mqty= String.valueOf((s));
                String  mservicecharge= (generailServiceDetailsArrayList.get(position).getServiceCharge());
                value=CalculateToTalValue(mqty,mservicecharge);
                holder.mSerTotalValtv.setText((String.valueOf(value)));
                generailServiceDetailsArrayList.get(position).setmTotalVal(holder.mSerTotalValtv.getText().toString().trim());
                generailServiceDetailsArrayList.get(position).setmQty(holder.mSerQtytv.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        holder.mSerRemarkstv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                generailServiceDetailsArrayList.get(position).setmRemarks(holder.mSerRemarkstv.getText().toString().trim());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }

    private float CalculateToTalValue(String getmQty, String getmMrp) {
        float finalValue=0.0f;
        try {
            float mrp = 0.0f, Qty = 0.0f;
            mrp = Float.valueOf(Float.parseFloat(getmMrp));
            Qty = Float.valueOf(Float.parseFloat(getmQty));
            finalValue = mrp * Qty;
        }catch (Exception e){
            e.printStackTrace();
        }
        return finalValue;
    }

    @Override
    public int getItemCount() {
        return generailServiceDetailsArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mSnotv, mSerDescptv,mSerQtytv,mSerRateTv,mSerTotalValtv,mSerRemarkstv;
        private ImageButton IdDeleteIconImg;
        public ItemHolder(View itemView) {
            super(itemView);
            mSnotv = (TextView) itemView.findViewById(R.id.mSnotv);
            mSerDescptv = (TextView) itemView.findViewById(R.id.mSerDescptv);
            mSerQtytv = (TextView) itemView.findViewById(R.id.mSerQtytv);
            mSerRateTv = (TextView) itemView.findViewById(R.id.mSerRateTv);
            mSerTotalValtv = (TextView) itemView.findViewById(R.id.mSerTotalValtv);
            mSerRemarkstv = (TextView) itemView.findViewById(R.id.mSerRemarkstv);
            IdDeleteIconImg = (ImageButton) itemView.findViewById(R.id.IdDeleteIconImg);
            IdDeleteIconImg.setOnClickListener(this);
            mSerQtytv.setOnClickListener(this);
            mSerRemarkstv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v,getPosition(),generailServiceDetailsArrayList.get(getPosition()).getServiceName());
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
