package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
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
import com.myaccounts.vechicleserviceapp.Adapter.ModelAdpater;
import com.myaccounts.vechicleserviceapp.Pojo.ModelDetails;
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
import java.util.Locale;

public class ModelListActivity extends Activity {
    private ModelAdpater modelAdpater;
    private ArrayList<ModelDetails> modelDetailsArrayList;
    private ArrayList<ModelDetails> dynamicList;
    private JSONArray albums;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName, makeStr;
    private AlertDialogManager dialogManager = new AlertDialogManager();

    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            requestWindowFeature(Window.FEATURE_NO_TITLE);
           // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.model_search);
            makeStr = getIntent().getExtras().getString("makeName");
            Log.d("makestrrr", makeStr);
            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            inputSearchfield.requestFocus();
            dynamicList = new ArrayList<>();
            modelDetailsArrayList = new ArrayList<>();
            GetJobCardNoData();
            Log.d("calling", "calling");
            sessionManager = new SessionManager(getApplicationContext());
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
                    dynamicList = modelAdpater.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetJobCardNoData() {
        try {
            pDialog = new ProgressDialog(ModelListActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Make", makeStr);
                    Log.d("makestrrr", makeStr);
                    BackendServiceCall serviceCall = new BackendServiceCall(ModelListActivity.this, false);
                    requestName = "GetModelDetails";
                    serviceCall.setOnServiceCallCompleteListener(new ModelListActivity.OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetModelDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(ModelListActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
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
            if (requestName.equalsIgnoreCase("GetModelDetails")) {
                try {
                    pDialog.dismiss();
                    handeGetJobCardDetails(jsonArray);
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

    private void handeGetJobCardDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(ModelListActivity.this, Result, Toast.LENGTH_SHORT).show();

                    } else {
                        ModelDetails documentTypes = new ModelDetails();
                        documentTypes.setModelName(object.getString("Model"));
                        documentTypes.setModelId(object.getString("ModelId"));
                        documentTypes.setMake(object.getString("Make"));
                        documentTypes.setVehicletype(object.getString("vehicletype"));
                        modelDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {


            modelAdpater = new ModelAdpater(ModelListActivity.this, R.layout.model_row_item, modelDetailsArrayList);
            ListView listView = (ListView) findViewById(R.id.Id_ModelListView);
            listView.setAdapter(modelAdpater);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                    String ModelNo, ModelId, Make, VehicleType;

                    if (dynamicList.size() == 0) {

                        ModelNo = modelDetailsArrayList.get(position).getModelName();
                        ModelId = modelDetailsArrayList.get(position).getModelId();
                        Make = modelDetailsArrayList.get(position).getMake();
                        VehicleType = modelDetailsArrayList.get(position).getVehicletype();
                        Log.d("vehicleId", modelDetailsArrayList.get(position).getModelId());
                        sessionManager.storeVehicleId(modelDetailsArrayList.get(position).getModelId(),modelDetailsArrayList.get(position).getMake());


                    } else {

                        ModelNo = dynamicList.get(position).getModelName();
                        ModelId = dynamicList.get(position).getModelId();
                        Make = dynamicList.get(position).getMake();
                        VehicleType = dynamicList.get(position).getVehicletype();


                    }
                    Intent itemIntent = new Intent();

                    itemIntent.putExtra("Model", ModelNo);
                    itemIntent.putExtra("ModelId", ModelId);
                    itemIntent.putExtra("Make", Make);
                    itemIntent.putExtra("VehicleType", VehicleType);
                    Log.d("modelNo", ModelId);
                    sessionManager.storeVehicleId(ModelId,Make);
                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
