package com.myaccounts.vechicleserviceapp.Reports;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Adapter.JobCardHistoryAdpater;
import com.myaccounts.vechicleserviceapp.Adapter.TATReportAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TATReport extends Fragment {
    private Context context;
    private LayoutInflater inflater;
    View view;
    @BindView(R.id.IdTatRecyclerview)
    RecyclerView mTatRecyclerview;
    @BindView(R.id.IdFrmDateTv)
    TextView mFrmDateTv;
    @BindView(R.id.IdToDateTv)
    TextView mToDateTv;
    @BindView(R.id.TimeDurationSpner)
    Spinner TimeDurationSpner;
    private TATReportAdapter tatReportAdapter;
    private int month, day, year;
    private String requestName;
    private ArrayList<JobCardHistory> jobCardHistoryArrayList;
    private String CustFromDate, CustToDate;
    private String selectDuration, taskToId;
    private String finalselectedServiceList = "";
    CheckBox checkBox;
    private ProgressDialog pDialog;

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
        ButterKnife.bind(this, view);
        context = getActivity();
        mFrmDateTv.setText(ProjectMethods.GetCurrentDate());
        mToDateTv.setText(ProjectMethods.GetCurrentDate());
        jobCardHistoryArrayList = new ArrayList<>();
        // GetJobCardHistoryList();
        TimeDurationSpner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDuration = TimeDurationSpner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        mFrmDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int month= monthOfYear+1;
                                String fm=""+month;
                                String fd=""+dayOfMonth;
                                if(month<10){
                                    fm ="0"+month;
                                }
                                if (dayOfMonth<10){
                                    fd="0"+dayOfMonth;
                                }
                                String date= ""+fd+"-"+fm+"-"+year;
                                mFrmDateTv.setText(date);
                               // mFrmDateTv.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                              //  mFrmDateTv.setText(dayOfMonth + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        mToDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int month= monthOfYear+1;
                                String fm=""+month;
                                String fd=""+dayOfMonth;
                                if(month<10){
                                    fm ="0"+month;
                                }
                                if (dayOfMonth<10){
                                    fd="0"+dayOfMonth;
                                }
                                String date= ""+fd+"-"+fm+"-"+year;
                                mToDateTv.setText(date);
                               // mToDateTv.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                               // mToDateTv.setText(dayOfMonth + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        return view;
    }

    private void filter(String text) {
        ArrayList<JobCardHistory> countryLists = new ArrayList<>();
        for (JobCardHistory c : jobCardHistoryArrayList) {
            if (c.getmJobCardNo().toLowerCase().contains(text) || c.getmVehicleNo().toLowerCase().contains(text) || c.getMspareParts().toLowerCase().contains(text) || c.getMserviceCharge().toLowerCase().contains(text)) {
                countryLists.add(c);
            }

        }
        tatReportAdapter.setFilter(countryLists);
    }


    private void GetJobCardHistoryList() {
        try {
            String SelectedFromDate = "", SelectedToDate = "";
            String frmDate = mFrmDateTv.getText().toString();
            String toDate = mToDateTv.getText().toString();
            String Duration = "";

                if (selectDuration.equalsIgnoreCase("Time Duration")) {
                    Toast.makeText(context, "Please Select Duration", Toast.LENGTH_SHORT).show();
                }else {

                    if (frmDate.length() > 0) {
                        SelectedFromDate = frmDate;
                    } else {
                        SelectedFromDate = "";
                    }
                    if (toDate.length() > 0) {
                        SelectedToDate = toDate;
                    } else {
                        SelectedToDate = "";
                    }
                    pDialog = new ProgressDialog(getActivity(),
                            R.style.AppTheme_Dark_Dialog);
                    pDialog.setIndeterminate(true);
                    pDialog.setMessage("Please Wait..");
                    pDialog.show();
                    if (AppUtil.isNetworkAvailable(getActivity())) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.accumulate("FromDate", SelectedFromDate);
                            jsonObject.accumulate("ToDate", SelectedToDate);
                            jsonObject.accumulate("Duration", selectDuration);
                            BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                            requestName = "Get_TAT_Report";
                            serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerUserImpl());
                            serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.TATReport, jsonObject, Request.Priority.HIGH);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class OnServiceCallCompleteListenerUserImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("Get_TAT_Report")) {
                try {
                    pDialog.dismiss();
                    handeServiceMasterDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error)
        {
            pDialog.dismiss();

        }
    }


    private void handeServiceMasterDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                jobCardHistoryArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            JobCardHistory serviceMaster = new JobCardHistory();
                            serviceMaster.setmJobCardNo(object.getString("JobCardNo"));
                            serviceMaster.setmDate(object.getString("mDate"));
                            serviceMaster.setmVehicleNo(object.getString("VehicleNo"));
                            serviceMaster.setmTimeIn(object.getString("TimeIn"));
                            serviceMaster.setmTineOut(object.getString("TimeOut"));
                            serviceMaster.setMspareParts(object.getString("spareParts"));
                            serviceMaster.setMserviceCharge(object.getString("serviceCharge"));
                            serviceMaster.setStatus(object.getString("Status"));
                            serviceMaster.setRows(object.getString("Sno"));
                            jobCardHistoryArrayList.add(serviceMaster);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            // mswiperefreshlayout.setRefreshing(false);
            tatReportAdapter = new TATReportAdapter(getActivity(), R.layout.jobcard_history_row_item, jobCardHistoryArrayList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
            mTatRecyclerview.setLayoutManager(gridLayoutManager1);
            mTatRecyclerview.setItemAnimator(new DefaultItemAnimator());
            mTatRecyclerview.setAdapter(tatReportAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_reports, menu);
        menu.findItem(R.id.datetv).setTitle("");
        menu.findItem(R.id.datebtn).setIcon(0);
        menu.findItem(R.id.datebtn).setEnabled(false);
        menu.findItem(R.id.datetv).setEnabled(false);
//        menu.findItem(R.id.alltv).setTitle("");
        menu.findItem(R.id.datetv).setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshbtn:

                try {
                    if(Validate()) {
                        GetJobCardHistoryList();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean Validate() {
        boolean result = true;
        CustFromDate = mFrmDateTv.getText().toString().trim();
        CustToDate = mToDateTv.getText().toString().trim();
        int fromDate = ProjectMethods.GetDateToInt(CustFromDate);
        int toDate = ProjectMethods.GetDateToInt(CustToDate);
        if (toDate != 0) {
            if (fromDate > toDate) {
                Toast toast1 = Toast.makeText(context, " To Date Should Be Greater Than From Date ", Toast.LENGTH_SHORT);
                View view1 = toast1.getView();
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                TextView v1 = (TextView) toast1.getView().findViewById(android.R.id.message);
                v1.setTextColor(Color.WHITE);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                result = false;
            }
        }
        return result;
    }
}
