package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Adapter.CustomerSelectionAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.CustomerList;
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

public class CustomerSelectionActivity extends Activity {
    private CustomerSelectionAdapter customerSelectionAdapter;
    private ArrayList<CustomerList> userListArrayList;
    private ArrayList<CustomerList> dynamicList;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.customer_search);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            dynamicList = new ArrayList<>();
            userListArrayList = new ArrayList<>();
            GetCustomerList();
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
                    dynamicList = customerSelectionAdapter.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetCustomerList() {
        try {
            pDialog = new ProgressDialog(CustomerSelectionActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(CustomerSelectionActivity.this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag","20");
                    BackendServiceCall serviceCall = new BackendServiceCall(CustomerSelectionActivity.this, false);
                    requestName = "GetCustomerMaster";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImplMethod());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetCustomerMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(CustomerSelectionActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
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
            if (requestName.equalsIgnoreCase("GetCustomerMaster")) {
                try {
                    pDialog.dismiss();
                    handeGetUserDetails(jsonArray);
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

    private void handeGetUserDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            userListArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(CustomerSelectionActivity.this, Result, Toast.LENGTH_SHORT).show();
                    } else {
                        CustomerList userList = new CustomerList();
                        userList.setCustomerId(object.getString("CustomerId"));
                        userList.setCustomerName(object.getString("CustomerName"));
                        userList.setEmailId(object.getString("EmailId"));
                        userList.setMakeCompany(object.getString("MakeCompany"));
                        userList.setMakeYear(object.getString("MakeYear"));
                        userList.setMobileNo(object.getString("MobileNo"));
                        userList.setModelNo(object.getString("ModelNo"));
                        userList.setVehicleNo(object.getString("VehicleNo"));
                        userListArrayList.add(userList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                customerSelectionAdapter = new CustomerSelectionAdapter(CustomerSelectionActivity.this, R.layout.customer_row_item, userListArrayList);
                ListView listView = (ListView) findViewById(R.id.Id_CustomerListView);
                listView.setAdapter(customerSelectionAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                        String CustomerName, CustomerId,MobileNo,VehicleNo;

                        if (dynamicList.size() == 0) {

                            CustomerId = userListArrayList.get(position).getCustomerId();
                            CustomerName = userListArrayList.get(position).getCustomerName();
                            MobileNo = userListArrayList.get(position).getMobileNo();
                            VehicleNo=userListArrayList.get(position).getVehicleNo();


                        } else {

                            CustomerId = dynamicList.get(position).getCustomerId();
                            CustomerName = dynamicList.get(position).getCustomerName();
                            MobileNo = dynamicList.get(position).getMobileNo();
                            VehicleNo=userListArrayList.get(position).getVehicleNo();
                        }
                        Intent itemIntent = new Intent();

                        itemIntent.putExtra("CustomerId", CustomerId);
                        itemIntent.putExtra("CustomerName", CustomerName);
                        itemIntent.putExtra("MobileNo", MobileNo);
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
}
