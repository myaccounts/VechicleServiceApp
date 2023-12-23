package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.lvrenyang.pos.Cmd;
import com.myaccounts.vechicleserviceapp.Activity.CustomerSelectionActivity;
import com.myaccounts.vechicleserviceapp.Activity.GeneralServiceActivity;
import com.myaccounts.vechicleserviceapp.Activity.JobCardNoActivity;
import com.myaccounts.vechicleserviceapp.Activity.MainActivity;
import com.myaccounts.vechicleserviceapp.Activity.PayNowActivity2016;
import com.myaccounts.vechicleserviceapp.Activity.SparePartsActivity;
import com.myaccounts.vechicleserviceapp.Adapter.GeneralServiceEstimationAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceUpdateAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.SparePrtEstimationAdapter;
import com.myaccounts.vechicleserviceapp.LoginSetUp.LoginActivity;
import com.myaccounts.vechicleserviceapp.Pojo.CompanyDetails;
import com.myaccounts.vechicleserviceapp.Pojo.EstDetails;
import com.myaccounts.vechicleserviceapp.Pojo.EstimationPrintList;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceList;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartEstDetails;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartEstimation;
import com.myaccounts.vechicleserviceapp.Printernew.InitializePrinter;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AidlUtil;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.exception.CustomExceptionHandler;
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.myaccounts.vechicleserviceapp.network.InfDbSpecs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
//import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import static android.app.Activity.RESULT_OK;
import static com.myaccounts.vechicleserviceapp.R.id.jobcardId;
import static com.myaccounts.vechicleserviceapp.R.id.mSerTotalValtv;

public class SparePartEstAgstJobCard extends Fragment implements View.OnClickListener,ReceiveListener {
    private static int nFontStyle;
    String cardEdt="0",cashEdt="0",upiEdt="0",invEdt="0",savingState="FALSE";
    float totalAmtFromPayNow=0.00f;
    boolean payNowClick=false;
    View view;
    private Context context;
    @BindView(R.id.IdJobCardNoEdt)
    EditText JobCardNoEdt;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.IdCustNameEdt)
    EditText CustNameEdt;
    @BindView(R.id.IdMobileNoEdt)
    EditText MobileNoEdt;
    @BindView(R.id.IdQtyEdt)
    EditText QtyEdt;
    @BindView(R.id.IdCustomerNameEdt)
    EditText CustomerNameEdt;
    @BindView(R.id.IdReceiptDateEdt)
    EditText ReceiptDateEdt;
    @BindView(R.id.IdSparePartEdt)
    EditText SparePartEdt;
    @BindView(R.id.IdmrpEdt)
    EditText IdmrpEdt;
    @BindView(R.id.IdtechnicianNameEdt)
    EditText techEditText;
    @BindView(R.id.btn_paynow)
    Button btn_paynow;

    /*  @BindView(R.id.IdDiscountAmtEdt)
      EditText IdDiscountAmtEdt;*/
    @BindView(R.id.IdGeneralServiceEdt)
    EditText GeneralServiceEdt;
    @BindView(R.id.IdSpnEstimationList)
    Spinner spnerList;
    @BindView(R.id.IdAddImgbtn)
    ImageButton AddImgBtn;
    @BindView(R.id.IdJobCardCustomerRecyclerview)
    RecyclerView JobCardCustomerRecyclerview;
    @BindView(R.id.IdGeneralServiceReccylerview)
    RecyclerView GeneralServiceReccylerview;
    @BindView(R.id.IdRemarksEdt)
    EditText RemarksEdt;
    @BindView(R.id.IdNetAmt)
    EditText NetAmt;
    @BindView(R.id.IdAmtEdt)
    EditText AmtEdt;
    @BindView(R.id.TotalNoRowsTv)
    TextView TotalNoRowsTv;
    @BindView(R.id.TotalQtyTv)
    TextView TotalQtyTv;
    @BindView(R.id.TotalAmtTv)
    TextView TotalAmtTv;
    @BindView(R.id.IdGenSerTotRows)
    TextView GenSerTotRows;
    @BindView(R.id.IdGenSerFreeAmt)
    TextView GenSerFreeAmount;
    @BindView(R.id.IdGenSerTotQty)
    TextView GenSerTotQty;
    @BindView(R.id.grossAmtTv)
    TextView finalAmtTv;
    @BindView(R.id.IdGenSerTotAmt)
    TextView GenSerTotAmt;
    @BindView(R.id.JobCardSelectionLayout)
    TextInputLayout JobCardSelectionLayout;
    @BindView(R.id.CusomerSelectionLayout)
    TextInputLayout CusomerSelectionLayout;
    @BindView(R.id.TextLayout)
    TextInputLayout TextLayout;
    @BindView(R.id.UomTv)
    TextView UomTv;
    @BindView(R.id.IdDiscountCheckBox)
    CheckBox DiscountCheckBox;
    @BindView(R.id.IdDiscountAmt)
    EditText DiscountAmt;
    @BindView(R.id.IdDiscountPercent)
    EditText DiscountPercent;
    @BindView(R.id.LinerLayoutCustomerSelection)
    LinearLayout LinerLayoutCustomerSelection;
    @BindView(R.id.IdImgSearchBtn)
    ImageButton IdImgSearchBtn;
    private PopupWindow mPopupWindow;
    private LinearLayout lr;
    private String AvlQty = "", UOMName = "", UOMId = "", FinalDate;
    private int month, day, year;
    private ArrayList<EstDetails> estDetailsArrayList;
    private ArrayList<SparePartEstDetails> sparePartEstDetailsArrayList;
    private ArrayList<ServiceList> generailServiceDetailsArrayList;
    private ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList;
    private ArrayList<SparePartEstimation> sparePartEstimationArrayList;
    private ArrayList<EstimationPrintList> estimationPrintListArrayList;
    private SparePrtEstimationAdapter sparepArtEstimationAdapter;
    private SharedPreferences printerSharedpreferences;
    private GeneralServiceEstimationAdapter generalServiceEstimationAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    float totalAmount, totalQty,totalFreeAmount;
    String TotalGrossAmt, TotalQtyValue, TotalRows;
    private String selectedString, VehicleNo,JCDate,ModelNo,JCTime,JCRemarks;
    private int position, GeneralPosition;
    private String requestName;
    private String finalItemDetailList = "", finalServiceDetails = "";
    private ProgressDialog pDialog;
    private ProgressDialog progressDialog;
    private boolean printIsDone = false;
    float finalAmt = 0.00f;
    public static final String TAG ="JobCard";
    private ArrayList<String> printerList;
    private Printer mPrinter = null;
    private InitializePrinter initializePrinter;
    private  String mCustomerName,mCustomerID,mJobCardNo,EstimationDate,mMobileNo;
    private String TodayDate,printDate,printPhoneNumber,printCustomerName,printVehicleRegNumber,printVehicleModelNumber,printNetAmount,printCashAmount,printCardAmount,printUpiAmount;
    private NewServiceUpdateAdapter newServiceupdateAdapter;
    private RecyclerView ServiceMasterRecyclerview;
    private String printerIp,printerName;
    private ArrayList<CompanyDetails> companyDetailsArrayList;

    public static SparePartEstAgstJobCard newInstance() {
        Bundle args = new Bundle();
        SparePartEstAgstJobCard fragment = new SparePartEstAgstJobCard();
        fragment.setArguments(args);
        return fragment;
    }
    public ArrayList<NewServiceMasterDetails> getEdiServiceDetailsArrayList() {
        return ediServiceDetailsArrayList;
    }

    public void setEdiServiceDetailsArrayList(ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList) {
        this.ediServiceDetailsArrayList = ediServiceDetailsArrayList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sparepartestimationagstjobcard, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Service Estimation</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
        sparePartEstDetailsArrayList = new ArrayList<>();
        estDetailsArrayList=new ArrayList<>();
        generailServiceDetailsArrayList = new ArrayList<>();
        sparePartEstimationArrayList = new ArrayList<>();
        estimationPrintListArrayList = new ArrayList<>();
        companyDetailsArrayList = new ArrayList<>();
        JobCardNoEdt.setOnClickListener(this);
        SparePartEdt.setOnClickListener(this);
        CustomerNameEdt.setOnClickListener(this);
        AddImgBtn.setOnClickListener(this);
        GeneralServiceEdt.setOnClickListener(this);
        IdImgSearchBtn.setOnClickListener(this);
        btn_paynow.setOnClickListener(this);
        ServiceMasterRecyclerview = (RecyclerView)getActivity(). findViewById(R.id.ServiceMasterRecyclerview);
        dateFormat();

        initializePrinter = new InitializePrinter(getActivity(), this);
        printerSharedpreferences =getActivity().getSharedPreferences(InitializePrinter.PRINTER_PREFERENCES, Context.MODE_PRIVATE);
        spnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedString = spnerList.getSelectedItem().toString();
                if (selectedString.equalsIgnoreCase("Customer")) {
                    LinerLayoutCustomerSelection.setVisibility(View.VISIBLE);
                    JobCardSelectionLayout.setVisibility(View.GONE);
                    TextLayout.setVisibility(View.GONE);
                    sparePartEstDetailsArrayList.clear();
                    generailServiceDetailsArrayList.clear();
                    finalAmtTv.setText("0.00");
                    NetAmt.setText("0.00");
                    CustomerNameEdt.setText("");
                    MobileNoEdt.setText("");
                    techEditText.setText("");
                    TotalNoRowsTv.setText("0");
                    TotalQtyTv.setText("0");
                    TotalAmtTv.setText("0.00");
                    generalServiceEstimationAdapter.notifyDataSetChanged();
                    sparepArtEstimationAdapter.notifyDataSetChanged();
                } else {
                    LinerLayoutCustomerSelection.setVisibility(View.GONE);
                    JobCardSelectionLayout.setVisibility(View.VISIBLE);
                    TextLayout.setVisibility(View.VISIBLE);
                    JobCardNoEdt.setText("");
                    generailServiceDetailsArrayList.clear();
                    generalServiceEstimationAdapter.notifyDataSetChanged();
                    sparePartEstDetailsArrayList.clear();
                    sparepArtEstimationAdapter.notifyDataSetChanged();
                    finalAmtTv.setText("0.00");
                    NetAmt.setText("0.00");
                    MobileNoEdt.setText("");
                    techEditText.setText("");
                    TotalNoRowsTv.setText("0");
                    TotalQtyTv.setText("0");
                    TotalAmtTv.setText("0.00");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NetAmt.getText().toString().equalsIgnoreCase("0.00")) {
                    Intent intent = new Intent(getActivity(), PayNowActivity2016.class);
                    intent.putExtra("Amount",NetAmt.getText().toString());
                    intent.putExtra("Jobcard",JobCardNoEdt.getText().toString());
                    startActivityForResult(intent, 222);
                }else{
                    Toast.makeText(getActivity(),"Please Select Jobcard",Toast.LENGTH_SHORT).show();
                }
//                startActivityForResult(intent, 5);

                *//*LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                final View customView = inflater.inflate(R.layout.popup_paynow,null);

                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        true);

                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                final Button btn_submit = (Button)customView.findViewById(R.id.btn_submit);
                lr = (LinearLayout) customView.findViewById(R.id.rl_custom_layout);
                mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);
                // Set a click listener for the popup window close button
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });*//*


            }
        });*/
        DiscountAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    AmtEdt.setText(s.toString());
                    CalCulateDiscountAmt(DiscountAmt.getText().toString());

                }else {
                    AmtEdt.setText("");
                    CalCulateDiscountAmt("0");
                    finalCalculateData();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        DiscountPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    float percentage=Float.valueOf(s.toString());
//                    float finalAmt=Float.valueOf(finalAmtTv.getText().toString());
                    float Value=(finalAmt*percentage)/100;
                    AmtEdt.setText(String.valueOf(Value));
                    CalCulateDiscountAmt(String.valueOf(Value));

                }else {
                    AmtEdt.setText("");
                    CalCulateDiscountAmt("0");
                    finalCalculateData();
                }
            }
        });
        DiscountCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    QtyEdt.clearFocus();
                    DiscountPercent.setVisibility(View.VISIBLE);
                    DiscountAmt.setVisibility(View.GONE);
                    AmtEdt.setText("");
                    DiscountPercent.setText("");
                    DiscountAmt.setText("");
                    finalCalculateData();

                }else{
                    QtyEdt.clearFocus();
                    DiscountAmt.setVisibility(View.VISIBLE);
                    DiscountPercent.setVisibility(View.GONE);
                    AmtEdt.setText("");
                    DiscountAmt.setText("");
                    DiscountPercent.setText("");
                    finalCalculateData();

                }
            }
        });

        AidlUtil.getInstance().initPrinter();
        AddListToGrid();
        AddListToGridService();
        return view;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
           /* runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", getActivity());
                }
            });*/
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
           /* runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", getActivity());
                }
            });*/
        }

        finalizeObject();
    }




    private void CalCulateDiscountAmt(String toString) {
        try{
            float Amt=0.00f,DiscountValue=0.00f,finalAmt1=0.00f;
            // Amt=Float.valueOf(finalAmtTv.getText().toString());
            DiscountValue=Float.valueOf(toString);
            finalAmt1=finalAmt-DiscountValue;
            NetAmt.setText(String.valueOf(finalAmt1));
//            finalAmtTv.setText(NetAmt.getText().toString());
//            call paynow popup
            // user typed: start the timer
            /*Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // do your actual work here
                    DiscountValueCheck=false;
                    if(payNowClick && !DiscountAmt.getText().toString().isEmpty()) {
                        Intent payIntent = new Intent(getActivity(), PayNowActivity2016.class);
                        payIntent.putExtra("Amount", NetAmt.getText().toString());
                        payIntent.putExtra("Jobcard", JobCardNoEdt.getText().toString());
                        payIntent.putExtra("SavingValues", "TRUE");
                        startActivityForResult(payIntent, 222);
                    }
                }
            }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void AddListToGridService() {
        Log.e("AddListToGrid called", "AddListToGrid called");
        if (context!=null) {
            Log.e("AddListToGrid called", " INSIDE IF "+context);
            generalServiceEstimationAdapter = new GeneralServiceEstimationAdapter(context, R.layout.generalservice_row_item, generailServiceDetailsArrayList);
            GeneralServiceReccylerview.setHasFixedSize(true);
            GeneralServiceReccylerview.setItemAnimator(new DefaultItemAnimator());
            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GeneralServiceReccylerview.setLayoutManager(mLinearLayoutManager);
            GeneralServiceReccylerview.setAdapter(generalServiceEstimationAdapter);
            CalculateGeneralServiceTotal();

            generalServiceEstimationAdapter.SetOnItemClickListener(new GeneralServiceEstimationAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    boolean value = false;
                    float mqty = 0.0f, mservicecharge = 0.0f;
                    EditText editText;
                    TextView textView;

                    switch(view.getId()) {
                        case mSerTotalValtv:
                            try {
                                textView=(TextView)view.findViewById(R.id.mSerTotalValtv);
                                String serTotal=(textView.getText().toString());
                                generailServiceDetailsArrayList.get(position).setmTotalVal(serTotal);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                            break;
                        case R.id.mSerRemarkstv:
                            try {
                                editText=(EditText)view.findViewById(R.id.mSerRemarkstv);
                                String remarks=editText.getText().toString();
                                generailServiceDetailsArrayList.get(position).setmTotalVal(remarks);
                                generalServiceEstimationAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                            break;
                        case R.id.IdDeleteIconImg:
                            try {
                                CustomDailog("Spare Part Estimation", "Do You Want to Delete Details?", 39, "Delete", position);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                            break;
                    }
                }
            });
        }else{
            Log.e("AddListToGrid called", " INSIDE ELSE PART "+context);
        }

    }
    private void AddListToGrid() {
        sparepArtEstimationAdapter = new SparePrtEstimationAdapter(getActivity(), R.layout.sparepart_issue_row_item, sparePartEstDetailsArrayList);
        JobCardCustomerRecyclerview.setHasFixedSize(true);
        JobCardCustomerRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        JobCardCustomerRecyclerview.setLayoutManager(mLinearLayoutManager);
        JobCardCustomerRecyclerview.setAdapter(sparepArtEstimationAdapter);
        sparepArtEstimationAdapter.SetOnItemClickListener(new SparePrtEstimationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                boolean value = false;
                switch (view.getId()) {

                    case R.id.IdEditIconImg:
                        try {
                            CustomDailog("Spare Part Estimation", "Do You Want to Edit Details?", 33, "Edit", position);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.IdDeleteIconImg:
                        try {
                            CustomDailog("Spare Part Estimation", "Do You Want to Delete Details?", 34, "Delete", position);
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
            ReceiptDateEdt.setText(currentdate);
            ReceiptDateEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog picker = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    int month= monthOfYear+1;
                                    String fm=""+month;
                                    String fd=""+dayOfMonth;
                                    if(month<10){
                                        fm ="0"+month;
                                    }
                                    if (dayOfMonth<10){
                                        fd="0"+dayOfMonth;
                                    }
                                    String date= ""+fd+"-"+fm+"-"+year;
                                    ReceiptDateEdt.setText(date);
                                    // ReceiptDateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    //  ReceiptDateEdt.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_paynow:
                    Intent payIntent = new Intent(getActivity(), PayNowActivity2016.class);
                    payIntent.putExtra("Amount",NetAmt.getText().toString());
                    payIntent.putExtra("Jobcard",JobCardNoEdt.getText().toString());
                    payIntent.putExtra("mobileNo",MobileNoEdt.getText().toString());
                    payIntent.putExtra("SavingValues", savingState);
                    startActivityForResult(payIntent, 222);

                    break;
                case R.id.IdJobCardNoEdt:
                    Intent intent = new Intent(getActivity(), JobCardNoActivity.class);
                    intent.putExtra("STATUS","Ready");
                    startActivityForResult(intent, 101);
                    break;
                case R.id.IdSparePartEdt:
                    Intent spareparts = new Intent(getActivity(), SparePartsActivity.class);
                    startActivityForResult(spareparts, 102);
                    break;
                case R.id.IdImgSearchBtn:
                    Intent customerselection = new Intent(getActivity(), CustomerSelectionActivity.class);
                    startActivityForResult(customerselection, 103);
                    break;
                case R.id.IdGeneralServiceEdt:
                    Intent generalService = new Intent(getActivity(), GeneralServiceActivity.class);
                    generalService.putExtra("jobCardId","74E299180213");
                    startActivityForResult(generalService, 104);
                    break;
                case R.id.IdAddImgbtn:
                    try {
                        AddToGridView();
                    } catch (Exception e) {
                        e.printStackTrace();
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
                SparePartEstDetails sparePartEstDetails = new SparePartEstDetails();
                sparePartEstDetails.setmJobCardNo(JobCardNoEdt.getText().toString());
                sparePartEstDetails.setmCustomerName(CustNameEdt.getText().toString());
                // sparePartEstDetails.setmCustomerID(CustomerNameEdt.getTag().toString());
                sparePartEstDetails.setmMobileNo(MobileNoEdt.getText().toString());
                sparePartEstDetails.setmSparePartName(SparePartEdt.getText().toString());
                sparePartEstDetails.setmSparePartID(SparePartEdt.getTag().toString());
                sparePartEstDetails.setmIssueDate(ReceiptDateEdt.getText().toString());
                sparePartEstDetails.setmMrp(IdmrpEdt.getText().toString());
                sparePartEstDetails.setmQty(QtyEdt.getText().toString());
                sparePartEstDetails.setmAVLQty(AvlQty);
                sparePartEstDetails.setmUOM(UOMName);
                sparePartEstDetails.setmUOMID(UOMId);
                mrp = Float.valueOf(Float.parseFloat(IdmrpEdt.getText().toString()));
                Qty = Float.valueOf(Float.parseFloat(QtyEdt.getText().toString()));
                float finalValue = mrp * Qty;
                sparePartEstDetails.setmTotalValue((String.format("%.2f", finalValue)));
                // sparePartDetailsArrayList1.add(sparePartDetails);
                AddtemToGrid(sparePartEstDetails);
                CalculationPart();

                //clear edit values
                SparePartEdt.setText("");
                SparePartEdt.setTag("");
                QtyEdt.setText("");
                IdmrpEdt.setText("");

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
        int TotalRows = 0;

        totalAmount = 0.0f;
        float finalamt = 0.0f;
        totalQty = 0.0f;
        int finalrows = 0;
        for (int i = 0; i < sparePartEstDetailsArrayList.size(); i++) {
            finalrows++;
            TotalGrossAmt = sparePartEstDetailsArrayList.get(i).getmMrp();
            TotalQtyValue = sparePartEstDetailsArrayList.get(i).getmQty();
            finalamt=Float.parseFloat(TotalQtyValue)*Float.parseFloat(TotalGrossAmt);
            if (TotalQtyValue.length() != 0) { //Null Check

                totalQty += Float.valueOf(TotalQtyValue);
            }
            if (TotalGrossAmt.length() != 0) {
                totalAmount += Float.valueOf(finalamt);
            }
        }

        TotalNoRowsTv.setText(String.valueOf(finalrows));
        TotalQtyTv.setText(String.format("%.0f", totalQty));
        TotalAmtTv.setText(String.format("%.2f", totalAmount));
        finalCalculateData();

    }

    private void finalCalculateData() {
        finalAmt=0.00f;
        float sparepartvalue=0.0f,generalservicevalue=0.0f,totalValue=0.0f,GenSerAnt=0.0f,GenSerFreeAmt=0.0f,generalservicefreevalue=0.0f;
        try {
            if(!TotalAmtTv.getText().toString().isEmpty()){
                totalValue=Float.parseFloat(TotalAmtTv.getText().toString());
            }else{
                totalValue=0.00f;
            }
            if(!GenSerTotAmt.getText().toString().isEmpty()){
                GenSerAnt=Float.parseFloat(GenSerTotAmt.getText().toString());
            }else{
                GenSerAnt=0.00f;
            }
            if(!GenSerFreeAmount.getText().toString().isEmpty())
                GenSerFreeAmt=Float.parseFloat(GenSerFreeAmount.getText().toString());
            else
                GenSerFreeAmt=0.00f;

            sparepartvalue = Float.valueOf(totalValue);
            generalservicevalue = Float.valueOf(GenSerAnt);
            generalservicefreevalue=Float.valueOf(GenSerFreeAmt);
            finalAmt += sparepartvalue + generalservicevalue;//
            finalAmtTv.setText(String.valueOf(String.format("%.2f",finalAmt)));
            NetAmt.setText(finalAmtTv.getText().toString().trim());
//            NetAmt.setText(String.valueOf(String.format("%.2f",finalAmt)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getItemExistency(String SpareId) {
        try {
            for (int i = 0; i < sparePartEstDetailsArrayList.size(); i++) {
                if (sparePartEstDetailsArrayList.get(i).getmSparePartID().equalsIgnoreCase(SpareId)) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void AddtemToGrid(SparePartEstDetails SP) {
        try {
            float mrp = 0.0f, Qty = 0.0f;
            int selectedPosition = getItemExistency(SP.getmSparePartID());
            boolean IsItemAdded = false;
            if (selectedPosition == -1) {
                sparePartEstDetailsArrayList.add(SP);
                JobCardCustomerRecyclerview.smoothScrollToPosition(sparePartEstDetailsArrayList.size() - 1);
                IsItemAdded = true;
            } else {
                SparePartEstDetails ET1 = sparePartEstDetailsArrayList.get(selectedPosition);
                float Quantity = Float.parseFloat(ET1.getmQty());
                Quantity += Float.parseFloat(SP.getmQty());
                ET1.setmQty(String.valueOf(Quantity));
                IsItemAdded = true;
                JobCardCustomerRecyclerview.smoothScrollToPosition(selectedPosition);
                mrp = Float.valueOf(ET1.getmMrp());
                Qty = Float.valueOf(ET1.getmQty());
                float finalValue = mrp * Qty;
                ET1.setmTotalValue(String.valueOf(finalValue));
            }


            if (IsItemAdded) {


                sparepArtEstimationAdapter.notifyDataSetChanged();
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

    private boolean validate() {
        boolean Valid = true;
        String JobcardNoTv = JobCardNoEdt.getText().toString().trim();
        String CustomerNametv = CustomerNameEdt.getText().toString().trim();
        String SparePart = SparePartEdt.getText().toString().trim();
        String mrpValue = IdmrpEdt.getText().toString().trim();
        String qtyValue = QtyEdt.getText().toString().trim();
        if (selectedString.equalsIgnoreCase("Customer")) {
            if (CustomerNametv.length() == 0) {
                Toast.makeText(getActivity(), "Please Select Customer", Toast.LENGTH_SHORT).show();
                Valid = false;
            }
        } else {
            if (JobcardNoTv.length() == 0) {
                Toast.makeText(getActivity(), "Please Select JobCardNo", Toast.LENGTH_SHORT).show();
                Valid = false;
            }
        }

        if (SparePart.length() == 0) {
            Toast.makeText(getActivity(), "Please Select SparePart", Toast.LENGTH_SHORT).show();
            Valid = false;
        } else if (qtyValue.length() == 0) {
            QtyEdt.setError("Enter Qty");
            QtyEdt.requestFocus();
            Valid = false;
        } else if (mrpValue.length() == 0) {
            IdmrpEdt.setError("Enter MRP");
            IdmrpEdt.requestFocus();
            Valid = false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (data != null && resultCode == RESULT_OK) {
                JobInformation(data);
            }
        }else if (requestCode == 222) {
            if (data != null  && resultCode == RESULT_OK) {
                PayNowInformation(data);
            }
        } else if (requestCode == 102) {
            if (data != null && resultCode == RESULT_OK) {
                SparPartInformation(data);
            }
        } else if (requestCode == 103) {
            if (data != null && resultCode == RESULT_OK) {
                CustomerInformation(data);
            }
        } else if (requestCode == 104) {
            if (data != null && resultCode == RESULT_OK) {
                GeneralServiceInformation(data);
            }
        } else if (requestCode == 34 && data != null && resultCode == RESULT_OK) {
            DeleteDetails();
        } else if (requestCode == 33 && data != null && resultCode == RESULT_OK) {
            EditData();
        }else if (requestCode == 38&& data != null && resultCode == RESULT_OK) {
//            MainActivity.comingfrom="66";
            PrepareSparePartEstList();
        }else if (requestCode == 39&& data != null && resultCode == RESULT_OK) {
            DeleteServiceDetails();
        }else if(requestCode == 99 && data !=null && resultCode == RESULT_OK){
            PrintDetails();
        }
    }

    private void PrintDetails() {
        try {
            DatabaseHelper db = new DatabaseHelper(context);
            Cursor cursor = db.getPrinterDetails();
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    printerName = cursor.getString(cursor.getColumnIndex(InfDbSpecs.PRINTER_NAME));
                    printerIp = cursor.getString(cursor.getColumnIndex(InfDbSpecs.PRINTERIP));

                }
            }
        }
        catch(Exception e){
        }
        String TransactionNo="";
        Print_BillFormat(TransactionNo);
    }

    private void PayNowInformation(Intent data) {
        try {
            payNowClick=true;
            savingState=data.getStringExtra("SavingValues");
            cardEdt = data.getStringExtra("cardEdt");
            cashEdt = data.getStringExtra("cashEdt");
            upiEdt = data.getStringExtra("upiEdt");
            invEdt = data.getStringExtra("invEdt");
            int cardEdtValue=0,cashEdtValue=0,upiEdtValue=0,invEdtValue=0;
            if(!cardEdt.isEmpty()) {
                cardEdtValue = Integer.parseInt(cardEdt);
            }
            if(!cashEdt.isEmpty()){
                cashEdtValue=Integer.parseInt(cashEdt);
            }
            if(!upiEdt.isEmpty())
                upiEdtValue=Integer.parseInt(upiEdt);
            if(!invEdt.isEmpty())
                invEdtValue=Integer.parseInt(invEdt);

            totalAmtFromPayNow=cardEdtValue+cashEdtValue+upiEdtValue+invEdtValue;

            CustomDailog("Total Cost", "Do You Want to Print Bill?", 99, "PRINT", 0);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void DeleteServiceDetails() {
        try {
            generailServiceDetailsArrayList.remove(position);
            CalculateGeneralServiceTotal();
            generalServiceEstimationAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GeneralServiceInformation(Intent data) {
        try {
            String ServioceId = data.getStringExtra("ServioceId");
            String ServiceName = data.getStringExtra("ServiceName");
            String SubServioceId = data.getStringExtra("SubServioceId");
            String SubServiceName = data.getStringExtra("SubServiceName");
            String ServiceCharge = data.getStringExtra("ServiceCharge");
            ServiceList serviceList = new ServiceList();
            serviceList.setServiceId(ServioceId);
            serviceList.setServiceName(ServiceName);
            serviceList.setSubServiceId(SubServioceId);
            serviceList.setSubServiceName(SubServiceName);
            serviceList.setServiceCharge(ServiceCharge);
            serviceList.setmIssueType("Paid");
            serviceList.setmQty("1");
            serviceList.setmRemarks("");
            serviceList.setmTotalVal(CalculateToTalValue(serviceList.getmQty(),serviceList.getServiceCharge()));
            try {
                float mrp = 0.0f, Qty = 0.0f;
                int selectedPosition = getItemExistencyJ(serviceList.getSubServiceId());
                boolean IsItemAdded = false;
                if (selectedPosition == -1) {
                    generailServiceDetailsArrayList.add(serviceList);
                    GeneralServiceReccylerview.smoothScrollToPosition(generailServiceDetailsArrayList.size() - 1);
                    IsItemAdded = true;
                } else {
                    ServiceList ET1 = generailServiceDetailsArrayList.get(selectedPosition);
                    float Quantity = Float.parseFloat(ET1.getmQty());
                    Quantity += Float.parseFloat(serviceList.getmQty());
                    ET1.setmQty(String.valueOf(Quantity));
                    IsItemAdded = true;
                    GeneralServiceReccylerview.smoothScrollToPosition(selectedPosition);
                    mrp = Float.valueOf(ET1.getServiceCharge());
                    Qty = Float.valueOf(ET1.getmQty());
                    float finalValue = mrp * Qty;
                    ET1.setmTotalVal(String.valueOf(finalValue));
                }


                if (IsItemAdded) {


                    generalServiceEstimationAdapter.notifyDataSetChanged();
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
//            generailServiceDetailsArrayList.add(serviceList);
            finalCalculateData();
        } catch (Exception e) {
            e.printStackTrace();
        }


        generalServiceEstimationAdapter = new GeneralServiceEstimationAdapter(getActivity(), R.layout.generalservice_row_item, generailServiceDetailsArrayList);
        GeneralServiceReccylerview.setHasFixedSize(true);
        GeneralServiceReccylerview.setItemAnimator(new DefaultItemAnimator());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GeneralServiceReccylerview.setLayoutManager(mLinearLayoutManager);
        GeneralServiceReccylerview.setAdapter(generalServiceEstimationAdapter);
        CalculateGeneralServiceTotal();

        generalServiceEstimationAdapter.SetOnItemClickListener(new GeneralServiceEstimationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                boolean value = false;
                float mqty = 0.0f, mservicecharge = 0.0f;
                EditText editText;
                TextView textView;

                switch(view.getId()) {
                    case mSerTotalValtv:
                        try {
                            textView=(TextView)view.findViewById(R.id.mSerTotalValtv);
                            String serTotal=(textView.getText().toString());
                            generailServiceDetailsArrayList.get(position).setmTotalVal(serTotal);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.mSerRemarkstv:
                        try {
                            editText=(EditText)view.findViewById(R.id.mSerRemarkstv);
                            String remarks=editText.getText().toString();
                            generailServiceDetailsArrayList.get(position).setmTotalVal(remarks);
                            generalServiceEstimationAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.IdDeleteIconImg:
                        try {
                            CustomDailog("Spare Part Estimation", "Do You Want to Delete Details?", 39, "Delete", position);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                }
            }
        });
    }

    private int getItemExistencyJ(String subServiceId) {
        try {
            for (int i = 0; i < generailServiceDetailsArrayList.size(); i++) {
                if (generailServiceDetailsArrayList.get(i).getSubServiceId().equalsIgnoreCase(subServiceId)) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String CalculateToTalValue(String getmQty, String getmMrp) {
        float finalValue=0.0f;
        try {
            float mrp = 0.0f, Qty = 0.0f;
            mrp = Float.valueOf(Float.parseFloat(getmMrp));
            Qty = Float.valueOf(Float.parseFloat(getmQty));
            finalValue = mrp * Qty;

        }catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(finalValue);

    }

 /*   private void CalculateToTalValue(float mqty, float mservicecharge) {
        float mrp=0.0f,Qty=0.0f;

        mrp=Float.valueOf(Float.parseFloat(getmMrp));
        Qty=Float.valueOf(Float.parseFloat(getmQty));
        float finalValue=mrp*Qty;
        return finalValue;
    }*/

    private void CalculateGeneralServiceTotal() {
        try {
            TotalGrossAmt = "";
            TotalQtyValue = "";
            int TotalRows = 0;

            totalAmount = 0.0f;
            totalFreeAmount=0.0f;
            totalQty = 0.0f;
            int finalrows = 0;
            float finalamt = 0.0f;
            for (int i = 0; i < generailServiceDetailsArrayList.size(); i++) {
                finalrows++;
                TotalGrossAmt = generailServiceDetailsArrayList.get(i).getServiceCharge();
                TotalQtyValue = generailServiceDetailsArrayList.get(i).getmQty();
                if(generailServiceDetailsArrayList.get(i).getmIssueType().equalsIgnoreCase("Free")) {
                    totalFreeAmount += Float.parseFloat(TotalQtyValue) * Float.parseFloat(generailServiceDetailsArrayList.get(i).getmTotalVal());
                }else {
                    finalamt = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalGrossAmt);
                    if (TotalQtyValue.length() != 0) {

                        totalQty += Float.valueOf(TotalQtyValue);
                    }
                    if (TotalGrossAmt.length() != 0) {
                        totalAmount += Float.valueOf(finalamt);
                    }
                }
            }

            GenSerTotRows.setText(String.valueOf(finalrows));
            GenSerTotQty.setText(String.format("%.0f", totalQty));
            GenSerTotAmt.setText(String.format("%.2f", totalAmount));
            GenSerFreeAmount.setText(String.format("%.2f", totalFreeAmount));
            finalCalculateData();
        }catch (Exception e){
        }
    }

    private void DeleteDetails() {
        try {
            sparePartEstDetailsArrayList.remove(position);
            CalculationPart();
            sparepArtEstimationAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void EditData() {
        try {
            SparePartEstDetails SI = sparePartEstDetailsArrayList.get(position);
            SparePartEdt.setText(SI.getmSparePartName());
            SparePartEdt.setTag(SI.getmSparePartID());
            QtyEdt.setText(SI.getmQty());
            IdmrpEdt.setText(SI.getmMrp());
            UomTv.setText(SI.getmUOM());
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
            IdmrpEdt.setText(SparePartMRP);
            AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.menu_header, menu);
        try {
            getActivity().getMenuInflater().inflate(R.menu.menu_header, menu);
            super.onCreateOptionsMenu(menu, inflater);
            menu.findItem(R.id.datetv).setTitle("");
            menu.findItem(R.id.datebtn).setIcon(0);
            menu.findItem(R.id.datebtn).setEnabled(false);
            menu.findItem(R.id.datetv).setEnabled(false);
//            menu.findItem(R.id.alltv).setTitle("");
            menu.findItem(R.id.datetv).setEnabled(false);
            MenuItem pinMenuItem = menu.findItem(R.id.action_Add);
            MenuItem printer = menu.findItem(R.id.action_printer);
            pinMenuItem.setVisible(false);
            printer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
//                GetCompanyDetailsToPrint(); //testing purpose added here
//                PreparePrintList();
                if(Validate()) {
                    try {
                        CustomDailog("Spare Part Estimation", "Do You Want to Save Details?", 38, "SAVE", 0);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
                return true;

            case R.id.action_printer_image:
                try {
                    CustomDailog("Spare Part Estimation", "Do You Want to Print Details?", 99, "PRINT", 0);
                } catch (Exception e) {
                    e.printStackTrace();

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void Print_BillFormat(String GinvNo) {
        try {
//            PreparePrintList();
            GetCompanyDetailsToPrint();
            PrintEpson_fM30_Printer();
            /*if (printerName == Enums.Printers.EPSON.toString()) {
                PrintEpson_fM30_Printer();
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                Date currentdate = new Date();
                TodayDate = (df.format(currentdate));
                print(TodayDate);
            }else if (printerName == Enums.Printers.EPSON_M30.toString()) {
                PrintEpson_fM30_Printer();
              *//*  DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                Date currentdate = new Date();
                String TodayDate = (df.format(currentdate));
                print(TodayDate);*//*
            }*//* else if (ProjectMethods.getBillPrinterType() == Enums.Printers.MAESTROS) {
                PrintMaestrosPrinter();
            } else if (ProjectMethods.getBillPrinterType() == Enums.Printers.SCANTECH) {
                PrintScantechPrinter();
            } else if (ProjectMethods.getBillPrinterType() == Enums.Printers.SUNMI_28_COLUMN || ProjectVariables.getBillPrinterType() == Enums.Printers.SUNMI_42_COLUMN) {
                PrintSunmiPrinter();
            }*/
            if (GinvNo != null) {
 //                showDialog(WholeSaleBilling.this, getResources().getString(R.string.bill_saved_success_txt) + GinvNo, true);
                GinvNo = null;
//                ProjectMethodes.DeleteFolder(WholeSaleBilling.this);
//                ProjectMethodes.BackupDatabase(WholeSaleBilling.this);
            } else {
 //                showDialog(WholeSaleBilling.this, getResources().getString(R.string.bill_saved_failed_txt), false);
                GinvNo = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void Print_BillFormat(String GinvNo) {
        try {
            PreparePrintList();
            if (ProjectMethods.getBillPrinterType() == Enums.Printers.EPSON) {
                print(GinvNo);
            }else if (ProjectMethods.getBillPrinterType() == Enums.Printers.EPSON_M30 ||
                    ProjectMethods.getBillPrinterType() == Enums.Printers.EPSON_48) {
                PrintEpson_fM30_Printer();
            }
            else if (ProjectMethods.getBillPrinterType() == Enums.Printers.MAESTROS) {
                //PrintMaestrosPrinter();
            } else if (ProjectMethods.getBillPrinterType() == Enums.Printers.SCANTECH) {

            } else if (ProjectMethods.getBillPrinterType() == Enums.Printers.SUNMI_28_COLUMN || ProjectMethods.getBillPrinterType() == Enums.Printers.SUNMI_42_COLUMN) {
                // PrintSunmiPrinter();
            }
            if (GinvNo != null) {
                //showDialog(getActivity(), getResources().getString(R.string.bill_saved_success_txt) + GinvNo, true);
                GinvNo = null;
              *//*  ProjectMethodes.DeleteFolder(getActivity());
                ProjectMethodes.BackupDatabase(getActivity());*//*
            } else {
                //  showDialog(getActivity(), getResources().getString(R.string.bill_saved_failed_txt), false);
                GinvNo = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
    private void PrintEpson_fM30_Printer(String _GinvNo) {
        try {
            Log.v("Result3", "Now Trying to connect");
            String boldText="";
            if (!Connect_Epson_M_30_Printer()) {
                finalizeObject();
                return;
            }

            mPrinter.clearCommandBuffer();
            mPrinter.setReceiveEventListener(null);

            for (int PrintLine = 0; PrintLine < printerList.size(); PrintLine++) {
                String strPrintLine = printerList.get(PrintLine);
                if (strPrintLine.equalsIgnoreCase("CompName")) {
                    strPrintLine = printerList.get(PrintLine + 1);
                    PrintLine++;
                    nFontStyle |= Cmd.Constant.FONTSTYLE_BOLD;
                    boldText = boldText + printerList.get(PrintLine) + "\r\n";
                    Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.myaccounts600x135);
                    mPrinter.addImage(logoData, 0, 0,
                            logoData.getWidth(),
                            logoData.getHeight(),
                            Printer.COLOR_1,
                            Printer.MODE_MONO,
                            Printer.HALFTONE_DITHER,
                            Printer.PARAM_DEFAULT,
                            Printer.COMPRESS_AUTO);
                    PrintLine++;
                    PrintLine++;
                }
                mPrinter.addText(strPrintLine);
                mPrinter.addFeedLine(1);
            }
//            PrintScantechPrinter(boldText, true);
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            /*mPrinter.addBarcode(_GinvNo,
                    Printer.BARCODE_CODE39,
                    Printer.HRI_BELOW,
                    Printer.FONT_A,
                    2,
                    100);*/
            mPrinter.addCut(Printer.CUT_FEED);
            mPrinter.sendData(Printer.PARAM_DEFAULT);
            Log.v("Result3", "msg" + Printer.PARAM_DEFAULT);
//            printBarcode()
            After_Epson_M_30_Print();
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("PrintEpson_fM30_Printer", "QuickBilling");
        }
    }



    private void print(String systemDate) {
        new GenerateReceiptTask(systemDate).execute();
    }

    private void PrintEpson_fM30_Printer() {
        try {
            Log.v("Result3", "Now Trying to connect");
//            if (!Connect_Epson_M_30_Printer()) {
//                finalizeObject();
//                return;
//            }

            mPrinter = new Printer(Printer.TM_M30, 0, context);
//            mPrinter.connect("BT:00:01:90:84:FD:9", Printer.PARAM_DEFAULT);
//                        mPrinter.connect("BT:00:01:90:77:25:00", Printer.PARAM_DEFAULT);
            mPrinter.connect(printerIp, Printer.PARAM_DEFAULT);
            mPrinter.clearCommandBuffer();
            mPrinter.setReceiveEventListener(null);

            for (int PrintLine = 0; PrintLine < printerList.size(); PrintLine++) {
                String strPrintLine = printerList.get(PrintLine);
                if (strPrintLine.equalsIgnoreCase("CompName")) {
                    Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.myaccounts600x135);
                    mPrinter.addImage(logoData, 0, 0,
                            logoData.getWidth(),
                            logoData.getHeight(),
                            Printer.COLOR_1,
                            Printer.MODE_MONO,
                            Printer.HALFTONE_DITHER,
                            Printer.PARAM_DEFAULT,
                            Printer.COMPRESS_AUTO);
                    PrintLine++;
                    PrintLine++;
                }
                mPrinter.addText(strPrintLine);
                mPrinter.addFeedLine(1);
            }
            mPrinter.addCut(Printer.CUT_FEED);
            mPrinter.sendData(Printer.PARAM_DEFAULT);
            Log.v("Result3", "msg" + Printer.PARAM_DEFAULT);

            After_Epson_M_30_Print();
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("PrintEpson_fM30_Printer", "QuickBilling");
        }
    }

    private void finalizeObject() {
        try {
            if (mPrinter == null) {
                return;
            }
            mPrinter.clearCommandBuffer();
            mPrinter.setReceiveEventListener(null);
            mPrinter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Connect_Epson_M_30_Printer()
    {
        try {
            mPrinter = new Printer(Printer.TM_M30, 0, context);
            mPrinter.connect(ProjectMethods.get_BillPrinterIP(), Printer.PARAM_DEFAULT);
            mPrinter.beginTransaction();

        } catch (Exception e) {

            return false;
        }
        mPrinter.setReceiveEventListener(this);
        return true;
        /*boolean Result = false;
        try {
            try {
                mPrinter = new Printer(Printer.TM_M30, 0, context);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                mPrinter.setReceiveEventListener(null);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                mPrinter.connect(ProjectMethods.get_BillPrinterIP(), com.epson.epos2.printer.Printer.PARAM_DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;*/
    }

    private boolean After_Epson_M_30_Print() {
        boolean Result = false;
        try {
            Log.v("Result3", "end connection");
            boolean isBeginTransaction = false;
            try {
                mPrinter.beginTransaction();
                isBeginTransaction = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("After_Epson_M_30_Print", "PrinterTransaction");
            }
            if (!isBeginTransaction) {
                try {
                    mPrinter.disconnect();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mPrinter.endTransaction();
            mPrinter.disconnect();

//            PrinterStatusInfo status = mPrinter.getStatus();
//            if (!isPrintable(status)) {
//                ShowMsg.showMsg(makeErrorMessage(status), QuickBilling.this);
//                try {
//                    mPrinter.disconnect();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                return false;
//            }
//
//            try {
//                mPrinter.sendData(Printer.PARAM_DEFAULT);
//            } catch (Exception e) {
//                ShowMsg.showException(e, "sendData", QuickBilling.this);
//                try {
//                    mPrinter.disconnect();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                return true;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("After_Epson_M_30_Print", "");
            return false;
        }
        return true;
    }

    private void PreparePrintList() {
        try {
            printerList = new ArrayList<String>();
            int NoOfCols = 0;
            String BillPrintType = printerName;
            String CounrterValue = "";

            if (BillPrintType == Enums.Printers.SUNMI_28_COLUMN.toString()) {
                NoOfCols = 28;
            } else if (BillPrintType == Enums.Printers.MAESTROS.toString()) {
                NoOfCols = 40;
            } else if (BillPrintType == Enums.Printers.EPSON.toString()) {
                NoOfCols = 42;
            } else if (BillPrintType == Enums.Printers.EPSON_M30.toString()) {
                NoOfCols = 48;
            } else if (BillPrintType == Enums.Printers.SUNMI_42_COLUMN.toString()) {
                NoOfCols = 42;
            } else if (BillPrintType == Enums.Printers.SCANTECH.toString()) {
                NoOfCols = 48;
            } else {
                NoOfCols = 48;
            }
//            printerList.add("CompName");
            for (int j = 0; j < companyDetailsArrayList.size(); j++) {
                if (companyDetailsArrayList.get(j).getCompanyName() != null) {
                    printerList.add(AddSpace(companyDetailsArrayList.get(j).getCompanyName(), NoOfCols, Enums.AlignAt.Middle));

                }
                if (companyDetailsArrayList.get(j).getVATRegNo() != null) {
                    printerList.add(AddSpace(" GSTNO : " + companyDetailsArrayList.get(j).getVATRegNo(), NoOfCols, Enums.AlignAt.Middle));
                }
                if (companyDetailsArrayList.get(j).getCompCity() != null && companyDetailsArrayList.get(j).getZip() != null) {
                    printerList.add(AddSpace(" "+companyDetailsArrayList.get(j).getCompCity()+" - "+ companyDetailsArrayList.get(j).getZip()+" ", NoOfCols, Enums.AlignAt.Middle));
                }
                if (companyDetailsArrayList.get(j).getCompPlace() != null) {
                    printerList.add(AddSpace(" "+companyDetailsArrayList.get(j).getCompPlace(), NoOfCols, Enums.AlignAt.Middle));
                }
                if (companyDetailsArrayList.get(j).getCompState() != null && companyDetailsArrayList.get(j).getCountry() != null) {
                    printerList.add(AddSpace("State: "+companyDetailsArrayList.get(j).getCompState()+" Country: "+companyDetailsArrayList.get(j).getCountry(), NoOfCols, Enums.AlignAt.Middle));
                }
                if (companyDetailsArrayList.get(j).getPhone1() != null) {
                    printerList.add(AddSpace("Landline: "+companyDetailsArrayList.get(j).getPhone1(), NoOfCols, Enums.AlignAt.Middle));
                }
                if (companyDetailsArrayList.get(j).getPhone2() != null) {
                    printerList.add(AddSpace("Phone: "+companyDetailsArrayList.get(j).getPhone2(), NoOfCols, Enums.AlignAt.Middle));
                }


            }
//            printerList.add(AddSpace("MRF Tyres And Service", NoOfCols, Enums.AlignAt.Middle));
//            printerList.add(AddSpace(" VIJAYA TYRES ",NoOfCols, Enums.AlignAt.Middle));
//            printerList.add(AddSpace(" UNDI ROAD, Near BYPASS " ,NoOfCols,Enums.AlignAt.Middle));
//            printerList.add(AddSpace(" GSTNO : "+"37ACOPV7275N1ZD",NoOfCols,Enums.AlignAt.Middle));
//            printerList.add(AddSpace(" BHIMAVARAM- 534202 ", NoOfCols, Enums.AlignAt.Middle));
//            printerList.add(AddSpace("Phone: 8816 250 617", NoOfCols, Enums.AlignAt.Middle));
            printerList.add(ProjectMethods.GetLine(NoOfCols));


            //************************************Header Part2 Start**************************************************
            int intExtraCols = 0;
            if (NoOfCols > 40) {
                intExtraCols = NoOfCols - 40;
            }
            //  mCustomerName="";mCustomerID="";mJobCardNo=""; EstimationDate="";mMobileNo="";
            String Space = AddSpace(" ", intExtraCols, Enums.AlignAt.Left);
            GregorianCalendar gcalendar = new GregorianCalendar();
//            String time=gcalendar.get(Calendar.HOUR+":"+Calendar.MINUTE+":"+Calendar.SECOND);
            String hour=(gcalendar.get(Calendar.HOUR) + ":");
            String minute=(gcalendar.get(Calendar.MINUTE) + ":");
            String second=(gcalendar.get(Calendar.SECOND)+"");
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            Date currentdate = new Date();
            printDate = (df.format(currentdate));
            String BilldateandTime = AddSpace("Date: " +JCDate+","+JCTime, 50, Enums.AlignAt.Left);
            String VehicleNumber = AddSpace("Vehicle Regd  No:"+ printVehicleRegNumber, 50, Enums.AlignAt.Left);

            String CustName1 = AddSpace("Date: " +JCDate+","+JCTime, 40, Enums.AlignAt.Left);
            String MobNo1 = AddSpace("Vehicle Regd  No:"+ printVehicleRegNumber, 40, Enums.AlignAt.Left);
            String CustName = AddSpace("Name: " +printCustomerName, 40, Enums.AlignAt.Left);
            String MobNo = AddSpace("Phone No :" + printPhoneNumber, 24, Enums.AlignAt.Left);
            String VehicleModel=AddSpace("Vehicle Model:"+ModelNo,40,Enums.AlignAt.Left);
            String InvNumber=AddSpace("Invoice Number:"+jobcardId,40,Enums.AlignAt.Left);
            /*printerList.add(ginvNo_A_Value + Space + BillDate);
            printerList.add(CustName + Space + MobNo);*/
            printerList.add(CustName1);
            printerList.add(InvNumber);
            printerList.add(MobNo1);
            printerList.add(VehicleModel);
            printerList.add(CustName);
            printerList.add(MobNo);


            printerList.add(ProjectMethods.GetLine(NoOfCols));
            //************************************Body Part Start**************************************************
            int NextLineSpace = 0;
            int SnoLen = 2;
            int NameLen = 14 + intExtraCols;
            int QtyLen = 5;
            int UOMLen = 0;
            int MRPLen = 5;
            int AmtLen = 10;

            SnoLen = 0;
            UOMLen = 3;
            NameLen = NameLen - 1;

            if (NoOfCols < 40) {
                NameLen = NoOfCols - SnoLen - 2;
                NextLineSpace = NoOfCols - QtyLen - UOMLen - MRPLen - AmtLen - 1;
            }

            String strNextLineSpace = AddSpace("", NextLineSpace, Enums.AlignAt.Right);

//            String HeaderSNo = AddSpace("SN", SnoLen, Enums.AlignAt.Left);
            String HeaderItemName = AddSpace("Service Name", NameLen, Enums.AlignAt.Left) + " ";
            String HeaderQty = AddSpace("Qty", QtyLen, Enums.AlignAt.Left) +" ";
//            String HeaderUOM = AddSpace("Rate", UOMLen, Enums.AlignAt.Right);
            String HeaderMRP = AddSpace("Rate", MRPLen, Enums.AlignAt.Middle);
            String HeaderAmount = AddSpace("Total", AmtLen, Enums.AlignAt.Right);
            // String HeaderAmount = AddSpace("Value(AED)", AmtLen, Enums.AlignAt.Right);

          /*  if (SnoLen > 0) {
                HeaderSNo += " ";
            }
            if (UOMLen > 0) {
                HeaderUOM += " ";
            }*/
//            String ServiceList = AddSpace("Service Done:", 22, Enums.AlignAt.Left);
//            String ServicesNames=AddSpace(,18,Enums.AlignAt.Left);
//            printerList.add(ServiceList);
            //************************************Body Part Start**************************************************
//            printerList.add(ProjectMethods.GetLine(ServiceList.length()));
            String ServicesList = AddSpace("Services Details:", 22, Enums.AlignAt.Left);
            printerList.add(ServicesList);
            printerList.add(ProjectMethods.GetLine(NoOfCols));
            SnoLen = 0;
            UOMLen = 3;
            NameLen = NameLen - 1;

            if (NoOfCols < 40) {
                NameLen = NoOfCols - SnoLen - 2;
                NextLineSpace = NoOfCols - QtyLen - UOMLen - MRPLen - AmtLen - 1;
            }
            printerList.add(HeaderItemName +HeaderQty + HeaderMRP + HeaderAmount);
            /*if (UOMLen > 0) {
                HeaderUOM += " ";
            }*/
//            printerList.add(HeaderSNo + HeaderItemName + HeaderQty + HeaderMRP + HeaderAmount);
//            printerList.add(HeaderSNo + HeaderItemName + strNextLineSpace + HeaderQty +  HeaderMRP + HeaderAmount);
//            printerList.add(ProjectMethods.GetLine(NoOfCols));
//            String SParePartList = AddSpace("SparePartList List:" +mJobCardNo, 22, Enums.AlignAt.Left);
//            printerList.add(SParePartList);
            for (int i = 0; i < generailServiceDetailsArrayList.size(); i++) {
                String BodyItemName = AddSpace(generailServiceDetailsArrayList.get(i).getSubServiceName(), NameLen, Enums.AlignAt.Left) + " ";
                String BodyQty = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getmQty()), QtyLen, Enums.AlignAt.Left) ;
                String BodyMRP = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getServiceCharge()), MRPLen, Enums.AlignAt.Middle)+" ";
                String BodyAmount;
                float totalVal=0.00f;
                if(generailServiceDetailsArrayList.get(i).getmIssueType().equals("Paid")) {
                    totalVal=Float.parseFloat(generailServiceDetailsArrayList.get(i).getmTotalVal());
                    BodyAmount = AddSpace(String.format("%.2f", totalVal), AmtLen, Enums.AlignAt.Right);
                }else
                    BodyAmount="Free";

            /*for (int i = 0; i < generailServiceDetailsArrayList.size(); i++) {
                String BodyItemName = AddSpace(generailServiceDetailsArrayList.get(i).getSubServiceName(), NameLen, Enums.AlignAt.Left) + " ";
                String BodyQty = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getmQty()), QtyLen, Enums.AlignAt.Right) + " ";
                String BodyMRP = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getServiceCharge()), 10, Enums.AlignAt.Right) + " ";
                String BodyAmount;
                float totalVal=0.00f;
                if(generailServiceDetailsArrayList.get(i).getmIssueType().equals("Paid")) {
                    totalVal=Float.parseFloat(generailServiceDetailsArrayList.get(i).getmTotalVal());
                    BodyAmount = AddSpace(String.format("%.2f", totalVal), 10, Enums.AlignAt.Right);
                }else
                    BodyAmount="Free";*/

                printerList.add(BodyItemName +  BodyQty + BodyMRP + BodyAmount);
//                String BodySNo = AddSpace(String.valueOf(i + 1), SnoLen, Enums.AlignAt.Right);
//                String strName = AddSpace(generailServiceDetailsArrayList.get(i).getSubServiceName(),40, Enums.AlignAt.Left);
//                String BodyItemName = AddSpace(strName, NameLen, Enums.AlignAt.Left) + " ";
//                String BodyQty = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getmQty()), QtyLen, Enums.AlignAt.Right) + " ";
                // String BodyUOM = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getUomName()), UOMLen, Enums.AlignAt.Right);
//                String BodyMRP = AddSpace(generailServiceDetailsArrayList.get(i).getServiceCharge(), MRPLen, Enums.AlignAt.Right) + " ";


//                String BodyAmount = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getmTotalVal()), AmtLen, Enums.AlignAt.Right);
                /*if (SnoLen > 0) {
                    BodySNo += " ";
                }*/
               /* if (UOMLen > 0) {
                    BodyUOM += " ";
                }*/

//                printerList.add(BodySNo + BodyItemName + BodyQty + BodyMRP + BodyAmount);


                //  *****************To Print Item Name In Two Lines with alignment dont delete****************
                if (BodyItemName.length() > NameLen) {
                    BodyItemName = BodyItemName.substring(NameLen, BodyItemName.length());
                    if (BodyItemName.length() > 2) {
                        //  PrintList_DailySalesSummary.add(AddSpace("", BodySNo.length(), Enums.AlignAt.Left) + AddSpace(strName, BodyItemName.length(), Enums.AlignAt.Left) + AddSpace("", BodyQty.length(), Enums.AlignAt.Left) + AddSpace("", BodyMRP.length(), Enums.AlignAt.Left) + AddSpace("", BodyAmount.length(), Enums.AlignAt.Left));
                    }
                }
            }
            String GenSerAmt="";
            if(!GenSerTotAmt.getText().toString().isEmpty()){
                GenSerAmt=(GenSerTotAmt.getText().toString());
            }
            if(!GenSerAmt.isEmpty()){
                String ServiceTotalText=(AddSpace("Service Total:", 14, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("         ", 9, Enums.AlignAt.Middle);
                String ServiceTotalValue=(AddSpace(GenSerAmt, 10, Enums.AlignAt.Right) + " ");
                printerList.add(ServiceTotalText +space+space1+ServiceTotalValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }

            String HeaderItemName1 = AddSpace("Spare Part", NameLen, Enums.AlignAt.Left) + " ";
            String HeaderQty1 = AddSpace("Qty", QtyLen, Enums.AlignAt.Left) + " ";
//            String HeaderUOM = AddSpace("Rate", UOMLen, Enums.AlignAt.Right);
            String HeaderMRP1 = AddSpace("Rate", MRPLen, Enums.AlignAt.Middle);
            String HeaderAmount1 = AddSpace("Total", AmtLen, Enums.AlignAt.Right);


            //************************************Body Part End**************************************************
            String SparesList = AddSpace("Spares Details:", 22, Enums.AlignAt.Left);
//            String ServicesNames=AddSpace(,18,Enums.AlignAt.Left);
            printerList.add(SparesList);
            printerList.add(ProjectMethods.GetLine(NoOfCols));
            String headline=HeaderItemName1 + HeaderQty1 + HeaderMRP1 + HeaderAmount1;
            float bodyMRPFloat=0.00f,bodyMRPToatlFloat=0.00f;
            for (int i = 0; i < sparePartEstDetailsArrayList.size(); i++) {
                if(i==0)
                    printerList.add(headline);
//                String BodySNo = AddSpace(String.valueOf(i + 1), SnoLen, Enums.AlignAt.Right);
                int nameLength=sparePartEstDetailsArrayList.get(i).getmSparePartName().length();
                String strName = AddSpace(sparePartEstDetailsArrayList.get(i).getmSparePartName(),NameLen,Enums.AlignAt.Left)+" ";
//                String BodyItemName = AddSpace(strName, NameLen, Enums.AlignAt.Left) + " ";
                String BodyQty = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getmQty()), QtyLen, Enums.AlignAt.Left);
                // String BodyUOM = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getUomName()), UOMLen, Enums.AlignAt.Right);
                bodyMRPFloat=Float.parseFloat(sparePartEstDetailsArrayList.get(i).getmMrp());
                String BodyMRP1 = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getmMrp()), MRPLen, Enums.AlignAt.Middle) + " ";//String.format("%.2f", sparePartEstDetailsArrayList.get(i).getmTotalValue()

                bodyMRPToatlFloat=Float.parseFloat(sparePartEstDetailsArrayList.get(i).getmTotalValue());
                String BodyMRP = AddSpace(String.format("%.2f", bodyMRPToatlFloat), AmtLen, Enums.AlignAt.Right);
                printerList.add(strName  + BodyQty + BodyMRP1 + BodyMRP);


                //  *****************To Print Item Name In Two Lines with alignment dont delete****************
                if (strName.length() > NameLen) {
                    strName = strName.substring(NameLen, strName.length());
                    if (strName.length() > 2) {
                        //  PrintList_DailySalesSummary.add(AddSpace("", BodySNo.length(), Enums.AlignAt.Left) + AddSpace(strName, BodyItemName.length(), Enums.AlignAt.Left) + AddSpace("", BodyQty.length(), Enums.AlignAt.Left) + AddSpace("", BodyMRP.length(), Enums.AlignAt.Left) + AddSpace("", BodyAmount.length(), Enums.AlignAt.Left));
                    }
                }
            }
            String GenSparesAmt="";
            if(!TotalAmtTv.getText().toString().isEmpty()){
                GenSparesAmt=(TotalAmtTv.getText().toString());
            }
            if(!GenSparesAmt.isEmpty()){
                String SparesTotalText=(AddSpace("Spares Total:", 15, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("         ", 9, Enums.AlignAt.Middle);
                String SparesTotalValue=(AddSpace(GenSparesAmt, 10, Enums.AlignAt.Right) + " ");
                printerList.add(SparesTotalText+space+space1+SparesTotalValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }//TotalAmtTv
//            printerList.add(ProjectMethods.GetLine(NoOfCols));
            String discountAmount=DiscountAmt.getText().toString();
            String netAmount=NetAmt.getText().toString();
            String grossAmount=finalAmtTv.getText().toString();
            if(!grossAmount.isEmpty()){
                String TotalText=(AddSpace("Total:", 10, Enums.AlignAt.Left));
                String space=AddSpace("         ", 9, Enums.AlignAt.Middle);
                String space1=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String TotalValue=(AddSpace(grossAmount, 15, Enums.AlignAt.Right)+" ");
                printerList.add(TotalText+space+space1+TotalValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }
            if(!discountAmount.isEmpty()) {
                String DiscountText=(AddSpace("Discount:", 10, Enums.AlignAt.Left));
                String space=AddSpace("         ", 9, Enums.AlignAt.Middle);
                String space1=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String DiscountValue=(AddSpace(discountAmount, 15, Enums.AlignAt.Right)+" ");
                printerList.add(DiscountText+space+space1+DiscountValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }

            if(!netAmount.isEmpty()){
                String NetAmountText=(AddSpace("Net Amount:", 12, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("         ", 9, Enums.AlignAt.Middle);
                String NetValue=(AddSpace(netAmount, 15, Enums.AlignAt.Right)+" ");
                printerList.add(NetAmountText+space+space1+NetValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }
            printerList.add(AddSpace("Mode of Payment:", 22, Enums.AlignAt.Left));
            printerList.add(ProjectMethods.GetLine(NoOfCols));
            if(!cashEdt.isEmpty()) {
                String cashText=(AddSpace("Cash:", 10, Enums.AlignAt.Left));//cardEdt="0",cashEdt="0",upiEdt="0"
                String cashValue=(AddSpace(cashEdt, 10, Enums.AlignAt.Left));//cardEdt="0",cashEdt="0",upiEdt="0"
                printerList.add(cashText+cashValue);
            }
            if(!cardEdt.isEmpty()) {
                String cardText=(AddSpace("Card:" , 10, Enums.AlignAt.Left));
                String cardValue=(AddSpace(cardEdt, 10, Enums.AlignAt.Left));
                printerList.add(cardText+cardValue);
            }
            if(!upiEdt.isEmpty()) {
                String upiText=(AddSpace("UPI:", 10, Enums.AlignAt.Left));
                String upiValue=(AddSpace(upiEdt, 10, Enums.AlignAt.Left));
                printerList.add(upiText+upiValue);
            }
            if(!invEdt.isEmpty()) {
                String invoiceText=(AddSpace("Invoice:", 10, Enums.AlignAt.Left));
                String invoiceValue=(AddSpace(invEdt, 10, Enums.AlignAt.Left));
                printerList.add(invoiceText+invoiceValue);

            }
            float taxableCalValue=0.00f;
            taxableCalValue=(Float.parseFloat(netAmount)*100)/118;
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            double finaltaxableAmt= Double.valueOf(twoDForm.format(taxableCalValue));
            String taxableText=(AddSpace("Taxable Amount:", 16, Enums.AlignAt.Left));
            String taxableValue=(AddSpace(String.valueOf(finaltaxableAmt), 10, Enums.AlignAt.Left));
            printerList.add(taxableText+taxableValue);

            float gstCalValue=0.0f;
            gstCalValue=((Float.parseFloat(netAmount))-taxableCalValue)/2;
            String gstText=(AddSpace("CGST 9%:", 13, Enums.AlignAt.Left));
            String gstTextValue=(AddSpace(String.valueOf(gstCalValue), 10, Enums.AlignAt.Left));
            printerList.add(gstText+gstTextValue);

            String sgstText=(AddSpace("SGST 9%:", 13, Enums.AlignAt.Left));
            String sgstTextValue=(AddSpace(String.valueOf(gstCalValue), 10, Enums.AlignAt.Left));
            printerList.add(sgstText+sgstTextValue);
            String RemarksStr="";
            if(!RemarksEdt.getText().toString().equals("Remarks"))
                RemarksStr=RemarksStr+RemarksEdt.getText().toString();
            for (int i = 0; i < estDetailsArrayList.size(); i++) {
                RemarksStr=estDetailsArrayList.get(i).getmRemarks();

            }
            if(!RemarksStr.isEmpty()){
                printerList.add(ProjectMethods.GetLine(NoOfCols));
                String remarksText=(AddSpace("Remarks :", 9, Enums.AlignAt.Left));
                String remarksValue=(AddSpace(RemarksStr, 50, Enums.AlignAt.Left));
                printerList.add(remarksText+remarksValue);
            }

            printerList.add(AddSpace("THANK YOU VISIT AGAIN", NoOfCols, Enums.AlignAt.Middle));
            printerList.add(" ");
            printerList.add(" ");
            /*Log.d("ANUSHA "," ****ServiceEstimationJobcard Print copy ");
            for(int i=0;i<printerList.size();i++){
                Log.d("ANUSHA "," **** "+printerList.get(i).toString());
            }*/
            //************************************Footer Part End**************************************************
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String AddSpace(String strString, int TargetLength, Enums.AlignAt At) {
        StringBuffer strResult = new StringBuffer("");
        try {
            strResult.append(ProjectMethods.AddSpace(strString, TargetLength, At));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult.toString();
    }
    private void PrepareSparePartEstList() {

        try {
            finalItemDetailList = "";
            PrepareItemDetails();
            PrepareGeneralServiceDetails();
            sparePartEstimationArrayList.clear();

            SaveDataToServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Validate() {
        boolean valid=false;
        if(sparePartEstDetailsArrayList.size()>0||generailServiceDetailsArrayList.size()>0){
            valid=true;
        }else{
            valid=false;
            Toast.makeText(context, "Please Select Either One Spare Part Or One Service", Toast.LENGTH_SHORT).show();
        }
        float cardEdtValue=0.00f,cashEdtValue=0.00f,upiEdtValue=0.00f,invEdtValue=0.00f;
        if(!cardEdt.isEmpty()) {
            cardEdtValue = Float.valueOf(cardEdt);
        }
        if(!cashEdt.isEmpty()){
            cashEdtValue=Float.valueOf(cashEdt);
        }
        if(!upiEdt.isEmpty())
            upiEdtValue=Float.valueOf(upiEdt);
        if(!invEdt.isEmpty())
            invEdtValue=Float.valueOf(invEdt);
        if(!NetAmt.getText().toString().isEmpty()) {
            totalAmtFromPayNow = cardEdtValue + cashEdtValue + upiEdtValue + invEdtValue;
            float NetAmountValue = 0.0F;
            if (!NetAmt.getText().toString().isEmpty())
                NetAmountValue = Float.parseFloat(NetAmt.getText().toString());
            Float totalVal = Float.valueOf(totalAmtFromPayNow);
            if (totalVal==NetAmountValue)
                valid= true;
            else {
                Intent payIntent = new Intent(getActivity(), PayNowActivity2016.class);
                payIntent.putExtra("Amount", NetAmt.getText().toString());
                payIntent.putExtra("Jobcard", JobCardNoEdt.getText().toString());
                if(payNowClick)
                    payIntent.putExtra("SavingValues", "TRUE");
                else
                    payIntent.putExtra("SavingValues", "FALSE");
                startActivityForResult(payIntent, 222);
                valid = false;
            }
        }
        return valid;
    }

    private void PrepareGeneralServiceDetails() {
        try {
            finalServiceDetails="";

            if(GeneralServiceEstimationAdapter.generailServiceDetailsArrayList.size()>0)
            {
                generailServiceDetailsArrayList=new ArrayList<>();

                generailServiceDetailsArrayList=GeneralServiceEstimationAdapter.generailServiceDetailsArrayList;

                for (int j = 0; j < generailServiceDetailsArrayList.size(); j++) {
                    if (generailServiceDetailsArrayList.get(j).getRowNo() > 0) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getRowNo() + "!";
                    }
                    if (generailServiceDetailsArrayList.get(j).getServiceName() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getServiceName() + "!";
                    }

                    if (generailServiceDetailsArrayList.get(j).getmQty() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getmQty() + "!";
                    }
                    if (generailServiceDetailsArrayList.get(j).getServiceCharge() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getServiceCharge() + "!";
                    }

                    if (generailServiceDetailsArrayList.get(j).getmTotalVal() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getmTotalVal() + "!";
                    }
                    if (generailServiceDetailsArrayList.get(j).getmRemarks() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getmRemarks() + "!";
                    }
                    if (generailServiceDetailsArrayList.get(j).getServiceId() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getServiceId()+"!";//SubTypes, ServiceType, SubServiceId
                    }
                    if (generailServiceDetailsArrayList.get(j).getmResult() != null) {
                        finalServiceDetails += "N/A" + "!";//subtypes
                    }
                    if (generailServiceDetailsArrayList.get(j).getmIssueType() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getmIssueType() + "!";//servicetype
                    }
                    if (generailServiceDetailsArrayList.get(j).getSubServiceId() != null) {
                        finalServiceDetails += generailServiceDetailsArrayList.get(j).getSubServiceId();//subserviceId
                    }
                    if (finalServiceDetails != "") {
                        finalServiceDetails = finalServiceDetails + "~";
                    } else {
                        finalServiceDetails = "";
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void PrepareItemDetails() {
        try {
            //sparePartEstDetailsArrayList = new ArrayList<>();

            sparePartEstDetailsArrayList=new ArrayList<>();

            sparePartEstDetailsArrayList=SparePrtEstimationAdapter.sparePartEstDetailsArrayList;

            for (int j = 0; j < sparePartEstDetailsArrayList.size(); j++)
            {

                Log.e(TAG, "PrepareItemDetails: " + sparePartEstDetailsArrayList.toString() + sparePartEstDetailsArrayList.size() );

                if (sparePartEstDetailsArrayList.get(j).getRowNo()>0) {
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getRowNo()+ "!";
                }
                if(sparePartEstDetailsArrayList.get(j).getmSparePartName()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmSparePartName()+ "!";
                }
                if(sparePartEstDetailsArrayList.get(j).getmUOM()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmUOM()+ "!";
                }
                if(sparePartEstDetailsArrayList.get(j).getmQty()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmQty()+ "!";
                }
                if(sparePartEstDetailsArrayList.get(j).getmMrp()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmMrp()+ "!";
                }

                if(sparePartEstDetailsArrayList.get(j).getmTotalValue()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmTotalValue()+ "!";
                }
                if(sparePartEstDetailsArrayList.get(j).getmSparePartID()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmSparePartID()+"!";
                }
                if(sparePartEstDetailsArrayList.get(j).getmUOMID()!=null){
                    finalItemDetailList += sparePartEstDetailsArrayList.get(j).getmUOMID()+"!";
                }
                finalItemDetailList += "SPE";

                if (finalItemDetailList != "") {
                    finalItemDetailList = finalItemDetailList + "~";
                } else {
                    finalItemDetailList = "";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SaveDataToServer() {
        try{

            estimationPrintListArrayList.clear();
            mCustomerName="";mCustomerID="";mJobCardNo=""; EstimationDate="";mMobileNo="";
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            EstimationDate=ReceiptDateEdt.getText().toString().trim();
            mMobileNo=MobileNoEdt.getText().toString().trim();
            String mRemarks=RemarksEdt.getText().toString().trim();
            mJobCardNo = JobCardNoEdt.getText().toString().trim();
            String mTotAmt=TotalAmtTv.getText().toString().trim();
            String mTotQty=TotalQtyTv.getText().toString().trim();
            String mTotQtyServ=GenSerTotQty.getText().toString().trim();
            int finalTotalQty=Integer.parseInt(mTotQty)+Integer.parseInt(mTotQtyServ);
            String DiscountType="A",DiscountAmtText="0",DiscountPerText="0";
            mCustomerName=CustNameEdt.getText().toString().trim();
            /*if(mCustomerName.length()>0){
                mCustomerID=CustomerNameEdt.getTag().toString().trim();
            }else{
                mCustomerID="";
            }*/
            EstimationPrintList estimationPrintList=new EstimationPrintList();
            estimationPrintList.setJobCardNo(mJobCardNo);
            estimationPrintList.setmDate(EstimationDate);
            estimationPrintList.setName(mCustomerName);
            estimationPrintList.setMobileNo(mMobileNo);
            estimationPrintList.setServiceList(finalServiceDetails);
            estimationPrintList.setSparePartList(finalItemDetailList);
            estimationPrintListArrayList.add(estimationPrintList);
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            Date currentdate = new Date();
            printDate = (df.format(currentdate));
            printPhoneNumber=mMobileNo;
//            printCustomerName=CustomerNameEdt.getText().toString().trim();
//            printVehicleModelNumber;
            printNetAmount=NetAmt.getText().toString();
            printCashAmount=cashEdt;
            printCardAmount=cardEdt;
            printUpiAmount=upiEdt;

            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("EstDate",ProjectMethods.GetCustomerCurrentTime());
                    jsonObject.accumulate("UserId", ProjectMethods.getUserId());
                    jsonObject.accumulate("MobileNo",MobileNoEdt.getText().toString());
                    jsonObject.accumulate("Remarks",RemarksEdt.getText().toString().trim());
                    jsonObject.accumulate("Type",selectedString);
                    jsonObject.accumulate("JobCardId",mJobCardNo);
                    jsonObject.accumulate("ItemDetails",finalItemDetailList);
                    jsonObject.accumulate("CustomerName",mCustomerName);
                    jsonObject.accumulate("CustomerID",mCustomerID);
                    jsonObject.accumulate("TotQty",finalTotalQty);
                    jsonObject.accumulate("TotAmount",finalAmtTv.getText().toString());
                    jsonObject.accumulate("ServiceDetails",finalServiceDetails);
                    jsonObject.accumulate("GrossAmount",finalAmtTv.getText().toString());
                    jsonObject.accumulate("NetAmount",NetAmt.getText().toString());
                    String discountAmtValue="";
                    if(!AmtEdt.getText().toString().isEmpty()){
                        discountAmtValue=AmtEdt.getText().toString();
                    }else{
                        discountAmtValue="0";
                    }
                    if(DiscountCheckBox.isChecked()) {
                        DiscountType = "P";
//                        DiscountAmtText="0";
                        DiscountAmtText=DiscountPercent.getText().toString();
                    }else{
                        DiscountType="A";
                        DiscountAmtText=DiscountAmt.getText().toString();
//                        DiscountPerText="0";

                    }

                    if(DiscountAmtText.equalsIgnoreCase(""))
                        DiscountAmtText="0";
                    if(DiscountPerText.equalsIgnoreCase(""))
                        DiscountPerText="0";
                    jsonObject.accumulate("DiscType",DiscountType);//a/p
                    jsonObject.accumulate("Discount",DiscountAmtText);
                    jsonObject.accumulate("DiscAmount",discountAmtValue);
                    jsonObject.accumulate("PayModeAmountCash",cashEdt);
                    jsonObject.accumulate("PayModeAmountCard",cardEdt);//900//1504
                    jsonObject.accumulate("PayModeAmountOnline",upiEdt);
                    jsonObject.accumulate("PayModeAmountInvoice",invEdt);
                    jsonObject.accumulate("PayModeAmountOther","0");
                    jsonObject.accumulate("PayModeAmount1","0");

                    Log.e("SparePartEstd ", " SparePartJSON_Obj " + jsonObject);
                    String TransactionNo="0";
                    Print_BillFormat(TransactionNo);
                    writeToFile(jsonObject.toString(),getContext(),JobCardNoEdt.getText().toString(),"estimate");
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "SaveSparePartEstiDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SaveSparePartEstiDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    //This is some error point to check
                    Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
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

    private void writeToFile(String data, Context context, String vNumber, String status) {
        try {

            long tsLong = System.currentTimeMillis()/1000;
            String ts = Long.toString(tsLong) +"_"+vNumber+ "_JSONSave.txt";
            String name=  status+"_"+vNumber+ "_JSON.txt";

            FileWriter out = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
            {
                out = new FileWriter(new File(Objects.requireNonNull(getContext()).getExternalFilesDir(null), name));
                out.write(data);
                out.close();
            }

            Toast.makeText(context,"Saved..",Toast.LENGTH_LONG).show();

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void JobInformation(Intent data) {
//        try {
        payNowClick=false;

        Log.e(TAG, "JobInformation: "  + data );

        String jobCardN = data.getStringExtra("JoBCardNo");
//        String jobCardN = "74E299230006";//"65E299210068";//74E299230006
        String CustName = data.getStringExtra("CustName");
        String CustMobNo = data.getStringExtra("CustMobNo");
        JCDate = data.getStringExtra("JCDate");
        JCTime=data.getStringExtra("JCTime");
        ModelNo=data.getStringExtra("ModelNo");
        VehicleNo = data.getStringExtra("VehicleNo");
        JCRemarks=data.getStringExtra("JCRemarks");
        printVehicleRegNumber=data.getStringExtra("VehicleNo");
        String TechnicianName = data.getStringExtra("TechnicianName");
        JobCardNoEdt.setText(jobCardN);
        CustNameEdt.setText(CustName);
        MobileNoEdt.setText(CustMobNo);
        ReceiptDateEdt.setText(JCDate);
        RemarksEdt.setText(JCRemarks);
        techEditText.setText(TechnicianName);
        printCustomerName=data.getStringExtra("CustName");
        printPhoneNumber=data.getStringExtra("CustMobNo");
//            savingState="FALSE";
        if(JobCardNoEdt.getText().toString()!=""){
            GetEditServiceDetails();

//                GetJoBCardRelatedServices();
                /*if (ediServiceDetailsArrayList == null || ediServiceDetailsArrayList.size() == 0) {
                    GetEditServiceDetails();
                } else {
                    AddListToGridService();
                }*/
        }


    }

    private void GetEstimationDetails() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId = JobCardNoEdt.getText().toString().trim();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobcardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetEstimationDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEstimationDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetJobCardDetails() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId = JobCardNoEdt.getText().toString().trim();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobcardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetJobCardDetailsReport";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailsReport, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetEditServiceDetails() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId = JobCardNoEdt.getText().toString().trim();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobcardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetServicesAgstJobCard";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditServiceDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetJoBCardRelatedServices() {
        try{
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId=JobCardNoEdt.getText().toString().trim();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JoBcardNo",jobcardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetNewJobCardSpeDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetNewJobCardSpeDetails, jsonObject, Request.Priority.HIGH);
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

    private void CustomerInformation(Intent data) {
        try {
            String custid = data.getStringExtra("CustomerId");
            String custname = data.getStringExtra("CustomerName");
            String MobileNo = data.getStringExtra("MobileNo");
            CustomerNameEdt.setText(custname);
            CustomerNameEdt.setTag(custid);
            MobileNoEdt.setText(MobileNo);
            if(custname==null){
                CustomerNameEdt.setText("");
                CustomerNameEdt.setTag("");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onPtrReceive(Printer printer, final int code, final PrinterStatusInfo status, String s) {
      /*  runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), getActivity());
//                dispPrinterWarnings(status);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });*/
    }

    private class OnServiceCallCompleteListenerSaveImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("SaveSparePartEstiDetails")) {
                try {
                    pDialog.dismiss();
                    handeSaveDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(requestName.equalsIgnoreCase("GetJobCardDetailsReport")){
                try {
                    pDialog.dismiss();
                    handeSaveDetails(jsonArray);
                    MainActivity.comingfrom="66";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(requestName.equalsIgnoreCase("GetEstimationDetails")){
                try {
                    pDialog.dismiss();
                    handeGetEstimationDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestName.equalsIgnoreCase("GetNewJobCardSpeDetails")) {
                try {
                    pDialog.dismiss();
                    handeServiceDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("GetServicesAgstJobCard")) {
                try {
                    //   mswiperefreshlayout.setRefreshing(false);
                    pDialog.dismiss();
                    handeEditServiceDetails(jsonArray);
                    CalculateGeneralServiceTotal();
//                    GetEstimationDetails();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("GetCompanyDetails")) {
                try {
                    pDialog.dismiss();
                    companyDetails(jsonArray);
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

    private void companyDetails(JSONArray jsonArray) {
        Log.e("handeEditServicecalled", "handeEditServiceDetails called" + jsonArray.length());
//        mswiperefreshlayout.setRefreshing(false);
        if (jsonArray.length() > 0) {
            if (companyDetailsArrayList == null) {
                companyDetailsArrayList = new ArrayList<>();
            }
            companyDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found")) {//excep
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.clear();
                    } else {
                        CompanyDetails documentTypes = new CompanyDetails();
                        documentTypes.setCompAddress(object.getString("CompAddress"));
                        documentTypes.setCompCity(object.getString("CompCity"));
                        documentTypes.setCompPlace(object.getString("CompPlace"));
                        documentTypes.setCompState(object.getString("CompState"));
                        documentTypes.setCompanyName(object.getString("CompanyName"));
                        documentTypes.setCountry(object.getString("Country"));
                        documentTypes.setEMail(object.getString("EMail"));
                        documentTypes.setPhone1(object.getString("Phone1"));
                        documentTypes.setPhone2(object.getString("Phone2"));
                        documentTypes.setVATRegNo(object.getString("VATRegNo"));
//                        documentTypes.setmSubServiceName(object.getString("SubServiceName"));
                        documentTypes.setZip(object.getString("Zip"));
                        companyDetailsArrayList.add(documentTypes);
                        PreparePrintList();

//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void handeGetEstimationDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            estDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Details Found") ||
                            Result.equalsIgnoreCase("No Records Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        estDetailsArrayList.clear();
                    } else {
                        EstDetails documentTypes = new EstDetails();
                        documentTypes.setmGrossAmount(object.getString("GrossAmount"));
                        documentTypes.setmNetAmount(object.getString("NetAmount"));
                        documentTypes.setmDiscType(object.getString("DiscType"));//a/p
                        documentTypes.setmDiscount(object.getString("Discount"));
                        documentTypes.setmDiscAmount(object.getString("DiscAmount"));
                        documentTypes.setmPayModeAmountCash(object.getString("PayModeAmountCash"));
                        documentTypes.setmPayModeAmountCard(object.getString("PayModeAmountCard"));
                        documentTypes.setmPayModeAmountOnline(object.getString("PayModeAmountOnline"));
                        documentTypes.setmPayModeAmountInvoice(object.getString("PayModeAmountInvoice"));
                        documentTypes.setmPayModeAmountOther(object.getString("PayModeAmountOther"));
                        documentTypes.setmPayModeAmount1(object.getString("PayModeAmount1"));
                        documentTypes.setmRemarks(object.getString("Remarks"));
                        estDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void handeServiceDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            sparePartEstDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found") ||
                            Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        sparePartEstDetailsArrayList.clear();
                    } else {
                        SparePartEstDetails documentTypes = new SparePartEstDetails();
                        documentTypes.setmSparePartName(object.getString("SPNAME"));
                        documentTypes.setmSparePartID(object.getString("SpareId"));
                        documentTypes.setmUOM(object.getString("UOMNAME"));
                        documentTypes.setmUOMID(object.getString("UOMID"));
                        documentTypes.setmQty(object.getString("QTY"));
                        documentTypes.setmMrp(object.getString("RATE"));
                        documentTypes.setmTotalValue(object.getString("Value"));
                        sparePartEstDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AddListToGrid();
            CalculationPart();
            GetEstimationDetails();
        }
    }

    private void handeSaveDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                try {
                    JSONObject object = jsonArray.getJSONObject(0);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found") ||
                            Result.equalsIgnoreCase("No Records Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                    }else{
                        String TransactionNo="0";
//                        Print_BillFormat(TransactionNo);

                        clearData();
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handeEditServiceDetails(JSONArray jsonArray) {
        Log.e("handeEditServicecalled", "handeEditServiceDetails called" + jsonArray.length());
//        mswiperefreshlayout.setRefreshing(false);
        if (jsonArray.length() > 0) {
            if (ediServiceDetailsArrayList == null) {
                ediServiceDetailsArrayList = new ArrayList<>();
            }
            ediServiceDetailsArrayList.clear();
            generailServiceDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                   //                    {"IssueType":"Paid","Qty":"1","Rate":"250","Remarks":"","Result":null,"SerStatus":"true","ServiceId":"SR10062","ServiceName":"Checkups","SlNo":"1","Status":"true","SubServiceId":"SRT10134","SubServiceName":"ALIGNMENT","TotValue":"250"}
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found")) {//excep
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.clear();
                        generailServiceDetailsArrayList.clear();
                    } else {
                        ServiceList documentTypes = new ServiceList();
                        documentTypes.setmIssueType(object.getString("IssueType"));
                        documentTypes.setmQty(object.getString("Qty"));
                        documentTypes.setServiceCharge(object.getString("Rate"));
                        documentTypes.setmRemarks(object.getString("Remarks"));
                        documentTypes.setmResult(object.getString("Result"));
                        documentTypes.setServiceId(object.getString("ServiceId"));
                        documentTypes.setServiceName(object.getString("ServiceName"));
                        documentTypes.setSubServiceName(object.getString("SubServiceName"));
                        documentTypes.setRowNo(Integer.parseInt(object.getString("SlNo")));
                        documentTypes.setSubServiceId(object.getString("SubServiceId"));
//                        documentTypes.setmSubServiceName(object.getString("SubServiceName"));
                        documentTypes.setmTotalVal(object.getString("TotValue"));
                        generailServiceDetailsArrayList.add(documentTypes);
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AddListToGridService();
            GetJoBCardRelatedServices();//exce
            //  GetServiceManListRelatedToServices();
        }
    }

    private void clearData() {
        try{
            sparePartEstDetailsArrayList.clear();
            sparePartEstimationArrayList.clear();
            generailServiceDetailsArrayList.clear();
            String custName="";
            RemarksEdt.setText("");
            JobCardNoEdt.setText("");
            MobileNoEdt.setText("");
            CustNameEdt.setText(custName);
            techEditText.setText("");
            TotalNoRowsTv.setText("0");
            finalAmtTv.setText("0.00");
            NetAmt.setText("0.00");
            TotalQtyTv.setText("0");
            TotalAmtTv.setText("0.00");
            GenSerFreeAmount.setText("0.00");
            GenSerTotRows.setText("0");
            GenSerTotQty.setText("0");
            GenSerTotAmt.setText("0.00");
            DiscountPercent.setText("");
            DiscountAmt.setText("");
            DiscountCheckBox.setChecked(false);
            IdmrpEdt.setText("");
            sparepArtEstimationAdapter.notifyDataSetChanged();
            generalServiceEstimationAdapter.notifyDataSetChanged();
            savingState="FALSE";
            focusOnView();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    private void focusOnView(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, JobCardNoEdt.getTop());
            }
        });
        Fragment frag = new SparePartEstAgstJobCard();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, frag).commit();
    }

    public class GenerateReceiptTask extends AsyncTask<Void, Void, Void> {
        private String ginvNo_A;

        public GenerateReceiptTask(String systemDate) {
            ginvNo_A = systemDate;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getResources().getString(R.string.please_wait_txt));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (initializePrinter.runPrintReceiptSequence("", printerList, ginvNo_A)) {
                printIsDone = true;
                if (printerList != null) {
                    printerList.clear();
                }
            } else {
                Log.i(TAG, "no print");
                printIsDone = false;
                printerSharedpreferences.edit().putString(InitializePrinter.TARGET, "").commit();
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();

        context=getActivity();
        MainActivity.comingfrom="MAINACTIVITY";
    }

    private void GetCompanyDetailsToPrint() {
        try {
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    jsonObject.accumulate("CompId", "C001");
                    requestName = "GetCompanyDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetCompanyDetails, jsonObject, Request.Priority.HIGH);
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
}
