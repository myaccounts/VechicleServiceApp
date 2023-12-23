package com.myaccounts.vechicleserviceapp.Reports;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.myaccounts.vechicleserviceapp.Activity.CustomerSelectionActivity;
import com.myaccounts.vechicleserviceapp.Adapter.EstimationAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.EstimationHistory;
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

public class EstimationReport extends Fragment implements View.OnClickListener {
    private Context context;
    private LayoutInflater inflater;
    View view;
    @BindView(R.id.IdEstimationHistoryRecyclerview)
    RecyclerView mEstimationHistoryRecyclerview;
    @BindView(R.id.IdFrmDateTv)
    TextView mFrmDateTv;
    @BindView(R.id.IdToDateTv)
    TextView mToDateTv;
    @BindView(R.id.inputJobcardSearchEdt)
    EditText inputJobcardSearchEdt;
    @BindView(R.id.DateSelectionLinearLayout)
    LinearLayout DateSelectionLinearLayout;
    private EstimationAdapter estimationAdapter;
    private int month, day, year;
    private String requestName;
    private ArrayList<EstimationHistory> estimationHistoryArrayList;
    private ArrayList<UserList> usersList = new ArrayList<>();
    private String selectUser, taskToId;
    private String finalselectedServiceList = "";
    CheckBox checkBox;
    private ProgressDialog pDialog;
    private String CustFromDate, CustToDate;
    public static EstimationReport newInstance() {
        Bundle args = new Bundle();
        EstimationReport fragment = new EstimationReport();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.estimationreport_layout, container, false);
       // getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Estimation Report</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
        mFrmDateTv.setText(ProjectMethods.GetCurrentDate());
        mToDateTv.setText(ProjectMethods.GetCurrentDate());
        estimationHistoryArrayList = new ArrayList<>();
        inputJobcardSearchEdt.setOnClickListener(this);
         GetJobCardHistoryList();

        inputJobcardSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (estimationHistoryArrayList != null) {


                        filter(s.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                             //   mFrmDateTv.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
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
        ArrayList<EstimationHistory> countryLists = new ArrayList<>();
        for (EstimationHistory c : estimationHistoryArrayList) {
            if (c.getAmount().toLowerCase().contains(text) || c.getCustomerName().toLowerCase().contains(text) || c.getTranNo().toLowerCase().contains(text) || c.getMobile().toLowerCase().contains(text)) {
                countryLists.add(c);
            }

        }
        estimationAdapter.setFilter(countryLists);
    }


    private void GetJobCardHistoryList() {
        try {
            String SelectedFromDate = "", SelectedToDate = "";
            String frmDate = mFrmDateTv.getText().toString();
            String toDate = mToDateTv.getText().toString();
            String VehicleNo = "";
            try {
                if (inputJobcardSearchEdt.getTag().toString().equalsIgnoreCase("")) {
                    VehicleNo = "";
                } else {
                    VehicleNo = inputJobcardSearchEdt.getTag().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    jsonObject.accumulate("CustomerId", VehicleNo);
                    jsonObject.accumulate("FromDate", SelectedFromDate);
                    jsonObject.accumulate("ToDate", SelectedToDate);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetEstimationReport";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerUserImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEstimationReport, jsonObject, Request.Priority.HIGH);
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
                case R.id.inputJobcardSearchEdt:
                    Intent customerselection = new Intent(getActivity(), CustomerSelectionActivity.class);
                    startActivityForResult(customerselection, 103);
                    break;
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
            if (requestName.equalsIgnoreCase("GetEstimationReport")) {
                try {
                    pDialog.dismiss();
                    handeServiceMasterDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            pDialog.dismiss();

        }
    }


    private void handeServiceMasterDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                estimationHistoryArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        } else {
                            EstimationHistory serviceMaster = new EstimationHistory();
                            serviceMaster.setJobCardNo(object.getString("JobCardId"));
                            serviceMaster.setTransDate(object.getString("TransDate"));
                            serviceMaster.setTranNo(object.getString("TranNo"));
                            serviceMaster.setCustomerName(object.getString("CustomerName"));
                            serviceMaster.setMobile(object.getString("MobileNo"));
                            serviceMaster.setSpareParts(object.getString("SparePart"));
                            serviceMaster.setServices(object.getString("Service"));
                            serviceMaster.setAmount(object.getString("TotalAmount"));
                            serviceMaster.setRows(object.getInt("SlNo"));
                            estimationHistoryArrayList.add(serviceMaster);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            estimationAdapter = new EstimationAdapter(getActivity(), R.layout.estimation_row_item, estimationHistoryArrayList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 1);
            mEstimationHistoryRecyclerview.setLayoutManager(gridLayoutManager1);
            mEstimationHistoryRecyclerview.setItemAnimator(new DefaultItemAnimator());
            mEstimationHistoryRecyclerview.setAdapter(estimationAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_reports, menu);
        menu.findItem(R.id.datetv).setTitle("");
        menu.findItem(R.id.datebtn).setIcon(0);
        menu.findItem(R.id.datebtn).setEnabled(false);
        menu.findItem(R.id.datetv).setEnabled(false);
//        menu.findItem(R.id.alltv).setTitle("");
        menu.findItem(R.id.datetv).setEnabled(false);
        super.onCreateOptionsMenu(menu, inflater);
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
                    if (estimationHistoryArrayList.size() > 0) {
                        CustomDailog("Estimation Report", "Do You Want to Delete Estimation Details?", 38, "Delete");
                    } else {
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
                estimationHistoryArrayList.clear();
                estimationAdapter.notifyDataSetChanged();
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
        try {
            inputJobcardSearchEdt.setText("");
            DateSelectionLinearLayout.setVisibility(View.GONE);
            estimationHistoryArrayList.clear();
            estimationAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 103) {
            if (data != null && resultCode == RESULT_OK) {
                CustomerInformation(data);
            }
        } else if (requestCode == 38 && data != null && resultCode == RESULT_OK) {
            RemoveData();
        }
    }

    private void CustomerInformation(Intent data) {
        try {
            String custid = data.getStringExtra("CustomerId");
            String custname = data.getStringExtra("CustomerName");
            String vehcileNumber = data.getStringExtra("VehicleNo");
            inputJobcardSearchEdt.setText(custname);
            inputJobcardSearchEdt.setTag(vehcileNumber);
            if (inputJobcardSearchEdt.getText().toString() != "") {

                DateSelectionLinearLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
