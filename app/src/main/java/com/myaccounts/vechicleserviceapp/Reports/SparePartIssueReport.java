package com.myaccounts.vechicleserviceapp.Reports;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.VehicleNoActivity;
import com.myaccounts.vechicleserviceapp.Adapter.SparePartEstAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.VehicleHistoryAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartEst;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
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

import static android.app.Activity.RESULT_OK;

public class SparePartIssueReport extends Fragment implements View.OnClickListener {
    private Context context;
    private LayoutInflater inflater;
    View view;
    @BindView(R.id.IdJobCardHistoryRecyclerview)
    RecyclerView mVehicleHistoryRecyclerview;
    @BindView(R.id.IdFrmDateTv)
    TextView mFrmDateTv;
    @BindView(R.id.IdToDateTv)
    TextView mToDateTv;
    @BindView(R.id.inputJobcardSearchEdt)
    EditText inputJobcardSearchEdt;
    @BindView(R.id.DateSelectionLinearLayout)
    LinearLayout DateSelectionLinearLayout;
    private SparePartEstAdapter sparePartEstAdapter;
    private String CustFromDate, CustToDate;
    private String requestName;
    private ArrayList<SparePartEst> jobCardHistoryArrayList;
    private ArrayList<UserList> usersList = new ArrayList<>();
    private String selectUser, taskToId;
    private String finalselectedServiceList = "";
    CheckBox checkBox;
    private ProgressDialog pDialog;
    private int month, day, year;
    public static SparePartIssueReport newInstance() {
        Bundle args = new Bundle();
        SparePartIssueReport fragment = new SparePartIssueReport();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sparepartestimation_layout, container, false);
        //getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Vehicle History</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
       // inputJobcardSearchEdt.setHint("Vehicle No");
       // inputJobcardSearchEdt.setOnClickListener(this);
        jobCardHistoryArrayList = new ArrayList<>();
        // GetJobCardHistoryList();
        mFrmDateTv.setText(ProjectMethods.GetCurrentDate());
        mToDateTv.setText(ProjectMethods.GetCurrentDate());
     /*   inputJobcardSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (jobCardHistoryArrayList != null) {


                        filter(s.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
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
                                //  mFrmDateTv.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                                //mFrmDateTv.setText(dayOfMonth + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
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
                                //mToDateTv.setText(dayOfMonth + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        return view;
    }

    private void filter(String text) {
        ArrayList<SparePartEst> countryLists = new ArrayList<>();
        for (SparePartEst c : jobCardHistoryArrayList) {
            if (c.getJobCard().toLowerCase().contains(text) || c.getTranNo().toLowerCase().contains(text) || c.getMobileNo().toLowerCase().contains(text) || c.getCustomer().toLowerCase().contains(text)) {
                countryLists.add(c);
            }

        }
        sparePartEstAdapter.setFilter(countryLists);
    }


    private void GetJobCardHistoryList() {
        try {
            String SelectedFromDate = "", SelectedToDate = "";
            String frmDate = mFrmDateTv.getText().toString();
            String toDate = mToDateTv.getText().toString();
            String VehicleNo = inputJobcardSearchEdt.getText().toString();
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
                    jsonObject.accumulate("FromDate",SelectedFromDate);
                    jsonObject.accumulate("ToDate",SelectedToDate);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetIssueReport";
                    serviceCall.setOnServiceCallCompleteListener(new SparePartIssueReport.OnServiceCallCompleteListenerUserImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetIssueReport, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
              /*  case R.id.inputJobcardSearchEdt:
                    Intent intent = new Intent(getActivity(), VehicleNoActivity.class);
                    startActivityForResult(intent, 100);
                    break;*/
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
            if (requestName.equalsIgnoreCase("GetIssueReport")) {
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
//SlNo, TransDate, TranNo, JobCardId, CustomerName,ServiceMan, MobileNo, SparePart, Service, TotalAmount, Result
            if (jsonArray.length() > 0) {
                jobCardHistoryArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        } else {
                            SparePartEst serviceMaster = new SparePartEst();
                            serviceMaster.setTranNo(object.getString("TranNo"));
                            serviceMaster.setJobCard(object.getString("JobCardId"));
                            serviceMaster.setCustomer(object.getString("CustomerName"));
                            serviceMaster.setMobileNo(object.getString("MobileNo"));
                            serviceMaster.setSparePartCount(object.getString("SparePart"));
                            serviceMaster.setServiceCount(object.getString("Service"));
                            serviceMaster.setAmount(object.getString("TotalAmount"));
                            serviceMaster.setRows(object.getString("SlNo"));
                            jobCardHistoryArrayList.add(serviceMaster);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            sparePartEstAdapter = new SparePartEstAdapter(getActivity(), R.layout.sparepartest_history_row_item, jobCardHistoryArrayList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 1);
            mVehicleHistoryRecyclerview.setLayoutManager(gridLayoutManager1);
            mVehicleHistoryRecyclerview.setItemAnimator(new DefaultItemAnimator());
            mVehicleHistoryRecyclerview.setAdapter(sparePartEstAdapter);


        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null && resultCode == RESULT_OK) {
                VehicleNoInformation(data);
            }
        }else if (requestCode == 38 && data != null && resultCode == RESULT_OK) {
            RemoveData();
        }
    }

    private void VehicleNoInformation(Intent data) {
        try {

            String CustName = data.getStringExtra("CustName");
            String CustMobNo = data.getStringExtra("CustMobNo");
            String VehicleNo = data.getStringExtra("VehicleNo");
            inputJobcardSearchEdt.setText(VehicleNo);
            if (inputJobcardSearchEdt.getText().toString() != "") {

                DateSelectionLinearLayout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.datetv).setTitle("");
        menu.findItem(R.id.datebtn).setIcon(0);
        menu.findItem(R.id.datebtn).setEnabled(false);
        menu.findItem(R.id.datetv).setEnabled(false);
//        menu.findItem(R.id.alltv).setTitle("");
        menu.findItem(R.id.datetv).setEnabled(false);
        getActivity().getMenuInflater().inflate(R.menu.menu_reports, menu);
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
            case R.id.deletebtn:
                try {
                    if(jobCardHistoryArrayList.size()>0) {
                        CustomDailog("Vehicle History", "Do You Want to Delete  Vehicle History Details?", 38, "Delete");

                    }else{
                        inputJobcardSearchEdt.setText("");
                        DateSelectionLinearLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
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
                jobCardHistoryArrayList.clear();
                sparePartEstAdapter.notifyDataSetChanged();
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

    private void CustomDailog(String Title, String msg, int value, String btntxt) {
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(getActivity(), CustomDialogClass.class);
            intent.putExtra("msgbtn", btntxt);
            startActivityForResult(intent, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RemoveData() {
        try{
            inputJobcardSearchEdt.setText("");
            DateSelectionLinearLayout.setVisibility(View.VISIBLE);
            jobCardHistoryArrayList.clear();
            sparePartEstAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
