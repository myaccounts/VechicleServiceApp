package com.myaccounts.vechicleserviceapp.retrofit;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.myaccounts.vechicleserviceapp.R;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    Context applicationContext;
    List<Li> mydata;

    public HorizontalAdapter(Context applicationContext, List<Li> mydata) {

        this.applicationContext = applicationContext;
        this.mydata = mydata;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        /*Log.e("bind", "onBindViewHolder: " + mydata.get(position).key.toString() );
        Log.e("bind", "onBindViewHolder: " + mydata.get(position).value );*/
        holder.txt1.setText(mydata.get(position).key);
        holder.txt2.setText(mydata.get(position).value);

    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt1,txt2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);

        }
    }
}
