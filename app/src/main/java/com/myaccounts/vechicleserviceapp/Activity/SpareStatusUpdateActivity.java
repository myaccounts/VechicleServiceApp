package com.myaccounts.vechicleserviceapp.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Adapter.SparePartUpdateAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.JSONVariables;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class SpareStatusUpdateActivity extends AppCompatActivity {
    public static boolean checkBoxStatus=false;
    private TextView txtDocumentList,FreeTotalTv, IdtotalRows, IdTotalQty, IdTotalAmt, AvlQtyTv, UomTv, mrpTv,freemrpTv;
    private String AvlQty = "", UOMName = "", UOMId = "", MRP = "";
    float totalAmount, totalQty,totalFreeAmount;
    String TotalGrossAmt, TotalQtyValue,TotalFreeAmount;
    private ArrayList<SparePartDetails> sparePartDetailsArrayList;
    private ArrayList<SparePartDetails> updtSpareDetailsArrayList;
    private RecyclerView  SparePartrecyclerview;
    private SparePartUpdateAdapter sparePartIssueAdapter;
    private EditText VehicleNoEdt, CustVehicleTypeEdt, CustNameEdt, CustEmailIdEdt, CustMobileNoEdt, QtyEdt, JobCardNoEdt, SparePartEdt, CustDateEdt, CustVehiclemakemodelEdt, CustVehicleModelEdt, CustOdometerReadingEdt, AvgkmsperdayEdt,
            RegNoEdt, MileageEdt, PlaceEdt, CustTimeInEdt, RemarksEdt;
    ImageButton IdMakeImg, IdModelImg, IdVehicleTypeImg, AddImgBtn;
    TextView dateAndTimeTV;
    private int position;
    private String finalSparePartDetailList = null, CloseServiceId = "";
    public static final String TAG = "Spare Parts";
    SessionManager sessionManager;
    Toolbar toolbar;
    Button sparePartsSelectedFragmentBtn;
    Spinner MainSparePartsIssueTypeSpinner;
    String issueTypeString;
    String[] issueType = {"Paid", "Free"};
    int newFinalRows = 0;
    String qty = "1";
    //EditSpares
    TextView custNameTv,custNoTv,vehicleNoTv,jobcardTv;
    private JSONObject jsonObject;
    String modelId = null,vehicleNo=null,custName=null,custNo=null,jobcardId=null,jcdate=null,jctime=null;
    private String requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    SwipeRefreshLayout mswiperefreshlayout;
    Button btn_submit;

    public ArrayList<SparePartDetails> getEdiSpareDetailsArrayList() {
        return sparePartDetailsArrayList;
    }

    public void setEdiSpareDetailsArrayList(ArrayList<SparePartDetails> sparePartDetailsArrayList) {
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: called");
        setContentView(R.layout.activity_spare_status_update);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spare Update");
        dateAndTimeTV = (TextView) findViewById(R.id.dateAndTimeTV);
        Log.e("handeEditServicecalled", "h"+dateAndTimeTV.getText().toString());
        custNameTv = (TextView) findViewById(R.id.custNameTv);
        custNoTv = (TextView) findViewById(R.id.mobileNoTv);
        vehicleNoTv = (TextView) findViewById(R.id.vehicleNoTv);
        jobcardTv=(TextView) findViewById(R.id.jobcardIdTv);
        SparePartrecyclerview = (RecyclerView) findViewById(R.id.ServiceMasterRecyclerview);
        btn_submit = (Button) findViewById(R.id.btn_submit_new);

        updtSpareDetailsArrayList = new ArrayList<>();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selectStatus=false;

                for(int i=0;i<sparePartDetailsArrayList.size();i++){
                    if(sparePartDetailsArrayList.get(i).getSelected()) {
                        selectStatus = true;
                    }
                }
                if(selectStatus) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SpareStatusUpdateActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Updated");
                        builder.setMessage("Spares updated successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SparePartDetails newSpareMasterDetails = new SparePartDetails();
                                for (int i = 0; i < sparePartDetailsArrayList.size(); i++) {

                                    Log.i(":getmSparePartID ", sparePartDetailsArrayList.get(i).getmSparePartID());
                                    Log.i("getSelected: ", String.valueOf(sparePartDetailsArrayList.get(i).getSelected()));

                                    newSpareMasterDetails.setmSparePartID(sparePartDetailsArrayList.get(i).getmSparePartID());

                                    newSpareMasterDetails.setSelected(sparePartDetailsArrayList.get(i).getSelected());

                                    updtSpareDetailsArrayList.add(newSpareMasterDetails);
                                }
                                UpdateSpares();
//                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if user select "No", just cancel this dialog and continue with app
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        Toast.makeText(SpareStatusUpdateActivity.this, "Please Select At least One Spare", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getVehicleDetails();
        try {

            modelId = user.get(SessionManager.KEY_VEHICLE_ID);
            custName = user.get(SessionManager.KEY_CUSTOMER_NAME);
            custNo = user.get(SessionManager.KEY_MOBILE_NO);
            vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);
            jobcardId = user.get(SessionManager.KEY_JOBCARD_ID);
            jcdate=user.get(SessionManager.KEY_JOBCARD_JCDATE);
            jctime=user.get(SessionManager.KEY_JOBCARD_JCTIME);
            dateAndTimeTV.setText(jcdate + ", " + jctime);
            custNameTv.setText(custName);
            custNoTv.setText(custNo);
            vehicleNoTv.setText(vehicleNo);
            jobcardTv.setText(jobcardId);
//            jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);
//
//            Log.d("modeiddd", modelId + "," + jobCardId);
        } catch (NullPointerException e) {
        }


        SparePartEdt = (EditText)findViewById(R.id.IdSparePartEdt);
        AddImgBtn = (ImageButton)findViewById(R.id.AddImgBtn);
        AvlQtyTv = (TextView) findViewById(R.id.AvlQtyTv);
        UomTv = (TextView) findViewById(R.id.UomTv);
        mrpTv = (TextView) findViewById(R.id.mrpTv);
        freemrpTv = (TextView) findViewById(R.id.freemrpTv);
        QtyEdt = (EditText) findViewById(R.id.IdQtyEdt);


        try {

            if (jobcardId.equalsIgnoreCase("empty")) {
//            serviceMasterDetailsArrayList.clear();
            } else {
                if (sparePartDetailsArrayList == null || sparePartDetailsArrayList.size() == 0) {
                    GetEditSparesDetails();
                } else {
                    AddListToGrid();
                }
            }
        } catch (NullPointerException e) {
        }
    }

    private void UpdateSpares() {

        try {


            if (AppUtil.isNetworkAvailable(this)) {
                try {
//                    swipeRefreshLayout.setRefreshing(true);

                    if (sparePartDetailsArrayList.size() > 0) {

                        for (int i = 0; i < sparePartDetailsArrayList.size(); i++) {

                            JSONObject jsonObject = new JSONObject();

                            jsonObject.accumulate(JSONVariables.JobCardId, jobcardId);
                            jsonObject.accumulate(JSONVariables.SpareId, sparePartDetailsArrayList.get(i).getmSparePartID());
                            String status = String.valueOf(sparePartDetailsArrayList.get(i).getSelected());
                            jsonObject.accumulate(JSONVariables.Status,status);
//add date and time pending
                            Log.e("sending",jobcardId);
                            Log.e("sending",sparePartDetailsArrayList.get(i).getmSparePartID());
                            Log.e("sending", status);
                            BackendServiceCall serviceCall = new BackendServiceCall(this, false);
                            requestName = "UpdateSpareStatus";
                            serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                            serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.UpdateSpareStatus, jsonObject, Request.Priority.HIGH);
                            Log.e("UpdateSpareStatusdetail", "" + jsonObject);



                        }
                    } else {
                        Log.e("updtSpareDetsArrList", "" + sparePartDetailsArrayList.size());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //  swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OnServiceCallCompleteListenerImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetSparesAgstJobCard")) {
                try {
                    handeUpdtSparesDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("UpdateSpareStatus")) {
                try {
                    handeUpdtSparesSubmitDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        private void handeUpdtSparesDetails(JSONArray jsonArray) {

            try {

                if (jsonArray.length() > 0) {
                    String result = jsonArray.getJSONObject(0).getString("Result");
                    String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");

                    Log.d("jobbbbbbb", JobCardNumber);
                    if (result != null) {

                        sessionManager.clearSession();

                        Toast.makeText(SpareStatusUpdateActivity.this, result + "" + JobCardNumber, Toast.LENGTH_SHORT).show();
//                    ClearData();
//                    isFirstRun = true;
                    }
//                JobCardNoEdt.setText(JobCardNumber);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onErrorResponse(VolleyError error) {

           // swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void handeUpdtSparesSubmitDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                String result = jsonArray.getJSONObject(0).getString("Result");
//                String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");

//                Log.d("jobbbbbbb", JobCardNumber);
                if (result != null) {

                    sessionManager.clearSession();

                    Toast.makeText(SpareStatusUpdateActivity.this, result , Toast.LENGTH_SHORT).show();
                    MainActivity.comingfrom="3";
                    finish();
                    /*Fragment f = JobCardReportsFragment.newInstance();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, f);
                    ft.commit();*/
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
//                    ClearData();
//                    isFirstRun = true;
                }
//                JobCardNoEdt.setText(JobCardNumber);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddtemToGrid (SparePartDetails SP){
        Log.e("AddtemToGrid called", "AddtemToGrid called");

        try {


            sparePartDetailsArrayList.add(SP);
            SparePartrecyclerview.smoothScrollToPosition(sparePartDetailsArrayList.size() - 1);


            PrepareItemDetailsList();

            sparePartIssueAdapter.notifyDataSetChanged();

            // checkGridValue();
            try {
                InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(AddImgBtn.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddListToGrid () {
        Log.e("AddListToGrid called", "AddListToGrid called");


        sparePartIssueAdapter = new SparePartUpdateAdapter(this, R.layout.sparepart_update_row_item, sparePartDetailsArrayList, "jobcard");
        SparePartrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        SparePartrecyclerview.setItemAnimator(new DefaultItemAnimator());
        SparePartrecyclerview.setHasFixedSize(true);
        SparePartrecyclerview.setAdapter(sparePartIssueAdapter);
        sparePartIssueAdapter.notifyDataSetChanged();
    }




    private void SparPartInformation (Intent data){
        try {
            AvlQty = "";
            UOMId = "";
            UOMName = "";
            String SprptId = data.getStringExtra("SprptId");
            String SprptName = data.getStringExtra("SprptName");
            String ShortBalQty = data.getStringExtra("ShortBalQty");
            String ShortUomId = data.getStringExtra("ShortUomId");
            String ShortUomName = data.getStringExtra("ShortUomName");
            String SparePartMRP = data.getStringExtra("SparePartMRP");
            String SpareFreeRate = data.getStringExtra("SparePartMRP");


            SparePartEdt.setText(SprptName);
            SparePartEdt.setTag(SprptId);
            mrpTv.setText(SparePartMRP);
            freemrpTv.setText(SpareFreeRate);
           /* AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;*/
            //mrpTv
            // QtyEdt.setText(AvlQty);
            AvlQtyTv.setText(ShortBalQty);
            UomTv.setText(ShortUomName);
            Log.e("Uomname",ShortUomName);
            UomTv.setTag(ShortUomId);
            Log.e("uomid",ShortUomId);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void CustomDailog (String Title, String msg,int value, String btntxt,int position){
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(this, CustomDialogClass.class);
            intent.putExtra("msgbtn", btntxt);
            startActivityForResult(intent, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 103) {
            if (data != null && resultCode == RESULT_OK) {
                Log.d("spareee", "sparee");
                SparPartInformation(data);
            }
        }

    }


    private void PrepareItemDetailsList () {
        Log.e("PrepareItem called", "PrepareItemDetailsList called");
        try {
            finalSparePartDetailList = "";
            for (int j = 0; j < sparePartDetailsArrayList.size(); j++) {
                if (sparePartDetailsArrayList.get(j).getRowNo() >= 0) {
                    finalSparePartDetailList += (j + 1) + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmSparePartName() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSparePartName() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmUOMName() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmUOMName() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmRate() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmRate() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmAVLQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmAVLQTY() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmQTY() + "!";
                }

                if (sparePartDetailsArrayList.get(j).getmUOMID() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmUOMID() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmSparePartID() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSparePartID() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmIssueType() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmIssueType();
                }

                if (sparePartDetailsArrayList.size() > 0) {

                    if (sparePartDetailsArrayList.size() > 1) {
                        finalSparePartDetailList = finalSparePartDetailList + "~";

                    } else {
                        finalSparePartDetailList = finalSparePartDetailList;
                    }

//                    Log.d("finalSparePart", "" + finalrows);

                    Log.d("SPAREEEE", "" + finalSparePartDetailList.length() + "," + finalSparePartDetailList);




                } else {
                    finalSparePartDetailList = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handeEditSparesDetails (JSONArray jsonArray){
        Log.e("handeEditServicecalled", "handeEditServiceDetails called"+jsonArray.length());
//        mswiperefreshlayout.setRefreshing(false);
        if (jsonArray.length() > 0) {
            if (sparePartDetailsArrayList == null) {
                sparePartDetailsArrayList = new ArrayList<>();
            }
            // sparePartDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found")) {
                        btn_submit.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, Result, Toast.LENGTH_SHORT).show();
                        sparePartDetailsArrayList.clear();
                    } else {
                        btn_submit.setVisibility(View.VISIBLE);
                        SparePartDetails documentTypes = new SparePartDetails();
                        Log.e("handeEditServicecalled", "handeEditServiceDetails called"+object);
                        documentTypes.setmIssueType(object.getString("IssueType"));
                        Log.e("IssueType",object.getString("IssueType"));

                        documentTypes.setmQTY(object.getString("Qty"));
                        Log.e("Qty",object.getString("Qty"));

                        documentTypes.setmRate(object.getString("Rate"));
                        Log.e("Rate",object.getString("Rate"));

                        documentTypes.setmResult(object.getString("Result"));
                        Log.e("Result",object.getString("Result"));

                        documentTypes.setmSno(object.getString("SlNo"));
                        Log.e("SlNo",object.getString("SlNo"));

                        documentTypes.setmSparePartID(object.getString("SpareId"));
                        Log.e("SpareId",object.getString("SpareId"));

                        documentTypes.setmSparePartName(object.getString("SpareName"));
                        Log.e("SpareName",object.getString("SpareName"));

                        documentTypes.setmTotlValue(object.getString("TotValue"));
                        Log.e("TotValue",object.getString("TotValue"));

                        documentTypes.setmUOMID(object.getString("UOMId"));
                        Log.e("UOMId",object.getString("UOMId"));

                        documentTypes.setmUOMName(object.getString("UOMName"));
                        Log.e("UOMName",object.getString("UOMName"));

                        documentTypes.setmAVLQTY(object.getString("AvailQty"));
                        String status = (object.getString("Status"));
                        if (status.equals("true"))
                        {
                            documentTypes.setSelected(true);
                        }
                        else if (status.equals("false"))
                        {
                            documentTypes.setSelected(false);
                        }
                        sparePartDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AddListToGrid();
        }


//        UIUpdateListLeadView();

    }



    private void GetEditSparesDetails() {
        Log.e("GetEditServiceDetails", "GetEditServiceDetails called");

        if (AppUtil.isNetworkAvailable(this)) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("JobCardId", jobcardId);
                Log.e("Jobcardid ",jobcardId);
                BackendServiceCall serviceCall = new BackendServiceCall(this, false);
                Log.d("servicecall", "" + serviceCall);
                requestName = "GetSparesAgstJobCard";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditSparesDetails, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }

    private class OnServiceCallCompleteListenerReportsImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {
        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetSparesAgstJobCard")) {
                try {
                    //   mswiperefreshlayout.setRefreshing(false);
                    handeEditSparesDetails(jsonArray);
//                    CalculationPart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mswiperefreshlayout.setRefreshing(false);
            // pd.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume called", "onresume called");
        if (sparePartDetailsArrayList != null) {
            sparePartIssueAdapter.notifyDataSetChanged();
        }
        if (sparePartDetailsArrayList == null) {
            return;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Discard?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.clearSession();
                        //if user pressed "yes", then he is allowed to exit from application
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
//                sessionManager.clearSession();
//                this.finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }



}
