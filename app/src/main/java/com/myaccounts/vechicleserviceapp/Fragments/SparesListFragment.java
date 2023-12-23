package com.myaccounts.vechicleserviceapp.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.myaccounts.vechicleserviceapp.network.InfDbSpecs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SparesListFragment extends Fragment {
    private View view;
    private Context context;
    @BindView(R.id.gridview)
    GridView gridView;
    private String jobcard,sparesList;
    private ArrayList<String> printerList;
    public static SparesListFragment newInstance() {
        Bundle args = new Bundle();
        SparesListFragment fragment = new SparesListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.services_popup, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Spares List</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
        printerList = new ArrayList<String>();
        printerList.add("Jobcard Id");
        printerList.add("Spares List");
        File dbFile = context.getDatabasePath(DatabaseHelper.DATABASE_NAME);
        try {
            DatabaseHelper db = new DatabaseHelper(context);
            Cursor cursor = db.getSparesDetails();
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        jobcard = cursor.getString(cursor.getColumnIndex(InfDbSpecs.JobcardNo));
                        sparesList = cursor.getString(cursor.getColumnIndex(InfDbSpecs.SpareList));
                        printerList.add(jobcard);
                        printerList.add(sparesList);
                    }
                }
            }
        }
        catch(Exception e){
        }

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(getActivity().getExternalCacheDir(), "JGOWHEELS_SPARES_FILE.txt");//new File(root, "JGOWHEELS_SPARES_FILE");
            FileWriter writer = new FileWriter(gpxfile);
            for(int i=0;i<printerList.size();i++)
            writer.append(printerList.get(i).toString()+"\n");
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, printerList);
        gridView.setAdapter(adapter);
        return view;
    }

}