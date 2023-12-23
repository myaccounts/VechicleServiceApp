package com.myaccounts.vechicleserviceapp.Reports;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.retrofit.ApiService;
import com.myaccounts.vechicleserviceapp.retrofit.RequestModel;
import com.myaccounts.vechicleserviceapp.retrofit.RetroClient;
import com.myaccounts.vechicleserviceapp.retrofit.TatResponseModel;
import com.myaccounts.vechicleserviceapp.retrofit.VerticalAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TATReport extends Fragment {

    private static final String TAG = "Tat_report";
    private Context context;
    View view;
    RecyclerView TAT_RecyclerView;
    Spinner TimeDurationSpner;
    ImageView refresh_tat;
    TextView fromdate,todate;
    List<TatResponseModel> data;
    ProgressDialog progressDialog;

    public static TATReport newInstance() {
        Bundle args = new Bundle();
        TATReport fragment = new TATReport();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tatreport_layout, container, false);
        // getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>TAT Report</font>"));
        setHasOptionsMenu(true);
        context = getActivity();

        data = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait.");
        progressDialog.setCancelable(false);

        fromdate = view.findViewById(R.id.FromDate_tat);
        todate = view.findViewById(R.id.ToDate_tat);
        refresh_tat = view.findViewById(R.id.refresh_tat);
        TimeDurationSpner = view.findViewById(R.id.TimeDurationSpner);
        TAT_RecyclerView = view.findViewById(R.id.TAT_RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        TAT_RecyclerView.setLayoutManager(linearLayoutManager);

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                                String dateString = dateFormat.format(calendar.getTime());

                                fromdate.setText(dateString);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                                String dateString = dateFormat.format(calendar.getTime());

                                todate.setText(dateString);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        refresh_tat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fromdate.getText().toString().equals("")||todate.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(),"Please Select From and To Date.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.show();
                    String spinnerText = TimeDurationSpner.getSelectedItem().toString();
                    GetJobCardHistoryList(spinnerText,fromdate.getText().toString(),todate.getText().toString());
                }
            }
        });

        return view;
    }

    private void GetJobCardHistoryList(String spinnerText,String fromdate, String todate) {

        ApiService api = RetroClient.getApiService();

        RequestModel requestModel = new RequestModel(fromdate,todate,spinnerText);

        Call<List<TatResponseModel>> call = api.getList(requestModel);

        call.enqueue(new Callback<List<TatResponseModel>>() {
            @Override
            public void onResponse(Call<List<TatResponseModel>> call, Response<List<TatResponseModel>> response) {

                data.clear();

                if (response.isSuccessful())
                {
                    assert response.body() != null;
                    Log.e(TAG, "onResponse: " + response.body().toString() );
                    data = response.body();

                }

                VerticalAdapter verticalAdapter = new VerticalAdapter(context,data.get(0).li);
                TAT_RecyclerView.setAdapter(verticalAdapter);
                progressDialog.cancel();

            }

            @Override
            public void onFailure(Call<List<TatResponseModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.menu_reports, menu);
        }
        menu.findItem(R.id.datetv).setTitle("");
        menu.findItem(R.id.datebtn).setIcon(0);
        menu.findItem(R.id.datebtn).setEnabled(false);
        menu.findItem(R.id.datetv).setEnabled(false);
//        menu.findItem(R.id.alltv).setTitle("");
        menu.findItem(R.id.datetv).setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.refreshbtn) {
            // GetJobCardHistoryList();

                /*try {
                    if(Validate())
                    {

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
