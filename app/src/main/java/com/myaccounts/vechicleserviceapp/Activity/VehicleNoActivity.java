package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.myaccounts.vechicleserviceapp.Adapter.JonCardNoAdpater;
import com.myaccounts.vechicleserviceapp.Adapter.VehicleNoAdpater;
import com.myaccounts.vechicleserviceapp.Fragments.HomeFragment;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleHistory;
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

public class VehicleNoActivity extends Activity {
    private VehicleNoAdpater vehicleNoAdpater;
    private ArrayList<VehicleDetails> vehicleDetailsArrayList;
    private ArrayList<VehicleDetails> dynamicList;
    private JSONArray albums;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName;
    private String ServiceIdKey, ServiceName, ServiceValue, mCustomerId = "", mVehicleNo = "";
    private AlertDialogManager dialogManager = new AlertDialogManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.vehicleno_search);
            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            dynamicList = new ArrayList<>();
            vehicleDetailsArrayList = new ArrayList<>();
            GetJobCardNoData();
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
                    dynamicList = vehicleNoAdpater.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetJobCardNoData() {
        try{
            pDialog = new ProgressDialog(VehicleNoActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", "90");
                    BackendServiceCall serviceCall = new BackendServiceCall(VehicleNoActivity.this, false);
                    requestName = "GetVehicleInfo";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetVehicleInfo, jsonObject, Request.Priority.HIGH);

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(VehicleNoActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }





    private class OnServiceCallCompleteListenerSpareImp implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
         if (requestName.equalsIgnoreCase("GetVehicleInfo")) {
                try {
                    pDialog.dismiss();
                    handeVehicleDetails(jsonArray);
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

    private void handeVehicleDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast toast = Toast.makeText(VehicleNoActivity.this, Result, Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.WHITE);
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                        v.setTextColor(Color.BLACK);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        VehicleDetails vehicleDetails = new VehicleDetails();
                        vehicleDetails.setSlNo(object.getInt("SlNo"));
                        vehicleDetails.setVehicleNo(object.getString("VehicleNO"));
                        vehicleDetailsArrayList.add(vehicleDetails);
                    }
                   /* String SlNo = jsonArray.getJSONObject(0).getString("SlNo");
                    mVehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");
                    vehicleDetails.setSlNo(jsonArray.getJSONObject(0).getInt("SlNo"));
                    vehicleDetails.setVehicleNo(jsonArray.getJSONObject(0).getString("VehicleNo"));
                    String result = jsonArray.getJSONObject(0).getString("Result");
                    if (result.equalsIgnoreCase("No Details Found")) {
                        Toast toast = Toast.makeText(VehicleNoActivity.this, result, Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.WHITE);
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                        v.setTextColor(Color.BLACK);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {

                            inputSearchfield.setText(mVehicleNo);


                    }*/
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {


            vehicleNoAdpater = new VehicleNoAdpater(VehicleNoActivity.this, R.layout.vehicleno_row_item, vehicleDetailsArrayList);
            ListView listView = (ListView) findViewById(R.id.Id_VehicleNoListView);
            listView.setAdapter(vehicleNoAdpater);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                    String Sno,VehicleNo;

                    if (dynamicList.size() == 0) {



                       // Sno = vehicleDetailsArrayList.get(position).getSlNo();
                        VehicleNo = vehicleDetailsArrayList.get(position).getVehicleNo();

                    } else {



                        //Sno = dynamicList.get(position).getMobileNo();
                        VehicleNo = dynamicList.get(position).getVehicleNo();

                    }
                    Intent itemIntent = new Intent();



                   // itemIntent.putExtra("CustMobNo", Sno);
                    itemIntent.putExtra("VehicleNo", VehicleNo);
                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
