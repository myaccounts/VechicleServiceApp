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
import com.myaccounts.vechicleserviceapp.Adapter.JonCardNoAdpater;
import com.myaccounts.vechicleserviceapp.Fragments.SparePartIssueFragment;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
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

public class JobCardNoActivity extends Activity {
    private JonCardNoAdpater jonCardNoAdpater;
    private ArrayList<JobCardDetails> jobCardDetailsArrayList;
    private ArrayList<JobCardDetails> dynamicList;
    private JSONArray albums;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName,jobCardStatus;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.jobcardno_search);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            dynamicList = new ArrayList<>();
            jobCardDetailsArrayList = new ArrayList<>();
            jobCardStatus=getIntent().getStringExtra("STATUS");
            GetJobCardNoData(jobCardStatus);
//            GetJobCardNoDataReady();
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
                    dynamicList = jonCardNoAdpater.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetJobCardNoDataReady() {
        try{
            pDialog = new ProgressDialog(JobCardNoActivity.this, R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject   jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", 10);
                    jsonObject.accumulate("Status", "Ready");
                    BackendServiceCall serviceCall = new BackendServiceCall(JobCardNoActivity.this, false);
                    requestName = "GetJobCardDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(JobCardNoActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void GetJobCardNoData(String status) {
        try{
            pDialog = new ProgressDialog(JobCardNoActivity.this, R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                 JSONObject   jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", 10);
                    jsonObject.accumulate("Status", status);
                    BackendServiceCall serviceCall = new BackendServiceCall(JobCardNoActivity.this, false);
                    requestName = "GetJobCardDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(JobCardNoActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
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
            if (requestName.equalsIgnoreCase("GetJobCardDetails")) {
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
            for (int i = 0; i < jsonArray.length() ; i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(JobCardNoActivity.this, Result, Toast.LENGTH_SHORT).show();
//{"CustomerName":"an rahan","JCDate":"18-02-2021","JCTime":"13:02:39","JobCardNo":"01010001","MobileNo":"8000080000","ModifiedDate":"18-02-2021","ModifiedTime":"13:01:24","Result":"","SLNO":"1","ScreenName":"JobCard","Status":"Completed","VehicleNo":"ap37bs1525"}
                        //{"CustomerName":"G. Ramana Rao","JCDate":"18-02-2021","JCTime":"17:56:59","JobCardNo":"01E296180014","MobileNo":"9399988355","ModifiedDate":"18-02-2021","ModifiedTime":"17:55:45","Result":"","SLNO":"13","ScreenName":"JobCard","Status":"Pending","VehicleNo":"aP39 eL  0951"}
                    } else {
                        JobCardDetails documentTypes = new JobCardDetails();
                        documentTypes.setCustomerName(object.getString("CustomerName"));
                        documentTypes.setJCDate(object.getString("JCDate"));
//                        documentTypes.setJCTime(object.getString("JCTime"));
                        documentTypes.setModifiedTime(object.getString("ModifiedTime"));
                        documentTypes.setJobCardNo(object.getString("JobCardNo"));
                        documentTypes.setMobileNo(object.getString("MobileNo"));
                        documentTypes.setModel(object.getString("ModelNo"));
                        documentTypes.setJobRemarks(object.getString("JCRemarks"));
//                        documentTypes.setSLNO(object.getString("SLNO"));
                        documentTypes.setSLNO(String.valueOf(i));
                        documentTypes.setStatus(object.getString("Status"));
                        documentTypes.setVehicleNo(object.getString("VehicleNo"));
                        documentTypes.setTechnicianName(object.getString("Technician"));
                        jobCardDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            jonCardNoAdpater = new JonCardNoAdpater(JobCardNoActivity.this, R.layout.jobcard_row_item, jobCardDetailsArrayList);
            ListView listView = (ListView) findViewById(R.id.Id_JobCardListView);
            listView.setAdapter(jonCardNoAdpater);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                    try {
                        String JobNo, CustName, CustMobNo, VehicleNo, JCDate,JCTime, TechnicianName,ModelNo,JCRemarks;

                        if (dynamicList.size() == 0) {//Type--i\Insert Update//Technician ""
                            JobNo = jobCardDetailsArrayList.get(position).getJobCardNo();
                            CustName = jobCardDetailsArrayList.get(position).getCustomerName();
                            CustMobNo = jobCardDetailsArrayList.get(position).getMobileNo();
                            VehicleNo = jobCardDetailsArrayList.get(position).getVehicleNo();
                            JCDate = jobCardDetailsArrayList.get(position).getJCDate();
//                            JCTime=jobCardDetailsArrayList.get(position).getJCTime();
                            JCTime=jobCardDetailsArrayList.get(position).getModifiedTime();
                            TechnicianName = jobCardDetailsArrayList.get(position).getTechnicianName();
                            ModelNo=jobCardDetailsArrayList.get(position).getModel();
                            JCRemarks=jobCardDetailsArrayList.get(position).getJobRemarks();

                        } else {
                            JobNo = dynamicList.get(position).getJobCardNo();
                            CustName = dynamicList.get(position).getCustomerName();
                            CustMobNo = dynamicList.get(position).getMobileNo();
                            VehicleNo = dynamicList.get(position).getVehicleNo();
                            JCDate = dynamicList.get(position).getJCDate();
//                            JCTime=jobCardDetailsArrayList.get(position).getJCTime();
                            JCTime=jobCardDetailsArrayList.get(position).getModifiedTime();
                            TechnicianName = jobCardDetailsArrayList.get(position).getTechnicianName();
                            ModelNo=jobCardDetailsArrayList.get(position).getModel();
                            JCRemarks=jobCardDetailsArrayList.get(position).getJobRemarks();

                        }
                        Intent itemIntent = new Intent();
                        itemIntent.putExtra("JoBCardNo", JobNo);
                        itemIntent.putExtra("CustName", CustName);
                        itemIntent.putExtra("CustMobNo", CustMobNo);
                        itemIntent.putExtra("VehicleNo", VehicleNo);
                        itemIntent.putExtra("JCDate", JCDate);
                        itemIntent.putExtra("JCTime", JCTime);
                        itemIntent.putExtra("TechnicianName", TechnicianName);
                        itemIntent.putExtra("ModelNo", ModelNo);
                        itemIntent.putExtra("JCRemarks", JCRemarks);
                        setResult(RESULT_OK, itemIntent);
                        finish();
                    }catch(Exception e){
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
