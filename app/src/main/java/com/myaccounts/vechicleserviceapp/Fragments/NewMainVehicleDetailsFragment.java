package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adeel.library.BuildConfig;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.myaccounts.vechicleserviceapp.Activity.CustomViewPager;
import com.myaccounts.vechicleserviceapp.Activity.DrawingSignatureActivity;
import com.myaccounts.vechicleserviceapp.Activity.GeneralMasterActivity;
import com.myaccounts.vechicleserviceapp.Activity.MakeListActivity;
import com.myaccounts.vechicleserviceapp.Activity.ModelListActivity;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.SparePartsActivity;
import com.myaccounts.vechicleserviceapp.Activity.VehicleTypeActivity;
import com.myaccounts.vechicleserviceapp.MainActivity;
import com.myaccounts.vechicleserviceapp.Pojo.CategoryModelClass;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ModelDetails;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.TechnicianTypes;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleTypes;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.myaccounts.vechicleserviceapp.Utils.UploadimageServer;
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.myaccounts.vechicleserviceapp.network.InfDbSpecs;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
//import static com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain.tClock;
import static com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain.tabLayout;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.MEDIA_TYPE;
import static com.myaccounts.vechicleserviceapp.network.InfDbSpecs.TABLE_SERVICES_DATA;

public class NewMainVehicleDetailsFragment extends Fragment implements View.OnClickListener {
    private DBManager dbManager;
    Cursor cursor;
    final String[] from = new String[] { DatabaseHelper1._ID,
            DatabaseHelper1.SUBJECT, DatabaseHelper1.DESC };
    HashMap<String, String> hmap = new HashMap<String, String>();
    private boolean userDeailsServiceCallCheck=true;
    private ArrayList<ModelDetails> modelDetailsArrayList;
    View view;
    public static boolean modelClick=false;
    public static EditText VehicleNoEdt, CustVehicleTypeEdt, CustNameEdt, CustEmailIdEdt, CustMobileNoEdt, QtyEdt, JobCardNoEdt, SparePartEdt, CustDateEdt, CustVehiclemakemodelEdt, CustVehicleModelEdt, CustOdometerReadingEdt, AvgkmsperdayEdt,
            RegNoEdt, MileageEdt, PlaceEdt, CustTimeInEdt; //RemarksEdt;
    public TextInputLayout CustTimeEdtLayout,CustDateEdtLayout;
    private String ServiceIdKey, ServiceId, BusinessDate, ServiceName, ServiceValue, mCustomerId = "", mVehicleNo = "";

    public static String vehicleNo, requestName, CustName, CustMobileNo, CustEmailId, JobCardNo, CustFromDate, Mileage,
            CustVehiclemakemodel,CustVehiclemake, CustVehicleType, CustVehicleModel, CustVehicleModelId, CustOdometerReading,
            Avgkmsperday, RegNo, Place, Remarks, CustTimeIn,Block,PreviousDateIn,PreviousTimeIn,Technician;
    private ProgressDialog pd;
    private Context context;
    private Button IdSaveBtn;

    private ArrayList<VehicleTypes> BlockArrayList = new ArrayList<>();
    int blockPos;
    boolean blockSelection=false;

    private ArrayList<TechnicianTypes> TechnicianArrayList = new ArrayList<>();
    int technicianPos;
    private Spinner TyreTypesSpn, SpnBlockSelection,SpnTechnicianSelection;

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

    public static String selectBlock="";
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
        Log.d("ANUSHA ","___3"+"Oncreateview NewMainVehicleDetailFragment");
        Log.d("ANUSHA ", "onCreateView: called"+"NewMainVehicleDetailsFragment");
        view = inflater.inflate(R.layout.new_main_vehicle_detail_fragment, container, false);
        dbManager = new DBManager(view.getContext());
        dbManager.open();
        InitializeVariables();

        modelDetailsArrayList = new ArrayList<>();

//        ValidationCheckForTabSelection();
        sessionManager = new SessionManager(getActivity());
        try {

            HashMap<String, String> user = sessionManager.getVehicleDetails();
            ModelId = user.get(SessionManager.KEY_VEHICLE_ID);
            jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);
            Log.e("Received in fragment",jobCardId);
            vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);
            CustName = user.get(SessionManager.KEY_CUSTOMER_NAME);
            CustMobileNo = user.get(SessionManager.KEY_MOBILE_NO);
            Place = user.get(SessionManager.KEY_PLACE);
            Block = user.get(SessionManager.KEY_BLOCK);
            Technician= user.get(SessionManager.KEY_TECHINICIANNAME);
            PreviousTimeIn=user.get(SessionManager.KEY_INTIME);
            PreviousDateIn=user.get(SessionManager.KEY_INDATE);
            Mileage=user.get(SessionManager.KEY_NEXT_MILEAGE);
            if(!userDeailsServiceCallCheck)
            {
                VehicleNoEdt.setText(VehicleNo);
                CustMobileNoEdt.setText(CustMobileNo);
                CustNameEdt.setText(CustName);
                PlaceEdt.setText(Place);
                CustOdometerReadingEdt.setText(CustOdometerReading);
                MileageEdt.setText(Mileage);
                AvgkmsperdayEdt.setText(Avgkmsperday);
                CustVehicleModel = CustVehicleModelEdt.getText().toString().trim();
                CustVehicleModelEdt.setText(CustVehiclemakemodel);
                CustVehiclemakemodelEdt.setText(CustVehiclemake);
                CustVehicleTypeEdt.setText(CustVehicleType);

//                CustTimeEdtLayout.setVisibility(View.VISIBLE);
//                CustDateEdtLayout.setVisibility(View.VISIBLE);
            }
            Log.d("modeiddd", jobCardId);
            Log.d("modeiddd", ""+ModelId + "," + jobCardId + "," + vehicleNo +","+CustMobileNo+","+CustName+","+Place+","+user.get(SessionManager.KEY_BLOCK)+","+CustTimeIn);
            Log.d("ANUSHA ", ""+ModelId + "," + jobCardId + "," + vehicleNo +","+CustMobileNo+","+CustName+","+Place+","+user.get(SessionManager.KEY_BLOCK)+","+CustTimeIn);

        } catch (NullPointerException e) {
        }

        //   apiService = RetrofitBuilder.getApiBuilder().create(RetrofitInterface.class);

        CustFromDate = CustDateEdt.getText().toString().trim();
        vehicleDetailsArrayList = new ArrayList<>();
        Log.d("modeiddd", ModelId + "," + jobCardId + "," + vehicleNo +","+CustMobileNo+","+CustName+","+CustFromDate+","+Block+","+CustTimeIn);
        dateFormat();



        //getVehicleDetails();
//         new GetDetailsBasedOnVehicleNo().execute();

        try {

            if (jobCardId.equalsIgnoreCase("empty")) {
                shredPrefencesSaving(sessionManager.KEY_BLOCK,"");
                shredPrefencesSaving(sessionManager.KEY_TECHINICIANNAME,"");
                GetTheBusinessDate();
                GetBlockDetailsFromgeneralMaster();

//                NewJobCardDetailsMain.validationCheck=true;
//                Toast.makeText(getActivity(),"EMPTY"+NewJobCardDetailsMain.validationCheck,Toast.LENGTH_LONG).show();
//            serviceMasterDetailsArrayList.clear();
            } else {
                if(userDeailsServiceCallCheck)
                    GetUserDetails();
//                Toast.makeText(getActivity(),"ANUSHA",Toast.LENGTH_LONG).show();
                //GetDetailsBasedOnVehicleNo();
            }
        } catch (NullPointerException e) {
            Log.e("error caught pp ",e.toString());
        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                userDeailsServiceCallCheck=false;
                /*Log.d("ANUSHA ","___key"+VehicleNoEdt.getText().toString());
                Log.d("ANUSHA ","___key"+CustMobileNoEdt.getText().toString());
                Log.d("ANUSHA ","___key"+CustNameEdt.getText().toString());
                Log.d("ANUSHA ","___key"+PlaceEdt.getText().toString());
                Log.d("ANUSHA ","___key"+SpnBlockSelection.getSelectedItem().toString());
                Log.d("ANUSHA ","___key"+CustOdometerReadingEdt.getText().toString());
                Log.d("ANUSHA ","___key"+MileageEdt.getText().toString().trim());
                Log.d("ANUSHA ","___key"+AvgkmsperdayEdt.getText().toString().trim());
                Log.d("ANUSHA ","___key"+ModelId);
                Log.d("ANUSHA ","___key"+CustVehiclemakemodelEdt.getText().toString().trim());
                Log.d("ANUSHA ","___key"+"");
                Log.d("ANUSHA ","___key"+CustVehicleTypeEdt.getText().toString().trim());*/
//                sessionManager.clearSession();
                Log.d("ANUSHA ", " +++ ONRESUME TABSELECTION " + Block);
                Log.d("ANUSHA ", " +++ ONRESUME TABSELECTION " + Technician);
                try {
                    String remarksLocal = null;
                    if (jobCardId != null && !jobCardId.isEmpty() && !jobCardId.equals("null") && !jobCardId.equals("empty"))
                        remarksLocal = Remarks;
                    else
                        remarksLocal = "";
                    sessionManager.storefirsFragmentDetails(VehicleNoEdt.getText().toString(), CustMobileNoEdt.getText().toString(), CustNameEdt.getText().toString(), PlaceEdt.getText().toString(),
                            Block, CustOdometerReadingEdt.getText().toString(), MileageEdt.getText().toString().trim(),
                            AvgkmsperdayEdt.getText().toString().trim(), CustVehicleModelEdt.getText().toString(), CustVehiclemakemodelEdt.getText().toString().trim(), remarksLocal, CustVehicleTypeEdt.getText().toString().trim(), CustVehicleModelEdt.getTag().toString().trim(),Technician);
                }catch (Exception e){
                    Log.d("ANUSHA "," "+e.toString());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                if (vehicleNo.length() >= 8 && !vehicleNo.startsWith("NA")) {
                    GetDetailsBasedOnVehicleNo(vehicleNo);
                }else{
                    Log.d("ANUSHA ","++++"+"ELSE PART IN VEHICLE DETAILS API CALL");
                }
            }
        });
        CustOdometerReadingEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shredPrefencesSaving(SessionManager.KEY_ODO_READING,CustOdometerReadingEdt.getText().toString());

            }
        });
        AvgkmsperdayEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shredPrefencesSaving(SessionManager.KEY_AVG_KMS,AvgkmsperdayEdt.getText().toString());
            }
        });


        return view;
    }

    public void sendData() {
        //INTENT OBJ
        Intent i = new Intent(getActivity().getBaseContext(), NewJobCardDetailsMain.class);


        i.putExtra("SENDER_KEY", namenew);


        //START ACTIVITY
        getActivity().startActivity(i);
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
            CustVehicleTypeEdt = (EditText) view.findViewById(R.id.CustVehicleTypeEdt);
            CustNameEdt = (EditText) view.findViewById(R.id.CustNameEdt);
            CustEmailIdEdt = (EditText) view.findViewById(R.id.CustEmailIdEdt);
            CustMobileNoEdt = (EditText) view.findViewById(R.id.CustMobileNoEdt);
            JobCardNoEdt = (EditText) view.findViewById(R.id.JobCardNoEdt);
//            SparePartEdt = (EditText) view.findViewById(R.id.IdSparePartEdt);
            QtyEdt = (EditText) view.findViewById(R.id.IdQtyEdt);
            SpnBlockSelection = (Spinner) view.findViewById(R.id.SpnBlockSelection);
            SpnTechnicianSelection=(Spinner)view.findViewById(R.id.SpnTechnicianSelection);
//            JobCardNoEdt.setText(SavedJobCardNo);
            IdModelImg = (ImageButton) view.findViewById(R.id.IdModelImg);
            CustDateEdt = (EditText) view.findViewById(R.id.CustDateEdt);
            CustVehiclemakemodelEdt = (EditText) view.findViewById(R.id.CustVehiclemakemodelEdt);
            CustVehicleModelEdt = (EditText) view.findViewById(R.id.CustVehicleModelEdt);
            CustOdometerReadingEdt = (EditText) view.findViewById(R.id.CustOdometerReadingEdt);
            AvgkmsperdayEdt = (EditText) view.findViewById(R.id.AvgkmsperdayEdt);
            RegNoEdt = (EditText) view.findViewById(R.id.RegNoEdt);
            MileageEdt = (EditText) view.findViewById(R.id.MileageEdt);
            PlaceEdt = (EditText) view.findViewById(R.id.PlaceEdt);
            CustTimeInEdt = (EditText) view.findViewById(R.id.CustTimeInEdt);
            CustTimeEdtLayout=(TextInputLayout)view.findViewById(R.id.CustTimeEdtLayout);
            CustDateEdtLayout=(TextInputLayout)view.findViewById(R.id.CustDateEdtLayout);
            IdSaveBtn = (Button) view.findViewById(R.id.IdNextBtn);
            IdSaveBtn.setOnClickListener(this);

            IdModelImg.setOnClickListener(this);

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
                    if (vehicleNo.length() >= 8 && !vehicleNo.startsWith("NA")) {
//                        GetDetailsBasedOnVehicleNo(vehicleNo);
                    }
                 /*   else{
                        Toast.makeText(getActivity(), "Please Enter Correct VehicleNo not Less 6", Toast.LENGTH_SHORT).show();
                    }*/

                }
            });
            int valueStr = 0;
            float finalAmtStr = 0.00f;
            CustOdometerReadingEdt.addTextChangedListener(new TextWatcher() {


                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (s.length() != 0) {

                        int ordermeterValue = Integer.parseInt(s.toString());
                        int finalAmtStr = ordermeterValue + 5000;
                        Log.d("ffffff", "" + finalAmtStr);
                        MileageEdt.setText(String.valueOf(finalAmtStr));
                        shredPrefencesSaving(SessionManager.KEY_NEXT_MILEAGE,MileageEdt.getText().toString());
                    } else {
                        MileageEdt.setText("");
                    }

                }
            });

            CustVehicleModelEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
//                    selectBlock = SpnBlockSelection.getSelectedItem().toString();
                    Log.d("vehicleno", vehicleNo);
                    Log.d("ANUSHA frag", selectBlock);
                    CustVehicleModelEdt.setError(null);
                    Intent modelinten = new Intent(getActivity(), ModelListActivity.class);
                    //sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModel, CustVehiclemakemodel, "car");
                    modelinten.putExtra("makeName", "");
                    startActivityForResult(modelinten, 52);
                }
            });
            SpnBlockSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
//                    selectBlock = SpnBlockSelection.getSelectedItem().toString();
//                    shredPrefencesSaving(sessionManager.KEY_BLOCK,selectBlock);
                    selectBlock =adapterView.getItemAtPosition(i).toString();
                    Block=adapterView.getItemAtPosition(i).toString();
                    shredPrefencesSaving(sessionManager.KEY_BLOCK,selectBlock);
                    Log.d("ANUSHA fr", selectBlock);
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";
                    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//                    sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModel, CustVehiclemakemodel,Remarks, "car",ModelId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            SpnTechnicianSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
//                    Technician = SpnTechnicianSelection.getSelectedItem().toString();
                    Technician=adapterView.getItemAtPosition(i).toString();
                    shredPrefencesSaving(sessionManager.KEY_TECHINICIANNAME,Technician);
                    Log.d("ANUSHA fr", Technician);
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";
                    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//                    sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModel, CustVehiclemakemodel,Remarks, "car",ModelId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //CustDateEdt.setText(BusinessDate);

            IdSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newValidation();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ModelInformation(Intent data) {
        try {
            String Model = data.getStringExtra("Model");
            ModelId = data.getStringExtra("ModelId");
            CustVehiclemakemodel = data.getStringExtra("Make");
            CustVehicleType = data.getStringExtra("VehicleType");
            CustVehicleModelEdt.setText(Model);
            CustVehicleModelEdt.setTag(ModelId);
            modelClick=true;
            Log.d("modelIdd", ModelId);
            Log.d("modelIdd", CustVehiclemakemodel);
            Log.d("modelIdd", CustVehicleType);
            shredPrefencesSaving(sessionManager.KEY_VEHICLE_ID,ModelId);

//            sessionManager.storeVehicleId(ModelId);
            CustVehiclemakemodelEdt.setText(CustVehiclemakemodel);
            CustVehicleTypeEdt.setText(CustVehicleType);

//            LatestNewServiceSelectedFragment ldf = new LatestNewServiceSelectedFragment();
//            Bundle args = new Bundle();
//            args.putString("modelId", ModelId);
//            ldf.setArguments(args);
//            getFragmentManager().beginTransaction().add(R.id.LinerLayOutHeader, ldf).commit();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void GetDetailsBasedOnVehicleNo(String vehicleNo) {
        Log.d("getinngg", "getting"+vehicleNo);
        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                Log.d("getinngg", "getting");
                JSONObject jsonObject = new JSONObject();
                Log.d("getinngg", "getting"+jsonObject.toString());

                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                jsonObject.accumulate("VehicleNo", vehicleNo);
                requestName = "GetDetailsByVehicleNo";
                Log.d("vehireq", jsonObject.toString());
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetDetailsByVehicleNo, jsonObject, Request.Priority.HIGH);
                Log.d("vehireq", jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
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
            CustEmailIdEdt.setText("");
            CustMobileNoEdt.setText("");
            //  JobCardNoEdt.setText("");
            CustVehiclemakemodelEdt.setText("");
            CustVehicleModelEdt.setText("");
            CustOdometerReadingEdt.setText("");
            AvgkmsperdayEdt.setText("");
            RegNoEdt.setText("");
            MileageEdt.setText("");
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
            GetBlockDetailsFromgeneralMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetBlockDetailsFromgeneralMaster() {
        Log.e("vehicccc","vehiclccc"+"GetBlockDetailsFromgeneralMaster");
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    jsonObject.accumulate("TypeName", "BLOCK");
                    requestName = "GetBlockDetails";
                    Log.e("vehicccc","vehiclccc"+"GetBlockDetailsFromgeneralMaster"+requestName);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImplData());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
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

    private void GetTechnicianDetailsFromgeneralMaster() {
        Log.e("vehicccc","vehiclccc"+"GetTechnicianDetailsFromgeneralMaster");
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    jsonObject.accumulate("TypeName", "Technician");
                    requestName = "GetTechnicianDetails";
                    Log.e("vehicccc","vehiclccc"+"GetTechnicianDetailsFromgeneralMaster"+requestName);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImplData());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
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
            if (requestName.equalsIgnoreCase("GetBusinessDate")) {
                try {
                    handleGetBusinessDate(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetBlockDetails")) {
                try {
                    handleGetBlockDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetTechnicianDetails")) {
                try {
                    handleGetTechnicianDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }//GetTechnicianDetailsLoadelse
            } /*else if (requestName.equalsIgnoreCase("GetTechnicianDetailsLoadelse")) {
                try {
                    handleGetTechnicianDetailsLoadelse(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }//GetTechnicianDetailsLoadelse
            } */else if(requestName.equalsIgnoreCase("GetDetailsByVehicleNo")){
                try {
                    handleGetDetailsByVehicleNo(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    }

    /*private void handleGetTechnicianDetailsLoadelse(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                TechnicianArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        Log.e("ANUSHA   ", "result" + object);
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result + " For Block", Toast.LENGTH_SHORT).show();

                        } else {
                            *//*Adding elements to HashMap*//*

                            HashMap hashmapobj = new HashMap();
                            hashmapobj.put(object.getString("Id"), object.getString("Name"));
                            *//* Display content using Iterator*//*
                            Log.e("Executing block","I am here a size "+hashmapobj.size());
                            Iterator trav=hashmapobj.entrySet().iterator();
                            while(trav.hasNext())
                            {
                                Map.Entry record=(Map.Entry)trav.next();  //will give next (Key, Value) pair
                                Log.e("Executing block","I am here a size "+hashmapobj.size());
                                if(record.getValue().equals("BOBY")){
                                    Log.e("Executing block","I am here a IF PART "+record.getKey());

                                }else{
                                    Log.e("Executing block","I am here a else Part ELSE PART "+record.getKey());
                                }
//                        System.out.println(record.getKey()+”–>”+record.getValue());

                            }



                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/

    private void handleGetTechnicianDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                TechnicianArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        Log.e("ANUSHA   ","result TECHNICIAN NAMES "+object);
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {

                            Log.e("Executing block","I am here a size "+object.getString("Id"));
                            Log.e("Executing block","I am here a size "+object.getString("Name"));
                            TechnicianTypes userList = new TechnicianTypes();
                            userList.setGId(object.getString("Id"));
                            userList.setTechnicianName(object.getString("Name"));
                            TechnicianArrayList.add(userList);
//                            dbManager.delete();
                            dbManager.insert(object.getString("Id"), object.getString("Name"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {


                    String[] us = new String[TechnicianArrayList.size()];
                    //    Log.e("Block ",Block);

                    for (int i = 0; i < TechnicianArrayList.size(); i++) {
                        us[i] = TechnicianArrayList.get(i).getTechnicianName();
                        Log.e("block names",i + " : " +TechnicianArrayList.get(i).getTechnicianName()+" : "+TechnicianArrayList.get(i).getGId());

                        if(Technician != null) {
                            Log.e("Block ", Technician);

                            if (Technician.equals(us[i])) {
                                Log.e("Block ", Technician + " , " + us[i]);
                                //  SpnBlockSelection.setSelection(i,true);
                                technicianPos = i;
                            }
                        }
                    }


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpnTechnicianSelection.setAdapter(spinnerArrayAdapter);
                    if(Technician != null) {
                        SpnTechnicianSelection.setSelection(technicianPos);
                    }
                    else{
                        SpnTechnicianSelection.setSelection(0,true);
                    }
                    shredPrefencesSaving(sessionManager.KEY_TECHINICIANNAME,Technician);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGetDetailsByVehicleNo(JSONArray jsonArray) {
        Log.d("ANUSHA ", "jsonlength" + jsonArray.length());
        try {
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Log.d("ANUSHA ", "jsonlength" + object.toString());
                    } catch (Exception e) {
                        Log.d("ANUSHA ", "jsonlength" + e.toString());
                    }
                    Log.d("ANUSHA ", "jsonlength" + jsonArray.toString());
                }
//            GetBlockDetailsFromgeneralMaster();
            }
        }catch (Exception e){

        }
    }
    private void handleGetBlockDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                BlockArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        Log.e("ANUSHA   ","result for block "+object);
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result + " For Block", Toast.LENGTH_SHORT).show();

                        } else {
                            VehicleTypes userList = new VehicleTypes();
                            userList.setId(object.getString("Id"));
                            userList.setName(object.getString("Name"));
                            BlockArrayList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {


                    String[] us = new String[BlockArrayList.size()];
                    //    Log.e("Block ",Block);

                    for (int i = 0; i < BlockArrayList.size(); i++) {
                        us[i] = BlockArrayList.get(i).getName();
                        Log.e("block names",i + " : " +BlockArrayList.get(i).getName()+" : "+BlockArrayList.get(i).getId());

                        if(Block != null) {
                            Log.e("Block ", Block);

                            if (Block.equals(us[i])) {
                                Log.e("Block ", Block + " , " + us[i]);
                                //  SpnBlockSelection.setSelection(i,true);
                                blockPos = i;
                            }
                        }
                    }


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpnBlockSelection.setAdapter(spinnerArrayAdapter);
                    if(Block != null) {
                        SpnBlockSelection.setSelection(blockPos);
                    }
                    else{
                        SpnBlockSelection.setSelection(0,true);
                    }
                    shredPrefencesSaving(sessionManager.KEY_BLOCK,Block);

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                GetTechnicianDetailsFromgeneralMaster();
                GetDataBaseValues();
                SettingArrayListToSpinner();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SettingArrayListToSpinner() {
        try {


            String[] us = new String[TechnicianArrayList.size()];
            //    Log.e("Block ",Block);

            for (int i = 0; i < TechnicianArrayList.size(); i++) {
                us[i] = TechnicianArrayList.get(i).getTechnicianName();
                Log.e("block names",i + " : " +TechnicianArrayList.get(i).getTechnicianName()+" : "+TechnicianArrayList.get(i).getGId());

                if(Technician != null) {
                    Log.e("Block ", Technician);

                    if (Technician.equals(us[i])) {
                        Log.e("Block ", Technician + " , " + us[i]);
                        //  SpnBlockSelection.setSelection(i,true);
                        technicianPos = i;
                    }
                }
            }


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpnTechnicianSelection.setAdapter(spinnerArrayAdapter);
            if(Technician != null) {
                SpnTechnicianSelection.setSelection(technicianPos);
            }
            else{
                SpnTechnicianSelection.setSelection(0,true);
            }
            shredPrefencesSaving(sessionManager.KEY_TECHINICIANNAME,Technician);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetDataBaseValues() {
        TechnicianArrayList.clear();
        DatabaseHelper1 db = new DatabaseHelper1(view.getContext());
        Cursor cr = db.getAllItemDetails();
        if (cr != null) {
            for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
                TechnicianTypes cse = new TechnicianTypes();

                String technicianName = cr.getString(cr.getColumnIndex(DatabaseHelper1.DESC));
                cse.setTechnicianName(technicianName);
                String GID=cr.getString(cr.getColumnIndex(DatabaseHelper1.SUBJECT));
                cse.setGId(GID);
                TechnicianArrayList.add(cse);
            }
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
                    Log.d("ANUSHA ","Exception "+e.toString());
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
                    Log.e("ANUSHA ",jsonObject.toString());
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
                    CustVehicleType = jsonArray.getJSONObject(0).getString("VehicleType");
                    Place = jsonArray.getJSONObject(0).getString("Place");
                    CustOdometerReading = jsonArray.getJSONObject(0).getString("OMReading");
                    Mileage = jsonArray.getJSONObject(0).getString("Mileage");
                    Avgkmsperday = jsonArray.getJSONObject(0).getString("AvgKmsPerDay");
                    Block= jsonArray.getJSONObject(0).getString("Block");
                    Technician= jsonArray.getJSONObject(0).getString("Technician");
                    Remarks = jsonArray.getJSONObject(0).getString("Remarks");
                    PreviousTimeIn= jsonArray.getJSONObject(0).getString("InTime");
                    PreviousDateIn = jsonArray.getJSONObject(0).getString("JCDate");
                    VehicleNoEdt.setText(VehicleNo);
                    CustMobileNoEdt.setText(CustMobileNo);
                    CustNameEdt.setText(CustName);
                    PlaceEdt.setText(Place);
                    CustOdometerReadingEdt.setText(CustOdometerReading);
                    MileageEdt.setText(Mileage);
                    AvgkmsperdayEdt.setText(Avgkmsperday);
                    CustVehicleModel = CustVehicleModelEdt.getText().toString().trim();
                    CustVehicleModelEdt.setText(CustVehiclemakemodel);
                    CustVehicleModelEdt.setTag(ModelId);
                    CustVehiclemakemodelEdt.setText(CustVehiclemake);
                    CustVehicleTypeEdt.setText(CustVehicleType);
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
                    Log.e("Executing block","I am here"+Technician);
                    Log.e("Executing block","I am here"+CustVehicleModel);
                    Log.e("Executing block","I am here"+PreviousDateIn);
                    Log.e("Executing block","I am here"+PreviousTimeIn);
                    Log.e("Executing block","I am here"+ModelId);
//                    Log.e("Executing block","I am here"+TechnicianArrayList.get());
                    Log.e("Executing block","I am here"+BlockArrayList.indexOf(Block));
                     SpnBlockSelection.setSelection(BlockArrayList.indexOf(Block));
//                    SpnTechnicianSelection.setSelection(TechnicianArrayList.indexOf(Technician));
                    String dbValue,position;
                    if(!Technician.isEmpty()) {
                        DatabaseHelper1 db = new DatabaseHelper1(view.getContext());
                        dbValue = db.jgetValues(Technician);
                        position = db.jgetId(dbValue);
                        Log.e("Executing block", "JESUS" + dbValue);
                        Log.e("Executing block", "JESUS" + TechnicianArrayList.indexOf(dbValue));
                        Log.e("Executing block", "JESUS position " + position);
                        Log.e("Executing block", "JESUS" + BlockArrayList.indexOf(Block));
                        Log.e("Executing block", "JESUS" + Block);
//                        SpnTechnicianSelection.setSelection(2);
                        SpnTechnicianSelection.setSelection(TechnicianArrayList.indexOf(dbValue));
                        Technician=dbValue;
                    }else{
                        SpnTechnicianSelection.setSelection(TechnicianArrayList.indexOf(Technician));
                        Technician="Select a Tachnician";
                    }
                    for(int i=0;i<TechnicianArrayList.size();i++){
                        Log.e("Executing block","I am here"+TechnicianArrayList.get(i).getGId());
                        Log.e("Executing block","I am here"+TechnicianArrayList.get(i).getTechnicianName());
                    }

                   // if(!Technician.equals("")) {
                        /*try {
                            DatabaseHelper1 db=new DatabaseHelper1(view.getContext());
                            cursor = db.getServiceDetails("GM10650");
                            if (cursor != null) {
                                dbValue = cursor.getString(cursor.getColumnIndex(DatabaseHelper1.DESC));
                                Log.e("Executing block", "JESUS I am here" + dbValue);
                            }
                        } catch (Exception e) {
                            Log.d("ANUSHA ", " JESUS Exception " + e.toString());
                        }*/
//                    }

//                    hmap.put("5", object.getString("Name"));


                    // Log.e("selected", String.valueOf(BlockArrayList.get());
//                    GetBlockDetailsFromgeneralMaster();

                    sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModelEdt.getText().toString(), CustVehiclemakemodel,Remarks, "car",ModelId,Technician);
                    sessionManager.storeEditDetails(CustName, ModelId, CustVehiclemakemodel, CustMobileNo,Remarks,PreviousDateIn,PreviousTimeIn);
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
                case R.id.IdMakeImg:
                    Intent makeintent = new Intent(getActivity(), MakeListActivity.class);
                    startActivityForResult(makeintent, 51);
                    break;
                case R.id.IdVehicleTypeImg:
                    Intent VehicleTypeImg = new Intent(getActivity(), VehicleTypeActivity.class);
                    startActivityForResult(VehicleTypeImg, 55);
                    break;
                case R.id.IdSparePartEdt:
                    Intent spareparts = new Intent(getActivity(), SparePartsActivity.class);
                    startActivityForResult(spareparts, 102);
                    break;

                case R.id.IdModelImg:
                    modelClick=true;
                    Intent modelinten = new Intent(getActivity(), ModelListActivity.class);
                    modelinten.putExtra("makeName", "");
                    startActivityForResult(modelinten, 52);
                  /*  if (!CustVehiclemakemodelEdt.getText().toString().isEmpty()) {
                        String makeName = CustVehiclemakemodelEdt.getText().toString();
                        Intent modelinten = new Intent(getActivity(), ModelListActivity.class);
                        modelinten.putExtra("makeName", makeName);
                        startActivityForResult(modelinten, 52);
                    } else {
                        Toast toast = Toast.makeText(context, " Please Select Make To Get Model Details ", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                        textView.setTextColor(Color.WHITE);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }*/
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

                case R.id.IdSignatureBtn:
                    try {

                        Intent sig = new Intent(getActivity(), DrawingSignatureActivity.class);
                        startActivityForResult(sig, 11);
                    } catch (Exception ex) {
                        ex.printStackTrace();
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

        } else if (requestCode == 102) {
            if (data != null && resultCode == RESULT_OK) {
                //  SparPartInformation(data);
            }
        } else if (requestCode == 134 && data != null && resultCode == RESULT_OK) {

        } else if (requestCode == 52) {
            if (data != null && resultCode == RESULT_OK) {
                ModelInformation(data);
            } else if (requestCode == 101 && data != null && resultCode == RESULT_OK) {

            }


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
                    GetBlockDetailsFromgeneralMaster();
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
                    //[{"CustomerId":"CUS10208","CustomerName":"ANUSHA","Make":"HYUNDAI","MobileNo":"909090909090","Model":"I 20","ModelId":"VH10379","Result":"","VehicleNo":"AP179090909090","vehicletype":"Car"}]
//                    mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    String CustomerName = jsonArray.getJSONObject(0).getString("CustomerName");
//                    String EmailId = jsonArray.getJSONObject(0).getString("EmailId");
                    String Make = jsonArray.getJSONObject(0).getString("Make");
//                    String MakeYear = jsonArray.getJSONObject(0).getString("MakeYear");
                    String MobileNo = jsonArray.getJSONObject(0).getString("MobileNo");
                    String ModelNo = jsonArray.getJSONObject(0).getString("Model");
                    ModelId = jsonArray.getJSONObject(0).getString("ModelId");
                    mVehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");
                    String vehicletype=jsonArray.getJSONObject(0).getString("vehicletype");
                    String place=jsonArray.getJSONObject(0).getString("Place");
                    CustNameEdt.setText(CustomerName);
                    PlaceEdt.setText(place);
//                    CustEmailIdEdt.setText(EmailId);
                    CustMobileNoEdt.setText(MobileNo);
                    CustVehicleModelEdt.setText(ModelNo);
                    CustVehiclemakemodelEdt.setText(Make);
                    CustVehicleTypeEdt.setText(vehicletype);
                    ModelDetails documentTypes = new ModelDetails();
                    documentTypes.setModelName(jsonArray.getJSONObject(0).getString("Model"));
                    documentTypes.setModelId(jsonArray.getJSONObject(0).getString("ModelId"));
                    documentTypes.setMake(jsonArray.getJSONObject(0).getString("Make"));
                    documentTypes.setVehicletype(jsonArray.getJSONObject(0).getString("vehicletype"));
                    modelDetailsArrayList.add(documentTypes);
                    Log.d("modelNo", ModelId);

                    CustVehicleModelEdt.setText(jsonArray.getJSONObject(0).getString("Model"));
                    CustVehicleModelEdt.setTag(jsonArray.getJSONObject(0).getString("ModelId"));
                    modelClick=true;
//                    Log.d("modelIdd", ModelId);
//                    Log.d("modelIdd", CustVehiclemakemodel);
//                    Log.d("modelIdd", CustVehicleType);
                    Log.d("ANUSHA "," ************ "+jsonArray.getJSONObject(0).getString("ModelId"));
//                    ModelId=jsonArray.getJSONObject(0).getString("ModelId");
                    shredPrefencesSaving(sessionManager.KEY_VEHICLE_ID,jsonArray.getJSONObject(0).getString("ModelId"));
                    sessionManager.storeVehicleId(jsonArray.getJSONObject(0).getString("ModelId"),jsonArray.getJSONObject(0).getString("Make"));
                    RegNoEdt.setText("");
                }
            }
            vehicleDetailsArrayList.add(vehicleDetails);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ANUSHA ","Exception @ "+e.toString());
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
        vehicleNo = VehicleNoEdt.getText().toString().trim();
        VehicleNoEdt.setText(VehicleNoEdt.getText().toString().trim().replaceAll("\\s{2,}", " "));
        CustNameEdt.setText(CustNameEdt.getText().toString().trim().replaceAll("\\s{2,}", " "));
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
        PlaceEdt.setText(PlaceEdt.getText().toString().trim().replaceAll("\\s{2,}", " "));
        Place = PlaceEdt.getText().toString().trim();
        CustTimeIn = CustTimeInEdt.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        selectBlock = SpnBlockSelection.getSelectedItem().toString();

        if (vehicleNo.isEmpty()) {
            VehicleNoEdt.setError("Please Enter VehicleNo");
            VehicleNoEdt.requestFocus();
            result = false;
            resultValue = false;
        } else if (CustMobileNo.length() == 0) {
            CustMobileNoEdt.setError("Please Enter Mobile No");
            CustMobileNoEdt.requestFocus();
            result = false;
            resultValue = false;
        } else if (CustMobileNo.length() < 10) {
            CustMobileNoEdt.setError("Please Valid Enter Mobile No");
            CustMobileNoEdt.requestFocus();
            result = false;
            resultValue = false;
        } else if (CustName.isEmpty()) {
            CustNameEdt.setText(CustName.trim().replaceAll("\\s{2,}", " "));
            CustNameEdt.setError("Please Enter Name");
            CustNameEdt.requestFocus();
            result = false;
            resultValue = false;
        } else if (Place.isEmpty()) {
            PlaceEdt.setText(Place.trim().replaceAll("\\s{2,}", " "));
            PlaceEdt.setError("Please Enter Place");
            PlaceEdt.requestFocus();
            result = false;
            resultValue = false;
        } else if (CustVehicleModel.length() == 0) {
            CustVehicleModelEdt.setError("Please Select VehicleModel");
            Toast.makeText(getActivity().getBaseContext(), "Please Select VehicleModel", Toast.LENGTH_SHORT).show();
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
        }else {

//            Bundle b = new Bundle();
//            Fragment fragment = new LatestNewServiceSelectedFragment();
//            fragment.setArguments(b);
//            getFragmentManager().beginTransaction().replace(R.id.viewpager, fragment);
//            customViewPager = new CustomViewPager(getActivity());
//            customViewPager.setSwipeable(true);
            Log.d("ANUSHA "," @@@@@@@@ ModelId "+ModelId);

            sessionManager.storefirsFragmentDetails(vehicleNo, CustMobileNo, CustName, Place, selectBlock, CustOdometerReading, Mileage, Avgkmsperday, CustVehicleModelEdt.getText().toString(), CustVehiclemakemodel,"", "car",ModelId,Technician);
//                NewJobCardDetailsMain.ViewPagerAdapter adapter = new NewJobCardDetailsMain.ViewPagerAdapter(getSupportFragmentManager());

            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(1).select();
            // Toast.makeText(getContext(), "Enterd Succes", Toast.LENGTH_SHORT).show();
        }

//        else if (finalselectedServiceList.equalsIgnoreCase("")) {
//            Toast toast1 = Toast.makeText(context, " Please Select Atleast one Service ", Toast.LENGTH_SHORT);
//            View view1 = toast1.getView();
//            view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            TextView v1 = (TextView) toast1.getView().findViewById(android.R.id.message);
//            v1.setTextColor(Color.WHITE);
//            toast1.setGravity(Gravity.CENTER, 0, 0);
//            toast1.show();
//            // Toast.makeText(context, "Please Select Atleast one Service", Toast.LENGTH_SHORT).show();
//            result = false;
//
//        }
        int fromDate = ProjectMethods.GetDateToInt(CustFromDate);
       /* int toDate = ProjectMethods.GetDateToInt(CustToDate);
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
        }*/

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

        if (!CustEmailId.isEmpty()) {
            if (!CustEmailId.matches(EMAIL_PATTERN) == true) {

                CustEmailIdEdt.setError("Please Enter Valid EmailId");
                CustEmailIdEdt.requestFocus();
                CustEmailIdEdt.setFocusable(true);
                result = false;
            }
        }
        return result;
    }
    public boolean ValidationCheckForTabSelection(){
        boolean check=true;
        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
        ViewGroup vg = (ViewGroup) tabhost.getChildAt(0);
        //get number of tab
        int tabsCount = vg.getChildCount();
        if(vehicleNo == null || CustMobileNo == null ||
                CustName == null || Place == null || CustVehicleModel == null) {
            check =false;
            Toast.makeText(getActivity(),"Please Enter ALL details",Toast.LENGTH_SHORT).show();
            //loop the tab
            for (int j = 0; j < tabsCount; j++) {
                //get view of selected tab
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

                if (j == 1 || j == 2 || j == 3) {
                    //disable the selected tab
                    vgTab.setEnabled(false);
                }
            }
        }else{
            //loop the tab
            for (int j = 0; j < tabsCount; j++) {
                //get view of selected tab
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

                if (j == 1 || j == 2 || j == 3) {
                    //disable the selected tab
                    vgTab.setEnabled(false);
                }
            }
        }
        return check;
    }
    public void shredPrefencesSaving(String keyvalue,String value){
        editor.putString(keyvalue, value);
        editor.commit();
        Log.d("ANUSHA ","___key"+keyvalue);
        Log.d("ANUSHA ","___value"+value);
        Log.d("ANUSHA ","___shared preferences value"+sharedpreferences.getString(keyvalue,null));
    /*sessionManager.clearSession();
    sessionManager.storefirsFragmentDetails(VehicleNoEdt.getText().toString(),CustMobileNoEdt.getText().toString(),CustNameEdt.getText().toString(),PlaceEdt.getText().toString(),
            SpnBlockSelection.getSelectedItem().toString(),CustOdometerReadingEdt.getText().toString(),MileageEdt.getText().toString().trim(),
            AvgkmsperdayEdt.getText().toString().trim(),ModelId,CustVehiclemakemodelEdt.getText().toString().trim(),"",CustVehicleTypeEdt.getText().toString().trim());*/
    }
    public String getShredPrefences(String keyvalue){
        Log.d("ANUSHA ","___shared preferences value"+sharedpreferences.getString(keyvalue,null));
        return sharedpreferences.getString(keyvalue,null);
    /*sessionManager.clearSession();
    sessionManager.storefirsFragmentDetails(VehicleNoEdt.getText().toString(),CustMobileNoEdt.getText().toString(),CustNameEdt.getText().toString(),PlaceEdt.getText().toString(),
            SpnBlockSelection.getSelectedItem().toString(),CustOdometerReadingEdt.getText().toString(),MileageEdt.getText().toString().trim(),
            AvgkmsperdayEdt.getText().toString().trim(),ModelId,CustVehiclemakemodelEdt.getText().toString().trim(),"",CustVehicleTypeEdt.getText().toString().trim());*/
    }

    @Override
    public void onResume() {
        super.onResume();
            if(userDeailsServiceCallCheck)
                GetUserDetails();
            else {
                GetTheBusinessDate();
                GetBlockDetailsFromgeneralMaster();
                Block = getShredPrefences(SessionManager.KEY_BLOCK);
                Technician = getShredPrefences(SessionManager.KEY_TECHINICIANNAME);
            }

            Log.d("ANUSHA ", " +++ ONRESUME IFCONDITION" + userDeailsServiceCallCheck);
            Log.d("ANUSHA ", " +++ ONRESUME IFCONDITION" + jobCardId.equalsIgnoreCase("empty"));
            Log.d("ANUSHA ", " +++ ONRESUME IFCONDITION" + jobCardId);
            Log.d("ANUSHA ", " +++ ONRESUME IFCONDITION" + Block);
            Log.d("ANUSHA ", " +++ ONRESUME IFCONDITION" + Technician);


    }
}
