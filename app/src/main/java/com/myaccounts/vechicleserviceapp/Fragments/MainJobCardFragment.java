package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adeel.library.BuildConfig;
import com.adeel.library.easyFTP;
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
import com.myaccounts.vechicleserviceapp.Activity.MakeListActivity;
import com.myaccounts.vechicleserviceapp.Activity.ModelListActivity;
import com.myaccounts.vechicleserviceapp.Activity.SparePartsActivity;
import com.myaccounts.vechicleserviceapp.Activity.VehicleTypeActivity;
import com.myaccounts.vechicleserviceapp.Adapter.DocumentListAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.SelectedServiceAdpater;
import com.myaccounts.vechicleserviceapp.Adapter.ServiceDetailsAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.SparePartIssueAdapter;
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
import com.myaccounts.vechicleserviceapp.MainActivity;
import com.myaccounts.vechicleserviceapp.Pojo.CategoryModelClass;
import com.myaccounts.vechicleserviceapp.Pojo.DocumentTypes;
import com.myaccounts.vechicleserviceapp.Pojo.GetSubService;
import com.myaccounts.vechicleserviceapp.Pojo.SelectedMainServiceAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.Pojo.SubService;
import com.myaccounts.vechicleserviceapp.Pojo.SubServiceList;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleTypes;
import com.myaccounts.vechicleserviceapp.R;
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
import com.myaccounts.vechicleserviceapp.Utils.UploadimageServer;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class MainJobCardFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "";
    private Toolbar toolbar;
    private ImageView IdRightArrow, IdLeftArrow;
    private EditText VehicleNoEdt, CustVehicleTypeEdt, CustNameEdt, CustEmailIdEdt, CustMobileNoEdt, QtyEdt, JobCardNoEdt, SparePartEdt, CustDateEdt, CustVehiclemakemodelEdt, CustVehicleModelEdt, CustOdometerReadingEdt, AvgkmsperdayEdt,
            RegNoEdt, MileageEdt, PlaceEdt, CustTimeInEdt, RemarksEdt;
    private RecyclerView mWheelServiceRecyclerviewid, SparePartrecyclerview, SelectedServiceRecyclerviewid, SubServiceRecyclerviewid, SubServiceRecyclerview1, SubServiceRecyclerview2, SubServiceRecyclerview3, SubServiceRecyclerview4, SubServiceRecyclerview5, SubServiceRecyclerview6, SubServiceRecyclerview7, SubServiceRecyclerview8, SubServiceRecyclerview9, SelectedServiceRecyclerviewid1;
    private WheelServiceAdapter wheelServiceAdapter;
    private SelectedMainServiceAdapter selectedMainServiceAdapter;
    private ServiceDetailsAdapter serviceDetailsAdapter;
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
    private Button IdImageCaptureBtn, IdSignatureBtn, IdClearBtn, IdSaveBtn;
    private String vehicleNo, requestName, CustName, CustMobileNo, CustEmailId, JobCardNo, CustFromDate, Mileage, CustVehiclemakemodel, CustVehicleModel, CustVehicleModelId, CustOdometerReading, Avgkmsperday, RegNo, Place, Remarks, CustTimeIn;
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
    private ArrayList<GetSubService> getSubServiceArrayList;
    private ArrayList<SubService> subServiceArrayList = new ArrayList<>();
    private ArrayList<SubServiceList> subServiceListArrayList = new ArrayList<>();
    private ArrayList<VehicleTypes> typesArrayList = new ArrayList<>();
    private ArrayList<VehicleTypes> BlockArrayList = new ArrayList<>();
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
    private TextView txtDocumentList, IdtotalRows, IdTotalQty, IdTotalAmt, AvlQtyTv, UomTv, mrpTv;
    private LinearLayout SelectedSubServiceLayout1, ImgBackLinerLayout, SelectedSubServiceLayout2, SelectedSubServiceLayout3, SelectedSubServiceLayout4, SelectedSubServiceLayout5, SelectedSubServiceLayout6, SelectedSubServiceLayout7, SelectedSubServiceLayout8, SelectedSubServiceLayout9, SelectedSubServiceLayout10;
    ArrayList<DocumentTypes> myList = new ArrayList<DocumentTypes>();
    private String ServiceIdKey, ServiceId, BusinessDate, ServiceName, ServiceValue, mCustomerId = "", mVehicleNo = "";
    private TextView txtCheckedService10, txtCheckedService9, txtCheckedService8, txtCheckedService7, txtCheckedService6, txtCheckedService5, txtCheckedService4, txtCheckedService3, txtCheckedService2, txtCheckedService1;
    CheckBox checkBox;
    private String finalServiceDetailList = "";
    String finalselectedServiceList = "", finalselectedIds = "", Crosstableresults1 = "", Crosstableresults2 = "", Crosstableresults3 = "", Crosstableresults4 = "", Crosstableresults5 = "", Crosstableresults6 = "", Crosstableresults7 = "", Crosstableresults8 = "", Crosstableresults9 = "",
            Crosstableresults10 = "", Crosstableresults11 = "", Crosstableresults12 = "", Crosstableresults13 = "", Crosstableresults14 = "", Crosstableresults15 = "";
    String byteArryImage;
    Bitmap bitmap;
    byte[] Imagepath;
    String imageEncoded = "";
    View view;
    private static final int CAMERA_IMAGE = 200;
    String ImageName = "";
    String imageURI = "";
    private Uri fileUri; // file url to store image/video
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private ProgressDialog pd;
    private Context context;
    private static final int WRITE_PERMISSION = 0x01;
    public static final String JOBCARD_STATUS = "jobcardstatus";
    private static Bitmap scaledphoto = null;
    private String filePath = null;
    private Uri u = null;
    private Boolean picTaken = false;
    private Object MyScrollListener;
    private File captureImagePath;
    private static final int TAKE_PHOTO = 115;
    private String imageURl = "", DocumentsName = "", DocumentsIds = "", DocumentIdList = "", DocumentNumList = "", SavedJobCardNo = "";
    private int month, day, year;
    private SharedPreferences jobcardPref;
    private boolean isFirstRun = true;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    ImageButton IdMakeImg, IdModelImg, IdVehicleTypeImg, AddImgBtn;
    Boolean drawImage = false;
    Dialog dialog;
    private Spinner TyreTypesSpn, SpnBlockSelection;
    private RecyclerView ServiceDetailsrecyclerview;
    private String selectUser, taskToId;
    private TextView selectedSericeListTv;
    private String checkedposition = "";
    private SparePartIssueAdapter sparePartIssueAdapter;
    private String AvlQty = "", UOMName = "", UOMId = "", MRP = "";
    float totalAmount, totalQty;
    String TotalGrossAmt, TotalQtyValue;
    private ArrayList<SparePartDetails> sparePartDetailsArrayList;
    private int position, ServiceIdSelectedPosition;
    private String finalSparePartDetailList = "", CloseServiceId = "";

    String ModelId;

    // new code
    ArrayList<CategoryModelClass> categoryList;
    // new code select type
    ArrayList<CategoryModelClass> selectTypeList;
    JSONArray result;
    public static final String TAG_ACTIVE_STATUS = "ActiveStatus";
    public static final String TAG_RESULT = "Result";
    public static final String TAG_SERVICE_ID = "ServiceId";
    public static final String TAG_SERVICE_NAME = "ServiceName";
    public static final String TAG_SUB_SERVICE = "SubService";
    ArrayList<HashMap<String, String>> productsList;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    RelativeLayout back_dim_layout;

    List<String> weightList, weightIdList;
    ArrayAdapter<String> weightAdapter;

    Spinner subSubServiceSpinner,subServiceSpinner;

    String selectedServiceId;

    List<String> selectList, selectIdList;


    public static MainJobCardFragment newInstance() {
        Bundle args = new Bundle();
        MainJobCardFragment fragment = new MainJobCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_jobcardscreen, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Job Card</font>"));
        context = getActivity();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setHasOptionsMenu(true);
        jobcardPref = getActivity().getSharedPreferences(JOBCARD_STATUS, Context.MODE_PRIVATE);
        SavedJobCardNo = jobcardPref.getString("JobCardNumber", null);
        vehicleDetailsArrayList = new ArrayList<>();
        serviceMasterArrayList = new ArrayList<>();
        subServiceHeaderArrayList = new ArrayList<>();
        sparePartDetailsArrayList = new ArrayList<>();
        getSubServiceArrayList = new ArrayList<>();
        InitializeVariables();
        // retrivesharedPreferences();
        initPermissions();
        dateFormat();
        // requestWritePermission();
        IdRightArrow = (ImageView) view.findViewById(R.id.IdRightArrow);
        IdLeftArrow = (ImageView) view.findViewById(R.id.IdLeftArrow);
        IdRightArrow.setOnClickListener(this);
        IdLeftArrow.setOnClickListener(this);
        AddListToGrid();

        //new code
        categoryList = new ArrayList<CategoryModelClass>();
        productsList = new ArrayList<>();
        weightList = new ArrayList<>();
        weightList.add("Select Weight");
        weightIdList = new ArrayList<>();
        weightIdList.add("Select Weight");
        new newCategories().execute();

        return view;
    }

    private void GetTheBusinessDate() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetBusinessDate";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImplData());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetBusinessDate, jsonObject, Request.Priority.HIGH);
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

    private void AddListToGrid() {
        sparePartIssueAdapter = new SparePartIssueAdapter(getActivity(), R.layout.sparepart_issue_row_item, sparePartDetailsArrayList, "jobcard");
        SparePartrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        SparePartrecyclerview.setItemAnimator(new DefaultItemAnimator());
        SparePartrecyclerview.setHasFixedSize(true);
        SparePartrecyclerview.setAdapter(sparePartIssueAdapter);

        sparePartIssueAdapter.SetOnItemClickListener(new SparePartIssueAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                boolean value = false;
                switch (view.getId()) {

                    case R.id.IdEditIconImg:
                        try {
                            CustomDailog("Spare Part Issue", "Do You Want to Edit Details?", 133, "Edit", position);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.IdDeleteIconImg:
                        try {
                            CustomDailog("Spare Part Issue", "Do You Want to Delete Details?", 134, "Delete", position);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                }
            }
        });

    }

    private void CustomDailog(String Title, String msg, int value, String btntxt, int pos) {
        try {
            position = pos;
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

    private void retrivesharedPreferences() {
        SharedPreferences shared = getActivity().getSharedPreferences("App_settings", Context.MODE_PRIVATE);
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
                if (bitmap != null) {
                    ImgBackLinerLayout.setVisibility(View.VISIBLE);
                    drawingImage.setVisibility(View.VISIBLE);
                    drawingImage.setImageBitmap(bitmap);
                }
              /*  Uri resultUri = byteArryImage.getUri();
                imageURl = resultUri.getPath();
                captureImagePath = new File(imageURl);
                imageURl = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                try {
                    UploadimageServer server = new UploadimageServer(captureImagePath, imageURl, resultUri, getActivity());
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitializeVariables() {
        try {
            VehicleNoEdt = (EditText) view.findViewById(R.id.VehicleNoEdt);
            CustVehicleTypeEdt = (EditText) view.findViewById(R.id.CustVehicleTypeEdt);
            CustNameEdt = (EditText) view.findViewById(R.id.CustNameEdt);
            CustEmailIdEdt = (EditText) view.findViewById(R.id.CustEmailIdEdt);
            CustMobileNoEdt = (EditText) view.findViewById(R.id.CustMobileNoEdt);
            JobCardNoEdt = (EditText) view.findViewById(R.id.JobCardNoEdt);
            SparePartEdt = (EditText) view.findViewById(R.id.IdSparePartEdt);
            QtyEdt = (EditText) view.findViewById(R.id.IdQtyEdt);
            SpnBlockSelection = (Spinner) view.findViewById(R.id.SpnBlockSelection);
            JobCardNoEdt.setText(SavedJobCardNo);
            CustDateEdt = (EditText) view.findViewById(R.id.CustDateEdt);
            CustVehiclemakemodelEdt = (EditText) view.findViewById(R.id.CustVehiclemakemodelEdt);
            CustVehicleModelEdt = (EditText) view.findViewById(R.id.CustVehicleModelEdt);
            CustOdometerReadingEdt = (EditText) view.findViewById(R.id.CustOdometerReadingEdt);
            AvgkmsperdayEdt = (EditText) view.findViewById(R.id.AvgkmsperdayEdt);
            RegNoEdt = (EditText) view.findViewById(R.id.RegNoEdt);
            MileageEdt = (EditText) view.findViewById(R.id.MileageEdt);
            PlaceEdt = (EditText) view.findViewById(R.id.PlaceEdt);
            CustTimeInEdt = (EditText) view.findViewById(R.id.CustTimeInEdt);
            RemarksEdt = (EditText) view.findViewById(R.id.RemarksEdt);
            IdMakeImg = (ImageButton) view.findViewById(R.id.IdMakeImg);
            IdModelImg = (ImageButton) view.findViewById(R.id.IdModelImg);
            AddImgBtn = (ImageButton) view.findViewById(R.id.AddImgBtn);
            IdVehicleTypeImg = (ImageButton) view.findViewById(R.id.IdVehicleTypeImg);
            txtDocumentList = (TextView) view.findViewById(R.id.txtDocumentList);
            IdtotalRows = (TextView) view.findViewById(R.id.TotalNoRowsTv);
            IdTotalQty = (TextView) view.findViewById(R.id.TotalQtyTv);
            IdTotalAmt = (TextView) view.findViewById(R.id.TotalAmtTv);

            AvlQtyTv = (TextView) view.findViewById(R.id.AvlQtyTv);
            UomTv = (TextView) view.findViewById(R.id.UomTv);
            mrpTv = (TextView) view.findViewById(R.id.mrpTv);
            mWheelServiceRecyclerviewid = (RecyclerView) view.findViewById(R.id.mWheelServiceRecyclerviewid);
            SparePartrecyclerview = (RecyclerView) view.findViewById(R.id.SparePartrecyclerview);
            SelectedServiceRecyclerviewid = (RecyclerView) view.findViewById(R.id.SelectedServiceRecyclerviewid);
            SelectedServiceRecyclerviewid1 = (RecyclerView) view.findViewById(R.id.SelectedServiceRecyclerviewid1);
            SubServiceRecyclerviewid = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerviewid);
            SubServiceRecyclerviewid.setNestedScrollingEnabled(false);
            // SubServiceRecyclerviewid.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

            SubServiceRecyclerview1 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview1);
            SubServiceRecyclerview2 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview2);
            SubServiceRecyclerview3 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview3);
            SubServiceRecyclerview4 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview4);
            SubServiceRecyclerview5 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview5);
            SubServiceRecyclerview6 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview6);
            SubServiceRecyclerview7 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview7);
            SubServiceRecyclerview8 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview8);
            SubServiceRecyclerview9 = (RecyclerView) view.findViewById(R.id.SubServiceRecyclerview9);
            IdModelImg.setOnClickListener(this);
            IdVehicleTypeImg.setOnClickListener(this);
            IdMakeImg.setOnClickListener(this);
            CustVehicleTypeEdt.setOnClickListener(this);
            SubServiceRecyclerview1.setNestedScrollingEnabled(false);
            SubServiceRecyclerview2.setNestedScrollingEnabled(false);
            SubServiceRecyclerview3.setNestedScrollingEnabled(false);
            SubServiceRecyclerview4.setNestedScrollingEnabled(false);
            SubServiceRecyclerview5.setNestedScrollingEnabled(false);
            SubServiceRecyclerview6.setNestedScrollingEnabled(false);
            SubServiceRecyclerview7.setNestedScrollingEnabled(false);
            SubServiceRecyclerview8.setNestedScrollingEnabled(false);
            SubServiceRecyclerview9.setNestedScrollingEnabled(false);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
            mWheelServiceRecyclerviewid.setLayoutManager(gridLayoutManager);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            SubServiceRecyclerviewid.setLayoutManager(llm);
            SelectedSubServiceLayout1 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout1);
            SelectedSubServiceLayout2 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout2);
            SelectedSubServiceLayout3 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout3);
            SelectedSubServiceLayout4 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout4);
            SelectedSubServiceLayout5 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout5);
            SelectedSubServiceLayout6 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout6);
            SelectedSubServiceLayout7 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout7);
            SelectedSubServiceLayout8 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout8);
            SelectedSubServiceLayout9 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout9);
            SelectedSubServiceLayout10 = (LinearLayout) view.findViewById(R.id.SelectedSubServiceLayout10);
            ImgBackLinerLayout = (LinearLayout) view.findViewById(R.id.ImgBackLinerLayout);
            txtCheckedService10 = (TextView) view.findViewById(R.id.txtCheckedService10);
            txtCheckedService9 = (TextView) view.findViewById(R.id.txtCheckedService9);
            txtCheckedService8 = (TextView) view.findViewById(R.id.txtCheckedService8);
            txtCheckedService7 = (TextView) view.findViewById(R.id.txtCheckedService7);
            txtCheckedService6 = (TextView) view.findViewById(R.id.txtCheckedService6);
            txtCheckedService5 = (TextView) view.findViewById(R.id.txtCheckedService5);
            txtCheckedService4 = (TextView) view.findViewById(R.id.txtCheckedService4);
            txtCheckedService3 = (TextView) view.findViewById(R.id.txtCheckedService3);
            txtCheckedService2 = (TextView) view.findViewById(R.id.txtCheckedService2);
            txtCheckedService1 = (TextView) view.findViewById(R.id.txtCheckedService1);

            IdCaptureImgView = (ImageView) view.findViewById(R.id.IdCaptureImgView);
            drawingImage = (ImageView) view.findViewById(R.id.drawingImage);
            IdSignatureBtn = (Button) view.findViewById(R.id.IdSignatureBtn);
            IdImageCaptureBtn = (Button) view.findViewById(R.id.IdImageCaptureBtn);

            back_dim_layout = (RelativeLayout) view.findViewById(R.id.bac_dim_layout);

            IdClearBtn = (Button) view.findViewById(R.id.IdClearBtn);
            IdSaveBtn = (Button) view.findViewById(R.id.IdSaveBtn);
            IdSaveBtn.setOnClickListener(this);
            IdClearBtn.setOnClickListener(this);
            IdImageCaptureBtn.setOnClickListener(this);
            IdSignatureBtn.setOnClickListener(this);
            AddImgBtn.setOnClickListener(this);

            //  CustTimeInEdt.setOnClickListener(this);
            SparePartEdt.setOnClickListener(this);
            // CustDateEdt.setText(ProjectMethods.GetCurrentDate());
            CustDateEdt.setText(ProjectMethods.getBusinessDate());
            CustTimeInEdt.setText(ProjectMethods.GetCurrentTime());
            /*wheelServiceAdapter = new WheelServiceAdapter(getActivity());
            selectedServiceAdpater = new SelectedServiceAdpater(getActivity(), R.layout.selectedservice_row_item, serviceMasterArrayList);
            mWheelServiceRecyclerviewid.setAdapter(wheelServiceAdapter);
            SelectedServiceRecyclerviewid.setAdapter(selectedServiceAdpater);*/
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
            swipeRefreshLayout.setOnRefreshListener(this);

            // Checking camera availability
            if (!isDeviceSupportCamera()) {
                Toast.makeText(getActivity(),
                        "Sorry! Your device doesn't support camera",
                        Toast.LENGTH_LONG).show();
                // will close the app if the device does't have camera
                getActivity().finish();
            }
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
                    if (vehicleNo.length() >= 8) {
                        GetDetailsBasedOnVehicleNo(s);
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
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0) {
                        int ordermeterValue = Integer.parseInt(s.toString());
                        int finalAmtStr = ordermeterValue + 5000;
                        MileageEdt.setText(String.valueOf(finalAmtStr));
                    } else {
                        MileageEdt.setText("");
                    }

                }
            });

            CustVehicleModelEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustVehicleModelEdt.setError(null);
                    Intent modelinten = new Intent(getActivity(), ModelListActivity.class);
                    modelinten.putExtra("makeName", "");
                    startActivityForResult(modelinten, 52);
                }
            });

            //CustDateEdt.setText(BusinessDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void GetServicesFromServer() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", "60");
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "ServiceMaster";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.ServiceMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.CAMERA)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission((Activity) context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission((Activity) context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
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
                }
            } else {
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handelCustomerDetailsDashboard(CharSequence mobileNo) {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    // swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", 20);
                    jsonObject.accumulate("MobileNo", mobileNo);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetCustomerDataOpenData";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetUserData, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.menu_header, menu);
        try {
            getActivity().getMenuInflater().inflate(R.menu.menu_header, menu);
            menu.findItem(R.id.datetv).setTitle("");
            menu.findItem(R.id.datebtn).setIcon(0);
            menu.findItem(R.id.datebtn).setEnabled(false);
            menu.findItem(R.id.datetv).setEnabled(false);
//            menu.findItem(R.id.alltv).setTitle("");
            menu.findItem(R.id.datetv).setEnabled(false);
            super.onCreateOptionsMenu(menu, inflater);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                PrepareServiceStringFormat();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                case R.id.AddImgBtn:
                    AddToGridView();
                    break;
                case R.id.IdModelImg:
                    Intent modelinten = new Intent(getActivity(), ModelListActivity.class);
                    modelinten.putExtra("makeName", "");
                    startActivityForResult(modelinten, 52);
                    CustVehicleModelEdt.clearFocus();
                    CustVehicleModelEdt.setEnabled(false);
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
                    try {
                        PrepareServiceStringFormat();
                       /* finalServiceDetailList = "";
                        finalselectedServiceList = "";
                        for (int i = 0; i < selectedMainServiceAdapter.serviceMasterArrayList.size(); i++) {
                            if (selectedMainServiceAdapter.serviceMasterArrayList.get(i).getSelected()) {
                                finalselectedServiceList += selectedMainServiceAdapter.serviceMasterArrayList.get(i).getServiceId().toString() + ",";
                                String serviceListName = selectedMainServiceAdapter.serviceMasterArrayList.get(i).getServiceName().toString();
                            }
                        }

                        // finalServiceDetailList = (new StringBuilder()).append(Crosstableresults1).append(Crosstableresults2).append(Crosstableresults3).append(Crosstableresults4).append(Crosstableresults5).append(Crosstableresults6).append(Crosstableresults7).append(Crosstableresults8).append(Crosstableresults9).append(Crosstableresults10).append(Crosstableresults11).append(Crosstableresults12).append(Crosstableresults13).append(Crosstableresults14).append(Crosstableresults15).toString();

                        if (Validation()) {


                            CustomDailog("Job Card(MRF)", "Do You Want to Save JobCard Details?", 38, "save");


                        }
*/
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
               /* case R.id.IdDocumentTypeBtn:
                    try {
                        String listId = "", listStrings = "";
                        if (txtDocumentList.getText().toString().trim() != "") {
                            // listId = txtDocumentList.getTag().toString();
                            listId = DocumentIdList;
                            listStrings = DocumentNumList;
                        } else {
                            listId = "";
                            listStrings = "";
                        }
                        GetDocumentDetails(listId, listStrings);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;*/
              /*  case R.id.CustTimeOutEdt:
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
                                        CustTimeOutEdt.setText(hour + ":" + minute + " " + am_pm);
                                        //  CustTimeOutEdt.setText(hourOfDay + ":" + minute + " " + "AM");
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;*/
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
                case R.id.IdImageCaptureBtn:
                    try {
                        //  FileUriExposedException
                        Intent photocapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri photoURI = null;
                        captureImagePath = new File(getActivity().getExternalCacheDir(), "temp.jpg");
                        if (Build.VERSION.SDK_INT >= 24) {

                            photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", captureImagePath);
                        } else {
                            photoURI = Uri.fromFile(captureImagePath);
                        }
                        photocapture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(photocapture, TAKE_PHOTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //...............................
                    /*   try {
                     *//* Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
                        }
*//*
                       // ImageName = rootDir, System.currentTimeMillis() + ".jpg";
                       *//* ImageName = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                        String state = Environment.getExternalStorageState();
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            ImageName = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                            File root = android.os.Environment.getExternalStorageDirectory();
                            File f = new File(root.getAbsolutePath(), ImageName);
                            f.mkdirs();
                            Uri  u = Uri.fromFile(f);
                            Intent intent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
                            intent2.putExtra("crop", "true");
                            intent2.putExtra("aspectX", 0);
                            intent2.putExtra("aspectY", 0);
                            intent2.putExtra("outputX", 200);
                            intent2.putExtra("outputY", 150);
                            startActivityForResult(intent2, CAMERA_IMAGE);
                        }*//*

                       // File f = new File(Environment.getExternalStorageDirectory(), ImageName);


                      *//*  Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                        // start the image capture Intent
                        startActivityForResult(intent2, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
*//*
                        ImageName = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                        File f = new File(Environment.getExternalStorageDirectory(), ImageName);
                        Uri u = Uri.fromFile(f);
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        CropImage.activity(u)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(getActivity());

                        startActivityForResult(intent2, 003);
                    } catch (ActivityNotFoundException anfe) {
                        //display an error message
                        String errorMessage = "oops - your device doesn't support capturing images!";
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }*/

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

    private void AddToGridView() {
        try {
            float mrp = 0.0f, Qty = 0.0f;
            if (validate()) {
                QtyEdt.clearFocus();
                SparePartDetails sparePartDetails = new SparePartDetails();
                sparePartDetails.setmSparePartName(SparePartEdt.getText().toString());
                sparePartDetails.setmSparePartID(SparePartEdt.getTag().toString());
                sparePartDetails.setmMRP(mrpTv.getText().toString());
                sparePartDetails.setmAVLQTY(AvlQtyTv.getText().toString());
                sparePartDetails.setmQTY(QtyEdt.getText().toString());
                sparePartDetails.setmUOMName(UomTv.getText().toString());
                sparePartDetails.setmUOMID(UomTv.getTag().toString());
                mrp = Float.valueOf(mrpTv.getText().toString());
                Qty = Float.valueOf(QtyEdt.getText().toString());
                float finalValue = mrp * Qty;
                sparePartDetails.setmQryMrp(String.valueOf(finalValue));
                // sparePartDetailsArrayList1.add(sparePartDetails);
                AddtemToGrid(sparePartDetails);
                CalculationPart();

                //clear edit values
                SparePartEdt.setText("");
                SparePartEdt.setTag("");
                QtyEdt.setText("");
                // mrpEdt.setText("");

                try {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(AddImgBtn.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CalculationPart() {
        TotalGrossAmt = "";
        TotalQtyValue = "";
        float TotalAmtValue = 0.0f;
        int TotalRows = 0;

        totalAmount = 0.0f;
        totalQty = 0.0f;
        int finalrows = 0;
        for (int i = 0; i < sparePartDetailsArrayList.size(); i++) {
            finalrows++;
            TotalGrossAmt = sparePartDetailsArrayList.get(i).getmMRP();
            TotalQtyValue = sparePartDetailsArrayList.get(i).getmQTY();
            TotalAmtValue = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalGrossAmt);
            if (TotalQtyValue.length() != 0) {

                totalQty += Float.valueOf(TotalQtyValue);
            }
            if (TotalGrossAmt.length() != 0) {
                totalAmount += Float.valueOf(TotalAmtValue);
            }
        }

        IdtotalRows.setText(String.valueOf(finalrows));
        IdTotalQty.setText(String.format("%.0f", totalQty));
        IdTotalAmt.setText(String.format("%.2f", totalAmount));
    }

    private void AddtemToGrid(SparePartDetails SP) {
        try {
            float mrp = 0.0f, Qty = 0.0f;
            int selectedPosition = getItemExistency(SP.getmSparePartID());
            boolean IsItemAdded = false;
            if (selectedPosition == -1) {
                sparePartDetailsArrayList.add(SP);
                SparePartrecyclerview.smoothScrollToPosition(sparePartDetailsArrayList.size() - 1);
                IsItemAdded = true;
            } else {
                SparePartDetails ET1 = sparePartDetailsArrayList.get(selectedPosition);
                float Quantity = Float.parseFloat(ET1.getmQTY());
                Quantity += Float.parseFloat(SP.getmQTY());
                ET1.setmQTY(String.valueOf(Quantity));
                IsItemAdded = true;
                SparePartrecyclerview.smoothScrollToPosition(selectedPosition);
                mrp = Float.valueOf(ET1.getmMRP());
                Qty = Float.valueOf(ET1.getmQTY());
                float finalValue = mrp * Qty;
                ET1.setmQryMrp(String.valueOf(finalValue));
            }
            for(int i=0;i<sparePartDetailsArrayList.size();i++) {
                Toast.makeText(context," "+sparePartDetailsArrayList.get(i).getmQTY()+sparePartDetailsArrayList.get(i).getmAVLQTY(),Toast.LENGTH_LONG).show();
            }

            if (IsItemAdded) {


                sparePartIssueAdapter.notifyDataSetChanged();
            }
            // checkGridValue();
            try {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(AddImgBtn.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getItemExistency(String SpareId) {
        try {
            for (int i = 0; i < sparePartDetailsArrayList.size(); i++) {
                if (sparePartDetailsArrayList.get(i).getmSparePartID().equalsIgnoreCase(SpareId)) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean validate() {
        boolean valid = true;
        String SparePart = SparePartEdt.getText().toString().trim();
        String qtyValue = QtyEdt.getText().toString().trim();
        if (SparePart.length() == 0) {
            Toast.makeText(getActivity(), "Please Select SparePart", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (qtyValue.length() == 0) {
            QtyEdt.setError("Enter Qty");
            QtyEdt.requestFocus();
            valid = false;
        }
        return valid;
    }

    private void PrepareServiceStringFormat() {
        try {
            finalServiceDetailList = "";
            finalselectedServiceList = "";
            finalselectedIds = "";
            for (int i = 0; i < selectedMainServiceAdapter.serviceMasterArrayList.size(); i++) {
                if (selectedMainServiceAdapter.serviceMasterArrayList.get(i).getSelected()) {
                    String Serviceid = selectedMainServiceAdapter.serviceMasterArrayList.get(i).getServiceId().toString();
                    String ServiceName = selectedMainServiceAdapter.serviceMasterArrayList.get(i).getServiceName().toString();
                    String Paidorfree = selectedMainServiceAdapter.serviceMasterArrayList.get(i).getWheeltyreDetails().toString();
                    finalselectedServiceList += Serviceid + "!" + ServiceName + "!" + Paidorfree + "~";
                    finalselectedIds += Serviceid + ",";

                }
            }

            //   finalServiceDetailList = (new StringBuilder()).append(Crosstableresults1).append(Crosstableresults2).append(Crosstableresults3).append(Crosstableresults4).append(Crosstableresults5).append(Crosstableresults6).append(Crosstableresults7).append(Crosstableresults8).append(Crosstableresults9).append(Crosstableresults10).append(Crosstableresults11).append(Crosstableresults12).append(Crosstableresults13).append(Crosstableresults14).append(Crosstableresults15).toString();
            if (Validation()) {

                CustomDailog("Job Card", "Do You Want to Save JobCard Details?", 38, "save");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyImages");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
               /* Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");*/
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrivesharedPreferences();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getActivity().getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.remove("PRODUCT_PHOTO");
        editor.commit();
    }

    private void GetDocumentDetails(String listId, String listStrings) {
        try {
            Intent salesman = new Intent(getActivity(), DocumentListActivity.class);
            salesman.putExtra("mylist", documentTypesArrayList);
            salesman.putExtra("ListId", listId);
            salesman.putExtra("listStrings", listStrings);
            salesman.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(salesman, 101);
           /* try {
                swipeRefreshLayout.setRefreshing(true);
                if (AppUtil.isNetworkAvailable(getActivity())) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("TypeName", "DOCUMENTS");
                    requestName = "GeneralMasterData";
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
                } else {

                    //   dialogManager.showAlertDialog(getActivity(), "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
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
            Intent intent = new Intent(getActivity(), CustomDialogClass.class);
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
            //  JobCardNoEdt.setText("");
            CustVehiclemakemodelEdt.setText("");
            CustVehicleModelEdt.setText("");
            CustOdometerReadingEdt.setText("");
            AvgkmsperdayEdt.setText("");
            RegNoEdt.setText("");
            MileageEdt.setText("");
            PlaceEdt.setText("");
           // CustTimeInEdt.setText(""); we shouldnot clear current time
            RemarksEdt.setText("");
            txtDocumentList.setText("");
            subServiceArrayList.clear();
            ClearSubServices();
            serviceMasterArrayList.clear();
            sparePartDetailsArrayList.clear();
            sparePartIssueAdapter.notifyDataSetChanged();
            IdtotalRows.setText("");
            IdTotalQty.setText("");
            IdTotalAmt.setText("");
            GetServicesFromServer();
            onDestroy();
            drawingImage.setVisibility(View.GONE);
            drawingImage.setImageBitmap(null);
            IdCaptureImgView.setVisibility(View.GONE);
            IdCaptureImgView.setImageResource(android.R.color.transparent);
            ImgBackLinerLayout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearData() {

    }

    private void ClearSubServices() {
        SelectedSubServiceLayout1.setVisibility(View.GONE);
        SelectedSubServiceLayout2.setVisibility(View.GONE);
        SelectedSubServiceLayout3.setVisibility(View.GONE);
        SelectedSubServiceLayout4.setVisibility(View.GONE);
        SelectedSubServiceLayout5.setVisibility(View.GONE);
        SelectedSubServiceLayout6.setVisibility(View.GONE);
        SelectedSubServiceLayout7.setVisibility(View.GONE);
        SelectedSubServiceLayout8.setVisibility(View.GONE);
        SelectedSubServiceLayout9.setVisibility(View.GONE);
        SelectedSubServiceLayout10.setVisibility(View.GONE);
        txtCheckedService10.setVisibility(View.GONE);
        txtCheckedService9.setVisibility(View.GONE);
        txtCheckedService8.setVisibility(View.GONE);
        txtCheckedService7.setVisibility(View.GONE);
        txtCheckedService6.setVisibility(View.GONE);
        txtCheckedService5.setVisibility(View.GONE);
        txtCheckedService4.setVisibility(View.GONE);
        txtCheckedService3.setVisibility(View.GONE);
        txtCheckedService2.setVisibility(View.GONE);
        txtCheckedService1.setVisibility(View.GONE);

    }
    /*private boolean ValidationCustVehicleModel(){
        boolean result=true;
        boolean resultValue=true;
        CustVehicleModel = CustVehicleModelEdt.getText().toString().trim();
        if (CustVehicleModel.isEmpty()) {
            Toast toast = Toast.makeText(context, "+++++", Toast.LENGTH_SHORT);
            CustVehicleModelEdt.requestFocus();
            CustVehicleModelEdt.setError("Please Enter VehicleModel");
            CustVehicleModelEdt.setInputType(InputType.TYPE_NULL);
            result = false;
            resultValue=false;
        }
        if (!resultValue) {
            Toast toast = Toast.makeText(context, "  Please Enter Mandatory Fields  ", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            // isFirstRun = false;
        }
        return result;
    }*/
    private boolean Validation() {
        boolean result = true;
        boolean resultValue = true;
        vehicleNo = VehicleNoEdt.getText().toString().trim();
        VehicleNoEdt.setText(VehicleNoEdt.getText().toString().trim().replaceAll("\\s{2,}", " "));
        CustName = CustNameEdt.getText().toString().trim();
        CustNameEdt.setText(CustNameEdt.getText().toString().trim().replaceAll("\\s{2,}", " "));
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
        PlaceEdt.setText(PlaceEdt.getText().toString().trim().replaceAll("\\s{2,}", " "));
        CustTimeIn = CustTimeInEdt.getText().toString().trim();
        Remarks = RemarksEdt.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        if (vehicleNo.isEmpty()) {
            VehicleNoEdt.setError("Please Enter VehicleNo");
            VehicleNoEdt.requestFocus();
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
        } else if (CustVehicleModel.length() == 0) {
            if (CustMobileNoEdt.isFocused()) {
                Rect outRect = new Rect();
                CustMobileNoEdt.getGlobalVisibleRect(outRect);
                CustMobileNoEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) CustMobileNoEdt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(CustMobileNoEdt.getWindowToken(), 0);
            }
            CustVehicleModelEdt.setError("Please Select VehicleModel");
            Toast.makeText(getActivity(), "Please Select VehicleModel", Toast.LENGTH_SHORT).show();
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
            Toast toast = Toast.makeText(context, "  Please Enter Mandatory Fields  ", Toast.LENGTH_SHORT);
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

       /* else if (CustVehiclemakemodel.length() == 0) {
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

        }*/
       /* else if (JobCardNo.length() == 0 || JobCardNo.toString().equalsIgnoreCase("")) {
            JobCardNoEdt.requestFocus();
            JobCardNoEdt.setError("Please Enter JobCardNo");
            result = false;

        } */

       /* else if (RegNo.toString().length() == 0 || RegNo.toString().equalsIgnoreCase("")) {

            RegNoEdt.requestFocus();
            RegNoEdt.setError("Please Enter RegNo");
            result = false;
        }*/
     /*   else if (Avgkmsperday.toString().length() == 0 || Avgkmsperday.toString().equalsIgnoreCase("")) {

            AvgkmsperdayEdt.requestFocus();
            AvgkmsperdayEdt.setError("Please Select AvgKmsPerDay");
            result = false;
        }*/
       /* else if (Remarks.toString().length() == 0 || Remarks.toString().equalsIgnoreCase("")) {

            RemarksEdt.requestFocus();
            RemarksEdt.setError("Please Select Remarks");
            result = false;
        }*/
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
              /*  picUri = data.getData();
                performCrop();*/
                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                IdCaptureImgView.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                IdCaptureImgView.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            picUri = data.getData();
            performCrop();
        } else if (requestCode == 102) {
            if (data != null && resultCode == RESULT_OK) {
                SparPartInformation(data);
                QtyEdt.requestFocus();//request is focusing once sparepart selection done
            }
        } else if (requestCode == 133 && data != null && resultCode == RESULT_OK) {
            EditData();
        } else if (requestCode == 134 && data != null && resultCode == RESULT_OK) {
            DeleteDetails();
        } else if (requestCode == TAKE_PHOTO) {
            try {

                startImageCrop(Uri.fromFile(captureImagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                IdCaptureImgView.setVisibility(View.VISIBLE);
                Uri resultUri = result.getUri();
                imageURl = resultUri.getPath();
                captureImagePath = new File(imageURl);
                imageURl = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";
                try {
                    UploadimageServer server = new UploadimageServer(captureImagePath, imageURl, resultUri, getActivity());
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    IdCaptureImgView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        } else if (resultCode == RESULT_OK && requestCode == 003) {
            imageURI = ImageName;
            try {
              /*  UploadTasks task = new UploadTasks(new File(Environment.getExternalStorageDirectory(), imageURI), imageURI, null);
                task.execute();*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            ImgBackLinerLayout.setVisibility(View.VISIBLE);
            IdCaptureImgView.setVisibility(View.VISIBLE);
            IdCaptureImgView.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    ImgBackLinerLayout.setVisibility(View.VISIBLE);
                    IdCaptureImgView.setVisibility(View.VISIBLE);

                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                    IdCaptureImgView.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//ImageView to set the picture taken from camera.

                picTaken = true; //to ensure picture is taken
              /*  BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                IdCaptureImgView.setImageBitmap(bitmap);
               // imageURI = ImageName;
                imageURI = fileUri.getPath();*/
                /*UploadTask task = new UploadTask(new File(Environment.getExternalStorageDirectory(), imageURI), imageURI, null);
                task.execute();
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Uploading......");

                IdCaptureImgView.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                pd.show();*/
            }
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
        } else if (requestCode == 11 && data != null && resultCode == RESULT_OK) {

        } else if (requestCode == 51) {
            if (data != null && resultCode == RESULT_OK) {
                MakeInformation(data);
            }
        } else if (requestCode == 55) {
            if (data != null && resultCode == RESULT_OK) {
                VehcileInformation(data);
            }
        } else if (requestCode == 52) {
            if (data != null && resultCode == RESULT_OK) {
                ModelInformation(data);
            }
        } else if (requestCode == 58) {
            if (data != null && resultCode == RESULT_OK) {
                ServiceTypeInformation();
            }
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
                StringBuilder stringBuilder3 = new StringBuilder();
                StringBuilder stringBuilder4 = new StringBuilder();
                for (int i = 0; i < DocumentListAdapter.salesmenlist.size(); i++) {
                    if (DocumentListAdapter.salesmenlist.get(i).getSelected()) {
                        String selectedRowLabel = DocumentListAdapter.salesmenlist.get(i).getName();
                        Log.d("sele", selectedRowLabel);
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

                        stringBuilder3.append(selectedRowId + ",");
                        if (edittextvalue == null) {
                            stringBuilder4.append("");
                        } else {
                            stringBuilder4.append(edittextvalue + ",");
                        }

                    }
                }
                // txtDocumentList.setText(data.getStringExtra("Documents"));
                txtDocumentList.setText(stringBuilder.toString());
                txtDocumentList.setTag(stringBuilder1.toString());
                DocumentsName = stringBuilder.toString();
                DocumentsIds = stringBuilder1.toString();
                DocumentIdList = stringBuilder3.toString();
                DocumentNumList = stringBuilder4.toString();
                /*if (intent != null) {
                    myList =getActivity().getIntent().getParcelableArrayListExtra("MyListData");

                }*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void DeleteDetails() {
        try {
            sparePartDetailsArrayList.remove(position);
            CalculationPart();
            sparePartIssueAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void EditData() {
        try {
            SparePartDetails SI = sparePartDetailsArrayList.get(position);
            SparePartEdt.setText(SI.getmSparePartName());
            SparePartEdt.setTag(SI.getmSparePartID());
            QtyEdt.setText(SI.getmQTY());
            mrpTv.setText(SI.getmMRP());
            AvlQtyTv.setText(SI.getmAVLQTY());
            UomTv.setText(SI.getmUOMName());
            UomTv.setTag(SI.getmUOMID());
            DeleteDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SparPartInformation(Intent data) {
        try {
            AvlQty = "";
            UOMId = "";
            UOMName = "";
            String SprptId = data.getStringExtra("SprptId");
            String SprptName = data.getStringExtra("SprptName");
            String ShortBalQty = data.getStringExtra("ShortBalQty");
            String ShortUomId = data.getStringExtra("ShortUomId");
            String ShortUomName = data.getStringExtra("ShortUomName");
            String SparePartMRP = data.getStringExtra("SparePartMRP");

            SparePartEdt.setText(SprptName);
            SparePartEdt.setTag(SprptId);
            mrpTv.setText(SparePartMRP);
           /* AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;*/
            //mrpTv
            // QtyEdt.setText(AvlQty);
            AvlQtyTv.setText(ShortBalQty);
            UomTv.setText(ShortUomName);
            UomTv.setTag(ShortUomId);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void ServiceTypeInformation() {
        try {
            dialog.dismiss();
            String paidorfree = "";
            // selectedSericeListTv=(TextView)view.findViewById(R.id.selectedSericeListTv);
            Log.d("servicetype", "servicetype");
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < ServiceDetailsAdapter.tyredetailsArraylist.size(); i++) {
                if (ServiceDetailsAdapter.tyredetailsArraylist.get(i).getSelected()) {
                    String selectedRowLabel = ServiceDetailsAdapter.tyredetailsArraylist.get(i).getServiceheader();
                    Log.d("servicetype", selectedRowLabel);
                    if (ServiceDetailsAdapter.tyredetailsArraylist.get(i).getPaidSelected() == true) {
                        paidorfree = "(" + "Free" + ")";
                    } else {
                        paidorfree = "";
                    }
                    stringBuilder.append(selectedRowLabel + paidorfree + ",");
                    stringBuilder1.append(selectedRowLabel + paidorfree + ",");


                }
            }
            int pos = Integer.valueOf(checkedposition);
            String ServiceStringValues = stringBuilder.toString();
            ServiceStringValues = ServiceStringValues.replaceAll(",$", "");
            if (selectedMainServiceAdapter.serviceMasterArrayList.get(pos).getSelected()) {
                selectedMainServiceAdapter.serviceMasterArrayList.get(pos).setWheeltyreDetails(selectUser + "," + ServiceStringValues);
                selectedMainServiceAdapter.serviceMasterArrayList.get(pos).setActiveStatus("1");
                selectedMainServiceAdapter.notifyDataSetChanged();
            }

            // selectedSericeListTv.setText(selectUser+" "+stringBuilder.toString());
            // txtDocumentList.setTag(stringBuilder1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void VehcileInformation(Intent data) {
        try {
            String VehicleName = data.getStringExtra("VehicleName");
            String VehicleId = data.getStringExtra("VehicleId");
            CustVehicleTypeEdt.setText(VehicleName);
            CustVehicleTypeEdt.setTag(VehicleId);


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void ModelInformation(Intent data) {
        try {
            String Model = data.getStringExtra("Model");
            ModelId = data.getStringExtra("ModelId");
            String Make = data.getStringExtra("Make");
            String VehicleType = data.getStringExtra("VehicleType");
            CustVehicleModelEdt.setText(Model);
            CustVehicleModelEdt.setTag(ModelId);
            Log.d("modelIdd", ModelId);
            CustVehiclemakemodelEdt.setText(Make);
            CustVehicleTypeEdt.setText(VehicleType);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void MakeInformation(Intent data) {
        try {
            String MakeName = data.getStringExtra("MakeName");
            if (CustVehiclemakemodelEdt.getText().toString().equalsIgnoreCase(MakeName)) {
                CustVehiclemakemodelEdt.setText(MakeName);
                CustVehicleModelEdt.setEnabled(false);
                CustVehicleModelEdt.clearFocus();
            } else {
                CustVehiclemakemodelEdt.setText(MakeName);
                CustVehicleModelEdt.setText("");
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void startImageCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(getContext(), this);
    }

    private void SaveData() {
        try {

            PrepareItemDetailsList();
            String VehicleType = CustVehicleTypeEdt.getText().toString();
            String list = "";
            list = DocumentsIds;
            String ServiceIdValues = finalselectedServiceList;
            String DocumentIdValues = list;
            ServiceIdValues = ServiceIdValues.replaceAll(",$", "");
            DocumentIdValues = DocumentIdValues.replaceAll(",$", "");
            finalselectedIds = finalselectedIds.replaceAll(",$", "");
            Gson userGson = new GsonBuilder().create();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(JSONVariables.JCDate, CustFromDate);
                    jsonObject.accumulate(JSONVariables.JCId, "");
                    jsonObject.accumulate(JSONVariables.JCNo, "");
                    jsonObject.accumulate(JSONVariables.JC_InTime, CustTimeIn);
                    jsonObject.accumulate(JSONVariables.JC_OutTime, "");
                    jsonObject.accumulate(JSONVariables.CustomerId, mCustomerId);
                    jsonObject.accumulate(JSONVariables.Vehicle_Id, mVehicleNo);
                    jsonObject.accumulate(JSONVariables.OMReading, CustOdometerReading);
                    jsonObject.accumulate(JSONVariables.AvgKMS_RPD, Avgkmsperday);
                    jsonObject.accumulate(JSONVariables.ServiceId, finalselectedIds);
                    jsonObject.accumulate(JSONVariables.SUserId, ProjectMethods.getUserId());
                    jsonObject.accumulate(JSONVariables.CounterId, ProjectMethods.getCounterId());
                    jsonObject.accumulate(JSONVariables.ServiceDetails, ServiceIdValues);
                    jsonObject.accumulate(JSONVariables.JCImage, imageURl);
                    jsonObject.accumulate(JSONVariables.Documents, "");
                    jsonObject.accumulate(JSONVariables.CustName, CustName);
                    jsonObject.accumulate(JSONVariables.CustEmailId, CustEmailId);
                    jsonObject.accumulate(JSONVariables.CustMobileNo, CustMobileNo);
                    jsonObject.accumulate(JSONVariables.JCRemarks, Remarks);
                    jsonObject.accumulate(JSONVariables.JCVehicleNo, vehicleNo);
                    jsonObject.accumulate(JSONVariables.JCMake, CustVehiclemakemodel);
                    jsonObject.accumulate(JSONVariables.JCModel, CustVehicleModel);
                    jsonObject.accumulate(JSONVariables.JCModelId, ModelId);
                    jsonObject.accumulate(JSONVariables.JCRegno, RegNo);
                    jsonObject.accumulate(JSONVariables.ScreenName, "JobCard");
                    jsonObject.accumulate(JSONVariables.VehicleType, VehicleType);
                    jsonObject.accumulate(JSONVariables.Mileage, Mileage);
                    jsonObject.accumulate(JSONVariables.Place, Place);
                    jsonObject.accumulate(JSONVariables.ItemDetails, finalSparePartDetailList);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "SaveJobCardDetails_New";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SaveJobCardDetails_New, jsonObject, Request.Priority.HIGH);
                    Log.d("saveddetail", "" + jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PrepareItemDetailsList() {
        try {
            finalSparePartDetailList = "";
            for (int j = 0; j < sparePartDetailsArrayList.size(); j++) {
                if (sparePartDetailsArrayList.get(j).getRowNo() > 0) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getRowNo() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmSparePartName() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSparePartName() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmUOMName() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmUOMName() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmMRP() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmMRP() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmAVLQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmAVLQTY() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmQTY() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmQryMrp() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmUOMID() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmUOMID() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmSparePartID() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSparePartID();
                }
                if (finalSparePartDetailList != "") {
                    finalSparePartDetailList = finalSparePartDetailList + "~";
                } else {
                    finalSparePartDetailList = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveDataToServerDb() {
        try {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
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
                    handleTyreTypes(jsonArray);
                    //  handeDocumentDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetSubService")) {
                try {
                    handleGetSubService(jsonArray);
                    //  handeDocumentDetails(jsonArray);
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
                    Log.d("caaliingaaaa", "calling");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetServiceAgstData")) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    handeGetSubServiceMasterDetails(jsonArray);
                    Log.d("caaliing", "calling");
                    // handeGetServiceAgstData(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("Getlist")) {
                try {
                    handeGetlistDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("SaveJobCardDetails_New")) {
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

    private void handleGetBusinessDate(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                BusinessDate = jsonArray.getJSONObject(0).getString("Date");
                CustDateEdt.setText(BusinessDate);
            }
            GetBlockDetailsFromgeneralMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetBlockDetailsFromgeneralMaster() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    jsonObject.accumulate("TypeName", "BLOCK");
                    requestName = "GetBlockDetails";
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

    private void handleGetSubService(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                getSubServiceArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            GetSubService userList = new GetSubService();
                            userList.setSSID(object.getString("SSID"));
                            userList.setSSNAME(object.getString("SSNAME"));
                            getSubServiceArrayList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try {


                String[] us = new String[getSubServiceArrayList.size()];
                for (int i = 0; i < getSubServiceArrayList.size(); i++) {
                    us[i] = getSubServiceArrayList.get(i).getSSNAME();

                }


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                TyreTypesSpn.setAdapter(spinnerArrayAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handeGetServiceAgstData(final JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                subServiceListArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            SubServiceList userList = new SubServiceList();
                            userList.setServiceheader(object.getString("Data"));

                            subServiceListArrayList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_show_subservicedetails);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
            ImageButton CloseImgBtn = (ImageButton) dialog.findViewById(R.id.CloseImgBtn);
            final Spinner TyreTypesSpn = (Spinner) dialog.findViewById(R.id.IdVehicleTypeSpn);
            ImageButton SaveImgBtn = (ImageButton) dialog.findViewById(R.id.SaveImgBtn);
            ServiceDetailsrecyclerview = (RecyclerView) dialog.findViewById(R.id.ServiceDetailsrecyclerview);
            Button SaveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
          /*  TyreTypesSpinnerDetails();
            GetSubServiceSpinnerDetails();*/
            serviceDetailsAdapter = new ServiceDetailsAdapter(getActivity(), R.layout.mainsubservice_row_item, subServiceListArrayList);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
            ServiceDetailsrecyclerview.setLayoutManager(linearLayoutManager1);
            ServiceDetailsrecyclerview.setHasFixedSize(true);
            ServiceDetailsrecyclerview.setItemAnimator(new DefaultItemAnimator());
            ServiceDetailsrecyclerview.setAdapter(serviceDetailsAdapter);
            serviceDetailsAdapter.notifyDataSetChanged();
            selectUser = TyreTypesSpn.getSelectedItem().toString();
            CloseImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }
            });
            SaveImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CustomDailog("Service Details", "Do You Want to Save the Data?", 58, "SAVE");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });

             /*   TyreTypesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectUser = TyreTypesSpn.getSelectedItem().toString();
                   *//* if (typesArrayList.size() != 0)
                        taskToId = typesArrayList.get(position).getName();
                    Log.v("test", taskToId);*//*


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
*/


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GetSubServiceSpinnerDetails(String serviceId) {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("ServiceId", serviceId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetSubService";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetSubService, jsonObject, Request.Priority.HIGH);
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

    private void handleTyreTypes(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                typesArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            VehicleTypes userList = new VehicleTypes();
                            userList.setId(object.getString("Id"));
                            userList.setName(object.getString("Name"));
                            typesArrayList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {

                    String[] us = new String[typesArrayList.size()];
                    for (int i = 0; i < typesArrayList.size(); i++) {
                        us[i] = typesArrayList.get(i).getName();

                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    TyreTypesSpn.setAdapter(spinnerArrayAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeSaveJobCardDetails(JSONArray jsonArray) {
        try {

            swipeRefreshLayout.setRefreshing(false);
            if (jsonArray.length() > 0) {
                String result = jsonArray.getJSONObject(0).getString("Result");
                String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");
                if (result != null) {
                    Toast.makeText(getActivity(), result + "" + JobCardNumber, Toast.LENGTH_SHORT).show();
                    ClearData();
                    isFirstRun = true;
                }
                JobCardNoEdt.setText(JobCardNumber);

                SharedPreferences.Editor edit = jobcardPref.edit();
                edit.putString("JobCardNumber", JobCardNumber);
                edit.commit();
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
            if (jsonArray.length() > 0) {
                subServiceListArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Details Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d("going", "going");
                            SubServiceList userList = new SubServiceList();
                            userList.setServiceheader(object.getString("Data"));

                            subServiceListArrayList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            boolean finalResult = true;
            if (finalResult) {
                Log.d("gettinggg", "gettinggg");
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_show_subservicedetails);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                ImageButton CloseImgBtn = (ImageButton) dialog.findViewById(R.id.CloseImgBtn);
                TyreTypesSpn = (Spinner) dialog.findViewById(R.id.IdVehicleTypeSpn);
                ImageButton SaveImgBtn = (ImageButton) dialog.findViewById(R.id.SaveImgBtn);
                ServiceDetailsrecyclerview = (RecyclerView) dialog.findViewById(R.id.ServiceDetailsrecyclerview);
                Button SaveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
                TyreTypesSpinnerDetails();
                GetSubServiceSpinnerDetails(ServiceId);
                serviceDetailsAdapter = new ServiceDetailsAdapter(getActivity(), R.layout.subservice_row_item, subServiceListArrayList);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                ServiceDetailsrecyclerview.setLayoutManager(linearLayoutManager1);
                ServiceDetailsrecyclerview.setHasFixedSize(true);
                ServiceDetailsrecyclerview.setItemAnimator(new DefaultItemAnimator());
                ServiceDetailsrecyclerview.setAdapter(serviceDetailsAdapter);
                serviceDetailsAdapter.notifyDataSetChanged();
                //  selectUser = TyreTypesSpn.getSelectedItem().toString();
                CloseServiceId = "";
                CloseImgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        selectedMainServiceAdapter.serviceMasterArrayList.get(ServiceIdSelectedPosition).setSelected(false);
                        selectedMainServiceAdapter.notifyDataSetChanged();
                        // CloseServiceId=ServiceId;
                    }
                });

                SaveImgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            try {
                                String selectedRowLabel = "";
                                for (int i = 0; i < ServiceDetailsAdapter.tyredetailsArraylist.size(); i++) {
                                    if (ServiceDetailsAdapter.tyredetailsArraylist.get(i).getSelected()) {
                                        selectedRowLabel += ServiceDetailsAdapter.tyredetailsArraylist.get(i).getServiceheader();


                                    }
                                }
                                if (selectedRowLabel != "") {
                                    CustomDailog("Service Details", "Do You Want to Save the Data?", 58, "SAVE");
                                } else {
                                    Toast.makeText(context, "Please Select Atleast One Service Details", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }


                            // CustomDailog("Service Details", "Do You Want to Save the Data?", 58, "SAVE");
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                });

                SaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String selectedRowLabel = "";
                            Log.d("selecteLa", selectedRowLabel);
                            for (int i = 0; i < ServiceDetailsAdapter.tyredetailsArraylist.size(); i++) {
                                if (ServiceDetailsAdapter.tyredetailsArraylist.get(i).getSelected()) {
                                    selectedRowLabel += ServiceDetailsAdapter.tyredetailsArraylist.get(i).getServiceheader();

                                }
                            }

                            if (subServiceListArrayList.size() > 0) {
                                if (selectedRowLabel != "") {
                                    CustomDailog("Service Details", "Do You Want to Save the Data?", 58, "SAVE");
                                } else {
                                    Log.d("selecteLa", selectedRowLabel);
                                    Toast.makeText(context, "Please Select Atleast One Service Details", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                CustomDailog("Service Details", "Do You Want to Save the Data?", 58, "SAVE");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                TyreTypesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectUser = TyreTypesSpn.getSelectedItem().toString();
                        Log.d("selectUser", selectUser);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void TyreTypesSpinnerDetails() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("TypeName", "WHEEL TYPE");
                requestName = "GeneralMasterData";
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
            } else {
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
                // dialogManager.showAlertDialog(getActivity(), "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (JSONException e) {
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
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            ServiceMaster serviceMaster = new ServiceMaster();
                            serviceMaster.setServiceId(object.getString("ServiceId"));
                            serviceMaster.setServiceName(object.getString("ServiceName"));
                            serviceMaster.setActiveStatus(object.getString("ActiveStatus"));
                            serviceMaster.setSubService(object.getString("SubService"));
                            serviceMasterArrayList.add(serviceMaster);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                BindValueToString(serviceMasterArrayList);
            }
            swipeRefreshLayout.setRefreshing(false);
            selectedMainServiceAdapter = new SelectedMainServiceAdapter(getActivity(), R.layout.selectedmainservice_row_item, serviceMasterArrayList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 1);
            SelectedServiceRecyclerviewid.setLayoutManager(gridLayoutManager1);
            SelectedServiceRecyclerviewid.setItemAnimator(new DefaultItemAnimator());
            SelectedServiceRecyclerviewid.setAdapter(selectedMainServiceAdapter);
            GetTheBusinessDate();
           /* selectedServiceAdpater.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                }
            });*/


            selectedMainServiceAdapter.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    checkedposition = "";
                    switch (view.getId()) {

                        case R.id.ServiceNameCheckBox:
                            Log.d("selected", "selected");
                            ServiceIdSelectedPosition = position;
                            swipeRefreshLayout.setRefreshing(false);
                            checkBox = (CheckBox) view.findViewById(R.id.ServiceNameCheckBox);
                            TextView selectedSericeListTv = (TextView) view.findViewById(R.id.selectedSericeListTv);
                            boolean checked = ((CheckBox) view).isChecked();
                            Log.d("chedked", "" + checked);
                            ServiceId = selectedMainServiceAdapter.serviceMasterArrayList.get(position).getServiceId();
                            String SubService = selectedMainServiceAdapter.serviceMasterArrayList.get(position).getSubService();
                            Log.d("SubServicessss", ServiceId);
                            ServiceIdKey = selectedMainServiceAdapter.serviceMasterArrayList.get(position).getServiceName();

//                            if (selectedMainServiceAdapter.serviceMasterArrayList.get(position).getSelected()) {
//                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setSelected(false);
//                            } else {
//                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setSelected(true);
//                            }


                            if (checked) {
                                try {
                                    Log.v("before service", "checkboxclick");
                                    checkedposition = String.valueOf(position);
                                    Log.d("checkedPosVal", checkedposition);

                                    if (serviceMasterArrayList.get(position).getSubService().equalsIgnoreCase("False")) {
                                        selectedMainServiceAdapter.serviceMasterArrayList.get(position).setSelected(true);
//                                        getServiceAgstDataWithHeaders(ServiceId);

                                    } else {
                                        selectedMainServiceAdapter.serviceMasterArrayList.get(position).setSelected(true);
                                        getServiceAgstDataWithHeaders(ServiceId);
                                    }

                                    // getSubServiceList(ServiceId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {

                                swipeRefreshLayout.setRefreshing(false);
                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setSelected(false);
                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setActiveStatus("0");
                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setWheeltyreDetails(" ");
                                selectedMainServiceAdapter.notifyDataSetChanged();
                         /*       if (ServiceIdKey.equalsIgnoreCase(ProjectMethods.getServiceList1())) {
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
                                }*/
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
                           /* if(ServiceId.equalsIgnoreCase(CloseServiceId)){
                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setSelected(false);
                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setActiveStatus("0");
                                selectedMainServiceAdapter.serviceMasterArrayList.get(position).setWheeltyreDetails(" ");
                                selectedMainServiceAdapter.notifyDataSetChanged();
                            }*/
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

    private void getServiceAgstDataWithHeaders(String serviceId) {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("ServiceId", serviceId);
                    Log.d("serviceid", serviceId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetServiceAgstData";

                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetServiceAgstData, jsonObject, Request.Priority.HIGH);
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
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("ServiceId", serviceId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "SubServiceMaster";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SubServiceMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeVehicleDetails(JSONArray jsonArray) {
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
                    toast.show();
                } else {
                    mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    String CustomerName = jsonArray.getJSONObject(0).getString("CustomerName");
                    String EmailId = jsonArray.getJSONObject(0).getString("EmailId");
                    String MakeCompany = jsonArray.getJSONObject(0).getString("MakeCompany");
                    String MakeYear = jsonArray.getJSONObject(0).getString("MakeYear");
                    String MobileNo = jsonArray.getJSONObject(0).getString("MobileNo");
                    String ModelNo = jsonArray.getJSONObject(0).getString("ModelNo");
                    mVehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");
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
            prgDialog = new ProgressDialog(getActivity());
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

    private class UploadTask extends AsyncTask<Void, Void, String> {
        File f;
        String Image;
        Uri fileUri;

        public UploadTask(File file, String imageName, Uri u) {
            f = file;
            fileUri = u;
            Image = imageName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect(ProjectVariables.FTP_HOST, ProjectVariables.FTP_USER, ProjectVariables.FTP_PASS);
                boolean status = false;
                status = ftp.setWorkingDirectory(ProjectVariables.IMAGE_FOLD);
                if (fileUri == null) {
                    InputStream targetStream = new FileInputStream(f);
                    ftp.uploadFile(targetStream, Image);
                } else {
                    InputStream stream = getActivity().getContentResolver().openInputStream(fileUri);
                    ftp.uploadFile(stream, Image);
                }
                Log.e("Status", status + "");
                return new String("Upload Successful");
            } catch (Exception e) {

                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Uploading......");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }

    private class OnServiceCallCompleteListenerImplData implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

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
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {

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
                    for (int i = 0; i < BlockArrayList.size(); i++) {
                        us[i] = BlockArrayList.get(i).getName();

                    }


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpnBlockSelection.setAdapter(spinnerArrayAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class newCategories extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

//                    showProgressDialog();
                }
            });

        }

        /**
         * Creating Application
         */
        @SuppressWarnings("deprecation")
        protected String doInBackground(String... args) {

            final OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBuilder = new FormBody.Builder();
            JSONObject postData = new JSONObject();

            try {
                postData.put("Flag", "60");
            } catch (JSONException e) {

            }
            RequestBody formBody = RequestBody.create(MEDIA_TYPE, postData.toString());

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://Myaccountsonline.co.in/TaskManager/WheelAlignment.svc/ServiceMaster")
                    .post(formBody)
                    .build();

//            if (dDialog.isShowing() && dDialog != null) {
//                dDialog.dismiss();
//            }
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    String mMessage = e.getMessage();
                    Log.w("failure Response", mMessage);
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response)
                        throws IOException {
//                    if (dDialog.isShowing() && dDialog != null) {
//                        dDialog.dismiss();
//                    }
                    String mMessage = response.body().string();
                    if (response.isSuccessful()) {
//                        hideProgressDialog();
                        try {

                            final JSONArray json = new JSONArray(mMessage);
//                            int success = json.getInt("success");
                            Log.d("dasdasdasd", "" + json.toString());

                            categoryList.clear();

//                                result = json.getJSONArray(TAG_RESULTS);
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                // successfully created product
                                String activeStatus = c.getString(TAG_ACTIVE_STATUS);
                                String result = c.getString(TAG_RESULT);
                                String serviceId = c.getString(TAG_SERVICE_ID);
                                String serviceName = c.getString(TAG_SERVICE_NAME);
                                String subService = c.getString(TAG_SUB_SERVICE);

                                HashMap<String, String> map = new HashMap<String, String>();

                                map.put(TAG_ACTIVE_STATUS, activeStatus);
                                map.put(TAG_RESULT, result);
                                map.put(TAG_SERVICE_ID, serviceId);
                                map.put(TAG_SERVICE_NAME, serviceName);
                                map.put(TAG_SUB_SERVICE, subService);
                                productsList.add(map);

                                CategoryModelClass catModelClass = new CategoryModelClass(activeStatus, result, serviceId, serviceName, subService);
                                categoryList.add(catModelClass);
                                // Print out the colors in the ArrayList.
                                    Log.d("dasdasdasd","+"+categoryList.get(i).toString());


                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                        SelectedServiceRecyclerviewid1.setLayoutManager(mLayoutManager);

                                        NewCategoryAdapter categoryAdapter = new NewCategoryAdapter(getActivity(), categoryList);
                                        SelectedServiceRecyclerviewid1.setAdapter(categoryAdapter);

                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String status) {
            // dismiss the dialog once done


        }

    }

    public class NewCategoryAdapter extends RecyclerView.Adapter<NewCategoryAdapter.ViewHolder> {

        CategoryModelClass category;
        Context activity;
        private List<CategoryModelClass> categoryList;

        public NewCategoryAdapter(Activity activity2, List<CategoryModelClass> categoryList) {
            // TODO Auto-generated constructor stub

            activity = activity2;
            this.categoryList = categoryList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.selectedmainservice_row_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position1) {

            category = categoryList.get(position1);

            holder.ServiceNameCheckBox.setText(category.getServiceName().trim());
            back_dim_layout.setVisibility(View.GONE);

            holder.ServiceNameCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedServiceId = category.getServiceId();

                    Log.d("clicked", category.getServiceId());
                    popup();

                    back_dim_layout.setVisibility(View.VISIBLE);

                    new newGetCitiesDetails().execute();


                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryList.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView selectedSericeListTv;
            public CheckBox ServiceNameCheckBox;

            public ViewHolder(final View itemView) {
                super(itemView);

                selectedSericeListTv = itemView.findViewById(R.id.selectedSericeListTv);
                ServiceNameCheckBox = itemView.findViewById(R.id.ServiceNameCheckBox);


            }

        }

    }

    public void popup() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.activity_sub_services_popup, null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.CloseImgBtn);
        RelativeLayout topLayout = (RelativeLayout) customView.findViewById(R.id.topLayout);
        subSubServiceSpinner = (Spinner) customView.findViewById(R.id.subSubServiceSpinner);
        subServiceSpinner=(Spinner) customView.findViewById(R.id.subServiceSpinner);

        new newGetCitiesDetails().execute();

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(topLayout, Gravity.CENTER, 0, 0);
        mPopupWindow.setFocusable(true);
    }

    class newGetCitiesDetails extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

//                    pDialog = new ProgressDialog(BrandsActivity.this);
//                    pDialog.setMessage("Loading...");
//                    pDialog.setIndeterminate(false);
//                    pDialog.setCancelable(true);
//                    pDialog.setCanceledOnTouchOutside(false);
//                    pDialog.show();

                }
            });

        }

        /**
         * Creating Application
         */
        @SuppressWarnings("deprecation")
        protected String doInBackground(String... args) {

            final OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBuilder = new FormBody.Builder();

            JSONObject postData = new JSONObject();

            try {
                postData.put("ServiceId", selectedServiceId);
            } catch (JSONException e) {

            }
            RequestBody formBody = RequestBody.create(MEDIA_TYPE, postData.toString());

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://Myaccountsonline.co.in/TaskManager/WheelAlignment.svc/GetServiceAgstData")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    //call.cancel();
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response)
                        throws IOException {

                    String mMessage = response.body().string();
                    if (response.isSuccessful()) {
                        try {

                            final JSONArray json = new JSONArray(mMessage);
                            Log.d("jsonnnnn", json.toString());

                            weightList.clear();
                            weightList.add("Select Weight");

                            weightIdList.clear();
                            weightIdList.add("Select Weight");


                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);

                                // Constants.galleryList.clear();
                                String weight = c.getString("Data");

                                weightList.add(weight);

                                Log.d("jsonnnnn", "++"+weight);
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    weightAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, weightList);
                                    weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    subSubServiceSpinner.setAdapter(weightAdapter);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

            return null;
        }

        private void showToast() {
            // TODO Auto-generated method stub
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    // Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            });
        }


        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String status) {
            // dismiss the dialog once done


        }
    }

}
