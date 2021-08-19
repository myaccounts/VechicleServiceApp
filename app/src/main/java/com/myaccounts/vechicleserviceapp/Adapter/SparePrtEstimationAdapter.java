package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Pojo.SparePartEstDetails;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class SparePrtEstimationAdapter extends RecyclerView.Adapter<SparePrtEstimationAdapter.ItemHolder> {
    private Context context;
    public static ArrayList<SparePartEstDetails> sparePartEstDetailsArrayList;
    private OnItemClickListener onItemClickListener;
    public SparePrtEstimationAdapter(Context context, int sparepart_estimation_row_item, ArrayList<SparePartEstDetails> sparePartEstDetailsArrayList) {
        this.context = context;
        this.sparePartEstDetailsArrayList = sparePartEstDetailsArrayList;
    }

    @Override
    public SparePrtEstimationAdapter.ItemHolder onCreateViewHolder(ViewGroup holder, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.sparepart_estimation_row_item, holder, false);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(SparePrtEstimationAdapter.ItemHolder holder, int position) {
        SparePartEstDetails sparePartEstDetails = sparePartEstDetailsArrayList.get(position);
        sparePartEstDetails.setRowNo(position + 1);
        holder.SnoTv.setText(String.valueOf(sparePartEstDetails.getRowNo()));
        holder.SparePartNameTv.setText(String.valueOf(sparePartEstDetails.getmSparePartName()));
        holder.SparePartUomTv.setText(String.valueOf(sparePartEstDetails.getmUOM()));
        holder.SparePartQtyTv.setText(String.valueOf(sparePartEstDetails.getmQty()));
        holder.SparePartMrpTv.setText(String.valueOf(sparePartEstDetails.getmMrp()));
        holder.TotalValTv.setText(String.valueOf(sparePartEstDetails.getmTotalValue()));

    }

    @Override
    public int getItemCount() {
        return sparePartEstDetailsArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView SnoTv, SparePartNameTv, SparePartUomTv, SparePartQtyTv, SparePartMrpTv, TotalValTv;
        private ImageButton IdEditIconImg, IdDeleteIconImg;

        public ItemHolder(View itemView) {
            super(itemView);
            SnoTv = (TextView) itemView.findViewById(R.id.SnoTv);
            SparePartNameTv = (TextView) itemView.findViewById(R.id.SparePartNameTv);
            SparePartUomTv = (TextView) itemView.findViewById(R.id.SparePartUomTv);
            SparePartQtyTv = (TextView) itemView.findViewById(R.id.SparePartQtyTv);
            SparePartMrpTv = (TextView) itemView.findViewById(R.id.SparePartMrpTv);
            TotalValTv = (TextView) itemView.findViewById(R.id.TotalValTv);
            IdEditIconImg = (ImageButton) itemView.findViewById(R.id.IdEditIconImg);
            IdDeleteIconImg = (ImageButton) itemView.findViewById(R.id.IdDeleteIconImg);
            IdEditIconImg.setOnClickListener(this);
            IdDeleteIconImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v,getPosition(),sparePartEstDetailsArrayList.get(getPosition()).getmSparePartName());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void SetOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName);
    }
}
