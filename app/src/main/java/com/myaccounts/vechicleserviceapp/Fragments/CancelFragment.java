package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myaccounts.vechicleserviceapp.Activity.CustomViewPager;
import com.myaccounts.vechicleserviceapp.Activity.GeneralMasterActivity;
import com.myaccounts.vechicleserviceapp.Activity.MainActivity;
import com.myaccounts.vechicleserviceapp.Adapter.JAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.MyListData;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleTypes;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class CancelFragment extends Fragment implements View.OnClickListener {
    SharedPreferences sharedPreferences ;
    JAdapter adapter;
    List<Item> items;
    public String reamarksCheckString="";
    ItemsListAdapter myItemsListAdapter;
    ListView listView;
    private boolean userDeailsServiceCallCheck = true;
    public static String selectedItem;
    View view;
    public static boolean modelClic = false;
    public static EditText VehicleNoEdt, CustNameEdt,  CustMobileNoEdt, CustDateEdt,
            PlaceEdt, CustTimeInEdt,remarksEdt; //RemarksEdt;
    ArrayList dataModels;
    String name;
//    ListView listView;
//    private CustomAdapter adapter;
    public TextInputLayout CustTimeEdtLayout,CustDateEdtLayout;
    private String ServiceIdKey, ServiceId, BusinessDate, ServiceName, ServiceValue, mCustomerId = "", mVehicleNo = "";

    public static String vehicleNo, requestName, CustName, CustMobileNo, CustEmailId, JobCardNo, CustFromDate, Mileage,
            CustVehiclemakemodel,CustVehiclemake, CustVehicleType, CustVehicleModel, CustVehicleModelId, CustOdometerReading,
            Avgkmsperday, RegNo, Place, Remarks, CustTimeIn,Block,PreviousDateIn,PreviousTimeIn;
    private ProgressDialog pd;
    private Context context;
    private Button IdSaveBtn;

    private ArrayList<VehicleTypes> BlockArrayList = new ArrayList<>();
    int blockPos;
    private Spinner TyreTypesSpn, SpnBlockSelection;

    private int month, day, year;

    public static String ModelId;

    ImageButton IdMakeImg, IdModelImg, IdVehicleTypeImg, AddImgBtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ArrayList<VehicleDetails> vehicleDetailsArrayList;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    SessionManager sessionManager;
    public static String  jobCardId;

    CustomViewPager customViewPager;

    public static String selectBlock;
    public static String namenew = "kardas";
    String VehicleNo;
    JSONObject jsonObject;
    String jobcardNo;
    private ArrayList<VehicleDetails> vehicleDetailsDetailsArrayList;

    //RetrofitInterface apiService;CustVehicleModelEdt

    private AlertDialogManager dialogManager = new AlertDialogManager();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Log.d(TAG, "onCreateView: called");
        view = inflater.inflate(R.layout.fragment_cancel, container, false);
        context = getActivity();
        InitializeVariables();
//        modelDetailsArrayList = new ArrayList<>();

//        ValidationCheckForTabSelection();
        sessionManager = new SessionManager(getActivity());
        try {
            sharedPreferences = getActivity().getSharedPreferences("USER",MODE_PRIVATE);
            HashMap<String, String> user = sessionManager.getVehicleDetails();
            ModelId = user.get(SessionManager.KEY_VEHICLE_ID);
            jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);
            Log.e("Received in fragment",jobCardId);
            vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);
            CustName = user.get(SessionManager.KEY_CUSTOMER_NAME);
            CustMobileNo = user.get(SessionManager.KEY_MOBILE_NO);
            PreviousTimeIn=user.get(SessionManager.KEY_INTIME);
            PreviousDateIn=user.get(SessionManager.KEY_INDATE);
            if(!userDeailsServiceCallCheck)
            {
                VehicleNoEdt.setText(VehicleNo);
                CustMobileNoEdt.setText(CustMobileNo);
                CustNameEdt.setText(CustName);
                PlaceEdt.setText(Place);

            }
            Log.d("modeiddd", jobCardId);
            Log.d("modeiddd", ""+ModelId + "," + jobCardId + "," + vehicleNo +","+CustMobileNo+","+CustName+","+Place+","+user.get(SessionManager.KEY_BLOCK)+","+CustTimeIn);

        } catch (NullPointerException e) {
        }

        //   apiService = RetrofitBuilder.getApiBuilder().create(RetrofitInterface.class);

        CustFromDate = CustDateEdt.getText().toString().trim();
        vehicleDetailsArrayList = new ArrayList<>();
        Log.d("modeiddd", ModelId + "," + jobCardId + "," + vehicleNo +","+CustMobileNo+","+CustName+","+CustFromDate+","+Block+","+CustTimeIn);
        dateFormat();

        GetTheBusinessDate();

//        GetBlockDetailsFromgeneralMaster();
        //getVehicleDetails();
//         new GetDetailsBasedOnVehicleNo().execute();

        try {

            if (jobCardId.equalsIgnoreCase("empty")) {
//                NewJobCardDetailsMain.validationCheck=true;
//                Toast.makeText(getActivity(),"EMPTY"+NewJobCardDetailsMain.validationCheck,Toast.LENGTH_LONG).show();
//            serviceMasterDetailsArrayList.clear();
            } else {
                if(userDeailsServiceCallCheck)
                    GetUserDetails();
                //GetDetailsBasedOnVehicleNo();
            }
        } catch (NullPointerException e) {
            Log.e("error caught pp ",e.toString());
        }




        Bundle gameData = new Bundle();
        gameData.putString("key", namenew);
        Intent intent = getActivity().getIntent();
        intent.putExtras(gameData);
        CustNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shredPrefencesSaving(SessionManager.KEY_CUSTOMER_NAME,CustNameEdt.getText().toString());
                /*editor.putString(SessionManager.KEY_CUSTOMER_NAME, CustNameEdt.getText().toString());
                editor.commit();*/
            }
        });

        CustMobileNoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shredPrefencesSaving(SessionManager.KEY_CONTACT_NO,CustMobileNoEdt.getText().toString());
                /*editor.putString(SessionManager.KEY_CONTACT_NO, CustMobileNoEdt.getText().toString());
                editor.commit();*/
            }
        });

        PlaceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shredPrefencesSaving(SessionManager.KEY_PLACE,PlaceEdt.getText().toString());
               /* editor.putString(SessionManager.KEY_PLACE, PlaceEdt.getText().toString());
                editor.commit();*/
            }
        });

        VehicleNoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                vehicleNo = VehicleNoEdt.getText().toString();
//                shredPrefencesSaving(SessionManager.KEY_VEHICLE_NO,VehicleNoEdt.getText().toString());
//                editor.putString(SessionManager.KEY_VEHICLE_NO, VehicleNoEdt.getText().toString());
//                editor.commit();
                GetDetailsBasedOnVehicleNo(vehicleNo);
            }
        });


        return view;
    }


    private void GetTheBusinessDate() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId",jobCardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetJobCardDetailsReport";
                    Log.e("backend servise ","_"+requestName+jsonObject);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailsReport, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InitializeVariables() {
        try {
            sharedpreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            VehicleNoEdt = (EditText) view.findViewById(R.id.VehicleNoEdt);
            CustNameEdt = (EditText) view.findViewById(R.id.CustNameEdt);
            CustMobileNoEdt = (EditText) view.findViewById(R.id.CustMobileNoEdt);
            CustDateEdt = (EditText) view.findViewById(R.id.CustDateEdt);
            PlaceEdt = (EditText) view.findViewById(R.id.PlaceEdt);
            CustTimeInEdt = (EditText) view.findViewById(R.id.CustTimeInEdt);
            remarksEdt=(EditText)view.findViewById(R.id.RemarksEdt);
            CustTimeEdtLayout=(TextInputLayout)view.findViewById(R.id.CustTimeEdtLayout);
            CustDateEdtLayout=(TextInputLayout)view.findViewById(R.id.CustDateEdtLayout);

            IdSaveBtn = (Button) view.findViewById(R.id.IdNextBtn);
            IdSaveBtn.setOnClickListener(this);

            //  CustTimeInEdt.setOnClickListener(this);
//            SparePartEdt.setOnClickListener(this);
            // CustDateEdt.setText(ProjectMethods.GetCurrentDate());
            CustDateEdt.setText(ProjectMethods.getBusinessDate());
            CustTimeInEdt.setText(ProjectMethods.GetCurrentTime());

            VehicleNoEdt.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    shredPrefencesSaving(SessionManager.KEY_VEHICLE_NO,VehicleNoEdt.getText().toString());
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    String vehicleNo = String.valueOf(s);
                    if (vehicleNo.length() >= 8) {
//                        GetDetailsBasedOnVehicleNo(vehicleNo);
                    }
                 /*   else{
                        Toast.makeText(getActivity(), "Please Enter Correct VehicleNo not Less 6", Toast.LENGTH_SHORT).show();
                    }*/

                }
            });
            int valueStr = 0;
            float finalAmtStr = 0.00f;

            //CustDateEdt.setText(BusinessDate);

            IdSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(newValidation()) {
                        try {
                            CustomDailog("Job Card Cancellation", "Do You Want to Cancel This Job Card?", 133, "Yes", 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                    }
                }
            });
            MyListData[] myListData = new MyListData[] {
                    new MyListData(view.getResources().getString(R.string.cancel_type1), android.R.drawable.ic_dialog_email),
                    new MyListData(view.getResources().getString(R.string.cancel_type2), android.R.drawable.ic_dialog_info),
                    new MyListData(view.getResources().getString(R.string.cancel_type3), android.R.drawable.ic_delete),
                    new MyListData(view.getResources().getString(R.string.cancel_type4), android.R.drawable.ic_dialog_dialer),
                    new MyListData(view.getResources().getString(R.string.cancel_type5), android.R.drawable.ic_dialog_alert),
                    new MyListData(view.getResources().getString(R.string.cancel_type6), android.R.drawable.ic_dialog_map),
                    new MyListData(view.getResources().getString(R.string.cancel_type7), android.R.drawable.ic_dialog_email),
                    new MyListData(view.getResources().getString(R.string.cancel_type8), android.R.drawable.ic_dialog_info),
                    new MyListData(view.getResources().getString(R.string.cancel_type9), android.R.drawable.ic_delete),
                    new MyListData(view.getResources().getString(R.string.cancel_type10), android.R.drawable.ic_dialog_dialer),
                    new MyListData(view.getResources().getString(R.string.cancel_type11), android.R.drawable.ic_dialog_alert),
                    new MyListData(view.getResources().getString(R.string.cancel_type12), android.R.drawable.ic_dialog_map),
                    new MyListData(view.getResources().getString(R.string.cancel_type13), android.R.drawable.ic_dialog_map)
            };

            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
            adapter = new JAdapter(myListData,getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(adapter);


            /*listView = (ListView) view.findViewById(R.id.listView);
            initItems();
            myItemsListAdapter = new ItemsListAdapter(getActivity(), items);
            listView.setAdapter(myItemsListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                }});*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initItems() {
        items = new ArrayList<Item>();

//        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for(int i=0; i<arrayText.length(); i++){
            String s = arrayText.getString(i);
            boolean b = false;
            Item item = new Item(s, b);
            items.add(item);
        }

//        arrayDrawable.recycle();
        arrayText.recycle();
    }


    private void CustomDailog(String Title, String msg, int value, String btntxt, int position) {
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
    private void dateFormat() {
        try {
            final Calendar c = Calendar.getInstance();
            SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
            final Date date = new Date();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            String currentdate = ss.format(date);

           /* TimeOutDateEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog picker = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    int month = monthOfYear + 1;
                                    String fm = "" + month;
                                    String fd = "" + dayOfMonth;
                                    if (month < 10) {
                                        fm = "0" + month;
                                    }
                                    if (dayOfMonth < 10) {
                                        fd = "0" + dayOfMonth;
                                    }
                                    String date = "" + fd + "-" + fm + "-" + year;
                                    TimeOutDateEdt.setText(date);
                                    // TimeOutDateEdt.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear+1 < 10 ? ("0" + monthOfYear) : (monthOfYear+1)) + "-" + year);
                                    // ReceiptDateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ClearData() {
        try {
            VehicleNoEdt.setText("");
            CustNameEdt.setText("");
            CustMobileNoEdt.setText("");
            PlaceEdt.setText("");
            CustTimeInEdt.setText("");
            // RemarksEdt.setText("");

            onDestroy();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetBusinessDate(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                BusinessDate = jsonArray.getJSONObject(0).getString("Date");
                CustDateEdt.setText(BusinessDate);
                Log.d("dsdsdds", "dsdsdsd");
            }
//            GetBlockDetailsFromgeneralMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class OnServiceCallCompleteListenerImplData implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {
            try {
                Log.e("inside object","object received");
            }
            catch (Exception e){
                Log.e("error in object","objecterror");
            }
        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    }
    private void handleGetDetailsByVehicleNo(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                    } catch (Exception e) {
                    }
                }
//            GetBlockDetailsFromgeneralMaster();
            }
        }catch (Exception e){

        }
    }
    private void GetDetailsBasedOnVehicleNo(CharSequence VehicleNo) {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("VehicleNo", VehicleNo);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetDetailsByVehicleNo";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetDetailsByVehicleNo, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetUserDetails() {

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("JobCardId", jobCardId);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                Log.e("GetUserDetailsservice", "" + serviceCall);
                requestName = "GetJobCardDetailsReport";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailsReport, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }

    private void handeVehicleDetails(JSONArray jsonArray) {

//        mswiperefreshlayout.setRefreshing(false);

        try {

            if (jsonArray.length() > 0) {

                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Log.e("Executing block","I am here");
                    //   mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    CustName = jsonArray.getJSONObject(0).getString("CustomerName");
                    Log.e("CustName",CustName);
                    CustVehiclemake = jsonArray.getJSONObject(0).getString("Make");
                    Log.e("CustVehiclemake",CustVehiclemake);
//                    Toast.makeText(getActivity().getBaseContext(),"Model"+CustVehiclemake,Toast.LENGTH_SHORT).show();
                    CustMobileNo = jsonArray.getJSONObject(0).getString("MobileNo");
                    CustVehiclemakemodel = jsonArray.getJSONObject(0).getString("ModelName");
                    ModelId = jsonArray.getJSONObject(0).getString("ModelNo");
                    String Result = jsonArray.getJSONObject(0).getString("Result");
                    VehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");
                    //CustVehicleType = jsonArray.getJSONObject(0).getString("VehicleType");
                    Place = jsonArray.getJSONObject(0).getString("Place");
                    CustOdometerReading = jsonArray.getJSONObject(0).getString("OMReading");
                    Mileage = jsonArray.getJSONObject(0).getString("Mileage");
                    Avgkmsperday = jsonArray.getJSONObject(0).getString("AvgKmsPerDay");
                    Block= jsonArray.getJSONObject(0).getString("Block");
                    Remarks = jsonArray.getJSONObject(0).getString("Remarks");
                    PreviousTimeIn= jsonArray.getJSONObject(0).getString("InTime");
                    PreviousDateIn = jsonArray.getJSONObject(0).getString("JCDate");
                    VehicleNoEdt.setText(VehicleNo);
                    CustMobileNoEdt.setText(CustMobileNo);
                    CustNameEdt.setText(CustName);
                    PlaceEdt.setText(Place);
                    CustTimeEdtLayout.setVisibility(View.VISIBLE);
                    CustDateEdtLayout.setVisibility(View.VISIBLE);
                    CustDateEdt.setText(PreviousDateIn);
                    CustTimeInEdt.setText(PreviousTimeIn);
//                    tClock.setVisibility(View.INVISIBLE);
                    Log.e("Executing block","I am here"+jsonObject.toString());
                    Log.e("Executing block","I am here"+vehicleNo+CustMobileNo);
                    Log.e("Executing block","I am here"+CustName);
                    Log.e("Executing block","I am here"+Place);
                    Log.e("Executing block","I am here"+CustOdometerReading);
                    Log.e("Executing block","I am here"+Mileage);
                    Log.e("Executing block","I am here"+Avgkmsperday);
                    Log.e("Executing block","I am here"+Block);
                    Log.e("Executing block","I am here"+CustVehicleModel);
                    Log.e("Executing block","I am here"+PreviousDateIn);
                    Log.e("Executing block","I am here"+PreviousTimeIn);
                    Log.e("Executing block","I am here"+ModelId);
                    // SpnBlockSelection.setSelection(BlockArrayList.indexOf(Block));
                    // Log.e("selected", String.valueOf(BlockArrayList.get());
//                    GetBlockDetailsFromgeneralMaster();
//                    sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModelEdt.getText().toString(), CustVehiclemakemodel,Remarks, "car",ModelId);
//                    sessionManager.storeEditDetails(CustName, ModelId, CustVehiclemakemodel, CustMobileNo,Remarks,PreviousDateIn,PreviousTimeIn);
                }

                //  GetServiceManListRelatedToServices();
            }
        } catch (Exception e) {
        }


//        UIUpdateListLeadView();

    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.IdLeftArrow:
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.IdRightArrow:
                    Intent intent1 = new Intent(getActivity(), GeneralMasterActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.IdSaveBtn:
                    Log.d("clicking", "clicking");
//                    Validation();
                    break;
                case R.id.IdClearBtn:

                    break;

                case R.id.CustTimeInEdt:
                    try {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker picker, int hour,
                                                          int minute) {

                                        String am_pm;
                                        if (Build.VERSION.SDK_INT >= 23) {
                                            hour = picker.getHour();
                                            minute = picker.getMinute();
                                        } else {
                                            hour = picker.getCurrentHour();
                                            minute = picker.getCurrentMinute();
                                        }
                                        if (hour > 12) {
                                            am_pm = "PM";
                                            hour = hour - 12;
                                        } else {
                                            am_pm = "AM";
                                        }
                                        CustTimeInEdt.setText(hour + ":" + minute + " " + am_pm);
                                        // CustTimeInEdt.setText(hourOfDay + ":" + minute + " " + "AM");
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public boolean Validation() {

        Log.d("validatedd", "validated");
        boolean result = true;
        boolean resultValue = true;
        vehicleNo = VehicleNoEdt.getText().toString().trim();
        CustName = CustNameEdt.getText().toString().trim();
        CustEmailId = CustEmailIdEdt.getText().toString().trim();
        CustMobileNo = CustMobileNoEdt.getText().toString().trim();
        // JobCardNo = JobCardNoEdt.getText().toString().trim();
        CustFromDate = CustDateEdt.getText().toString().trim();
        Mileage = MileageEdt.getText().toString().trim();
        CustVehiclemakemodel = CustVehiclemakemodelEdt.getText().toString().trim();
        CustVehicleModel = CustVehicleModelEdt.getText().toString().trim();
        CustOdometerReading = CustOdometerReadingEdt.getText().toString().trim();
        Avgkmsperday = AvgkmsperdayEdt.getText().toString().trim();
        RegNo = RegNoEdt.getText().toString().trim();
        Place = PlaceEdt.getText().toString().trim();
        CustTimeIn = CustTimeInEdt.getText().toString().trim();
        selectBlock = SpnBlockSelection.getSelectedItem().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        if (vehicleNo.isEmpty()) {
            VehicleNoEdt.setError("Please Enter VehicleNo");
            result = false;
            resultValue = false;
        } else if (CustName.isEmpty()) {
            CustNameEdt.setError("Please Enter Name");
            result = false;
            resultValue = false;
        } else if (Place.isEmpty()) {
            PlaceEdt.setError("Please Enter Place");
            result = false;
            resultValue = false;
        } else if (CustMobileNo.length() == 0 || CustMobileNo.length() < 10) {
            CustMobileNoEdt.setError("Please Enter Valid Mobile No");
            result = false;
            resultValue = false;
        } else if (CustVehicleModel.isEmpty()) {
            CustVehicleModelEdt.setError("Please Enter Vehicle Model");
            result = false;
            resultValue = false;

        } else if (CustFromDate.isEmpty()) {
            CustDateEdt.setError("Please Enter CustomerDate");
            result = false;
            resultValue = false;
        } else if (CustTimeIn.isEmpty()) {
            CustTimeInEdt.setError("Please Enter TimeIn");
            result = false;
            resultValue = false;
        } else {

//            Bundle b = new Bundle();
//            Fragment fragment = new LatestNewServiceSelectedFragment();
//            fragment.setArguments(b);
//            getFragmentManager().beginTransaction().replace(R.id.viewpager, fragment);
//            customViewPager = new CustomViewPager(getActivity());
//            customViewPager.setSwipeable(true);
            sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModel, CustVehiclemakemodel,Remarks, "car");


            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(1).select();
            // Toast.makeText(getContext(), "Enterd Succes", Toast.LENGTH_SHORT).show();
        }
        int fromDate = ProjectMethods.GetDateToInt(CustFromDate);
       *//* int toDate = ProjectMethods.GetDateToInt(CustToDate);
        if (toDate != 0) {
            if (fromDate > toDate) {
                Toast toast1 = Toast.makeText(context, "  Out Date  Greater Than From Date  ", Toast.LENGTH_SHORT);
                View view1 = toast1.getView();
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                TextView v1 = (TextView) toast1.getView().findViewById(android.R.id.message);
                v1.setTextColor(Color.WHITE);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                // Toast.makeText(context, "Please Select Atleast one Service", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }*//*

//        if (!resultValue) {
//            Toast toast = Toast.makeText(context, "  Please Enter Mandatory Fields  ", Toast.LENGTH_SHORT);
//            View view = toast.getView();
//            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//            v.setTextColor(Color.WHITE);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//            // isFirstRun = false;
//        }

        if (!CustEmailId.isEmpty()) {
            if (!CustEmailId.matches(EMAIL_PATTERN) == true) {

                CustEmailIdEdt.setError("Please Enter Valid EmailId");
                CustEmailIdEdt.requestFocus();
                CustEmailIdEdt.setFocusable(true);
                result = false;
            }
        }

       *//* else if (CustVehiclemakemodel.length() == 0) {
            CustVehiclemakemodelEdt.requestFocus();
            CustVehiclemakemodelEdt.setError("Please Enter VehicleMake");
            result = false;

        } else if (CustVehicleModel.length() == 0) {
            CustVehicleModelEdt.requestFocus();
            CustVehicleModelEdt.setError("Please Enter VehicleModel");
            result = false;

        } else if (CustOdometerReading.length() == 0) {
            CustOdometerReadingEdt.requestFocus();
            CustOdometerReadingEdt.setError("Please Enter OdometerReadingNo");
            result = false;

        }*//*
     *//* else if (JobCardNo.length() == 0 || JobCardNo.toString().equalsIgnoreCase("")) {
            JobCardNoEdt.requestFocus();
            JobCardNoEdt.setError("Please Enter JobCardNo");
            result = false;

        } *//*

     *//* else if (RegNo.toString().length() == 0 || RegNo.toString().equalsIgnoreCase("")) {

            RegNoEdt.requestFocus();
            RegNoEdt.setError("Please Enter RegNo");
            result = false;
        }*//*
     *//*   else if (Avgkmsperday.toString().length() == 0 || Avgkmsperday.toString().equalsIgnoreCase("")) {

            AvgkmsperdayEdt.requestFocus();
            AvgkmsperdayEdt.setError("Please Select AvgKmsPerDay");
            result = false;
        }*//*
     *//* else if (Remarks.toString().length() == 0 || Remarks.toString().equalsIgnoreCase("")) {

            RemarksEdt.requestFocus();
            RemarksEdt.setError("Please Select Remarks");
            result = false;
        }*//*
        return result;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 133 && data != null && resultCode == RESULT_OK) {
            CancelData();
            /*Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);*/
        }

    }

    private void CancelData() {
        Log.e("GetEditServiceDetails", "GetEditServiceDetails called");

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
//                {"JobCardId":"56E296340066","CancelDate":"02-03-2021","CancelTime":"19:29:00","CancelBy":"M kesava nadh","ReasonforCancel":"sdfsfsfsfsfs"}
//                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();//JobCardId, CancelDate, CancelTime, CancelBy, ReasonforCancel
                jsonObject.accumulate("JobCardId", jobCardId);
                jsonObject.accumulate("CancelDate", CustDateEdt.getText().toString());
                jsonObject.accumulate("CancelTime", CustTimeInEdt.getText().toString());
                jsonObject.accumulate("CancelBy", CustNameEdt.getText().toString());
                String checkListStr="";
                Gson gson = new Gson();
                String json = sharedPreferences.getString("Set", "");
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> arrPackageData = gson.fromJson(json, type);
                    for(String data:arrPackageData) {
                        checkListStr=checkListStr+""+data+", ";
                    }
                jsonObject.accumulate("ReasonforCancel", remarksEdt.getText().toString()+", "+checkListStr);
                Log.e("Reasons_for_cancel"," "+jsonObject.toString());
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                Log.d("servicecall", "" + serviceCall);

                requestName = "CancelJobCard";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.CancelJobCard, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }


    private class OnServiceCallCompleteListenerImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            Log.e("vehicccc", "vehiclccc"+requestName);
            if (requestName.equalsIgnoreCase("GetJobCardDetailsReport")) {
                try {
                    Log.e("vehicccc", "vehiclccc");
                    handeVehicleDetails(jsonArray);
//                    GetBlockDetailsFromgeneralMaster();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("CancelJobCard")) {
                try {
                    handeCancelJobCard(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetDetailsByVehicleNo")) {
                try {
                    handeVehicleDetailsByVehicleNumber(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GeneralMasterData")) {
                try {
//                    handleTyreTypes(jsonArray);
                    //  handeDocumentDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetSubService")) {
                try {
//                    handleGetSubService(jsonArray);
                    //  handeDocumentDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("ServiceMaster")) {
                try {
//                    handeServiceMasterDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetJobCardNumber")) {
                try {
//                    handeGetJobCardNumberDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("SubServiceMaster")) {
                try {
//                    handeGetSubServiceMasterDetails(jsonArray);
                    Log.d("caaliingaaaa", "calling");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetServiceAgstData")) {
                try {
//                    swipeRefreshLayout.setRefreshing(false);
//                    handeGetSubServiceMasterDetails(jsonArray);
                    Log.d("caaliing", "calling");
                    // handeGetServiceAgstData(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("Getlist")) {
                try {
//                    handeGetlistDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("SaveJobCardDetails_New")) {
                try {
//                    handeSaveJobCardDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
//            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void handeCancelJobCard(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                String result = jsonArray.getJSONObject(0).getString("Result");
//                    String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");

//                    Log.d("jobbbbbbb", JobCardNumber);
                if (result != null) {

                    sessionManager.clearSession();
                    MainActivity.comingfrom="2";
                    Toast.makeText(view.getContext(), result , Toast.LENGTH_SHORT).show();
//                    view.finish();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
//                    ClearData();
//                    isFirstRun = true;
                }
//                JobCardNoEdt.setText(JobCardNumber);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handeVehicleDetailsByVehicleNumber(JSONArray jsonArray) {
        try {
            VehicleDetails vehicleDetails = new VehicleDetails();
            if (jsonArray.length() > 0) {
                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    //toast.show();
                } else {
                    //[{"CustomerId":"CUS10208","CustomerName":"ahjah","Make":"HYUNDAI","MobileNo":"909090909090","Model":"I 20","ModelId":"VH10379","Result":"","VehicleNo":"AP179090909090","vehicletype":"Car"}]
//                    mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    String CustomerName = jsonArray.getJSONObject(0).getString("CustomerName");
                    String MobileNo = jsonArray.getJSONObject(0).getString("MobileNo");
                    mVehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");
//                    String vehicletype=jsonArray.getJSONObject(0).getString("vehicletype");
                    String place=jsonArray.getJSONObject(0).getString("Place");
                    CustNameEdt.setText(CustomerName);
                    PlaceEdt.setText(place);
//                    CustEmailIdEdt.setText(EmailId);
                    CustMobileNoEdt.setText(MobileNo);

                }
            }
            vehicleDetailsArrayList.add(vehicleDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    class GetDetailsBasedOnVehicleNo extends AsyncTask<String, String, String> {
//        /**
//         * Before starting background thread Show Progress Dialog
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
////                    showProgressDialog();
//                }
//            });
//
//        }
//
//        /**
//         * Creating Application
//         */
//        @SuppressWarnings("deprecation")
//        protected String doInBackground(String... args) {
//
//            final OkHttpClient client = new OkHttpClient();
//            FormBody.Builder formBuilder = new FormBody.Builder();
//
//            JSONObject postData = new JSONObject();
//
//            try {
//                postData.put("VehicleNo", "TS68555");
//            } catch (JSONException e) {
//
//            }
//
//            RequestBody formBody = RequestBody.create(MEDIA_TYPE, postData.toString());
//
//            final okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url("http://Myaccountsonline.co.in/TestServices/WheelAlignment.svc/GetDetailsByVehicleNo")
//                    .post(formBody)
//                    .build();
//
//
//            Log.d("requessssss", new Gson().toJson(postData));
////            if (dDialog.isShowing() && dDialog != null) {
////                dDialog.dismiss();
////            }
//            client.newCall(request).enqueue(new okhttp3.Callback() {
//                @Override
//                public void onFailure(okhttp3.Call call, IOException e) {
//                    String mMessage = e.getMessage();
//                    Log.w("failure Response", mMessage);
//                }
//
//                @Override
//                public void onResponse(okhttp3.Call call, okhttp3.Response response)
//                        throws IOException {
////                    if (dDialog.isShowing() && dDialog != null) {
////                        dDialog.dismiss();
////                    }
//                    Log.d("eereeeee", new Gson().toJson(response));
//                    String mMessage = response.body().string();
//                    if (response.isSuccessful()) {
////                        hideProgressDialog();
//                        try {
//
//                            JSONArray json = new JSONArray();
////                            int success = json.getInt("success");
//                            Log.d("dasdasdasd", "" + json.toString());
//
//
////                                result = json.getJSONArray(TAG_RESULTS);
//                            for (int i = 0; i < json.length(); i++) {
//                                JSONObject c = json.getJSONObject(i);
//                                // successfully created product
//                                String activeStatus = c.getString("CustomerId");
//                                String result = c.getString("CustomerName");
//
//
//                                Log.d("dsdss", result);
//
//
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                    }
//                                });
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//
//            });
//
//            return null;
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         **/
//        protected void onPostExecute(String status) {
//            // dismiss the dialog once done
//
//
//        }
//
//    }
//new code for validation as per bug
    private boolean newValidation(){
        boolean result = true;
        boolean resultValue = true;
        String remarksStr=remarksEdt.getText().toString();
        if (remarksStr.isEmpty()) {
            remarksEdt.setError("Please Enter Remarks");
            result = false;
            resultValue = false;
        }
        if (!resultValue) {
//                NewJobCardDetailsMain.validationCheck=true;
            Toast toast = Toast.makeText(getActivity().getBaseContext(), "  Please Enter Mandatory Fields  ", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            // isFirstRun = false;
        }

        return result;
    }
    public void shredPrefencesSaving(String keyvalue,String value){
        editor.putString(keyvalue, value);
        editor.commit();
     /*sessionManager.clearSession();
    sessionManager.storefirsFragmentDetails(VehicleNoEdt.getText().toString(),CustMobileNoEdt.getText().toString(),CustNameEdt.getText().toString(),PlaceEdt.getText().toString(),
            SpnBlockSelection.getSelectedItem().toString(),CustOdometerReadingEdt.getText().toString(),MileageEdt.getText().toString().trim(),
            AvgkmsperdayEdt.getText().toString().trim(),ModelId,CustVehiclemakemodelEdt.getText().toString().trim(),"",CustVehicleTypeEdt.getText().toString().trim());*/
    }
    private void initListViewData()  {
        UserAccount tom = new UserAccount("Tom","admin");
        UserAccount jerry = new UserAccount("Jerry","user");
//        UserAccount donald = new UserAccount("Donald","guest", false);

        UserAccount[] users = new UserAccount[]{tom,jerry};

        // android.R.layout.simple_list_item_checked:
        // ListItem is very simple (Only one CheckedTextView).
       /* ArrayAdapter<UserAccount> arrayAdapter
                = new ArrayAdapter<UserAccount>(getActivity(), android.R.layout.simple_list_item_checked , users);

        this.listView.setAdapter(arrayAdapter);*/

        for(int i=0;i< users.length; i++ )  {
            this.listView.setItemChecked(i,users[i].isActive());
        }
    }

    // When user click "Print Selected Items".
    public void printSelectedItems()  {

        SparseBooleanArray sp = listView.getCheckedItemPositions();

        StringBuilder sb= new StringBuilder();

        for(int i=0;i<sp.size();i++){
            if(sp.valueAt(i)==true){
                UserAccount user= (UserAccount) listView.getItemAtPosition(i);
                // Or:
                // String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();
                //
                String s= user.getUserName();
                sb = sb.append(" "+s);
                selectedItem=selectedItem + " "+sb;
                sessionManager.storeSelectedString(sb.toString());
            }
        }
//        sessionManager.getSelectedString());
        Toast.makeText(getActivity(), "Selected items are: "+sb.toString(), Toast.LENGTH_LONG).show();
    }
    public class Item {
        boolean checked;
//        Drawable ItemDrawable;
        String ItemString;
        Item(String t, boolean b){
//            ItemDrawable = drawable;
            ItemString = t;
            checked = b;
        }

        public boolean isChecked(){
            return checked;
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
//        ImageView icon;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public boolean isChecked(int position) {
            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((getActivity())).getLayoutInflater();
                rowView = inflater.inflate(R.layout.checkbox_layout, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
//                viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
                viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

//            viewHolder.icon.setImageDrawable(list.get(position).ItemDrawable);
            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).ItemString;
            viewHolder.text.setText(itemStr);

            viewHolder.checkBox.setTag(position);

            /*
            viewHolder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).checked = b;

                    Toast.makeText(getApplicationContext(),
                            itemStr + "onCheckedChanged\nchecked: " + b,
                            Toast.LENGTH_LONG).show();
                }
            });
            */

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    selectedItem=selectedItem+itemStr;
                }
            });

            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }
    }
}