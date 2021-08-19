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
import com.myaccounts.vechicleserviceapp.Adapter.ServiceManAdpater;
import com.myaccounts.vechicleserviceapp.Fragments.UserWisePrevilegeFragment;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceManList;
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

public class ServiceManNamesActivity extends Activity {
    private ServiceManAdpater serviceManAdpater;
    private ArrayList<ServiceManList> userListArrayList ;
    private ArrayList<ServiceManList> dynamicList;
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
            setContentView(R.layout.serviceman_row_layout);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            dynamicList = new ArrayList<>();
            userListArrayList = new ArrayList<>();
            GetUsersList();
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
                    dynamicList = serviceManAdpater.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetUsersList() {
        try {
            pDialog = new ProgressDialog(ServiceManNamesActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(ServiceManNamesActivity.this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("TypeName", "SERVICE MAN");
                    BackendServiceCall serviceCall = new BackendServiceCall(ServiceManNamesActivity.this, false);
                    requestName = "GeneralMasterData";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(ServiceManNamesActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
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
            if (requestName.equalsIgnoreCase("GeneralMasterData")) {
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
                        Toast.makeText(ServiceManNamesActivity.this, Result, Toast.LENGTH_SHORT).show();

                    } else {
                        ServiceManList userList = new ServiceManList();
                        userList.setId(object.getString("Id"));
                        userList.setName(object.getString("Name"));
                        userList.setShortName(object.getString("ShortName"));
                        userListArrayList.add(userList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {


                serviceManAdpater = new ServiceManAdpater(ServiceManNamesActivity.this, R.layout.serviceman_row_item, userListArrayList);
                ListView listView = (ListView) findViewById(R.id.Id_UserListView);
                listView.setAdapter(serviceManAdpater);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                        String id, mName,mShortName;

                        if (dynamicList.size() == 0) {

                            id = userListArrayList.get(position).getId();
                            mName = userListArrayList.get(position).getName();
                            mShortName = userListArrayList.get(position).getShortName();

                        } else {

                            id = dynamicList.get(position).getId();
                            mName = dynamicList.get(position).getName();
                            mShortName = dynamicList.get(position).getShortName();

                        }
                        Intent itemIntent = new Intent();

                        itemIntent.putExtra("ServiceManId", id);
                        itemIntent.putExtra("ServiceManName", mName);
                        itemIntent.putExtra("ServiceManShortName", mShortName);
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
