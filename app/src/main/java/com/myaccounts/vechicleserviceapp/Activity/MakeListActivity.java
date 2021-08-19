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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Adapter.JonCardNoAdpater;
import com.myaccounts.vechicleserviceapp.Adapter.MakeAdpater;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.MakeDetails;
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

public class MakeListActivity extends Activity {
    private MakeAdpater makeAdpater;
    private ArrayList<MakeDetails> jobCardDetailsArrayList;
    private ArrayList<MakeDetails> dynamicList;
    private JSONArray albums;
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
            setContentView(R.layout.make_search);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            dynamicList = new ArrayList<>();
            jobCardDetailsArrayList = new ArrayList<>();
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
                    dynamicList = makeAdpater.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetJobCardNoData() {
        try{
            pDialog = new ProgressDialog(MakeListActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(MakeListActivity.this, false);
                    requestName = "GetMakeDetails";
                    serviceCall.setOnServiceCallCompleteListener(new MakeListActivity.OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetMakeDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(MakeListActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
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
            if (requestName.equalsIgnoreCase("GetMakeDetails")) {
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
                        Toast.makeText(MakeListActivity.this, Result, Toast.LENGTH_SHORT).show();

                    } else {
                        MakeDetails documentTypes = new MakeDetails();
                        documentTypes.setMakeName(object.getString("Make"));
                        jobCardDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {


            makeAdpater = new MakeAdpater(MakeListActivity.this, R.layout.make_row_item, jobCardDetailsArrayList);
            ListView listView = (ListView) findViewById(R.id.Id_MakeListView);
            listView.setAdapter(makeAdpater);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                    String MakeName,MakeId;

                    if (dynamicList.size() == 0) {

                        MakeName = jobCardDetailsArrayList.get(position).getMakeName();
                      //  MakeId = jobCardDetailsArrayList.get(position).getMakeId();

                    } else {

                        MakeName = dynamicList.get(position).getMakeName();
                       // MakeId = dynamicList.get(position).getMakeId();


                    }
                    Intent itemIntent = new Intent();

                    itemIntent.putExtra("MakeName", MakeName);
                   // itemIntent.putExtra("MakeId", MakeId);

                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
