package com.myaccounts.vechicleserviceapp.LoginSetUp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.MainActivity;
import com.myaccounts.vechicleserviceapp.Fragments.DBManager;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.JSONVariables;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity.this";
    private static final int REQUEST_SIGNUP = 0;
    private EditText txtPassword, txtUserName;
    private Button _loginButton;
    private String mUsername, mPassword, requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    ProgressDialog progressDialog;
    public static final String mypreference = "LoginPref";
    public static final String Name = "NameKey";
    public static final String Password = "PasswordKey";
    private SharedPreferences sharedPreferences;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.loginactivity_layout);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        dbManager = new DBManager(this);
        dbManager.open();
        initializeVariables();

    }


    private void initializeVariables() {
        try {
            txtUserName = (EditText) findViewById(R.id.txtUserName);
            txtPassword = (EditText) findViewById(R.id.txtPassword);
            _loginButton = (Button) findViewById(R.id.btn_login);
            _loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                      login();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {
        if (validate()) {
            String username = txtUserName.getText().toString();
            String password = txtPassword.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Name, username);
            editor.putString(Password, password);
            editor.commit();

            progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            mUsername = txtUserName.getText().toString().trim();
            mPassword = txtPassword.getText().toString().trim();
            String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            String substr = android_id.substring(android_id.length() - 5);
            Log.e("substr", substr);

            try {
                if (AppUtil.isNetworkAvailable(LoginActivity.this)) {
                    JSONObject loginObject = new JSONObject();
                    loginObject.accumulate(JSONVariables.JUserName, mUsername);
                    loginObject.accumulate(JSONVariables.JPasswords, mPassword);
                    loginObject.accumulate(JSONVariables.AndroidId, android_id);

                    Log.e("login", "loginobject : " + loginObject );

                    requestName = "CheckLogIn";
                    BackendServiceCall serviceCall = new BackendServiceCall(LoginActivity.this, false);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerItems());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.CheckLogIn, loginObject, Request.Priority.HIGH);
                } else {
                    progressDialog.dismiss();
                    dialogManager.showAlertDialog(LoginActivity.this, "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

         /*   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            return;*/
        }

    }

    private boolean validate() {
        boolean valid = true;

        String username = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        if (username.isEmpty()) {
            txtUserName.setError("Enter UserName");
            valid = false;
        } else {
            txtUserName.setError(null);
        }

        if (password.isEmpty()) {
            txtPassword.setError("Enter Password");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }

    private class OnServiceCallCompleteListenerItems implements OnServiceCallCompleteListener {

        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            progressDialog.dismiss();
            if (requestName.equalsIgnoreCase("CheckLogIn")) {
                try {
                    handelLoginStatus(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("GetTechnicianDetails")) {
                try {
                    handleGetTechnicianDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }//GetTechnicianDetailsLoadelse
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
        }
    }
    private void handleGetTechnicianDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(this, Result, Toast.LENGTH_SHORT).show();

                        } else {

                            Log.e("Executing block","I am here a size "+object.getString("Id"));
                            Log.e("Executing block","I am here a size "+object.getString("Name"));
//                            dbManager.delete();
                            dbManager.insert(object.getString("Id"), object.getString("Name"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handelLoginStatus(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Log.e("login", "CounterId " + object.getString("CounterId") );

                        ProjectMethods.setBusinessDate(object.getString("Date"));
                        ProjectMethods.setUserId(object.getString("UserId"));
                        ProjectMethods.setCounterId(object.getString("CounterId"));
                        String Result = object.getString("Result");
                        String Remarks = object.getString("Remarks");

                        if (Result.equalsIgnoreCase("Failed")) {
                            Toast.makeText(LoginActivity.this, Remarks, Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d("checkckck", "chekddk");
                            GetTechnicianDetailsFromgeneralMaster();
                            ProjectMethods.setUserName(txtUserName.getText().toString());
                            Toast.makeText(LoginActivity.this, Remarks, Toast.LENGTH_SHORT).show();
//                            MainActivity.comingfrom="00";
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            txtUserName.setText("");
            txtPassword.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetTechnicianDetailsFromgeneralMaster() {
        Log.e("vehicccc","vehiclccc"+"GetTechnicianDetailsFromgeneralMaster");
        try {
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(this, false);
                    jsonObject.accumulate("TypeName", "Technician");
                    requestName = "GetTechnicianDetails";
                    Log.e("vehicccc","vehiclccc"+"GetTechnicianDetailsFromgeneralMaster"+requestName);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerItems());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
