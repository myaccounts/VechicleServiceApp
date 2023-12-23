package com.myaccounts.vechicleserviceapp.retrofit;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myaccounts.vechicleserviceapp.R;

import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

    Context applicationContext;
    List<List<Li>> data;
    HorizontalAdapter adapter;

    public VerticalAdapter(Context applicationContext, List<List<Li>> data) {

        this.applicationContext = applicationContext;
        this.data = data;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        List<Li> mydata = data.get(position);

        Log.e("onBind", "onBindViewHolder: " + mydata.toString() );
        for(int i=0;i<mydata.size();i++)

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false));

        adapter = new HorizontalAdapter(applicationContext,mydata);
        holder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.vertical_rec);

        }
    }
}