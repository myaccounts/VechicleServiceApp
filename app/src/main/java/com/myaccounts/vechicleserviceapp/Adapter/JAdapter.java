package com.myaccounts.vechicleserviceapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class JAdapter extends RecyclerView.Adapter<JAdapter.ViewHolder>{
    private MyListData[] listdata;
    SharedPreferences.Editor editor ;
    SharedPreferences sharedPreferences ;
    final ArrayList<String> arrPackage;
    public static String remarksStr;
    SessionManager sessionManager;
    HashSet<String> mSet = new HashSet<>();
    Context ctx;
    // RecyclerView recyclerView;
    public JAdapter(MyListData[] listdata, Context ctx) {
        this.listdata = listdata;
        this.ctx=ctx;
        sharedPreferences = ctx.getSharedPreferences("USER",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sessionManager = new SessionManager(ctx);
        arrPackage = new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.checkbox_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MyListData myListData = listdata[position];
        holder.textView.setText(listdata[position].getDescription());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ANUSHA ","+++ "+position);
                Log.d("ANUSHA ","+++ "+listdata[position].getDescription());
                mSet.add(listdata[position].getDescription());
                String nameData = listdata[position].getDescription();
                arrPackage.add(nameData);
                Gson gson = new Gson();
                String json = gson.toJson(arrPackage);
                editor.putString("Set",json );
                editor.commit();
//                remarksStr=remarksStr+listdata[position].getDescription();
//                sessionManager.storeRemarksCheckListDetails(remarksStr);

            }
        });


    }

    private void saveStringSet(Context ctx, HashSet<String> mSet) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet("JESUS", mSet);
        editor.apply();
    }
    public static Set<String> getSavedStringSets(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getStringSet("JESUS", null);
    }



    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (CheckBox) itemView.findViewById(R.id.rowCheckBox);
            this.textView = (TextView) itemView.findViewById(R.id.rowTextView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
