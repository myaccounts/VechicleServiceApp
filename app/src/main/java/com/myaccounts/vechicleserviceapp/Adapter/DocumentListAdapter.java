package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.myaccounts.vechicleserviceapp.Pojo.DocumentTypes;
import com.myaccounts.vechicleserviceapp.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ItemHolder> {

    public static ArrayList<DocumentTypes> salesmenlist;
    Context context;
    public  static String ListId,EdtStrings;
    private OnItemClickListener mItemClickListener;

    public DocumentListAdapter(Context context, int textViewResourceId, ArrayList<DocumentTypes> salesmenlist,  String editTextValue,String EdtStrings) {
        this.context=context;
        this.salesmenlist=salesmenlist;
        this.ListId=editTextValue;
        this.EdtStrings=EdtStrings;
    }




    public void SetOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }


    public void setFilter(ArrayList<DocumentTypes> countryLists) {
        salesmenlist = new ArrayList<>();
        salesmenlist.addAll(countryLists);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName);
    }



    @Override
    public DocumentListAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.document_row_list, parent, false);
        return new ItemHolder(view1, this);
    }

    @Override
    public void onBindViewHolder(final DocumentListAdapter.ItemHolder holder, final int position) {
        try {
            final DocumentTypes salesMen = salesmenlist.get(position);
            holder.ServiceNameCheckBox.setText(salesMen.getName());
            holder.ServiceNameCheckBox.setTag(salesMen.getId());
            holder.ServiceShortNameCheckBox.setText(salesMen.getShortName());
            holder.entryFieldEdt.setText(salesMen.getEdtValue());


            holder.ServiceNameCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //checkCheckBox(position, !mSelectedItemsIds.get(position));
                    try {
                        if (salesmenlist.get(position).getSelected()) {
                            salesmenlist.get(position).setSelected(false);
                        } else {
                            salesmenlist.get(position).setSelected(true);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });
            holder.entryFieldEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int pos, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int pos, int i1, int i2) {
                    try {
                        salesmenlist.get(position).setEdtValue(holder.entryFieldEdt.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            List<String> ListData = Arrays.asList(ListId.split(","));
            List<String> Strings = Arrays.asList(EdtStrings.split(","));
           // String list = Arrays.toString(ListData.toArray()).split("(","");
            for(int i=0;i<ListData.size();i++){
                if (holder.ServiceNameCheckBox.getTag().toString().equalsIgnoreCase(ListData.get(i).toString())) {
                    holder.ServiceNameCheckBox.setChecked(true);
                    salesmenlist.get(position).setSelected(true);
                    if(Strings.size()>0){
                        holder.entryFieldEdt.setText(Strings.get(i).toString());
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void removeSelection() {
        salesmenlist = new ArrayList<>();
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return salesmenlist.size();
    }




    public class ItemHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public CheckBox ServiceNameCheckBox,ServiceShortNameCheckBox;
        private EditText entryFieldEdt;
        public ItemHolder(View itemView, DocumentListAdapter documentListAdapter) {
            super(itemView);
            ServiceNameCheckBox= (CheckBox) itemView.findViewById(R.id.ServiceNameCheckBox);
            ServiceShortNameCheckBox = (CheckBox) itemView.findViewById(R.id.ServiceShortNameCheckBox);
            entryFieldEdt= (EditText) itemView.findViewById(R.id.entryFieldEdt);
            entryFieldEdt.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v,getPosition(),salesmenlist.get(getPosition()).getName());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
