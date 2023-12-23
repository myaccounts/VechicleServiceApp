package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Context context;
    @BindView(R.id.IdClearBtn)
    Button clearBtn;
    @BindView(R.id.IdSaveBtn)
    Button SaveBtn;
    @BindView(R.id.txtUserName)
    EditText txtUserName;
    @BindView(R.id.txtOldPassword)
    EditText txtOldPassword;
    @BindView(R.id.newPasswordedt)
    EditText newPasswordedt;
    @BindView(R.id.retypenewpwdedt)
    EditText retypenewpwdedt;
    private ProgressDialog pDialog;
    private String requestName;

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chnagepassword_layout, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Change Password</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
        SaveBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        txtUserName.setText(ProjectMethods.getUserName());
        return view;
    }

    @Override
    public void onClick(View v) {
        try{
            try {
                switch (v.getId()) {
                    case R.id.IdSaveBtn:
                        try {
                            if(Validate()) {
                                CustomDailog("Change Password", "Do You Want to Save  Details?", 38, "Save");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.IdClearBtn:
                        try {
                            CustomDailog("Change Password", "Do You Want to Clear  Details?", 39, "Clear");

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean Validate() {
        boolean valid = true;
        String oldpwd=txtOldPassword.getText().toString().trim();
        String newpwd=newPasswordedt.getText().toString().trim();
        String retypeped=retypenewpwdedt.getText().toString().trim();


        if (oldpwd.isEmpty()) {
            txtOldPassword.setError("Enter Old Password");
            valid = false;
        } else {
            txtOldPassword.setError(null);
        }

        if (newpwd.isEmpty()) {
            newPasswordedt.setError("Enter  New Password");
            valid = false;
        } else {
            newPasswordedt.setError(null);
        }
        if (retypeped.isEmpty()) {
            retypenewpwdedt.setError("Retype  New Password");
            valid = false;
        } else {
            retypenewpwdedt.setError(null);
        }
        if (!newpwd.equals(retypeped)) {
            retypenewpwdedt.setError("Password Not matching");
            valid = false;
        } else {
            retypenewpwdedt.setError(null);
        }

        if (newpwd.length()<6) {
            newPasswordedt.setError("Password Length Should be 6 to 15 Characters");
            valid = false;
        }
        return valid;
    }

    private void CustomDailog(String Title, String msg, int value, String btntxt) {
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(getActivity(), CustomDialogClass.class);
            intent.putExtra("msgbtn", btntxt);
            startActivityForResult(intent, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 39) {
            if (data != null && resultCode == RESULT_OK) {
                RemoveData();
            }
        }else if (requestCode == 38 && data != null && resultCode == RESULT_OK) {

            SaveInformation(data);
        }
    }

    private void RemoveData() {
        try{
            txtOldPassword.setText("");
            newPasswordedt.setText("");
            retypenewpwdedt.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void SaveInformation(Intent data) {
        try{
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String oldpwd=txtOldPassword.getText().toString().trim();
            String newpwd=newPasswordedt.getText().toString().trim();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("UserId",ProjectMethods.getUserId());
                    jsonObject.accumulate("OLDPassword",oldpwd);
                    jsonObject.accumulate("NewPassword",newpwd);

                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "ChangePassword";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerChangePwdImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.ChangePassword, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class OnServiceCallCompleteListenerChangePwdImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("ChangePassword")) {
                try {
                    pDialog.dismiss();
                    handeServiceMasterDetails(jsonArray);
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

    private void handeServiceMasterDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {

            try {
                JSONObject object = jsonArray.getJSONObject(0);
                String Result = object.getString("Result");
                if (Result != ""||Result!=null) {
                    Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                }
               if(Result.equalsIgnoreCase("Password Changed Successfully")){
                   RemoveData();
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

