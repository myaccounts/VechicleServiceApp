package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.MainActivity;
import com.myaccounts.vechicleserviceapp.Printernew.InitializePrinter;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.network.GMailSender;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

//import java.util.Properties;


import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.myaccounts.vechicleserviceapp.network.GMailSender.OTP_PREFERENCES;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    // This is the activity main thread Handler.
    private Handler updateUIHandler = null;
    // Message type code.
    private final static int MESSAGE_UPDATE_TEXT_CHILD_THREAD =1;
    Random r;
    int min, max, output;
    boolean visibility=false;
    private View view;
    private Context context;
    @BindView(R.id.IdClearBtn)
    Button clearBtn;
    @BindView(R.id.IdSaveBtn)
    Button SaveBtn;
    @BindView(R.id.IdSubmitBtn)
    Button SubmitBtn;
    @BindView(R.id.txtMailId)
    EditText txtUserName;
    @BindView(R.id.otpEdtId)
    EditText otpEdt;
    private ProgressDialog pDialog;
    private String requestName, customerEmailId;

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_settings, container, false);
            getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Settings</font>"));
            setHasOptionsMenu(true);
            ButterKnife.bind(this, view);
            context = getActivity();
            r = new Random();
            SaveBtn.setOnClickListener(this);
            clearBtn.setOnClickListener(this);
            SubmitBtn.setOnClickListener(this);
        /*try {
            DatabaseHelper db = new DatabaseHelper(context);
            Cursor cursor = db.getEmailidDetails();
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    customerEmailId = cursor.getString(cursor.getColumnIndex(InfDbSpecs.EMAILID));
//                    printerIp = cursor.getString(cursor.getColumnIndex(InfDbSpecs.PRINTERIP));

                }
            }
        }
        catch(Exception e){
        }
        if(customerEmailId.isEmpty())
        txtUserName.setText(ProjectMethods.getUserName()+"@admin.com");
        else*/
            txtUserName.setText(customerEmailId);
            otpEdt.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    String otpStr = String.valueOf(s);
                    SharedPreferences sharedpreferences = context.getSharedPreferences(OTP_PREFERENCES, Context.MODE_PRIVATE);
                    int randomStoredValue = sharedpreferences.getInt(InitializePrinter.OTP, 0);
                    if (otpStr.length() >= 4) {
                        if (String.valueOf(randomStoredValue).equals(otpStr)) {
                            SubmitBtn.setEnabled(true);
                            SubmitBtn.setClickable(true);
                        } else {
                            otpEdt.setError("Please Enter Valid OTP");
                        }
                    }else{
                        otpEdt.setError("Please Enter Valid OTP");
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        try {
            try {
                switch (v.getId()) {
                    case R.id.IdSubmitBtn:
                        try{
                            String otpStr = otpEdt.getText().toString();
                            SharedPreferences sharedpreferences = context.getSharedPreferences(OTP_PREFERENCES, Context.MODE_PRIVATE);
                            int randomStoredValue = sharedpreferences.getInt(InitializePrinter.OTP, 0);
                            if (otpStr.length() >= 4) {
                                if (String.valueOf(randomStoredValue).equals(otpStr)) {
                                    MainActivity.comingfrom="2";
                                    Toast.makeText(view.getContext(), "OTP Submit Successfully..." , Toast.LENGTH_SHORT).show();
//                    view.finish();
                                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                                    startActivity(intent);

//                                    SubmitBtn.setEnabled(true);
//                                    SubmitBtn.setClickable(true);
                                } else {
                                    otpEdt.setError("Please Enter Valid OTP");
                                }
                            }
                        }catch (Exception e){

                        }
                        break;
                    case R.id.IdSaveBtn:
                        try {
                            if (Validate()) {
                                CustomDailog("Change EmailId", "Do You Want to Save  Details?", 38, "Send");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.IdClearBtn:
                        try {
                            CustomDailog("Change EmailId", "Do You Want to Clear  Details?", 39, "Clear");

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Validate() {
        boolean valid = true;

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
        } else if (requestCode == 38 && data != null && resultCode == RESULT_OK) {

            SaveInformation(data);
        }
    }

    private void RemoveData() {
        try {
            txtUserName.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SaveInformation(Intent data) {
        sendMessage();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                otpEdt.setVisibility(View.VISIBLE);
                SubmitBtn.setVisibility(View.VISIBLE);
                clearBtn.setVisibility(View.GONE);
                SaveBtn.setVisibility(View.GONE); // Shows view
            }
        }, 3000); // After 3 seconds


    }

    private void sendMessage() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread workerThread = new Thread()
        {
            @Override
            public void run() {
                // Can not update ui component directly when child thread run.
                // updateText();
                // Build message object.
                try {
                    int conformationCode=generateConformationCode();
                    String body="Dear Sir/Madam,\n" +
                            "\n" +
                            "Greetings from Gowheel!\n" +
                            "\n" +
                            conformationCode+" is your OTP code for completing your registration. Please do not share this with anyone.\n" +
                            "\n" +
                            "Thanks,\n" +
                            "Gowheel\n" +
                            "\n" +
                            "Note: This is a system-generated email. Please do not reply to this email.\n";
                    GMailSender sender = new GMailSender(context,"support@myaccounts.in", "Mysupport#!5522",conformationCode);
                    sender.sendMail("GoWheels App Conformation Mail",
                            body,
                            "anusha.nalluri04@gmail.com",
                            txtUserName.getText().toString());
                    dialog.dismiss();

                } catch (Exception e) {
                }
//                        updateUIHandler.sendMessage(message);
            }
        };
        workerThread.start();



        /*Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        sender.start();*/
    }

    private int generateConformationCode() {
        min = 10000;
        max = 100000;
        output = r.nextInt((max - min) +1) + min;

        return output;
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
                if (Result != "" || Result != null) {
                    Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                }
                if (Result.equalsIgnoreCase("Password Changed Successfully")) {
                    RemoveData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}