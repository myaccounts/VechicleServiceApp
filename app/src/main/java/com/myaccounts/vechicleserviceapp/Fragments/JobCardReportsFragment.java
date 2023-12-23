package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.DrawingSignatureActivity;
import com.myaccounts.vechicleserviceapp.Activity.MainActivity;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Adapter.JobCardReportsAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.JobDetailsAdpater;
import com.myaccounts.vechicleserviceapp.Pojo.DocumentTypes;
import com.myaccounts.vechicleserviceapp.Pojo.GetJobDetails;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceManList;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.myaccounts.vechicleserviceapp.Utils.DatePickerFragment.currentdate;
import static com.myaccounts.vechicleserviceapp.Utils.DatePickerFragment.menuCalenderSelectonDate;

public class JobCardReportsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    private ProjectVariables gV1 = new ProjectVariables();
    private JSONObject jsonObject;
    @BindView(R.id.IdJobCardRecyclerview)
    RecyclerView mJobCardRecyclerview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mswiperefreshlayout;
    @BindView(R.id.inputSearchEdt)
    EditText SearchFieldEdt;
    @BindView(R.id.newJobFormLayout)
    RelativeLayout newJobFormLayout;
    @BindView(R.id.SpnStatusSelection)
    Spinner SpnStatusSelection;
    @BindView(R.id.refreshmain)
    ImageView refreshImage;
    private String requestName;
    private JobCardReportsAdapter jobCardReportsAdapter;
    private ArrayList<JobCardDetails> jobCardDetailsArrayList;
    private ArrayList<GetJobDetails> getJobDetailsArrayList;
    private ArrayList<ServiceManList> userServiceListArrayList;
    private Context context;
    private ImageView IdCapImgView, IdDrawImgView;
    private Button SavedetailsBtn, signatureBtn;
    private RecyclerView Detailsrecyclerview;
    private JobDetailsAdpater jobDetailsAdpater;
    private TextView JobCardNoTv, VehicleTypeTv, MileageTv, NameTv, DateTv, OdomerterTv, AvgkmsTv, ModelTv, ServiceListTv, VehicleNoTv, MobileNoTv, TimeInTv, IdOutDateTv, MakeTv, DocumentsTv, RemarksTv;
    ProgressDialog pd;
    Dialog dialog;
    private String JobCardNo;
    LinearLayout ImageLinearLayout;
    private ProgressDialog progressDialog;
    private ArrayList<DocumentTypes> documentTypesArrayList;
    private ArrayList<UserList> userListArrayList;
    private String finalJobCardDetailList = "", ServiceDetails = "", SelectedStatus = "";
    private boolean GirdStatusResult = false;
    String byteArryImage;
    Bitmap bitmap;
    byte[] Imagepath;
    String imageEncoded = "";

    SessionManager sessionManager;

    public static JobCardReportsFragment newInstance() {
        Bundle args = new Bundle();
        JobCardReportsFragment fragment = new JobCardReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.jobcardreport_layout, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>JobCard Details</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();

        mswiperefreshlayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mswiperefreshlayout.setOnRefreshListener(this);
        jobCardDetailsArrayList = new ArrayList<>();
        getJobDetailsArrayList = new ArrayList<>();
        documentTypesArrayList = new ArrayList<>();
        userServiceListArrayList = new ArrayList<>();
        userListArrayList = new ArrayList<>();
        SpnStatusSelection.setSelection(1);
        SelectedStatus = SpnStatusSelection.getSelectedItem().toString();
        //  GetServiceManListRelatedToServices();
        GetJobCardStatusDetails(MainActivity.menuClick);
//        GetServiceStatusDetails();
//        GetJobCardNoDataReady();
        sessionManager = new SessionManager(getActivity());
        sessionManager.clearSession();

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetJobCardStatusDetails(MainActivity.menuClick);
            }
        });

        SearchFieldEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (jobCardDetailsArrayList != null) {
                        filter(s.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        newJobFormLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NewJobCardDetailsMain.class);
                sessionManager.clearSession();
                sessionManager.storeJobCardId("empty", "empty", "empty", "empty","empty","empty","empty");
                intent.putExtra("new","new");
                startActivity(intent);

            }
        });

        SpnStatusSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // ((TextView) parent.getChildAt(0)).setTextSize(12);
                SelectedStatus = SpnStatusSelection.getSelectedItem().toString();
                GetJobCardStatusDetails(MainActivity.menuClick);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    private void GetJobCardNoDataReady() {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("Flag", 10);
                jsonObject.accumulate("Status", "Ready");
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                requestName = "GetServicesAgstJobCard";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditServiceDetails, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }
    private void GetServiceStatusDetails() {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("Flag", 10);
                jsonObject.accumulate("Status", SelectedStatus);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                requestName = "GetServicesAgstJobCard";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditServiceDetails, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }

    private void GetServiceManListRelatedToServices() {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("TypeName", "SERVICE MAN");
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                requestName = "GetUserWiseData";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }

    private void filter(String text)
    {
        ArrayList<JobCardDetails> countryLists = new ArrayList<>();
        for (JobCardDetails c : jobCardDetailsArrayList) {
            if (c.getCustomerName().toLowerCase().contains(text) || c.getJobCardNo().toLowerCase().contains(text) || c.getVehicleNo().toLowerCase().contains(text) || c.getMobileNo().toLowerCase().contains(text)) {
                countryLists.add(c);
            }

        }
        jobCardReportsAdapter.setFilter(countryLists);
    }

    private void GetJobCardStatusDetails(boolean menuclick) {

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("Flag", 10);
                jsonObject.accumulate("Status", SelectedStatus);
                Log.d("jobcard details 1 ", "" + jsonObject);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                requestName = "GetJobCardDetails";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetails, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }

    @Override
    public void onRefresh() {
        mswiperefreshlayout.setRefreshing(false);
    }

    private class OnServiceCallCompleteListenerReportsImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {
        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetJobCardDetails")) {
                try {
                    mswiperefreshlayout.setRefreshing(false);
                    handeGetJobCardDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetJobCardDetailsReport")) {
//                try {
//                    mswiperefreshlayout.setRefreshing(false);
////                    handleGetJobCardWiseListDetails(jsonArray);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else if (requestName.equalsIgnoreCase("GetJobCardDetailedGrid")) {
                try {
                    mswiperefreshlayout.setRefreshing(false);
                    handleGetJobCardDetailedGrid(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
          /*  else if(requestName.equalsIgnoreCase("GeneralMasterData")) {
                try {
                    mswiperefreshlayout.setRefreshing(false);
                    progressDialog.dismiss();
                    handeSpinnerDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            else if (requestName.equalsIgnoreCase("GetUserWiseData")) {
                try {
                    mswiperefreshlayout.setRefreshing(false);
                    handleGetUsersRelatedToService(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("UpdateJobCardDetails")) {
                try {
                    mswiperefreshlayout.setRefreshing(false);
                    handleUpdateJobCardDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mswiperefreshlayout.setRefreshing(false);
            // pd.dismiss();
        }
    }

    private void handleUpdateJobCardDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                try {
                    JSONObject object = jsonArray.getJSONObject(0);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        mswiperefreshlayout.setRefreshing(false);
                    } else {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetUsersRelatedToService(JSONArray jsonArray) {
        try {
            userServiceListArrayList = new ArrayList<>();
            if (jsonArray.length() > 0) {
                userServiceListArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            ServiceManList userList = new ServiceManList();
                            userList.setId(object.getString("Id"));
                            userList.setName(object.getString("Name"));
                            userList.setShortName(object.getString("ShortName"));
                            userServiceListArrayList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            GetServiceListDetails(JobCardNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpDateDataToListView() {
        mswiperefreshlayout.setRefreshing(false);

    }

    private void handleGetJobCardDetailedGrid(JSONArray jsonArray) {
        try {
            getJobDetailsArrayList = new ArrayList<>();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        GetJobDetails documentTypes = new GetJobDetails();
                        StringBuilder query = new StringBuilder();
                        StringBuilder query1 = new StringBuilder();
                        JSONObject object = jsonArray.getJSONObject(i);
                        JSONArray jsonArray1 = object.getJSONArray("Li");
                        JSONArray jsonArray2 = jsonArray1.getJSONArray(0);
                        JSONObject object1 = jsonArray2.getJSONObject(0);
                        String ServiceName = object1.getString("Key");
                        JSONArray ServiceValue = object1.getJSONArray("Value");
                        documentTypes.setmServiceName(ServiceName);
                        for (int j = 0; j < ServiceValue.length(); j++) {
                            JSONArray jsonArray4 = ServiceValue.getJSONArray(j);

                            for (int k = 0; k < jsonArray4.length(); k++) {
                                JSONObject object2 = jsonArray4.getJSONObject(k);
                                String SubService = object2.getString("Key");
                                String Value = object2.getString("Value");

                                if (SubService.equalsIgnoreCase("SubService"))
                                {
                                    // query.append( Value + ":");
                                    String SubServiceHeader = Value + "";
                                    documentTypes.setmSubServiceName(SubServiceHeader);
                                } else {
                                    String SubServiceValue = SubService + "=" + Value;
                                    documentTypes.setmSubServiceValue(SubServiceValue);
                                    documentTypes.setmSubServiceValueStr(SubService);
                                    // query1.append(SubService).append("=").append(Value);
                                }

                                if (k != 0) {
                               /*     documentTypes.setmSubServiceName(query.toString());
                                    documentTypes.setmSubServiceValue(query1.toString());*/
                                    getJobDetailsArrayList.add(documentTypes);

                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    GirdStatusResult = true;
                    handleSpinnerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSpinnerList() {
        try {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("TypeName", "WORK STATUS");
                requestName = "GeneralMasterData";
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpinner());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
            } else {
                progressDialog.dismiss();
                dialogManager.showAlertDialog(getActivity(), "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetJobCardWiseListDetails(JSONArray jsonArray) {
        try {
            pd.dismiss();
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_show_jobcarddetails);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
            ImageButton CloseButton = (ImageButton) dialog.findViewById(R.id.IdCloseImgBtn);
            ImageButton IdSaveImgBtn = (ImageButton) dialog.findViewById(R.id.IdSaveImgBtn);
            JobCardNoTv = (TextView) dialog.findViewById(R.id.IdJobCardNoTv);
            VehicleTypeTv = (TextView) dialog.findViewById(R.id.IdVehicleTypeTv);
            MileageTv = (TextView) dialog.findViewById(R.id.IdMileageTv);
            VehicleNoTv = (TextView) dialog.findViewById(R.id.IdVehicleNoTv);
            NameTv = (TextView) dialog.findViewById(R.id.IdNameTv);
            MobileNoTv = (TextView) dialog.findViewById(R.id.IdMobileNoTv);
            RemarksTv = (TextView) dialog.findViewById(R.id.IdRemarksTv);
            ServiceListTv = (TextView) dialog.findViewById(R.id.IdServiceListTv);
            DocumentsTv = (TextView) dialog.findViewById(R.id.IdDocumentsTv);
            ModelTv = (TextView) dialog.findViewById(R.id.IdModelTv);
            MakeTv = (TextView) dialog.findViewById(R.id.IdMakeTv);
            AvgkmsTv = (TextView) dialog.findViewById(R.id.IdAvgKmsTv);
            IdOutDateTv = (TextView) dialog.findViewById(R.id.IdOutDateTv);
            OdomerterTv = (TextView) dialog.findViewById(R.id.IdOdometerReadingTv);
            TimeInTv = (TextView) dialog.findViewById(R.id.IdTimeInTv);
            DateTv = (TextView) dialog.findViewById(R.id.IdDateTv);
            IdCapImgView = (ImageView) dialog.findViewById(R.id.IdCapImgView);
            SavedetailsBtn = (Button) dialog.findViewById(R.id.SavedetailsBtn);
            signatureBtn = (Button) dialog.findViewById(R.id.signatureBtn);
            IdDrawImgView = (ImageView) dialog.findViewById(R.id.IdDrawImgView);
            ImageLinearLayout = (LinearLayout) dialog.findViewById(R.id.ImageLinearLayout);
            Detailsrecyclerview = (RecyclerView) dialog.findViewById(R.id.Detailsrecyclerview);
            ServiceListTv.setText("");
            DocumentsTv.setText("");
            SavedetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CustomDailog("Job Card Details", "Do You Want to Save the Data?", 33, "SAVE");
                    } catch (Exception e) {
                        e.printStackTrace();


                    }

                }
            });
            signatureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent sig = new Intent(getActivity(), DrawingSignatureActivity.class);
                        sig.putExtra("JobCardDetailsSignature", "1");
                        startActivityForResult(sig, 11);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            CloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
            IdSaveImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CustomDailog("Job Card Details", "Do You Want to Save the Data?", 33, "SAVE");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {

                            JobCardNoTv.setText(object.getString("JobCardNo"));
                            NameTv.setText(object.getString("CustomerName"));
                            DateTv.setText(object.getString("JCDate"));
                            OdomerterTv.setText(object.getString("OMReading"));
                            AvgkmsTv.setText(object.getString("AvgKmsPerDay"));
                            ModelTv.setText(object.getString("ModelNo"));
                            ServiceListTv.setText(object.getString("ServicesList"));
                            VehicleNoTv.setText(object.getString("VehicleNo"));
                            MobileNoTv.setText(object.getString("MobileNo"));
                            TimeInTv.setText(object.getString("InTime"));
                            //  IdOutDateTv.setText(object.getString("RegNo"));
                            MakeTv.setText(object.getString("Make"));
                            DocumentsTv.setText(object.getString("DocumentsList"));
                            RemarksTv.setText(object.getString("Remarks"));
                            VehicleTypeTv.setText(object.getString("VehicleType"));
                            MileageTv.setText(object.getString("Mileage"));

                           /* IdCapImgView.setImageBitmap("");
                            IdDrawImgView.setImageBitmap("");*/
                            //call the gridList

                            //call the gridList
                            GetJobDetails documentTypes = new GetJobDetails();
                           /* documentTypes.setmJobCardNo(object.getString("CustomerName"));
                            documentTypes.setmName(object.getString("JobCardNo"));
                            documentTypes.setMdate(object.getString("MobileNo"));
                            documentTypes.setmOdometerReading(object.getString("SLNO"));
                            documentTypes.setMavgkms(object.getString("Status"));
                            documentTypes.setmModel(object.getString("VehicleNo"));
                            documentTypes.setmServiceList(object.getString("VehicleNo"));
                            documentTypes.setmVehicleNo(object.getString("VehicleNo"));
                            documentTypes.setmMobileNo(object.getString("VehicleNo"));
                            documentTypes.setmTimein(object.getString("VehicleNo"));
                            documentTypes.setmRegNo(object.getString("VehicleNo"));
                            documentTypes.setmMake(object.getString("VehicleNo"));
                            documentTypes.setmDocuments(object.getString("VehicleNo"));
                            documentTypes.setmRemarks(object.getString("VehicleNo"));
                            documentTypes.setmImage(object.getString("VehicleNo"));
                            documentTypes.setmSignature(object.getString("VehicleNo"));*/
                            GirdStatusResult = false;
                            // GetJobCardDetailedGrid(JobCardNo);//cmt
                           /* documentTypes.setmServiceName(object.getString("VehicleNo"));
                            documentTypes.setmServiceList(object.getString("VehicleNo"));
                            getJobDetailsArrayList.add(documentTypes);*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
//            Collections.reverse
//            Collections.sort(documentTypesArrayList);
           /* Collections.sort(userServiceListArrayList, new Comparator() {
                @Override
                public int compare(userServiceListArrayList o1, userServiceListArrayList o2) {
                    return o1.name.compareTo(o2.name);
                }
            });*/
            jobDetailsAdpater = new JobDetailsAdpater(context, getJobDetailsArrayList, documentTypesArrayList, userServiceListArrayList, R.layout.jobcarddetails_row_item);
            Detailsrecyclerview.setLayoutManager(new LinearLayoutManager(context));
            Detailsrecyclerview.setItemAnimator(new DefaultItemAnimator());
            Detailsrecyclerview.setHasFixedSize(true);
            Detailsrecyclerview.setAdapter(jobDetailsAdpater);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GetJobCardStatusDetails(MainActivity.menuClick);
        retrivesharedPreferences();
        /*Fragment frag = new JobCardReportsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, frag).commit();*/

    }

    private void retrivesharedPreferences() {
        SharedPreferences shared = getActivity().getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        byteArryImage = shared.getString("CUSTOMER_SIGNATURE", "");
        assert byteArryImage != null;
        try {
            if (!byteArryImage.equals("")) {
                byte[] b = Base64.decode(byteArryImage, Base64.DEFAULT);
                InputStream is = new ByteArrayInputStream(b);
                bitmap = BitmapFactory.decodeStream(is);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                Imagepath = bos.toByteArray();
                imageEncoded = Base64.encodeToString(Imagepath, Base64.DEFAULT);
                if (bitmap != null) {
                    ImageLinearLayout.setVisibility(View.VISIBLE);
                    IdDrawImgView.setVisibility(View.VISIBLE);
                    IdDrawImgView.setImageBitmap(bitmap);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void SaveJobCardDetailsToServer() {
        try {
            finalJobCardDetailList = "";
            ServiceDetails = "";
            try {
                List<GetJobDetails> list = new ArrayList<>();
                for (int i = 0; i < JobDetailsAdpater.getJobDetailsArrayList.size(); i++) {
                  /*  View viewtype = Detailsrecyclerview.getChildAt(i);
                    EditText nameEditText = (EditText)viewtype.findViewById(R.id.Remarksedittext);
                    String name = nameEditText.getText().toString();
                    Spinner spinner = (Spinner) viewtype.findViewById(R.id.SpnStatusSelection);
                    String spnStatus = spinner.getSelectedItem().toString();
                    Spinner SpnUser = (Spinner) viewtype.findViewById(R.id.SpnUserSelection);
                    String spnUser = String.valueOf(SpnUser.getSelectedItemId());*/
                    GetJobDetails jb = new GetJobDetails();
                    jb.setmRemarks(JobDetailsAdpater.getJobDetailsArrayList.get(i).getmRemarks());
                    jb.setmSubServiceName(JobDetailsAdpater.getJobDetailsArrayList.get(i).getmSubServiceName());
                    jb.setmSubServiceValue(JobDetailsAdpater.getJobDetailsArrayList.get(i).getmSubServiceValue());
                    jb.setmServiceName(JobDetailsAdpater.getJobDetailsArrayList.get(i).getmServiceName());
                    jb.setmSelectedServiceId(JobDetailsAdpater.getJobDetailsArrayList.get(i).getmSelectedServiceId());
                    jb.setmSelectedUserId(JobDetailsAdpater.getJobDetailsArrayList.get(i).getmSelectedUserId());
                    list.add(jb);
                }
                try {
                    String ServiceNameStr = "", SubServiceStr = "", SubServiceStrValue = "";
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).getmServiceName() != "") {
                            ServiceDetails += list.get(j).getmServiceName() + "^";
                            ServiceNameStr = list.get(j).getmServiceName();
                        } else {
                            ServiceDetails += ServiceNameStr + "^";
                        }
                        if (list.get(j).getmSubServiceName() != "") {
                            ServiceDetails += list.get(j).getmSubServiceName() + "^";
                            SubServiceStr = list.get(j).getmSubServiceName();
                        } else {
                            ServiceDetails += SubServiceStr + "^" + "^";
                        }
                        if (list.get(j).getmSubServiceValue() != "") {
                            String str = list.get(j).getmSubServiceValue();
                            String[] arrOfStr = str.split("\\=");
                            ServiceDetails += arrOfStr[0] + "^";
                        } else {
                            ServiceDetails += SubServiceStrValue + "^";
                        }

                        if (list.get(j).getmRemarks() != "") {
                            ServiceDetails += list.get(j).getmRemarks() + "^";
                        } else {
                            ServiceDetails += " " + "^";
                        }
                        ServiceDetails += list.get(j).getmSelectedServiceId() + "^" + list.get(j).getmSelectedUserId() + "^";


                        if (ServiceDetails != "") {
                            ServiceDetails = ServiceDetails + "~";
                        } else {
                            ServiceDetails = "";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UpdateDetailsToserver();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdateDetailsToserver() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    String str = byteArryImage.replace("\n", "");
                    str = str.replace("/", "");
                    mswiperefreshlayout.setRefreshing(true);
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("JCId", JobCardNo);
                    jsonObject.accumulate("SUserId", ProjectMethods.getUserId());
                    jsonObject.accumulate("ServiceDetails", ServiceDetails);
                    jsonObject.accumulate("JCImage", str);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "UpdateJobCardDetails_New";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.UpdateJobCardDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 33 && data != null && resultCode == RESULT_OK) {
            SaveJobCardDetailsToServer();
        }
    }

    private void handeGetJobCardDetails(JSONArray jsonArray)
    {
        mswiperefreshlayout.setRefreshing(false);
        int arrayLength=0;
        if (jsonArray.length() > 0) {
            if(MainActivity.menuClick)
            arrayLength=50;
            else
                arrayLength=jsonArray.length();
            jobCardDetailsArrayList.clear();
            for (int i = 0; i < arrayLength; i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        jobCardDetailsArrayList.clear();
                    } else {
//                        if(!MainActivity.menuClick) {
                            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            Date currentdate1 = new Date();
                            String TodayDate = (df.format(currentdate1));
                            String jobcardDate = object.getString("JCDate");
                            int jserialNo=i+1;
                            String JSERIALNO=String.valueOf(jserialNo);
                            if (!menuCalenderSelectonDate)
                                TodayDate = (df.format(currentdate1));
                            else
                                TodayDate = currentdate;

                            if (jobcardDate.equalsIgnoreCase(TodayDate)) {
                                JobCardDetails documentTypes = new JobCardDetails();
                                documentTypes.setCustomerName(object.getString("CustomerName"));
                                documentTypes.setJobCardNo(object.getString("JobCardNo"));
                                documentTypes.setMobileNo(object.getString("MobileNo"));
//                                documentTypes.setSLNO(object.getString("SLNO"));
                                documentTypes.setSLNO(String.valueOf(i));
                                documentTypes.setStatus(object.getString("Status"));
                                documentTypes.setVehicleNo(object.getString("VehicleNo"));
                                documentTypes.setScreenName(object.getString("ScreenName"));
                                documentTypes.setJCDate(object.getString("JCDate"));
                                documentTypes.setJCTime(object.getString("JCTime"));
                                documentTypes.setTechnicianName(object.getString("Technician"));
//                        documentTypes.setMake(object.getString("Make"));
                                documentTypes.setModel(object.getString("ModelNo"));
                                documentTypes.setBlock("");
                                documentTypes.setJobRemarks(object.getString("JCRemarks"));

                                jobCardDetailsArrayList.add(documentTypes);
                            } else {
                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //  GetServiceManListRelatedToServices();
        }

        UIUpdateListLeadView();
//        MainActivity.menuClick=false;

    }

    private void UIUpdateListLeadView() {
        mswiperefreshlayout.setRefreshing(false);
        // GetServiceManListRelatedToServices();
        jobCardReportsAdapter = new JobCardReportsAdapter(context, jobCardDetailsArrayList, R.layout.jobcardreports_row_item);
        mJobCardRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mJobCardRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mJobCardRecyclerview.setHasFixedSize(true);
        mJobCardRecyclerview.setAdapter(jobCardReportsAdapter);

    }

    private void GetJobCardDetailedGrid(String jobCardNo, String screenName) {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    mswiperefreshlayout.setRefreshing(true);
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobCardNo);
                    jsonObject.accumulate("UserId", ProjectMethods.getUserId());
                    jsonObject.accumulate("ScreenName", screenName);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetJobCardDetailedGrid";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailedGrid, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetServiceListDetails(String jobCardNo) {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    mswiperefreshlayout.setRefreshing(true);
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobCardNo);
                    jsonObject.accumulate("UserId", ProjectMethods.getUserId());
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetJobCardDetailsReport";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailsReport, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class OnServiceCallCompleteListenerSpinner implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {
        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            progressDialog.dismiss();
            if (requestName.equalsIgnoreCase("GeneralMasterData")) {
                try {
                    handeSpinnerDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }

    private void handeSpinnerDetails(JSONArray jsonArray) {
        try {
            progressDialog.dismiss();
            documentTypesArrayList = new ArrayList<>();
            documentTypesArrayList.clear();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            DocumentTypes documentTypes = new DocumentTypes();
                            documentTypes.setId(object.getString("Id"));
                            documentTypes.setName(object.getString("Name"));
                            documentTypes.setShortName(object.getString("ShortName"));
                            documentTypesArrayList.add(documentTypes);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                GetServiceManListRelatedToServices();
                // GetServiceListDetails(JobCardNo);
               /* String[] us = new String[documentTypesArrayList.size()];
                for (int i = 0; i < documentTypesArrayList.size(); i++) {
                    us[i] = documentTypesArrayList.get(i).getName();

                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mUserListSpn.setAdapter(spinnerArrayAdapter);*/
//{"CustomerName":"B SRINIVASRAO","JCDate":"03-03-2021","JCTime":"18:48:00","JobCardNo":"57E296350077","Make":"HERO","MobileNo":"8897379831","ModelNo":"GLAMOUR","ModifiedDate":"04-03-2021","ModifiedTime":"15:10:43","Result":"","SLNO":"1","ScreenName":"JobCard","Status":"Pending","VehicleNo":"C3"}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

