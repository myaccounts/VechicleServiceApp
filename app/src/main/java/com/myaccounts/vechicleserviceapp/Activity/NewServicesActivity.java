package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterIssueAdapter;
import com.myaccounts.vechicleserviceapp.Fragments.LatestNewServiceSelectedFragment;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class NewServicesActivity extends Activity {

    private NewServiceMasterAdapter servicesAdapter;
    private ArrayList<ServiceMaster> servicesArrayList;
    private ArrayList<ServiceMaster> dynamicList;
    private JSONArray albums;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    LatestNewServiceSelectedFragment newFag = new LatestNewServiceSelectedFragment();

    String modelId;

    SessionManager sessionManager;

    String newVehicleId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.new_service_search_popup);

        sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> user = sessionManager.getVehicleDetails();


        try {
            newVehicleId = user.get(SessionManager.KEY_VEHICLE_ID);
//            Log.d("vehicleModelId", newVehicleId);
        } catch (NullPointerException e) {
        }


        inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
        dynamicList = new ArrayList<>();
        servicesArrayList = new ArrayList<>();

        GetSparePartsData();

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
                dynamicList = servicesAdapter.filter(text);
            }
        });

    }

    private void GetSparePartsData() {
        try {
            pDialog = new ProgressDialog(NewServicesActivity.this, R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("VehicleId", newVehicleId);
                    BackendServiceCall serviceCall = new BackendServiceCall(NewServicesActivity.this, false);
                    requestName = "JCServices";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.NEW_GET_SERVICE_MASTER, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(NewServicesActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class OnServiceCallCompleteListenerSpareImp implements OnServiceCallCompleteListener {
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
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            pDialog.dismiss();
        }
    }

    private void handeGetSparePartsDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(NewServicesActivity.this, Result, Toast.LENGTH_SHORT).show();

                    } else {
                        ServiceMaster documentTypes = new ServiceMaster();
                        documentTypes.setRate(object.getString("Rate"));
                        documentTypes.setResult(object.getString("Result"));
                        documentTypes.setServiceId(object.getString("ServiceId"));
                        documentTypes.setServiceName(object.getString("ServiceName"));
                        documentTypes.setSubServiceId(object.getString("SubServiceId"));
                        documentTypes.setSubServiceName(object.getString("SubServiceName"));
                        servicesArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {


            servicesAdapter = new NewServiceMasterAdapter(NewServicesActivity.this, R.layout.new_service_search_row_item, servicesArrayList);
//            TextView IdSprShortNameTv = (TextView) findViewById(R.id.IdSprShortNameTv);
//            IdSprShortNameTv.setText("Job Type");
            ListView listView = (ListView) findViewById(R.id.Id_SparePartsListView);
            listView.setAdapter(servicesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                    String ServiceRate, ServiceResult, ServiceId, ServiceName, SubServiceId, SubServiceName,serviceCharge, ServiceFreeRate;

                    if (dynamicList.size() == 0) {

                        ServiceFreeRate = servicesArrayList.get(position).getFreeRate();
                        ServiceRate = servicesArrayList.get(position).getRate();
                        ServiceResult = servicesArrayList.get(position).getResult();
                        ServiceId = servicesArrayList.get(position).getServiceId();
                        ServiceName = servicesArrayList.get(position).getServiceName();
                        SubServiceId = servicesArrayList.get(position).getSubServiceId();
                        SubServiceName = servicesArrayList.get(position).getSubServiceName();
//                        serviceCharge = servicesArrayList.get(position).getRate();
                        Log.d("fffffff", SubServiceName);
                    } else {

                        ServiceFreeRate = dynamicList.get(position).getFreeRate();
                        ServiceRate = dynamicList.get(position).getRate();
                        ServiceResult = dynamicList.get(position).getResult();
                        ServiceId = dynamicList.get(position).getServiceId();
                        ServiceName = dynamicList.get(position).getServiceName();
                        SubServiceId = dynamicList.get(position).getSubServiceId();
                        SubServiceName = dynamicList.get(position).getSubServiceName();
//                        serviceCharge = dynamicList.get(position).getRate();

                        Log.d("dsdsssd", SubServiceName);

                    }
                    Intent itemIntent = new Intent();

                    itemIntent.putExtra("ServiceFreeRate", ServiceFreeRate);
                    itemIntent.putExtra("ServiceRate", ServiceRate);
                    itemIntent.putExtra("ServiceResult", ServiceResult);
                    itemIntent.putExtra("ServiceId", ServiceId);
                    itemIntent.putExtra("ServiceName", ServiceName);
                    itemIntent.putExtra("SubServiceId", SubServiceId);
                    itemIntent.putExtra("SubServiceName", SubServiceName);
//                    itemIntent.putExtra("serviceCharge", serviceCharge);
//                    Log.d("aaaaaaa", serviceCharge);
                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
