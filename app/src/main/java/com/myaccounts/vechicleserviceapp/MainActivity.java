package com.myaccounts.vechicleserviceapp;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myaccounts.vechicleserviceapp.Activity.DocumentListActivity;
import com.myaccounts.vechicleserviceapp.Activity.DrawingSignatureActivity;
import com.myaccounts.vechicleserviceapp.Activity.GeneralMasterActivity;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.ServiceStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Activity.SparePartEstAgstCustomerActivity;
import com.myaccounts.vechicleserviceapp.Activity.SpareStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Fragments.SparePartEstAgstJobCard;
import com.myaccounts.vechicleserviceapp.Activity.SparePartIssueActivity;
import com.myaccounts.vechicleserviceapp.Activity.SparePartMasterActivity;
import com.myaccounts.vechicleserviceapp.Adapter.DocumentListAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.SelectedServiceAdpater;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter1;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter2;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter3;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter4;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter5;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter6;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter7;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter8;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceAdapter9;
import com.myaccounts.vechicleserviceapp.Adapter.SubServiceSelectedAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.WheelServiceAdapter;
import com.myaccounts.vechicleserviceapp.LoginSetUp.LoginActivity;
import com.myaccounts.vechicleserviceapp.Pojo.DocumentTypes;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.SubService;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.JSONVariables;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Toolbar toolbar;
    private PopupWindow mPopupWindow;
    private LinearLayout lr;
    private ImageView IdRightArrow, IdLeftArrow;
    private EditText VehicleNoEdt, CustNameEdt, CustEmailIdEdt, CustMobileNoEdt, JobCardNoEdt, CustDateEdt, CustVehiclemakemodelEdt, CustVehicleModelEdt, CustOdometerReadingEdt, AvgkmsperdayEdt,
            RegNoEdt, CustTimeOutEdt, CustTimeInEdt, RemarksEdt;
    private RecyclerView mWheelServiceRecyclerviewid, SelectedServiceRecyclerviewid, SubServiceRecyclerviewid, SubServiceRecyclerview1, SubServiceRecyclerview2, SubServiceRecyclerview3, SubServiceRecyclerview4, SubServiceRecyclerview5, SubServiceRecyclerview6, SubServiceRecyclerview7, SubServiceRecyclerview8, SubServiceRecyclerview9;
    private WheelServiceAdapter wheelServiceAdapter;
    private SelectedServiceAdpater selectedServiceAdpater;
    private SubServiceAdapter subServiceAdapter;
    private SubServiceAdapter1 subServiceAdapter1;
    private SubServiceAdapter2 subServiceAdapter2;
    private SubServiceAdapter3 subServiceAdapter3;
    private SubServiceAdapter4 subServiceAdapter4;
    private SubServiceAdapter5 subServiceAdapter5;
    private SubServiceAdapter6 subServiceAdapter6;
    private SubServiceAdapter7 subServiceAdapter7;
    private SubServiceAdapter8 subServiceAdapter8;
    private SubServiceAdapter9 subServiceAdapter9;
    private SubServiceAdapter subServiceAdapter10;
    private SubServiceAdapter subServiceAdapter11;
    private SubServiceAdapter subServiceAdapter12;
    private SubServiceAdapter subServiceAdapter13;
    private SubServiceAdapter subServiceAdapter14;
    private SubServiceAdapter subServiceAdapter15;
    private SubServiceSelectedAdapter subServiceSelectedAdapter;
    private ImageView IdCaptureImgView, drawingImage;
    private Button IdImageCaptureBtn, IdSignatureBtn, IdClearBtn, IdSaveBtn, IdDocumentTypeBtn;
    private String vehicleNo, requestName, CustName, CustMobileNo, CustEmailId, JobCardNo, CustDate, CustVehiclemakemodel, CustVehicleModel, CustOdometerReading, Avgkmsperday, RegNo, CustTimeOut, Remarks, CustTimeIn;
    //keep track of camera capture intent
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    //keep track of cropping intent
    final int PIC_CROP = 3;
    //keep track of gallery intent
    final int PICK_IMAGE_REQUEST = 2;
    //captured picture uri
    private Uri picUri;
    final Calendar myCalendar = Calendar.getInstance();

    private int mYear, mMonth, mDay, mHour, mMinute;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<VehicleDetails> vehicleDetailsArrayList;
    private ArrayList<ServiceMaster> serviceMasterArrayList;
    private ArrayList<DocumentTypes> documentTypesArrayList;
    private ArrayList<SubService> subServiceArrayList;
    private ArrayList<SubService> subServiceArrayList1;
     public ArrayList<SubService> subServiceArrayList2;
     public ArrayList<SubService> subServiceArrayList3;
     public ArrayList<SubService> subServiceArrayList4;
     public ArrayList<SubService> subServiceArrayList5;
     public ArrayList<SubService> subServiceArrayList6;
     public ArrayList<SubService> subServiceArrayList7;
     public ArrayList<SubService> subServiceArrayList8;
     public ArrayList<SubService> subServiceArrayList9;
     public ArrayList<SubService> subServiceArrayList11;
     public ArrayList<SubService> subServiceArrayList12;
     public ArrayList<SubService> subServiceArrayList13;
     public ArrayList<SubService> subServiceArrayList14;
     public ArrayList<SubService> subServiceArrayList15;
    private ArrayList<SubService> subServiceHeaderArrayList;
    private TextView txtDocumentList;
    private LinearLayout SelectedSubServiceLayout1, SelectedSubServiceLayout2, SelectedSubServiceLayout3, SelectedSubServiceLayout4, SelectedSubServiceLayout5, SelectedSubServiceLayout6, SelectedSubServiceLayout7, SelectedSubServiceLayout8, SelectedSubServiceLayout9, SelectedSubServiceLayout10;
    ArrayList<DocumentTypes> myList = new ArrayList<DocumentTypes>();
    private String ServiceIdKey, ServiceName, ServiceValue, mCustomerId = "", mVehicleNo = "";
    private TextView txtCheckedService10, txtCheckedService9, txtCheckedService8, txtCheckedService7, txtCheckedService6, txtCheckedService5, txtCheckedService4, txtCheckedService3, txtCheckedService2, txtCheckedService1;
    CheckBox checkBox;
    private String finalServiceDetailList;
    String finalselectedServiceList = "", Crosstableresults1 = "", Crosstableresults2 = "", Crosstableresults3 = "", Crosstableresults4 = "", Crosstableresults5 = "", Crosstableresults6 = "", Crosstableresults7 = "", Crosstableresults8 = "", Crosstableresults9 = "",
            Crosstableresults10 = "", Crosstableresults11 = "", Crosstableresults12 = "", Crosstableresults13 = "", Crosstableresults14 = "", Crosstableresults15 = "";
    String byteArryImage;
    Bitmap bitmap;
    byte[] Imagepath;
    String imageEncoded = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        vehicleDetailsArrayList = new ArrayList<>();
        serviceMasterArrayList = new ArrayList<>();
        subServiceHeaderArrayList = new ArrayList<>();
        InitializeVariables();
        retrivesharedPreferences();
        navigationView.setNavigationItemSelectedListener(this);
        IdRightArrow = (ImageView) findViewById(R.id.IdRightArrow);
        IdLeftArrow = (ImageView) findViewById(R.id.IdLeftArrow);
        IdRightArrow.setOnClickListener(this);
        IdLeftArrow.setOnClickListener(this);
    }

    private void retrivesharedPreferences() {
        SharedPreferences shared = getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        byteArryImage = shared.getString("PRODUCT_PHOTO", "");
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
                drawingImage.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void InitializeVariables() {
        try {
            VehicleNoEdt = (EditText) findViewById(R.id.VehicleNoEdt);
            CustNameEdt = (EditText) findViewById(R.id.CustNameEdt);
            CustEmailIdEdt = (EditText) findViewById(R.id.CustEmailIdEdt);
            CustMobileNoEdt = (EditText) findViewById(R.id.CustMobileNoEdt);
            JobCardNoEdt = (EditText) findViewById(R.id.JobCardNoEdt);
            CustDateEdt = (EditText) findViewById(R.id.CustDateEdt);
            CustVehiclemakemodelEdt = (EditText) findViewById(R.id.CustVehiclemakemodelEdt);
            CustVehicleModelEdt = (EditText) findViewById(R.id.CustVehicleModelEdt);
            CustOdometerReadingEdt = (EditText) findViewById(R.id.CustOdometerReadingEdt);
            AvgkmsperdayEdt = (EditText) findViewById(R.id.AvgkmsperdayEdt);
            RegNoEdt = (EditText) findViewById(R.id.RegNoEdt);
            CustTimeOutEdt = (EditText) findViewById(R.id.CustTimeOutEdt);
            CustTimeInEdt = (EditText) findViewById(R.id.CustTimeInEdt);
            RemarksEdt = (EditText) findViewById(R.id.RemarksEdt);
            txtDocumentList = (TextView) findViewById(R.id.txtDocumentList);
            mWheelServiceRecyclerviewid = (RecyclerView) findViewById(R.id.mWheelServiceRecyclerviewid);
            SelectedServiceRecyclerviewid = (RecyclerView) findViewById(R.id.SelectedServiceRecyclerviewid);
            SubServiceRecyclerviewid = (RecyclerView) findViewById(R.id.SubServiceRecyclerviewid);
            SubServiceRecyclerview1 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview1);
            SubServiceRecyclerview2 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview2);
            SubServiceRecyclerview3 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview3);
            SubServiceRecyclerview4 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview4);
            SubServiceRecyclerview5 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview5);
            SubServiceRecyclerview6 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview6);
            SubServiceRecyclerview7 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview7);
            SubServiceRecyclerview8 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview8);
            SubServiceRecyclerview9 = (RecyclerView) findViewById(R.id.SubServiceRecyclerview9);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
            mWheelServiceRecyclerviewid.setLayoutManager(gridLayoutManager);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            SubServiceRecyclerviewid.setLayoutManager(llm);
            SelectedSubServiceLayout1 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout1);
            SelectedSubServiceLayout2 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout2);
            SelectedSubServiceLayout3 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout3);
            SelectedSubServiceLayout4 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout4);
            SelectedSubServiceLayout5 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout5);
            SelectedSubServiceLayout6 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout6);
            SelectedSubServiceLayout7 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout7);
            SelectedSubServiceLayout8 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout8);
            SelectedSubServiceLayout9 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout9);
            SelectedSubServiceLayout10 = (LinearLayout) findViewById(R.id.SelectedSubServiceLayout10);
            txtCheckedService10 = (TextView) findViewById(R.id.txtCheckedService10);
            txtCheckedService9 = (TextView) findViewById(R.id.txtCheckedService9);
            txtCheckedService8 = (TextView) findViewById(R.id.txtCheckedService8);
            txtCheckedService7 = (TextView) findViewById(R.id.txtCheckedService7);
            txtCheckedService6 = (TextView) findViewById(R.id.txtCheckedService6);
            txtCheckedService5 = (TextView) findViewById(R.id.txtCheckedService5);
            txtCheckedService4 = (TextView) findViewById(R.id.txtCheckedService4);
            txtCheckedService3 = (TextView) findViewById(R.id.txtCheckedService3);
            txtCheckedService2 = (TextView) findViewById(R.id.txtCheckedService2);
            txtCheckedService1 = (TextView) findViewById(R.id.txtCheckedService1);

            IdCaptureImgView = (ImageView) findViewById(R.id.IdCaptureImgView);
            drawingImage = (ImageView) findViewById(R.id.drawingImage);
            IdSignatureBtn = (Button) findViewById(R.id.IdSignatureBtn);
            IdImageCaptureBtn = (Button) findViewById(R.id.IdImageCaptureBtn);
            IdDocumentTypeBtn = (Button) findViewById(R.id.IdDocumentTypeBtn);
            IdClearBtn = (Button) findViewById(R.id.IdClearBtn);
            IdSaveBtn = (Button) findViewById(R.id.IdSaveBtn);
            IdSaveBtn.setOnClickListener(this);
            IdClearBtn.setOnClickListener(this);
            IdImageCaptureBtn.setOnClickListener(this);
            IdSignatureBtn.setOnClickListener(this);
            CustTimeOutEdt.setOnClickListener(this);
            CustTimeInEdt.setOnClickListener(this);
            IdDocumentTypeBtn.setOnClickListener(this);
            // CustDateEdt.setText(ProjectMethods.GetCurrentDate());
            CustDateEdt.setText(ProjectMethods.getBusinessDate());
            CustTimeInEdt.setText(ProjectMethods.GetCurrentTime());
            /*wheelServiceAdapter = new WheelServiceAdapter(MainActivity.this);
            selectedServiceAdpater = new SelectedServiceAdpater(MainActivity.this, R.layout.selectedservice_row_item, serviceMasterArrayList);
            mWheelServiceRecyclerviewid.setAdapter(wheelServiceAdapter);
            SelectedServiceRecyclerviewid.setAdapter(selectedServiceAdpater);*/
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
            swipeRefreshLayout.setOnRefreshListener(this);
           /* swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        GetServicesFromServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/
            GetServicesFromServer();

       /*     CustMobileNoEdt.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    handelCustomerDetailsDashboard(s);
                }
            });*/
            VehicleNoEdt.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    String vehicleNo = String.valueOf(s);
                    if (vehicleNo.length() >= 6) {
                        GetDetailsBasedOnVehicleNo(s);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetJobCardNumberFromServer() {
        try {
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    requestName = "GetJobCardNumber";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardNumber, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GetServicesFromServer() {
        try {
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", "60");
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    requestName = "ServiceMaster";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.ServiceMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetDetailsBasedOnVehicleNo(CharSequence VehicleNo) {
        try {
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    // swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("VehicleNo", VehicleNo);
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    requestName = "GetDetailsByVehicleNo";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetDetailsByVehicleNo, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handelCustomerDetailsDashboard(CharSequence mobileNo) {
        try {
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    // swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", 20);
                    jsonObject.accumulate("MobileNo", mobileNo);
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    requestName = "GetCustomerDataOpenData";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetUserData, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AppUtil.displayExitDialog(MainActivity.this);
            drawer.closeDrawer(GravityCompat.START);
          /*  Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.datebtn:

                try {
                    /*LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                    // Inflate the custom layout/view
                    View customView = inflater.inflate(R.layout.activity_date_popup,null);
                    lr = (LinearLayout) findViewById(R.id.lr);

                    mPopupWindow = new PopupWindow(
                            customView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            true);

                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow.setElevation(5.0f);
                    }

                    // Get a reference for the custom view close button
                    final TextView titleText=(TextView)customView.findViewById(R.id.title);
                    ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                    final DatePicker picker  =(DatePicker)findViewById(R.id.datePicker);

                    // Set a click listener for the popup window close button
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                        }
                    });
                        *//*mPopupWindow.setFocusable(false);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(false);*//*
                    //mPopupWindow.setOutsideTouchable(false);
//                        mPopupWindow.setFocusable(true);
                    mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);

                    picker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();

                        }
                    });*/
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                }


                return true;
            case R.id.action_logout:
                try {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_sparepartmaster) {
            Intent intent = new Intent(MainActivity.this, SparePartMasterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sparepartissue) {
            Intent intent = new Intent(MainActivity.this, SparePartIssueActivity.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_sparepartegstcustomer) {
            Intent intent = new Intent(MainActivity.this, SparePartEstAgstCustomerActivity.class);
            startActivity(intent);

        } */
        else if (id == R.id.nav_sparepartagstjobcard) {
            Intent intent = new Intent(MainActivity.this, SparePartEstAgstJobCard.class);
            startActivity(intent);

        }
    /*    else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(MainActivity.this, GeneralMasterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(MainActivity.this, ServiceMasterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {
            Intent intent=new Intent(MainActivity.this, VechicleMasterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(MainActivity.this, SparePartMasterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(MainActivity.this, SparePartIssueActivity.class);
            startActivity(intent);
        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.IdLeftArrow:
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.IdRightArrow:
                    Intent intent1 = new Intent(MainActivity.this, GeneralMasterActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.IdSaveBtn:
                    try {

                        for (int i = 0; i < selectedServiceAdpater.serviceMasterArrayList.size(); i++) {
                            if (selectedServiceAdpater.serviceMasterArrayList.get(i).getSelected()) {
                                finalselectedServiceList += selectedServiceAdpater.serviceMasterArrayList.get(i).getServiceId().toString() + ",";
                                String serviceListName=selectedServiceAdpater.serviceMasterArrayList.get(i).getServiceName().toString();
                                if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
                                    for (int j = 0; j < subServiceAdapter.subServiceArrayList.size(); j++) {
                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter.subServiceArrayList.size()) {
                                            for (SubService d : subServiceAdapter.subServiceArrayList) {
                                                if (j == value) {
                                                    Crosstableresults1 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults1 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }
                                        }
                                        if(Crosstableresults1!=""){
                                            Crosstableresults1=Crosstableresults1+"^";
                                        }else{
                                            Crosstableresults1="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
                                    for (int j = 0; j < subServiceAdapter1.subServiceArrayList1.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter1.subServiceArrayList1.size()) {
                                            for (SubService d : subServiceAdapter1.subServiceArrayList1) {
                                                if (j == value) {
                                                    Crosstableresults2 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults2 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults2!=""){
                                            Crosstableresults2=Crosstableresults2+"^";
                                        }else{
                                            Crosstableresults2="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
                                    for (int j = 0; j < subServiceAdapter2.subServiceArrayList2.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        //int k = 0;
                                        if (j < subServiceAdapter2.subServiceArrayList2.size()) {
                                            for (SubService d : subServiceAdapter2.subServiceArrayList2) {
                                                if (j == value) {
                                                    Crosstableresults3 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults3 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults3!=""){
                                            Crosstableresults3=Crosstableresults3+"^";
                                        }else{
                                            Crosstableresults3="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
                                    for (int j = 0; j < subServiceAdapter3.subServiceArrayList3.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter3.subServiceArrayList3.size()) {
                                            for (SubService d :subServiceAdapter3.subServiceArrayList3) {
                                                if (j == value) {
                                                    Crosstableresults4 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults4 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults4!=""){
                                            Crosstableresults4=Crosstableresults4+"^";
                                        }else{
                                            Crosstableresults4="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
                                    for (int j = 0; j < subServiceAdapter4.subServiceArrayList4.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        //  int k = 0;
                                        if (j < subServiceAdapter4.subServiceArrayList4.size()) {
                                            for (SubService d : subServiceAdapter4.subServiceArrayList4) {
                                                if (j == value) {
                                                    Crosstableresults5 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults5 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults5!=""){
                                            Crosstableresults5=Crosstableresults5+"^";
                                        }else{
                                            Crosstableresults5="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
                                    for (int j = 0; j < subServiceAdapter5.subServiceArrayList5.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter5.subServiceArrayList5.size()) {
                                            for (SubService d : subServiceAdapter5.subServiceArrayList5) {
                                                if (j == value) {
                                                    Crosstableresults6 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults6 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults6!=""){
                                            Crosstableresults6=Crosstableresults6+"^";
                                        }else{
                                            Crosstableresults6="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
                                    for (int j = 0; j < subServiceAdapter6.subServiceArrayList6.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        //int k = 0;
                                        if (j < subServiceAdapter6.subServiceArrayList6.size()) {
                                            for (SubService d : subServiceAdapter6.subServiceArrayList6) {
                                                if (j == value) {
                                                    Crosstableresults7 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults7 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults7!=""){
                                            Crosstableresults7=Crosstableresults7+"^";
                                        }else{
                                            Crosstableresults7="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
                                    for (int j = 0; j < subServiceAdapter7.subServiceArrayList7.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter7.subServiceArrayList7.size()) {
                                            for (SubService d : subServiceAdapter7.subServiceArrayList7) {
                                                if (j == value) {
                                                    Crosstableresults8 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults8 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults8!=""){
                                            Crosstableresults8=Crosstableresults8+"^";
                                        }else{
                                            Crosstableresults8="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
                                    for (int j = 0; j < subServiceAdapter8.subServiceArrayList8.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter8.subServiceArrayList8.size()) {
                                            for (SubService d : subServiceAdapter8.subServiceArrayList8) {
                                                if (j == value) {
                                                    Crosstableresults9 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults9 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults9!=""){
                                            Crosstableresults9=Crosstableresults9+"^";
                                        }else{
                                            Crosstableresults9="";
                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList10())) {
                                    for (int j = 0; j < subServiceAdapter9.subServiceArrayList9.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        //  int k = 0;
                                        if (j < subServiceAdapter9.subServiceArrayList9.size()) {
                                            for (SubService d :subServiceAdapter9.subServiceArrayList9) {
                                                if (j == value) {
                                                    Crosstableresults10 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults10 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                        if(Crosstableresults10!=""){
                                            Crosstableresults10=Crosstableresults10+"^";
                                        }else{
                                            Crosstableresults10="";
                                        }
                                    }
                                }
                               /* else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList11())) {
                                    for (int j = 0; j < subServiceAdapter10.subServiceArrayList10.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        //  int k = 0;
                                        if (j < subServiceAdapter10.subServiceArrayList10.size()) {
                                            for (SubService d : subServiceAdapter10.subServiceArrayList10) {
                                                if (j == value) {
                                                    Crosstableresults11 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults11 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                    }
                                }
                                else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList12())) {
                                    for (int j = 0; j < subServiceAdapter11.subServiceArrayList11.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter11.subServiceArrayList11.size()) {
                                            for (SubService d : subServiceAdapter11.subServiceArrayList11) {
                                                if (j == value) {
                                                    Crosstableresults12 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults12 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList13())) {
                                    for (int j = 0; j < subServiceAdapter12.subServiceArrayList12.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter12.subServiceArrayList12.size()) {
                                            for (SubService d : subServiceAdapter12.subServiceArrayList12) {
                                                if (j == value) {
                                                    Crosstableresults13 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults13 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList14())) {
                                    for (int j = 0; j < subServiceAdapter13.subServiceArrayList13.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        //  int k = 0;
                                        if (j < subServiceAdapter13.subServiceArrayList13.size()) {
                                            for (SubService d : subServiceAdapter13.subServiceArrayList13) {
                                                if (j == value) {
                                                    Crosstableresults14 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults14 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                    }
                                } else if (serviceListName.equalsIgnoreCase(ProjectMethods.getServiceList15())) {
                                    for (int j = 0; j < subServiceAdapter14.subServiceArrayList14.size(); j++) {

                                        int value = subServiceHeaderArrayList.size();
                                        // int k = 0;
                                        if (j < subServiceAdapter14.subServiceArrayList14.size()) {
                                            for (SubService d : subServiceAdapter14.subServiceArrayList14) {
                                                if (j == value) {
                                                    Crosstableresults15 += "~" + d.getValue().toString().trim() + "!";
                                                    value = value + subServiceHeaderArrayList.size();
                                                    j++;
                                                } else {
                                                    Crosstableresults15 += d.getValue().toString().trim() + "!";
                                                    j++;
                                                }
                                            }

                                        }
                                    }
                                }*/
                            }
                        }

                        finalServiceDetailList = (new StringBuilder()).append(Crosstableresults1).append(Crosstableresults2).append(Crosstableresults3).append(Crosstableresults4).append(Crosstableresults5).append(Crosstableresults6).append(Crosstableresults7).append(Crosstableresults8).append(Crosstableresults9).append(Crosstableresults10).append(Crosstableresults11).append(Crosstableresults12).append(Crosstableresults13).append(Crosstableresults14).append(Crosstableresults15).toString();
                       /* int index=finalServiceDetailList.lastIndexOf('!');
                        System.out.println(finalServiceDetailList.substring(0,index));
                        finalServiceDetailList = finalServiceDetailList.replaceFirst("^^^^^^^^^^^^^^^$","");
                        Log.v("Result", finalServiceDetailList);
                        Log.v("Result", finalServiceDetailList);*/
                        if (Validation()) {

                            CustomDailog("Job Card", "Do You Want to Save JobCard Details?", 38, "save");


                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                case R.id.IdClearBtn:
                    try {
                        CustomDailog("Job Card", "Do You Want to Clear Data?", 33, "clear");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                case R.id.IdDocumentTypeBtn:
                    try {
                        GetDocumentDetails();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                case R.id.CustTimeOutEdt:
                    try {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        CustTimeOutEdt.setText(hourOfDay + ":" + minute + " " + "AM");
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                case R.id.CustTimeInEdt:
                    try {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        CustTimeInEdt.setText(hourOfDay + ":" + minute + " " + "AM");
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                case R.id.IdImageCaptureBtn:
                    try {
                        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
                        }

                    } catch (ActivityNotFoundException anfe) {
                        //display an error message
                        String errorMessage = "Whoops - your device doesn't support capturing images!";
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.IdSignatureBtn:
                    try {

                        Intent sig = new Intent(MainActivity.this, DrawingSignatureActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        retrivesharedPreferences();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.remove("PRODUCT_PHOTO");
        editor.commit();
    }

    private void GetDocumentDetails() {
        try {
            Intent salesman = new Intent(MainActivity.this, DocumentListActivity.class);
            salesman.putExtra("mylist", documentTypesArrayList);
            salesman.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(salesman, 101);
           /* try {
                swipeRefreshLayout.setRefreshing(true);
                if (AppUtil.isNetworkAvailable(MainActivity.this)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("TypeName", "DOCUMENTS");
                    requestName = "GeneralMasterData";
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
                } else {

                    //   dialogManager.showAlertDialog(MainActivity.this, "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CustomDailog(String Title, String msg, int value, String btntxt) {
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(MainActivity.this, CustomDialogClass.class);
            intent.putExtra("msgbtn", btntxt);
            startActivityForResult(intent, value);
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
            JobCardNoEdt.setText("");
            CustVehiclemakemodelEdt.setText("");
            CustVehicleModelEdt.setText("");
            CustOdometerReadingEdt.setText("");
            AvgkmsperdayEdt.setText("");
            RegNoEdt.setText("");
            CustTimeOutEdt.setText("");
            CustTimeInEdt.setText("");
            RemarksEdt.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Validation() {
        boolean result = true;
        vehicleNo = VehicleNoEdt.getText().toString().trim();
        CustName = CustNameEdt.getText().toString().trim();
        CustEmailId = CustEmailIdEdt.getText().toString().trim();
        CustMobileNo = CustMobileNoEdt.getText().toString().trim();
       // JobCardNo = JobCardNoEdt.getText().toString().trim();
        CustDate = CustDateEdt.getText().toString().trim();
        CustVehiclemakemodel = CustVehiclemakemodelEdt.getText().toString().trim();
        CustVehicleModel = CustVehicleModelEdt.getText().toString().trim();
        CustOdometerReading = CustOdometerReadingEdt.getText().toString().trim();
        Avgkmsperday = AvgkmsperdayEdt.getText().toString().trim();
        RegNo = RegNoEdt.getText().toString().trim();
        CustTimeOut = CustTimeOutEdt.getText().toString().trim();
        CustTimeIn = CustTimeInEdt.getText().toString().trim();
        Remarks = RemarksEdt.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (vehicleNo.length() == 0) {
            VehicleNoEdt.requestFocus();
            VehicleNoEdt.setError("Please Enter VehicleNo");
            result = false;
        } else if (CustName.length() == 0) {
            CustNameEdt.requestFocus();
            CustNameEdt.setError("Please Enter Name");
            result = false;
        } else if (!CustEmailId.matches(EMAIL_PATTERN) == true) {
            CustEmailIdEdt.requestFocus();
            CustEmailIdEdt.setFocusable(true);
            CustEmailIdEdt.setError("Please Enter Valid EmailId");
            result = false;
        } else if (CustMobileNo.length() == 0 || CustMobileNo.length() < 10) {
            CustMobileNoEdt.requestFocus();
            CustMobileNoEdt.setError("Please Enter Valid Mobile No");
            result = false;

        } else if (CustVehiclemakemodel.length() == 0) {
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

        }
       /* else if (JobCardNo.length() == 0 || JobCardNo.toString().equalsIgnoreCase("")) {
            JobCardNoEdt.requestFocus();
            JobCardNoEdt.setError("Please Enter JobCardNo");
            result = false;

        } */
        else if (CustDate.length() == 0) {
            CustDateEdt.requestFocus();
            CustDateEdt.setError("Please Enter CustomerDate");
            result = false;

        } else if (CustTimeIn.length() == 0) {
            CustTimeInEdt.requestFocus();
            CustTimeInEdt.setError("Please Enter TimeIn");
            result = false;

        } else if (RegNo.toString().length() == 0 || RegNo.toString().equalsIgnoreCase("")) {

            RegNoEdt.requestFocus();
            RegNoEdt.setError("Please Enter RegNo");
            result = false;
        } else if (Avgkmsperday.toString().length() == 0 || Avgkmsperday.toString().equalsIgnoreCase("")) {

            AvgkmsperdayEdt.requestFocus();
            AvgkmsperdayEdt.setError("Please Select AvgKmsPerDay");
            result = false;
        } else if (Remarks.toString().length() == 0 || Remarks.toString().equalsIgnoreCase("")) {

            RemarksEdt.requestFocus();
            RemarksEdt.setError("Please Select Remarks");
            result = false;
        }
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
              /*  picUri = data.getData();
                performCrop();*/
                IdCaptureImgView.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                IdCaptureImgView.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            picUri = data.getData();
            performCrop();
        }

        //user is returning from cropping the image
        else if (requestCode == PIC_CROP) {
            //get the returned data
            Bundle extras = data.getExtras();
            //get the cropped bitmap
            Bitmap thePic = (Bitmap) extras.get("data");
            //display the returned cropped image
            IdCaptureImgView.setImageBitmap(thePic);
        } else if (requestCode == 33 && data != null && resultCode == RESULT_OK) {
            ClearData();
        } else if (requestCode == 38 && data != null && resultCode == RESULT_OK) {
            SaveData();
        } else if (requestCode == 35 && data != null && resultCode == RESULT_OK) {
            SaveDataToServerDb();
        } else if (requestCode == 101 && data != null && resultCode == RESULT_OK) {
            try {
                // Intent intent=getIntent();
             /*   for (int i = 0; i < DocumentListAdapter.salesmenlist.size(); i++){
                    if(DocumentListAdapter.salesmenlist.get(i).getSelected()) {
                        tv.setText(tv.getText() + " " + CustomAdapter.imageModelArrayList.get(i).getAnimal());
                    }
                }*/
              /*  StringBuilder stringBuilder = new StringBuilder();
                SparseBooleanArray selectedRows = DocumentListAdapter.getSelectedIds();
                if (selectedRows.size() > 0) {
                    String EditValue = "";
                    for (int i = 0; i < selectedRows.size(); i++) {
                        if (selectedRows.valueAt(i)) {
                            String selectedRowLabel = DocumentListAdapter.salesmenlist.get(i).getName();
                            String edittextvalue = DocumentListAdapter.salesmenlist.get(i).getEdtValue();
                            if (edittextvalue == null) {
                                edittextvalue = "";
                            }
                            if (edittextvalue.length() > 0) {
                                EditValue = "(" + edittextvalue + ")";
                            } else {
                                EditValue = "";
                            }
                            stringBuilder.append(selectedRowLabel + EditValue + "\n");
                        }
                    }

                }*/
                String EditValue = "";
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int i = 0; i < DocumentListAdapter.salesmenlist.size(); i++) {
                    if (DocumentListAdapter.salesmenlist.get(i).getSelected()) {
                        String selectedRowLabel = DocumentListAdapter.salesmenlist.get(i).getName();
                        String selectedRowId = DocumentListAdapter.salesmenlist.get(i).getId();
                        String edittextvalue = DocumentListAdapter.salesmenlist.get(i).getEdtValue();
                        if (edittextvalue == null) {
                            edittextvalue = "";
                        }
                        if (edittextvalue.length() > 0) {
                            EditValue = "(" + edittextvalue + ")";
                        } else {
                            EditValue = "";
                        }
                        stringBuilder.append(selectedRowLabel + EditValue + ",");
                        stringBuilder1.append(selectedRowId + EditValue + ",");
                    }
                }
                // txtDocumentList.setText(data.getStringExtra("Documents"));
                txtDocumentList.setText(stringBuilder.toString());
                txtDocumentList.setTag(stringBuilder1.toString());
                /*if (intent != null) {
                    myList =this.getIntent().getParcelableArrayListExtra("MyListData");

                }*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void SaveData() {
        try {
            String list = txtDocumentList.getTag().toString();
            String ServiceIdValues = finalselectedServiceList;
            String DocumentIdValues = list;
            ServiceIdValues = ServiceIdValues.replaceAll(",$", "");
            DocumentIdValues = DocumentIdValues.replaceAll(",$", "");
            Gson userGson=new GsonBuilder().create();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(JSONVariables.JCDate, CustDate);
                    jsonObject.accumulate(JSONVariables.JCId, "");
                    jsonObject.accumulate(JSONVariables.JCNo, "");
                    jsonObject.accumulate(JSONVariables.JC_InTime, CustTimeIn);
                    jsonObject.accumulate(JSONVariables.JC_OutTime, CustTimeOut);
                    jsonObject.accumulate(JSONVariables.CustomerId, mCustomerId);
                    jsonObject.accumulate(JSONVariables.Vehicle_Id, mVehicleNo);
                    jsonObject.accumulate(JSONVariables.OMReading, CustOdometerReading);
                    jsonObject.accumulate(JSONVariables.AvgKMS_RPD, Avgkmsperday);
                    jsonObject.accumulate(JSONVariables.ServiceId, ServiceIdValues);
                    jsonObject.accumulate(JSONVariables.SUserId, ProjectMethods.getUserId());
                    jsonObject.accumulate(JSONVariables.CounterId, ProjectMethods.getCounterId());
                    jsonObject.accumulate(JSONVariables.ServiceDetails, finalServiceDetailList);
                    jsonObject.accumulate(JSONVariables.JCImage, "");
                    jsonObject.accumulate(JSONVariables.Documents, DocumentIdValues);
                    jsonObject.accumulate(JSONVariables.CustName, CustName);
                    jsonObject.accumulate(JSONVariables.CustEmailId, CustEmailId);
                    jsonObject.accumulate(JSONVariables.CustMobileNo, CustMobileNo);
                    jsonObject.accumulate(JSONVariables.JCRemarks, Remarks);
                    jsonObject.accumulate(JSONVariables.JCVehicleNo, vehicleNo);
                    jsonObject.accumulate(JSONVariables.JCMake, CustVehiclemakemodel);
                    jsonObject.accumulate(JSONVariables.JCModel, CustVehicleModel);
                    jsonObject.accumulate(JSONVariables.JCRegno, RegNo);
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    requestName = "SaveJobCardDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SaveJobCardDetails,jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveDataToServerDb() {
        try {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                //String URL = "http://Myaccountsonline.co.in/TestServices/LeadManagement.svc/SaveUpdateMeeting";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("LeadId", "1");
                jsonBody.put("LeadName", "");
                jsonBody.put("MobileNo", "");
                jsonBody.put("fromdate", "");
                jsonBody.put("fromtime", "");
                jsonBody.put("totime", "");
                jsonBody.put("UserId", "1");
                jsonBody.put("Location", "hyd");
                jsonBody.put("Description", "");
                jsonBody.put("ColorCode", "4");
                jsonBody.put("Eventid", "6");
                jsonBody.put("MeetingId", "");
                jsonBody.put("SaveMode", "SAVE");

                final String requestBody = jsonBody.toString();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, "url", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", "post responese" + response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jresponse = jsonArray.getJSONObject(i);
                                String Result = jresponse.getString("Result");
                                Log.d("Result", Result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsup ported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                requestQueue.add(stringRequest);
                stringRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 50000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 50000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performCrop() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    //create helping method cropCapturedImage(Uri picUri)
    public void cropCapturedImage(Uri picUri) {
        //call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }

    @Override
    public void onRefresh() {
        try {
            swipeRefreshLayout.setRefreshing(false);
            // handelCustomerDetailsDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OnServiceCallCompleteListenerImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetCustomerDataOpenData")) {
                try {
                    handeUserDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GeneralMasterData")) {
                try {
                    handeDocumentDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetDetailsByVehicleNo")) {
                try {
                    handeVehicleDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("ServiceMaster")) {
                try {
                    handeServiceMasterDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetJobCardNumber")) {
                try {
                    handeGetJobCardNumberDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("SubServiceMaster")) {
                try {
                    handeGetSubServiceMasterDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("Getlist")) {
                try {
                    handeGetlistDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("SaveJobCardDetails")) {
                try {
                    handeSaveJobCardDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
           swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void handeSaveJobCardDetails(JSONArray jsonArray) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            if(jsonArray.length()>0){
                String result=jsonArray.getJSONObject(0).getString("Result");
                String JobCardNumber=jsonArray.getJSONObject(0).getString("JobCardNumber");
                if(result!=null){
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                    ClearData();
                }
                JobCardNoEdt.setText(JobCardNumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeGetlistDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {

        }
    }

    private void handeGetSubServiceMasterDetails(final JSONArray jsonArray) {

        try {
            subServiceArrayList = new ArrayList<>();
            Log.v("before service", "checkboxclick");
            boolean result = true;
            boolean finalResult = false;
            if (jsonArray.length() > 0) {
                subServiceArrayList.clear();
                subServiceHeaderArrayList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        if (i == 0) {
                            JSONObject serviceName = jsonArray.getJSONObject(i);
                            ServiceName = serviceName.getString("Data");

                        } else if (i == 1) {
                            JSONObject serviceName = jsonArray.getJSONObject(i);
                            ServiceValue = serviceName.getString("Data");

                        } else {

                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONArray jsonArray1 = object.getJSONArray("Li");
                            for (int k = 0; k < jsonArray1.length(); k++) {
                                JSONArray jsonArray2 = jsonArray1.getJSONArray(k);

                                for (int j = 0; j < jsonArray2.length(); j++) {
                                    try {
                                        JSONObject Obj = jsonArray2.getJSONObject(j);
                                        String NoResult = Obj.getString("Key");
                                        if (NoResult.equalsIgnoreCase("Result")) {
                                            finalResult = false;
                                            //checkBox.setChecked(false);
                                            Toast.makeText(MainActivity.this, Obj.getString("Value"), Toast.LENGTH_SHORT).show();
                                            swipeRefreshLayout.setRefreshing(false);
                                        } else {
                                            finalResult = true;
                                            if (result) {
                                                for (int l = 0; l < jsonArray2.length(); l++) {
                                                    JSONObject listObj = jsonArray2.getJSONObject(l);
                                                    SubService subService = new SubService();
                                                    subService.setValue(listObj.getString("Key"));
                                                    subServiceArrayList.add(subService);
                                                    subServiceHeaderArrayList.add(subService);
                                                }
                                            }
                                            result = false;
                                            JSONObject listObj = jsonArray2.getJSONObject(j);
                                            SubService subService = new SubService();
                                            subService.setValue(listObj.getString("Value"));
                                            subServiceArrayList.add(subService);
                                    /*SubServiceHeader subServiceHeader = new SubServiceHeader();
                                    subServiceHeader.setKey(listObj.getString("Key"));*/
                                            // subService.setKey(listObj.getString("Key"));

                                   /* if (result) {
                                        subServiceHeaderArrayList.add(subServiceHeader);

                                    }*/
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                result = false;
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
                  /*  swipeRefreshLayout.setRefreshing(false);

                        SelectedSubServiceLayout1.setVisibility(View.VISIBLE);
                        txtCheckedService1.setVisibility(View.VISIBLE);
                        txtCheckedService1.setText(ServiceName);
                        txtCheckedService1.setTag(ServiceValue);
                        int numberOfColumns1 = subServiceHeaderArrayList.size();
                        SubServiceRecyclerviewid.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                       subServiceSelectedAdapter = new SubServiceSelectedAdapter(MainActivity.this, R.layout.selectedsubservice_row_item, subServiceArrayList);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns1, LinearLayoutManager.VERTICAL);
                        SubServiceRecyclerviewid.setLayoutManager(staggeredGridLayoutManager);
                        SubServiceRecyclerviewid.setHasFixedSize(true);
                        SubServiceRecyclerviewid.setItemAnimator(new DefaultItemAnimator());
                        SubServiceRecyclerviewid.setAdapter(subServiceSelectedAdapter);
*/


            if (finalResult) {
                Log.v("before service", "checkboxclick");
                swipeRefreshLayout.setRefreshing(false);
                if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
                    SelectedSubServiceLayout1.setVisibility(View.VISIBLE);
                    txtCheckedService1.setVisibility(View.VISIBLE);
                    txtCheckedService1.setText(ServiceName);
                    txtCheckedService1.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerviewid.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter = new SubServiceAdapter(MainActivity.this, R.layout.selectedsubservice_row_item, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerviewid.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerviewid.setHasFixedSize(true);
                    SubServiceRecyclerviewid.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerviewid.setAdapter(subServiceAdapter);
         /*           subServiceAdapter.setOnItemClickListner(new SubServiceAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter.subServiceArrayList.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter.subServiceArrayList.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });
*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
                    SelectedSubServiceLayout2.setVisibility(View.VISIBLE);
                    txtCheckedService2.setVisibility(View.VISIBLE);
                    txtCheckedService2.setText(ServiceName);
                    txtCheckedService2.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview1.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter1 = new SubServiceAdapter1(MainActivity.this, R.layout.selected_service_row_item_one_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview1.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview1.setHasFixedSize(true);
                    SubServiceRecyclerview1.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview1.setAdapter(subServiceAdapter1);
                   /* subServiceAdapter1.setOnItemClickListner(new SubServiceAdapter1.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter1.subServiceArrayList1.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter1.subServiceArrayList1.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
                    SelectedSubServiceLayout3.setVisibility(View.VISIBLE);
                    txtCheckedService3.setVisibility(View.VISIBLE);
                    txtCheckedService3.setText(ServiceName);
                    txtCheckedService3.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview2.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter2 = new SubServiceAdapter2(MainActivity.this, R.layout.selected_service_row_item_two_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview2.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview2.setHasFixedSize(true);
                    SubServiceRecyclerview2.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview2.setAdapter(subServiceAdapter2);
                  /*  subServiceAdapter2.setOnItemClickListner(new SubServiceAdapter2.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter2.subServiceArrayList2.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter2.subServiceArrayList2.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
                    SelectedSubServiceLayout4.setVisibility(View.VISIBLE);
                    txtCheckedService4.setVisibility(View.VISIBLE);
                    txtCheckedService4.setText(ServiceName);
                    txtCheckedService4.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview3.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter3 = new SubServiceAdapter3(MainActivity.this, R.layout.selected_service_row_item_three_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview3.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview3.setHasFixedSize(true);
                    SubServiceRecyclerview3.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview3.setAdapter(subServiceAdapter3);
                  /*  subServiceAdapter3.setOnItemClickListner(new SubServiceAdapter3.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter3.subServiceArrayList3.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter3.subServiceArrayList3.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
                    SelectedSubServiceLayout5.setVisibility(View.VISIBLE);
                    txtCheckedService5.setVisibility(View.VISIBLE);
                    txtCheckedService5.setText(ServiceName);
                    txtCheckedService5.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview4.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter4 = new SubServiceAdapter4(MainActivity.this, R.layout.selected_service_row_item_four_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview4.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview4.setHasFixedSize(true);
                    SubServiceRecyclerview4.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview4.setAdapter(subServiceAdapter4);
                   /* subServiceAdapter4.setOnItemClickListner(new SubServiceAdapter4.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter4.subServiceArrayList4.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter4.subServiceArrayList4.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
                    SelectedSubServiceLayout6.setVisibility(View.VISIBLE);
                    txtCheckedService6.setVisibility(View.VISIBLE);
                    txtCheckedService6.setText(ServiceName);
                    txtCheckedService6.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview5.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter5 = new SubServiceAdapter5(MainActivity.this, R.layout.selected_service_row_item_five_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview5.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview5.setHasFixedSize(true);
                    SubServiceRecyclerview5.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview5.setAdapter(subServiceAdapter5);
                    /*subServiceAdapter5.setOnItemClickListner(new SubServiceAdapter5.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter5.subServiceArrayList5.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter5.subServiceArrayList5.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
                    SelectedSubServiceLayout7.setVisibility(View.VISIBLE);
                    txtCheckedService7.setVisibility(View.VISIBLE);
                    txtCheckedService7.setText(ServiceName);
                    txtCheckedService7.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview6.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter6 = new SubServiceAdapter6(MainActivity.this, R.layout.selected_service_row_item_six_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview6.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview6.setHasFixedSize(true);
                    SubServiceRecyclerview6.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview6.setAdapter(subServiceAdapter6);
                   /* subServiceAdapter6.setOnItemClickListner(new SubServiceAdapter6.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter6.subServiceArrayList6.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter6.subServiceArrayList6.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
                    SelectedSubServiceLayout8.setVisibility(View.VISIBLE);
                    txtCheckedService8.setVisibility(View.VISIBLE);
                    txtCheckedService8.setText(ServiceName);
                    txtCheckedService8.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview7.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter7 = new SubServiceAdapter7(MainActivity.this, R.layout.selected_service_row_item_seven_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview7.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview7.setHasFixedSize(true);
                    SubServiceRecyclerview7.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview7.setAdapter(subServiceAdapter7);
                  /*  subServiceAdapter7.setOnItemClickListner(new SubServiceAdapter7.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter7.subServiceArrayList7.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter7.subServiceArrayList7.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
                    SelectedSubServiceLayout9.setVisibility(View.VISIBLE);
                    txtCheckedService9.setVisibility(View.VISIBLE);
                    txtCheckedService9.setText(ServiceName);
                    txtCheckedService9.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview8.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter8 = new SubServiceAdapter8(MainActivity.this, R.layout.selected_service_row_item_eight_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview8.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview8.setHasFixedSize(true);
                    SubServiceRecyclerview8.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview8.setAdapter(subServiceAdapter8);
                  /*  subServiceAdapter8.setOnItemClickListner(new SubServiceAdapter8.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter8.subServiceArrayList8.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter8.subServiceArrayList8.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList10())) {
                    SelectedSubServiceLayout10.setVisibility(View.VISIBLE);
                    txtCheckedService10.setVisibility(View.VISIBLE);
                    txtCheckedService10.setText(ServiceName);
                    txtCheckedService10.setTag(ServiceValue);
                    int numberOfColumns = subServiceHeaderArrayList.size();
                    SubServiceRecyclerview9.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                    subServiceAdapter9 = new SubServiceAdapter9(MainActivity.this, R.layout.selected_service_row_item_night_layout, subServiceArrayList, subServiceHeaderArrayList, ServiceName);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
                    SubServiceRecyclerview9.setLayoutManager(staggeredGridLayoutManager);
                    SubServiceRecyclerview9.setHasFixedSize(true);
                    SubServiceRecyclerview9.setItemAnimator(new DefaultItemAnimator());
                    SubServiceRecyclerview9.setAdapter(subServiceAdapter9);
                  /*  subServiceAdapter9.setOnItemClickListner(new SubServiceAdapter9.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, String Value) {
                            switch (view.getId()) {
                                case R.id.SubServiceValue:
                                    EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    SubService SI = subServiceArrayList.get(position);
                                    SubService IE = new SubService();
                                    IE.setValue(SI.getValue());
                                    subServiceArrayList.add(IE);
                                    //Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < subServiceAdapter9.subServiceArrayList9.size(); i++) {

                                        Log.v("ValueList", editvalue.getText().toString() + " " + subServiceAdapter9.subServiceArrayList9.get(i).getValue() + System.getProperty("line.separator"));

                                    }
                                    // EditText editvalue = (EditText) findViewById(R.id.SubServiceValue);
                                    editvalue.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            SubService SI = subServiceArrayList.get(position);
                                            SubService IE = new SubService();
                                            IE.setValue(SI.getValue());
                                            subServiceArrayList.add(IE);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            // SaveMethod(subServiceArrayList);

                                        }
                                    });

                                    break;


                            }

                        }
                    });*/
                }

            }


         /*
            int spanCount = 3; // 3 columns
            int spacing = 50; // 50px
            boolean includeEdge = true;
           // recyclerView.addItemDecoration(new GridLayoutManager(spanCount, spacing, includeEdge));
            if (result = false) {
                subServiceAdapter = new SubServiceAdapter(MainActivity.this, R.layout.selectedsubservice_row_item, subServiceHeaderArrayList,subServiceHeaderArrayList);
                LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1, GridLayoutManager.HORIZONTAL, false);
                SubServiceRecyclerviewid.setLayoutManager(layoutManager);
                SubServiceRecyclerviewid.setHasFixedSize(true);
              *//*  SubServiceRecyclerviewid.setAdapter(menuTableTopCursorAdapter);
                int numberOfColumns = subServiceHeaderArrayList.size();
                SubServiceRecyclerviewid.setLayoutManager(new GridLayoutManager(this, numberOfColumns));*//*

            } else {
                *//*int numberOfColumns = subServiceHeaderArrayList.size();
                SubServiceRecyclerviewid.setLayoutManager(new GridLayoutManager(this, numberOfColumns));*//*
                //...................................................................................................
              *//*  int numberOfColumns = subServiceHeaderArrayList.size();
                SubServiceRecyclerviewid.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                subServiceAdapter = new SubServiceAdapter(MainActivity.this, R.layout.selectedsubservice_row_item, subServiceArrayList);
                LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns, GridLayoutManager.HORIZONTAL, false);
               // LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns);
                SubServiceRecyclerviewid.setLayoutManager(layoutManager);
                SubServiceRecyclerviewid.setHasFixedSize(true);*//*
             // ...........................................................................
               *//* LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns, GridLayoutManager.VERTICAL, false);
                SubServiceRecyclerviewid.setLayoutManager(layoutManager);
                SubServiceRecyclerviewid.setHasFixedSize(true);*//*

                int numberOfColumns = subServiceHeaderArrayList.size();
                SubServiceRecyclerviewid.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
                subServiceAdapter = new SubServiceAdapter(MainActivity.this, R.layout.selectedsubservice_row_item, subServiceArrayList,subServiceHeaderArrayList);
                StaggeredGridLayoutManager staggeredGridLayoutManager =new StaggeredGridLayoutManager(numberOfColumns,LinearLayoutManager.VERTICAL);

               // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                SubServiceRecyclerviewid.setLayoutManager(staggeredGridLayoutManager);
                SubServiceRecyclerviewid.setHasFixedSize(true);

            }

          //  LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
           // SubServiceRecyclerviewid.setLayoutManager(linearLayoutManager1);
            SubServiceRecyclerviewid.setItemAnimator(new DefaultItemAnimator());
            SubServiceRecyclerviewid.setAdapter(subServiceAdapter);*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void SaveMethod(ArrayList<SubService> subServiceArrayList) {
        try {

            for (int i = 0; i < subServiceAdapter.subServiceArrayList.size(); i++) {

                Log.v("ValueList", subServiceAdapter.subServiceArrayList.get(i).getValue() + System.getProperty("line.separator"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void methodCalling() {

    }

    /* @Override
     protected void onMeasure(int widthSpec, int heightSpec) {
         super.onMeasure(widthSpec, heightSpec);
         if (columnWidth > 0) {
             int spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
             manager.setSpanCount(spanCount);
         }
     }*/
    private void handeGetJobCardNumberDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");
                String Result = jsonArray.getJSONObject(0).getString("Result");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeServiceMasterDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                serviceMasterArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(MainActivity.this, Result, Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            ServiceMaster serviceMaster = new ServiceMaster();
                            serviceMaster.setServiceId(object.getString("ServiceId"));
                            serviceMaster.setServiceName(object.getString("ServiceName"));
                            serviceMasterArrayList.add(serviceMaster);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                BindValueToString(serviceMasterArrayList);
            }
            swipeRefreshLayout.setRefreshing(false);
            selectedServiceAdpater = new SelectedServiceAdpater(MainActivity.this, R.layout.selectedservice_row_item, serviceMasterArrayList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 2);
            SelectedServiceRecyclerviewid.setLayoutManager(gridLayoutManager1);
            SelectedServiceRecyclerviewid.setItemAnimator(new DefaultItemAnimator());
            SelectedServiceRecyclerviewid.setAdapter(selectedServiceAdpater);

           /* selectedServiceAdpater.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                }
            });*/


            selectedServiceAdpater.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    switch (view.getId()) {

                        case R.id.ServiceNameCheckBox:
                            checkBox = (CheckBox) findViewById(R.id.ServiceNameCheckBox);
                            boolean checked = ((CheckBox) view).isChecked();
                            if (checked) {
                                // SelectedSubServiceLayout1.setVisibility(View.VISIBLE);
                                String ServiceId = serviceMasterArrayList.get(position).getServiceId();
                                ServiceIdKey = serviceMasterArrayList.get(position).getServiceName();
                                if (serviceMasterArrayList.get(position).getSelected()) {
                                    serviceMasterArrayList.get(position).setSelected(false);
                                } else {
                                    serviceMasterArrayList.get(position).setSelected(true);
                                }
                                // GetSubServiceListfromInternet u = null;

                                try {
                                    Log.v("before service", "checkboxclick");
                                    getSubServiceList(ServiceId);
                                  /*  u = new GetSubServiceListfromInternet(ServiceId);
                                    u.execute();*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // new getSubServiceListfromInternet().execute(ServiceId);
                                //  getSubServiceList(ServiceId);

                                // Toast.makeText(MainActivity.this, "" + serviceMasterArrayList.get(position).getServiceName(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
                                    SelectedSubServiceLayout1.setVisibility(View.GONE);
                                    txtCheckedService1.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList2())) {
                                    SelectedSubServiceLayout2.setVisibility(View.GONE);
                                    txtCheckedService2.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList3())) {
                                    SelectedSubServiceLayout3.setVisibility(View.GONE);
                                    txtCheckedService3.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList4())) {
                                    SelectedSubServiceLayout4.setVisibility(View.GONE);
                                    txtCheckedService4.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList5())) {
                                    SelectedSubServiceLayout5.setVisibility(View.GONE);
                                    txtCheckedService5.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList6())) {
                                    SelectedSubServiceLayout6.setVisibility(View.GONE);
                                    txtCheckedService6.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList7())) {
                                    SelectedSubServiceLayout7.setVisibility(View.GONE);
                                    txtCheckedService7.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList8())) {
                                    SelectedSubServiceLayout8.setVisibility(View.GONE);
                                    txtCheckedService8.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList9())) {
                                    SelectedSubServiceLayout9.setVisibility(View.GONE);
                                    txtCheckedService9.setVisibility(View.GONE);
                                } else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList10())) {
                                    SelectedSubServiceLayout10.setVisibility(View.GONE);
                                    txtCheckedService10.setVisibility(View.GONE);
                                }
                               /* else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList11())) {
                                    SelectedSubServiceLayout11.setVisibility(View.GONE);
                                    txtCheckedService11.setVisibility(View.GONE);
                                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList12())) {
                                    SelectedSubServiceLayout6.setVisibility(View.GONE);
                                    txtCheckedService6.setVisibility(View.GONE);
                                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList13())) {
                                    SelectedSubServiceLayout6.setVisibility(View.GONE);
                                    txtCheckedService6.setVisibility(View.GONE);
                                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList14())) {
                                    SelectedSubServiceLayout6.setVisibility(View.GONE);
                                    txtCheckedService6.setVisibility(View.GONE);
                                }else if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList15())) {
                                    SelectedSubServiceLayout6.setVisibility(View.GONE);
                                    txtCheckedService6.setVisibility(View.GONE);
                                }*/


                            }
                            break;
                    }

                }
            });
            /*if(serviceMasterArrayList.size()>0){
                AddTheNamesToLIst(serviceMasterArrayList);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BindValueToString(ArrayList<ServiceMaster> serviceMasterArrayList) {
        try {
            try {
                if (serviceMasterArrayList.size() > 0) {
                    for (int k = 0; k < serviceMasterArrayList.size(); k++) {
                        if (k == 0 && serviceMasterArrayList.get(0).getServiceName() != "") {
                            ProjectMethods.setServiceList1(serviceMasterArrayList.get(0).getServiceName());
                        } else if (k == 1 && serviceMasterArrayList.get(1).getServiceName() != "") {
                            ProjectMethods.setServiceList2(serviceMasterArrayList.get(1).getServiceName());
                        } else if (k == 2 && serviceMasterArrayList.get(2).getServiceName() != "") {
                            ProjectMethods.setServiceList3(serviceMasterArrayList.get(2).getServiceName());
                        } else if (k == 3 && serviceMasterArrayList.get(3).getServiceName() != "") {
                            ProjectMethods.setServiceList4(serviceMasterArrayList.get(3).getServiceName());
                        } else if (k == 4 && serviceMasterArrayList.get(4).getServiceName() != "") {
                            ProjectMethods.setServiceList5(serviceMasterArrayList.get(4).getServiceName());
                        } else if (k == 5 && serviceMasterArrayList.get(5).getServiceName() != "") {
                            ProjectMethods.setServiceList6(serviceMasterArrayList.get(5).getServiceName());
                        } else if (k == 6 && serviceMasterArrayList.get(6).getServiceName() != "") {
                            ProjectMethods.setServiceList7(serviceMasterArrayList.get(6).getServiceName());
                        } else if (k == 7 && serviceMasterArrayList.get(7).getServiceName() != "") {
                            ProjectMethods.setServiceList8(serviceMasterArrayList.get(7).getServiceName());
                        } else if (k == 8 && serviceMasterArrayList.get(8).getServiceName() != "") {
                            ProjectMethods.setServiceList9(serviceMasterArrayList.get(8).getServiceName());
                        } else if (k == 9 && serviceMasterArrayList.get(9).getServiceName() != "") {
                            ProjectMethods.setServiceList10(serviceMasterArrayList.get(9).getServiceName());
                        } else if (k == 10 && serviceMasterArrayList.get(10).getServiceName() != "") {
                            ProjectMethods.setServiceList11(serviceMasterArrayList.get(10).getServiceName());
                        } else if (k == 11 && serviceMasterArrayList.get(11).getServiceName() != "") {
                            ProjectMethods.setServiceList12(serviceMasterArrayList.get(11).getServiceName());
                        } else if (k == 12 && serviceMasterArrayList.get(12).getServiceName() != "") {
                            ProjectMethods.setServiceList13(serviceMasterArrayList.get(12).getServiceName());
                        } else if (k == 13 && serviceMasterArrayList.get(13).getServiceName() != "") {
                            ProjectMethods.setServiceList14(serviceMasterArrayList.get(13).getServiceName());
                        } else if (k == 14 && serviceMasterArrayList.get(14).getServiceName() != "") {
                            ProjectMethods.setServiceList15(serviceMasterArrayList.get(14).getServiceName());
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getSubServiceList(String serviceId) {
        try {
            Log.v("before service", "checkboxclick");
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("ServiceId", serviceId);
                    BackendServiceCall serviceCall = new BackendServiceCall(MainActivity.this, false);
                    requestName = "SubServiceMaster";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SubServiceMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeVehicleDetails(JSONArray jsonArray) {
        try {
            VehicleDetails vehicleDetails = new VehicleDetails();
            if (jsonArray.length() > 0) {
                mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                String CustomerName = jsonArray.getJSONObject(0).getString("CustomerName");
                String EmailId = jsonArray.getJSONObject(0).getString("EmailId");
                String MakeCompany = jsonArray.getJSONObject(0).getString("MakeCompany");
                String MakeYear = jsonArray.getJSONObject(0).getString("MakeYear");
                String MobileNo = jsonArray.getJSONObject(0).getString("MobileNo");
                String ModelNo = jsonArray.getJSONObject(0).getString("ModelNo");
                mVehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");

                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    CustNameEdt.setText(CustomerName);
                    CustEmailIdEdt.setText(EmailId);
                    CustMobileNoEdt.setText(MobileNo);
                    CustVehicleModelEdt.setText(ModelNo);
                    RegNoEdt.setText("");


                }
            }
            vehicleDetailsArrayList.add(vehicleDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeDocumentDetails(JSONArray jsonArray) {
        try {
            documentTypesArrayList = new ArrayList<>();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(MainActivity.this, Result, Toast.LENGTH_SHORT).show();

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

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeUserDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String customerId = jsonArray.getJSONObject(i).getString("CustomerId");
                    String CustomerName = jsonArray.getJSONObject(i).getString("CustomerName");
                    String Result = jsonArray.getJSONObject(i).getString("Result");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class GetSubServiceListfromInternet extends AsyncTask<String, String, Void> {
        ProgressDialog prgDialog;
        String serId;

        public GetSubServiceListfromInternet(String serviceId) {
            serId = serviceId;

        }

        @Override
        protected Void doInBackground(String... params) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        try {
                            URL url = new URL(ProjectVariables.BASE_URL + ProjectVariables.SubServiceMaster); //in the real code, there is an ip and a port
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            conn.connect();

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.accumulate("ServiceId", serId);
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            os.writeBytes((jsonParam.toString()));
                            os.flush();
                            os.close();

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());

                            conn.disconnect();
                        } catch (Exception e) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgDialog = new ProgressDialog(MainActivity.this);
            prgDialog.setMessage("Please wait...");
            prgDialog.show();

        }

        @Override
        protected void onPostExecute(Void s) {
            if (prgDialog.isShowing()) {
                prgDialog.dismiss();
            }
        }
    }
}
