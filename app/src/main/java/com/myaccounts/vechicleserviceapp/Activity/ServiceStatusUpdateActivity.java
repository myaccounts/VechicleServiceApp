package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.EthiopicCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.NewServicesActivity;
import com.myaccounts.vechicleserviceapp.Adapter.JobCardReportsAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterIssueAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceUpdateAdapter;
import com.myaccounts.vechicleserviceapp.Fragments.JobCardReportsFragment;
import com.myaccounts.vechicleserviceapp.Pojo.EditServiceDetails;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
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
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class ServiceStatusUpdateActivity extends AppCompatActivity {


    public static boolean checkBoxStatus=false;
    private TextView IdtotalRows, IdTotalQty, IdTotalAmt, FreeTotalTv, ServiceSubServiceTv, ServiceSubServiceIdTv, UomTv, mrpTv, freemrpTv;
    private String AvlQty = "", UOMName = "", UOMId = "", MRP = "";
    float totalAmount, totalQty, totalFreeAmount;
    String TotalGrossAmt, TotalQtyValue, TotalFreeAmount;
    // private ArrayList<NewServiceMasterDetails> serviceMasterDetailsArrayList;

    private NewServiceUpdateAdapter newServiceupdateAdapter;
    private EditText IdServiceEdt, IdQtyEdt;
    ImageButton AddImgBtn;
    EditText vehicleNoEdt,custNoEdt,custNameEdt,jobCardEdt;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int position;
    public String finalServiceDetailsDetailList = null, CloseServiceId = "";
    TextView custNameTv,custNoTv,vehicleNoTv,jobcardTv;
    String newmodelId;

    SessionManager sessionManager;
    String modelId = null,vehicleNo=null,custName=null,custNo=null,jobcardId=null,jcdate=null,jctime=null;
    String qty = "1";
    public static final String TAG = "Services";
    Button serviceSelectedFragmentBtn;
    String[] issueType = {"Paid", "Free"};
    int size;
    Spinner MainServiceServiceIssueTypeSpinner;
    private RecyclerView ServiceMasterRecyclerview;
    String issueTypeString;
    Toolbar toolbar;
    //edit Job card
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mswiperefreshlayout;
    private ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList;
    private ArrayList<NewServiceMasterDetails> updtServiceDetailsArrayList;

    private JSONObject jsonObject;
    private String requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    TextView dateAndTimeTV;
    public static boolean isSelectAll = false;
    Button btn_submit,btn_select_all;

    public ArrayList<NewServiceMasterDetails> getEdiServiceDetailsArrayList() {
        return ediServiceDetailsArrayList;
    }

    public void setEdiServiceDetailsArrayList(ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList) {
        this.ediServiceDetailsArrayList = ediServiceDetailsArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: called");
        Log.e("ANUSHA ", "onCreate: called"+checkBoxStatus+" isSelectAll "+isSelectAll);

        setContentView(R.layout.service_status_update_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Service Update");
        dateAndTimeTV = (TextView) findViewById(R.id.dateAndTimeTV);
        ServiceMasterRecyclerview = (RecyclerView) findViewById(R.id.ServiceMasterRecyclerview);
        btn_submit = (Button) findViewById(R.id.btn_submit_new);
        btn_select_all=(Button) findViewById(R.id.btn_select_all);
        custNameTv = (TextView) findViewById(R.id.custNameTv);
        custNoTv = (TextView) findViewById(R.id.mobileNoTv);
        vehicleNoTv = (TextView) findViewById(R.id.vehicleNoTv);
        jobcardTv=(TextView) findViewById(R.id.jobcardIdTv);
        updtServiceDetailsArrayList = new ArrayList<>();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ANUSHA "," if condition outside"+checkBoxStatus);
                boolean selectStatus=false;

                for(int i=0;i<ediServiceDetailsArrayList.size();i++){
                    if(ediServiceDetailsArrayList.get(i).getSelected()) {
                        selectStatus = true;
                    }
                }
                Log.d("ANUSHA "," if condition outside selectStatus "+selectStatus);
                if(selectStatus) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceStatusUpdateActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Updated");
                    builder.setMessage("Service updated successfully.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
                            Log.e("ediSlsArrayList", String.valueOf(ediServiceDetailsArrayList.size()));
                            NewServiceMasterDetails newServiceMasterDetails = new NewServiceMasterDetails();
                            for (int i = 0; i < ediServiceDetailsArrayList.size(); i++) {
                                Log.i("getmServiceName: ", ediServiceDetailsArrayList.get(i).getmServiceName());
                                Log.i(":getmServiceId ", ediServiceDetailsArrayList.get(i).getmServiceId());
                                Log.i("getmSubServiceId: ", ediServiceDetailsArrayList.get(i).getmSubServiceId());
                                Log.i("getSelected: ", String.valueOf(ediServiceDetailsArrayList.get(i).getSelected()));

                                newServiceMasterDetails.setmServiceName(ediServiceDetailsArrayList.get(i).getmServiceName());

                                newServiceMasterDetails.setmServiceId(ediServiceDetailsArrayList.get(i).getmServiceId());

                                newServiceMasterDetails.setmSubServiceId(ediServiceDetailsArrayList.get(i).getmSubServiceId());

                                newServiceMasterDetails.setSelected(ediServiceDetailsArrayList.get(i).getSelected());

                                updtServiceDetailsArrayList.add(newServiceMasterDetails);

                         /*   Log.i("updgetmServiceName: ", updtServiceDetailsArrayList.get(i).getmServiceName());
                            Log.i(":updgetmServiceId ", updtServiceDetailsArrayList.get(i).getmServiceId());
                            Log.i("updgetmSubServiceId: ", updtServiceDetailsArrayList.get(i).getmSubServiceId());
                            Log.i("updgetSelected: ", String.valueOf(updtServiceDetailsArrayList.get(i).getSelected()));
*/

                            }
                            UpdateService();
                            checkBoxStatus=false;
                            isSelectAll=false;
//                            finish();
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
                }else{
                    Log.e("ANUSHA "," else condition"+checkBoxStatus);
                    Toast.makeText(ServiceStatusUpdateActivity.this,"Please Select At least One Service",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll=true;
                newServiceupdateAdapter.notifyDataSetChanged();
            }
        });

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getVehicleDetails();
        try {

            HashMap<String, String> user1 = sessionManager.getVehicleDetails();
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

        } catch (NullPointerException e) {
        }


        ServiceSubServiceTv = (TextView) findViewById(R.id.ServiceSubServiceTv);
        ServiceSubServiceIdTv = (TextView) findViewById(R.id.ServiceSubServiceIdTv);
        UomTv = (TextView) findViewById(R.id.UomTv);
        mrpTv = (TextView) findViewById(R.id.mrpTv);
        freemrpTv = (TextView) findViewById(R.id.freemrpTv);

        try {

            if (jobcardId.equalsIgnoreCase("empty")) {
//            serviceMasterDetailsArrayList.clear();
            } else {
                if (ediServiceDetailsArrayList == null || ediServiceDetailsArrayList.size() == 0) {
                    GetEditServiceDetails();
                } else {
                    AddListToGrid();
                }
            }
        } catch (NullPointerException e) {
        }
    }

    private void UpdateService() {

        try {


            if (AppUtil.isNetworkAvailable(this)) {
                try {
//                    swipeRefreshLayout.setRefreshing(true);

                    if (ediServiceDetailsArrayList.size() > 0) {

                        for (int i = 0; i < ediServiceDetailsArrayList.size(); i++) {

                            JSONObject jsonObject = new JSONObject();

                            jsonObject.accumulate(JSONVariables.JobCardId, jobcardId);
                            jsonObject.accumulate(JSONVariables.ServiceId, ediServiceDetailsArrayList.get(i).getmServiceId());
                            jsonObject.accumulate(JSONVariables.SubServiceId, ediServiceDetailsArrayList.get(i).getmSubServiceId());
                            String status = String.valueOf(ediServiceDetailsArrayList.get(i).getSelected());
                            jsonObject.accumulate(JSONVariables.Status,status);

                           Log.e("sending",jobcardId);
                           Log.e("sending",ediServiceDetailsArrayList.get(i).getmServiceId());
                           Log.e("sending",ediServiceDetailsArrayList.get(i).getmSubServiceId());
                           Log.e("sending", status);
                           Log.d("ANUSHA "," "+jsonObject.toString());
                            BackendServiceCall serviceCall = new BackendServiceCall(this, false);
                            requestName = "UpdateServiceStatus";
                            serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                            serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.UpdateServiceStatus, jsonObject, Request.Priority.HIGH);
                            Log.e("updatedetail", "" + jsonObject);
                            Log.e("ANUSHA ", "" + jsonObject);
                            Log.d("JESUS 5","#### JSONOBJECT "+jsonObject.toString());
                            Log.d("ANUSHA "," "+ProjectVariables.BASE_URL + ProjectVariables.UpdateServiceStatus);
                        }
                    } else {
                        Log.e("updtServiceDetsArrList", "" + ediServiceDetailsArrayList.size());
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
            if (requestName.equalsIgnoreCase("GetServicesAgstJobCard")) {
                try {
                    handeUpdtServiceDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("UpdateServiceStatus")) {
                try {
                    handeSubmitServiceDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        private void handeUpdtServiceDetails(JSONArray jsonArray) {

            try {
                Log.d("ANUSHA ", " "+jsonArray);
                if (jsonArray.length() > 0) {
                    String result = jsonArray.getJSONObject(0).getString("Result");
                    String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");
                    Log.d("ANUSHA ", " result "+result);
                    Log.d("ANUSHA "," if condition outside selectStatus "+result);
                    Log.d("jobbbbbbb", JobCardNumber);
                    if (result != null) {

                        sessionManager.clearSession();

                        Toast.makeText(ServiceStatusUpdateActivity.this, result + "" + JobCardNumber, Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(ServiceStatusUpdateActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    ClearData();
//                    isFirstRun = true;
                    }
//                JobCardNoEdt.setText(JobCardNumber);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void handeSubmitServiceDetails(JSONArray jsonArray) {

            try {
                Log.d("ANUSHA ", " "+jsonArray.length());
                Log.d("ANUSHA ", " "+jsonArray.getJSONObject(0).toString());
                if (jsonArray.length() > 0) {
                    String result = jsonArray.getJSONObject(0).getString("Result");
//                    String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");

//                    Log.d("jobbbbbbb", JobCardNumber);
                    if (result != null) {

                        sessionManager.clearSession();
                        MainActivity.comingfrom="2";
                        Toast.makeText(ServiceStatusUpdateActivity.this, result , Toast.LENGTH_SHORT).show();
                        finish();
//                        Intent intent = new Intent(ServiceStatusUpdateActivity.thi, MainActivity.class);
//                        startActivity(intent);
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

            Log.e("EditServiceError", "onErrorResponse: " + error.getLocalizedMessage() );

            swipeRefreshLayout.setRefreshing(false);
        }
    }


    private void AddtemToGrid (NewServiceMasterDetails sp){
            Log.e("AddtemToGrid called", "AddtemToGrid called");

            try {

                    ediServiceDetailsArrayList.add(sp);
                    ServiceMasterRecyclerview.smoothScrollToPosition(ediServiceDetailsArrayList.size() - 1);



                PrepareItemDetailsList();

                newServiceupdateAdapter.notifyDataSetChanged();

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


            newServiceupdateAdapter = new NewServiceUpdateAdapter(this, R.layout.new_service_update_row_item, ediServiceDetailsArrayList, "jobcard");
            ServiceMasterRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            ServiceMasterRecyclerview.setItemAnimator(new DefaultItemAnimator());
            ServiceMasterRecyclerview.setHasFixedSize(true);
            ServiceMasterRecyclerview.setAdapter(newServiceupdateAdapter);


//            newServiceMasterIssueAdapter.notifyDataSetChanged();

            newServiceupdateAdapter.SetOnItemClickListener(new NewServiceUpdateAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    boolean value = false;
                    switch (view.getId()) {

                        case R.id.IdEditIconImg:
                            try {
                                CustomDailog("Service Issue", "Do You Want to Edit Details?", 133, "Edit", position);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case R.id.IdDeleteIconImg:
                            try {
                                CustomDailog("Service Issue", "Do You Want to Delete Details?", 134, "Delete", position);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            });



        }




        private void SparPartInformation (Intent data){
            try {
                AvlQty = "";
                UOMId = "";
                UOMName = "";
                String ServiceRate = data.getStringExtra("ServiceRate");
                String ServiceFreeRate = data.getStringExtra("ServiceRate");
                String ServiceResult = data.getStringExtra("ServiceResult");
                String ServiceId = data.getStringExtra("ServiceId");
                String ServiceName = data.getStringExtra("ServiceName");
                String SubServiceId = data.getStringExtra("SubServiceId");
                String SubServiceName = data.getStringExtra("SubServiceName");

                Log.d("sdsdsdd", ServiceName);

                IdServiceEdt.setText(ServiceName);
                IdServiceEdt.setTag(ServiceId);
                mrpTv.setText(ServiceRate);
                freemrpTv.setText(ServiceFreeRate);
           /* AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;*/
                //mrpTv
                // QtyEdt.setText(AvlQty);
                ServiceSubServiceTv.setText(SubServiceName);
                ServiceSubServiceIdTv.setText(SubServiceId);
//            UomTv.setText(ShortUomName);
//            UomTv.setTag(ShortUomId);

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
                finalServiceDetailsDetailList = "";
                for (int j = 0; j < ediServiceDetailsArrayList.size(); j++) {

                    Log.d("ddddddd", "" + ediServiceDetailsArrayList.get(j).getRowNo());
                    if (ediServiceDetailsArrayList.get(j).getRowNo() >= 0) {
                        Log.d("execute", "" + ediServiceDetailsArrayList.get(j).getRowNo());

                        finalServiceDetailsDetailList += (j + 1) + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmServiceId() != null) {
                        finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmServiceId() + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmSubServiceId() != null) {
                        finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmSubServiceId() + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmQty() != null) {
                        finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmQty() + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmRate() != null) {
                        finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmRate() + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                        finalServiceDetailsDetailList += totalAmount + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                        finalServiceDetailsDetailList += "N/A" + "!";
                    }
                    if (ediServiceDetailsArrayList.get(j).getmIssueType() != null) {
                        finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmIssueType() + "!";
                    }
                    if(ediServiceDetailsArrayList.get(j).getmSetSerStatus() != null) {
                        finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmSetSerStatus();//this field added as per status changing of job card
                    }

//                if (serviceMasterDetailsArrayList.get(j).getmServiceName() != null) {
//                    finalServiceDetailsDetailList += serviceMasterDetailsArrayList.get(j).getmServiceName() + "!";
//                }
//                if (serviceMasterDetailsArrayList.get(j).getmSubServiceName() != null) {
//                    finalServiceDetailsDetailList += serviceMasterDetailsArrayList.get(j).getmSubServiceName() + "!";
//                }

//                if (serviceMasterDetailsArrayList.get(j).getmSparePartID() != null) {
//                    finalSparePartDetailList += serviceMasterDetailsArrayList.get(j).getmSparePartID();
//                }

                    Log.d("length", "" + ediServiceDetailsArrayList.size());

//                if (serviceMasterDetailsArrayList.size() == 1) {
//                    finalServiceDetailsDetailList = finalServiceDetailsDetailList + "";
////                    Log.d("finalSparePart", "" + finalrows);
//
//                    Log.d("finnnnnyyyyy", "" + finalServiceDetailsDetailList.length() + "," + finalServiceDetailsDetailList);
//
//                    CalculationPart();
//                }
                    if (ediServiceDetailsArrayList.size() > 0) {

                        if (ediServiceDetailsArrayList.size() > 1) {
                            finalServiceDetailsDetailList = finalServiceDetailsDetailList + "~";
                        } else {
                            finalServiceDetailsDetailList = finalServiceDetailsDetailList;
                        }

//                    Log.d("finalSparePart", "" + finalrows);

                        Log.e("finnnnn", "" + finalServiceDetailsDetailList.length() + "," + finalServiceDetailsDetailList);




                    } else {
                        finalServiceDetailsDetailList = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void handeEditServiceDetails (JSONArray jsonArray){
            Log.e("handeEditServicecalled", "handeEditServiceDetails called"+jsonArray.length());
//        mswiperefreshlayout.setRefreshing(false);
            if (jsonArray.length() > 0) {
                if (ediServiceDetailsArrayList == null) {
                    ediServiceDetailsArrayList = new ArrayList<>();
                }
                //  ediServiceDetailsArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Records Found")) {
                            btn_submit.setVisibility(View.INVISIBLE);
                            btn_select_all.setVisibility(View.INVISIBLE);
                            Toast.makeText(this, Result, Toast.LENGTH_SHORT).show();
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.clear();
                            ediServiceDetailsArrayList.clear();
                        } else {
                            btn_submit.setVisibility(View.VISIBLE);
                            btn_select_all.setVisibility(View.INVISIBLE);
                            Log.e("handeEditServicecalled", "handeEditServiceDetails called"+object);
                            NewServiceMasterDetails documentTypes = new NewServiceMasterDetails();
                            documentTypes.setmIssueType(object.getString("IssueType"));
                            documentTypes.setmQty(object.getString("Qty"));
                            documentTypes.setmRate(object.getString("Rate"));
                            documentTypes.setRemarks(object.getString("Remarks"));
                            documentTypes.setmResult(object.getString("Result"));
                            documentTypes.setmServiceId(object.getString("ServiceId"));
                            documentTypes.setmServiceName(object.getString("ServiceName"));
                            documentTypes.setSno(object.getString("SlNo"));
                            documentTypes.setmSubServiceId(object.getString("SubServiceId"));
                            documentTypes.setmSubServiceName(object.getString("SubServiceName"));
                            documentTypes.setTotalValue(object.getString("TotValue"));
                            documentTypes.setmSetSerStatus(object.getString("SerStatus"));
//                            documentTypes.setmAvlQty(object.getString("AvailQty"));
                            String status = (object.getString("Status"));
                            Log.d("JESUS 5","#### JSONOBJECT "+object.toString());
                            Log.d("JESUS 5","#### JSONOBJECT "+object.getString("SerStatus"));
                            Log.d("JESUS 5","#### JSONOBJECT "+documentTypes.getmSetSerStatus());
                            Log.d("ANUSHA "," if condition outside selectStatus "+object.getString("SerStatus"));
//                            Log.d("ANUSHA "," if condition outside selectStatus "+documentTypes.getmSetSerStatus());
                            Log.d("ANUSHA "," if condition outside selectStatus ___ "+status);
                            if (status.equals("true"))
                            {
                                documentTypes.setSelected(true);
                            }
                            else if (status.equals("false"))
                            {
                                documentTypes.setSelected(false);
                            }
                            ediServiceDetailsArrayList.add(documentTypes);
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.add(documentTypes);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                AddListToGrid();
                //  GetServiceManListRelatedToServices();
            }

//        UIUpdateListLeadView();

        }



    private void GetEditServiceDetails() {
        Log.e("GetEditServiceDetails", "GetEditServiceDetails called");

        if (AppUtil.isNetworkAvailable(this)) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("JobCardId", jobcardId);
                BackendServiceCall serviceCall = new BackendServiceCall(this, false);
                Log.d("servicecall", "" + serviceCall);
                Log.d("ANUSHA "," "+jsonObject.toString());
                requestName = "GetServicesAgstJobCard";
                Log.d("ANUSHA "," "+ProjectVariables.BASE_URL + ProjectVariables.GetEditServiceDetails);
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditServiceDetails, jsonObject, Request.Priority.HIGH);
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
            if (requestName.equalsIgnoreCase("GetServicesAgstJobCard")) {
                try {
                    //   mswiperefreshlayout.setRefreshing(false);
                    handeEditServiceDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void onErrorResponse(VolleyError error) {
//            mswiperefreshlayout.setRefreshing(false);
            // pd.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        AddListToGrid();
        //Updating previous conditions
        Log.e("resume called", "onresume called");
        if (newServiceupdateAdapter != null) {
            newServiceupdateAdapter.notifyDataSetChanged();
        }
        if (ediServiceDetailsArrayList == null) {
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
