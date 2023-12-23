package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.myaccounts.vechicleserviceapp.Adapter.CustomerSelectionAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.GeneralServiceAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterAdapter;
import com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceList;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class GeneralServiceActivity extends Activity {
    private GeneralServiceAdapter genSerAdapter;
    private ArrayList<ServiceList> serviceListArrayList;
    private ArrayList<ServiceList> dynamicList;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName;
    String jobCardId,ModelId;
    private AlertDialogManager dialogManager = new AlertDialogManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.generalsearch);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            inputSearchfield.setHint("Search Service");
            dynamicList = new ArrayList<>();
            serviceListArrayList = new ArrayList<>();
            jobCardId=getIntent().getStringExtra("jobCardId");
            GetJobCardDetails();
//            GetUsersList();

            inputSearchfield.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = inputSearchfield.getText().toString().toLowerCase(Locale.getDefault());
                    dynamicList = genSerAdapter.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetJobCardDetails() {
        try {
            pDialog = new ProgressDialog(GeneralServiceActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(GeneralServiceActivity.this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobCardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(GeneralServiceActivity.this, false);
                    requestName = "GetJobCardDetailsReport";
                    serviceCall.setOnServiceCallCompleteListener(new GeneralServiceActivity.OnServiceCallCompleteListenerImplMethod());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailsReport, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(GeneralServiceActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetUsersList(String ModelId) {
        try {
            pDialog = new ProgressDialog(GeneralServiceActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(GeneralServiceActivity.this)) {
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.accumulate("VehicleId", ModelId);
                    BackendServiceCall serviceCall = new BackendServiceCall(GeneralServiceActivity.this, false);
                    requestName = "JCServices";
                    serviceCall.setOnServiceCallCompleteListener(new GeneralServiceActivity.OnServiceCallCompleteListenerImplMethod());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.NEW_GET_SERVICE_MASTER, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(GeneralServiceActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class OnServiceCallCompleteListenerImplMethod implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("JCServices")) {
                try {
                    pDialog.dismiss();
                    handeGetSparePartsDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(requestName.equalsIgnoreCase("GetJobCardDetailsReport")){
                try {
                    pDialog.dismiss();
                    handeJobCardReportDetails(jsonArray);

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
    private void handeJobCardReportDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {

                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(GeneralServiceActivity.this, result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Log.e("Executing block","I am here");
                    //   mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    String ModelId = jsonArray.getJSONObject(0).getString("ModelNo");
                    GetUsersList(ModelId);
                }

                //  GetServiceManListRelatedToServices();
            }
        } catch (Exception e) {
        }
    }

    private void handeGetSparePartsDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(GeneralServiceActivity.this, Result, Toast.LENGTH_SHORT).show();

                    } else {
                        ServiceList documentTypes = new ServiceList();
                        documentTypes.setServiceId(object.getString("ServiceId"));
                        documentTypes.setServiceName(object.getString("ServiceName"));
                        documentTypes.setServiceCharge(object.getString("Rate"));
                        documentTypes.setSubServiceId(object.getString("SubServiceId"));
                        documentTypes.setSubServiceName(object.getString("SubServiceName"));
                        serviceListArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            genSerAdapter = new GeneralServiceAdapter(GeneralServiceActivity.this, R.layout.generalservicelist, serviceListArrayList);
            ListView list = (ListView)findViewById(R.id.GeneralListView);
            list.setAdapter(genSerAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ServiceName, ServiceId, ServiceCharge,SubServiceName,SubServiceId;
                    int Sno;
                    if (dynamicList.size() == 0) {
                        ServiceId = serviceListArrayList.get(position).getServiceId();
                        ServiceName = serviceListArrayList.get(position).getServiceName();
                        SubServiceId = serviceListArrayList.get(position).getSubServiceId();
                        SubServiceName = serviceListArrayList.get(position).getSubServiceName();
                        ServiceCharge = serviceListArrayList.get(position).getServiceCharge();

                    } else {
                        ServiceId = dynamicList.get(position).getServiceId();
                        ServiceName = dynamicList.get(position).getServiceName();
                        SubServiceId = dynamicList.get(position).getSubServiceId();
                        SubServiceName = dynamicList.get(position).getSubServiceName();
                        ServiceCharge = dynamicList.get(position).getServiceCharge();

                    }
                    Intent itemIntent = new Intent();
                    itemIntent.putExtra("ServioceId", ServiceId);
                    itemIntent.putExtra("ServiceName", ServiceName);
                    itemIntent.putExtra("SubServioceId", SubServiceId);
                    itemIntent.putExtra("SubServiceName", SubServiceName);
                    itemIntent.putExtra("ServiceCharge", ServiceCharge);
                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeVehicleDetails(JSONArray jsonArray) {

//        mswiperefreshlayout.setRefreshing(false);

        try {

            if (jsonArray.length() > 0) {
                JSONObject jsonObject = new JSONObject();
                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(GeneralServiceActivity.this, result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Log.e("Executing block","I am here");
                    //   mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    ModelId = jsonArray.getJSONObject(0).getString("ModelNo");
                    Log.e("Executing block","I am here"+ModelId);
                    GetUsersList(ModelId);
                }

                //  GetServiceManListRelatedToServices();
            }
        } catch (Exception e) {
        }


//        UIUpdateListLeadView();

    }
    private void handeGetUserDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            serviceListArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(GeneralServiceActivity.this, Result, Toast.LENGTH_SHORT).show();
                    } else {
                        ServiceList userList = new ServiceList();
                        userList.setServiceId(object.getString("ServiceId"));
                        userList.setServiceName(object.getString("ServiceName"));
                        userList.setServiceCharge(object.getString("ServiceCharge"));
                        serviceListArrayList.add(userList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        try {
            genSerAdapter = new GeneralServiceAdapter(GeneralServiceActivity.this, R.layout.generalservicelist, serviceListArrayList);
            ListView list = (ListView)findViewById(R.id.GeneralListView);
            list.setAdapter(genSerAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ServiceName, ServiceId, ServiceCharge;
                    int Sno;
                    if (dynamicList.size() == 0) {
                        ServiceId = serviceListArrayList.get(position).getServiceId();
                        ServiceName = serviceListArrayList.get(position).getServiceName();
                        ServiceCharge = serviceListArrayList.get(position).getServiceCharge();

                    } else {
                        ServiceId = dynamicList.get(position).getServiceId();
                        ServiceName = dynamicList.get(position).getServiceName();
                        ServiceCharge = dynamicList.get(position).getServiceCharge();

                    }
                    Intent itemIntent = new Intent();
                    itemIntent.putExtra("ServioceId", ServiceId);
                    itemIntent.putExtra("ServiceName", ServiceName);
                    itemIntent.putExtra("ServiceCharge", ServiceCharge);
                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
