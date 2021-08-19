package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.myaccounts.vechicleserviceapp.Adapter.VehicleAdpater;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleTypes;
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

public class VehicleTypeActivity extends Activity {
    private VehicleAdpater vehicleAdpater;
    private ArrayList<VehicleTypes> vehicleTypesArrayList;
    private ArrayList<VehicleTypes> dynamicList;
    private JSONArray albums;
    private EditText inputSearchfield;
    String requestName;
    private ProgressDialog progressDialog;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    private TextView VehicleTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.make_search);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            inputSearchfield.setHint("Search Type");
            VehicleTv=(TextView)findViewById(R.id.Id_MakeTv);
            VehicleTv.setText("TYPE");
            dynamicList = new ArrayList<>();
            vehicleTypesArrayList = new ArrayList<>();
            GetVehicleTypeData();
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
                    dynamicList = vehicleAdpater.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetVehicleTypeData() {
        try {
            progressDialog = new ProgressDialog(VehicleTypeActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            if (AppUtil.isNetworkAvailable(VehicleTypeActivity.this)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("TypeName", "VEHICLE TYPE");
                requestName = "GeneralMasterData";
                BackendServiceCall serviceCall = new BackendServiceCall(VehicleTypeActivity.this, false);
                serviceCall.setOnServiceCallCompleteListener(new VehicleTypeActivity.OnServiceCallCompleteListenerVehicleTypes());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
            } else {
                progressDialog.dismiss();
                dialogManager.showAlertDialog(VehicleTypeActivity.this, "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class OnServiceCallCompleteListenerVehicleTypes implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GeneralMasterData")) {
                try {
                    progressDialog.dismiss();
                    handeGetJobCardDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
        }
    }

    private void handeGetJobCardDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(VehicleTypeActivity.this, Result, Toast.LENGTH_SHORT).show();
                    } else {
                        VehicleTypes documentTypes = new VehicleTypes();
                        documentTypes.setId(object.getString("Id"));
                        documentTypes.setName(object.getString("Name"));
                        documentTypes.setShortName(object.getString("ShortName"));
                        vehicleTypesArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {


                vehicleAdpater = new VehicleAdpater(VehicleTypeActivity.this, R.layout.make_row_item, vehicleTypesArrayList);
                ListView listView = (ListView) findViewById(R.id.Id_MakeListView);
                listView.setAdapter(vehicleAdpater);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                        String VehcileName, VehicleId;

                        if (dynamicList.size() == 0) {

                            VehcileName = vehicleTypesArrayList.get(position).getName();
                            VehicleId = vehicleTypesArrayList.get(position).getId();

                        } else {

                            VehcileName = dynamicList.get(position).getName();
                            VehicleId = dynamicList.get(position).getId();


                        }
                        Intent itemIntent = new Intent();

                        itemIntent.putExtra("VehicleName", VehcileName);
                        itemIntent.putExtra("VehicleId", VehicleId);
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
