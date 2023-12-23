package com.myaccounts.vechicleserviceapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.epson.epos2.printer.Printer;
import com.myaccounts.vechicleserviceapp.Activity.CancelJobCardDetails;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.ServiceStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Activity.SpareStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Fragments.SparePartEstAgstJobCard;
import com.myaccounts.vechicleserviceapp.Pojo.CompanyDetails;
import com.myaccounts.vechicleserviceapp.Pojo.EstDetails;
import com.myaccounts.vechicleserviceapp.Pojo.EstimationPrintList;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceList;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartEstDetails;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartEstimation;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.myaccounts.vechicleserviceapp.network.InfDbSpecs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class JobCardReportsAdapter extends RecyclerView.Adapter<JobCardReportsAdapter.ViewHolder> {
    private String TotalGrossAmt, TotalQtyValue;
    private float totalAmount=0.0f, totalQty,totalFreeAmount;
    private float finalAmt=0.0f;
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName, String screenName);
    }
    private ArrayList<ServiceList> generailServiceDetailsArrayList;
    private ArrayList<EstDetails> estDetailsArrayList;
    private ArrayList<SparePartEstDetails> sparePartEstDetailsArrayList;
    private ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList;
    private ArrayList<SparePartEstimation> sparePartEstimationArrayList;
    private ArrayList<EstimationPrintList> estimationPrintListArrayList;

    private ProgressDialog pDialog;
    private String jobcardIdValue,custName,custNo,vehicleNo,modleName,jcDate,jcRemarks,jcTime;
    private String requestName;
    private ArrayList<String> printerList;
    private Printer mPrinter = null;
    private String printerName, printerIp;
    Context context;
    ArrayList<JobCardDetails> jobCardDetailsArrayList;
    private int layout_rowitems;
    private OnItemClickListener mItemClickListener;
    SessionManager sessionManager;
    private PopupWindow mPopupWindow;
    private LinearLayout lr;
    TextView cancelby,canceldate,cancelfrom,canceltime,cancelreasons;
    private ArrayList<CompanyDetails> companyDetailsArrayList;
    private String tempvehicleRgdNo,tempname,tempmobileno;

    public JobCardReportsAdapter(ArrayList<JobCardDetails> items,OnItemClickListener listener){
        this.jobCardDetailsArrayList = items;
        this.mItemClickListener = listener;
    }

    public JobCardReportsAdapter(Context con, ArrayList<JobCardDetails> jobcardLis, int layout_rowi) {
        context = con;
        jobCardDetailsArrayList = jobcardLis;
        layout_rowitems = layout_rowi;
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View views;
        @BindView(R.id.IdSnotxt)
        TextView mSno;
        @BindView(R.id.IdjobCardNotxt)
        TextView mJobCardNo;
        @BindView(R.id.IdVehicleNotxt)
        TextView mVehicleNo;
        @BindView(R.id.IdNametxt)
        TextView mCustomerName;
        @BindView(R.id.IdMobileNotxt)
        TextView mCustomerMobNo;
        @BindView(R.id.IdStatustxt)
        ImageView mCustomerStatus;


        //@BindView(R.id.IdBlocktxt)
//        TextView IdBlocktxt;
        @BindView(R.id.JobCardRowLayout)
        LinearLayout JobCardRowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.views = itemView;
            lr = (LinearLayout) itemView.findViewById(R.id.lr);
            ButterKnife.bind(this, itemView);

           /* mJobCardNo.setSelected(true);
            mJobCardNo.setOnClickListener(this);*/
            //  JobCardRowLayout.setOnClickListener(this);
            sessionManager = new SessionManager(context);
        }
        public void bind(final JobCardDetails jobCardDetails,final OnItemClickListener mItemClickListener) {

            //  final JobCardDetails customerList = jobCardDetailsArrayList.get(position);
//                mSno.setText(jobCardDetails.getSLNO());
//                mJobCardNo.setText(jobCardDetails.getMake());
            estDetailsArrayList=new ArrayList<>();
            generailServiceDetailsArrayList = new ArrayList<>();
            sparePartEstDetailsArrayList = new ArrayList<>();
//                estDetailsArrayList=new ArrayList<>();
//                generailServiceDetailsArrayList = new ArrayList<>();
            sparePartEstimationArrayList = new ArrayList<>();
            estimationPrintListArrayList = new ArrayList<>();
            mJobCardNo.setText(jobCardDetails.getModel());
            mVehicleNo.setText(jobCardDetails.getVehicleNo());
            mCustomerName.setText(jobCardDetails.getCustomerName());
            mCustomerMobNo.setText(jobCardDetails.getMobileNo());
            //holder.mCustomerStatus.setText(customerList.getStatus());
//        holder.IdBlocktxt.setText(customerList.getBlock());
            companyDetailsArrayList = new ArrayList<>();

            Log.e("remarks", "remarks: " + jobCardDetails.getJobRemarks() );

            Log.e("JobCardAdapter", "name " + jobCardDetails.getCustomerName() + " status " + jobCardDetails.getStatus()  );

            if (jobCardDetails.getStatus().equalsIgnoreCase("Pending")) {
//                    mCustomerStatus.setText("Pending");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.red_color));
//                    mCustomerStatus.setTextColor(views.getResources().R.color.divider_color);
                mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.drawable.pending));

            } else if(jobCardDetails.getStatus().equalsIgnoreCase("Ready")){
//                    mCustomerStatus.setText("Ready");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.yellow_color));
                mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.mipmap.app_icon));

            }else if(jobCardDetails.getStatus().equalsIgnoreCase("Completed")){
//                    mCustomerStatus.setText("Completed");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.green_color));
                mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.drawable.completed));

            }
            else if(jobCardDetails.getStatus().equalsIgnoreCase("Canceled")){
//                    mCustomerStatus.setText("Completed");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.green_color));
                mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.drawable.cancelled));

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());


                    if (jobCardDetails.getStatus().equalsIgnoreCase("Completed"))
                    {
//                        jobcardIdValue="74E299230006";
//                            DatabaseHelper db = new DatabaseHelper(context);
//                            db.insert_PrinterDetails("BT:00:01:90:77:25:00","EPSON_30");
                        jobcardIdValue=jobCardDetails.getJobCardNo();
//                        jobcardIdValue="74E299100159";//68E299080057//64E298830122
//                        jobcardIdValue="65E299480001";
                        custNo=jobCardDetails.getMobileNo();
                        custName=jobCardDetails.getCustomerName();
                        vehicleNo=jobCardDetails.getVehicleNo();
                        modleName=jobCardDetails.getModel();
                        jcDate=jobCardDetails.getJCDate();
                        jcTime=jobCardDetails.getJCTime();
                        jcRemarks=jobCardDetails.getJobRemarks();
                        getServiceDetails(jobcardIdValue);


                    }
//                            Toast.makeText(context,"Service Completed For This Vehicle...",Toast.LENGTH_LONG).show();
                            /*try {
                                DatabaseHelper db = new DatabaseHelper(context);
                                db.insert_PrinterDetails("BT:00:90","");
                                showPopupForPrint();
//                                CustomDailog("Job Card Details", "Do You Want to Print the Data?", 33, "PRINT");
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }*/
                    else
                    {
                        showPopupForOptions();
                    }
                }
                private void showPopupForPrint(final String vehicleRgdNo,
                                               final String name, final String mobileno, final String modleName,
                                               final String InvNumber,final String jcDate,final String jcTime,final String jcRemarks){
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                    // Inflate the custom layout/view
                    final View customView = inflater.inflate(R.layout.action_popup_print,null);

                    mPopupWindow = new PopupWindow(
                            customView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            true);

                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow.setElevation(5.0f);
                    }

                    ImageButton closeButton = (ImageButton) customView.findViewById(R.id.btn_close);
                    final Button btn_cancel = (Button)customView.findViewById(R.id.btn_cancel);
                    final Button btn_ok = (Button)customView.findViewById(R.id.btn_ok);
                    // Set a click listener for the popup window close button
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                        }
                    });
                        /*mPopupWindow.setFocusable(false);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(false);*/
                    //mPopupWindow.setOutsideTouchable(false);
//                        mPopupWindow.setFocusable(true);
                    mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();                                try {
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
                            GetCompanyDetailsToPrint();
                            tempvehicleRgdNo = vehicleRgdNo;
                            tempname = name;
                            tempmobileno = mobileno;
//                            Print_BillFormat(vehicleRgdNo,name,mobileno,modleName,jobcardIdValue,jcDate,jcTime,jcRemarks);
                        }
                    });


                }
                private void showPopupForOptions() {

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                    // Inflate the custom layout/view
                    final View customView = inflater.inflate(R.layout.activity_popup,null);

                    mPopupWindow = new PopupWindow(
                            customView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            true);

                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow.setElevation(5.0f);
                    }

                    final LinearLayout layout_reasons = customView.findViewById(R.id.layout_reasons);
                    layout_reasons.setVisibility(View.GONE);
                    final LinearLayout layout_options = customView.findViewById(R.id.layout_options);
                    layout_options.setVisibility(View.VISIBLE);
                    // Get a reference for the custom view close button
                    final TextView titleText=(TextView)customView.findViewById(R.id.title);
                    final LinearLayout remarks_layout=(LinearLayout) customView.findViewById(R.id.remarks_layout);

                    final TextView remarks_in_dialog=(TextView)customView.findViewById(R.id.remarks_in_dialog);
                    ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                    final Button btn_edit_jobcard = (Button)customView.findViewById(R.id.btn_edit_jobcard);
                    final Button btn_edit_service_status = (Button)customView.findViewById(R.id.btn_edit_service_status);
                    final Button btn_edit_spare_status = (Button)customView.findViewById(R.id.btn_edit_spare_status);
                    final Button btn_cancle_jobCards = (Button)customView.findViewById(R.id.btn_cancel_jobcard);
                    final Button btn_cancellation_reason = (Button)customView.findViewById(R.id.btn_cancellation_reason);

                    cancelby = customView.findViewById(R.id.cancel_by);
                    canceldate = customView.findViewById(R.id.cancel_date);
                    cancelfrom = customView.findViewById(R.id.cancel_from);
                    canceltime = customView.findViewById(R.id.cancel_time);
                    cancelreasons = customView.findViewById(R.id.cancel_reasons);

                    if (jobCardDetails.getJobRemarks().equals("") || jobCardDetails.getJobRemarks() == null)
                    {
                        remarks_in_dialog.setVisibility(View.GONE);
                        remarks_layout.setVisibility(View.GONE);
                    }
                    else
                    {
                        remarks_in_dialog.setVisibility(View.VISIBLE);
                        remarks_layout.setVisibility(View.VISIBLE);
                        remarks_in_dialog.setText(jobCardDetails.getJobRemarks());
                    }

                    if(jobCardDetails.getStatus().equalsIgnoreCase("Pending"))
                    {
                        btn_cancle_jobCards.setVisibility(View.VISIBLE);
                    }
                    else {
                        btn_cancle_jobCards.setVisibility(View.GONE);
                    }

                    if (jobCardDetails.getStatus().equalsIgnoreCase("Canceled"))
                    {
                        btn_cancellation_reason.setVisibility(View.VISIBLE);
                    }
                    else {
                        btn_cancellation_reason.setVisibility(View.GONE);
                    }

                    // Set a click listener for the popup window close button
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                        }
                    });
                        /*mPopupWindow.setFocusable(false);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(false);*/
                    //mPopupWindow.setOutsideTouchable(false);
//                        mPopupWindow.setFocusable(true);
                    mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);

                    btn_cancellation_reason.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layout_options.setVisibility(View.GONE);
                            layout_reasons.setVisibility(View.VISIBLE);
                            requestCancellationReasonsApi(jobCardDetails.getJobCardNo());
                            Toast.makeText(context,"All Reasons are displayed here..", Toast.LENGTH_LONG).show();
                        }
                    });

                    btn_edit_jobcard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                            Intent intent = new Intent(context, NewJobCardDetailsMain.class);
                            sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                            intent.putExtra("jobcardNo", jobCardDetails.getJobCardNo());
                            intent.putExtra("technicianName", jobCardDetails.getTechnicianName());
                            context.startActivity(intent);
                        }
                    });

                    btn_edit_service_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                            Intent intent = new Intent (v.getContext(), ServiceStatusUpdateActivity.class);
                            sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                            v.getContext().startActivity(intent);

                        }
                    });

                    btn_edit_spare_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                            Intent intent = new Intent (v.getContext(), SpareStatusUpdateActivity.class);
                            sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(),jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                            v.getContext().startActivity(intent);

                        }
                    });//btn_cancel_jobcard

                    btn_cancle_jobCards.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                            try {
                                Intent intent = new Intent(context, CancelJobCardDetails.class);
                                sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                                intent.putExtra("jobcardNo", jobCardDetails.getJobCardNo());
                                context.startActivity(intent);
//                                    CustomDailog("Service Issue", "Do You Want to Cancle Jobcard?", 134, "Delete", position);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }

                private void requestCancellationReasonsApi(String jobcardNo) {

                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("JobCardId",jobcardNo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("jobcard_reports_adapter", "requestCancellationReasonsApi: " + jsonObject );

                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ProjectVariables.BASE_URL+ProjectVariables.GetCancelJobCardDetails, jsonObject, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {

                            for (int i = 0 ; i < jsonArray.length() ; i++)
                            {
                                try {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    Log.e("jobcard_reports_adapter", "obj: " + jsonObject1 );

                                    String reasons = jsonObject1.getString("ReasonforCancel");

                                    List<String> items = Arrays.asList(reasons.split("\\s*,\\s*"));

                                    Log.e("jobcard_reports_adapter", "obj: " + items.toString() );

                                    cancelby.setText(jsonObject1.getString("CancelBy"));
                                    canceldate.setText(jsonObject1.getString("CancelDate"));
                                    cancelfrom.setText(jsonObject1.getString("CancelFrom"));
                                    canceltime.setText(jsonObject1.getString("CancelTime"));

                                    String stringnewline = jsonObject1.getString("ReasonforCancel");
                                    stringnewline=stringnewline.replaceAll(", ", "\n\n");
                                    cancelreasons.setText("-> "+stringnewline);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                    requestQueue.add(jsonArrayRequest);

                }
            });

        }


        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition(), jobCardDetailsArrayList.get(getPosition()).getJobCardNo(), jobCardDetailsArrayList.get(getPosition()).getScreenName());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }



    }

    private void getServiceDetails(String jobCardNo) {
        try {
            pDialog = new ProgressDialog(context,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId = jobCardNo;
            if (AppUtil.isNetworkAvailable(context)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobcardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(context, false);
                    requestName = "GetServicesAgstJobCard";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditServiceDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(context, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class OnServiceCallCompleteListenerSaveImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            /*if (requestName.equalsIgnoreCase("SaveSparePartEstiDetails")) {
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else*/ if(requestName.equalsIgnoreCase("GetEstimationDetails")){
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
    private void Print_BillFormat(String vehicleRegNo,String name,String mobileNumber,String modleName,
                                  String InvNumber,String jcDate,String jcTime,String jcRemarks) {
        try {
            PreparePrintList(vehicleRegNo,name,mobileNumber,modleName,InvNumber,jcDate,jcTime,jcRemarks);
//            PreparePrintListTest();
            PrintEpson_fM30_Printer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void GetEstimationDetails() {
        try {
            pDialog = new ProgressDialog(context,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(context)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JobCardId", jobcardIdValue);
                    BackendServiceCall serviceCall = new BackendServiceCall(context, false);
                    requestName = "GetEstimationDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEstimationDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(context, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                        Toast.makeText(context, Result, Toast.LENGTH_SHORT).show();
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

//            PreparePrintList("vehicle","name","hsjdhsjdhjs","dhsjhd",jobcardIdValue);
            showPopupForPrint(vehicleNo,custName,custNo,
                    modleName,jobcardIdValue,jcDate,jcRemarks);
        }
    }

    private void showPopupForPrint(final String vehicleNo, final String custName, final String custNo, final String modleName, final String jobcardIdValue,final String jcDate,final String jcRemarks) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        final View customView = inflater.inflate(R.layout.action_popup_print,null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.btn_close);
        final Button btn_cancel = (Button)customView.findViewById(R.id.btn_cancel);
        final Button btn_ok = (Button)customView.findViewById(R.id.btn_ok);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });
                        /*mPopupWindow.setFocusable(false);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(false);*/
        //mPopupWindow.setOutsideTouchable(false);
//                        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
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
                GetCompanyDetailsToPrint();
                tempvehicleRgdNo = vehicleNo;
                tempname = custName;
                tempmobileno = custNo;
//                Print_BillFormat(vehicleNo,custName,custNo,modleName,jobcardIdValue,jcDate,jcTime,jcRemarks);
            }
        });
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
                        Toast.makeText(context, Result, Toast.LENGTH_SHORT).show();
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

//            AddListToGrid();
//            CalculationPart();
            GetEstimationDetails();
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
                        Toast.makeText(context, Result, Toast.LENGTH_SHORT).show();
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
//            AddListToGridService();
            GetJoBCardRelatedServices();//exce
            //  GetServiceManListRelatedToServices();
        }
    }
    private void GetJoBCardRelatedServices() {
        try{
            pDialog = new ProgressDialog(context,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId=jobcardIdValue;
            if (AppUtil.isNetworkAvailable(context)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("JoBcardNo",jobcardId);
                    BackendServiceCall serviceCall = new BackendServiceCall(context, false);
                    requestName = "GetNewJobCardSpeDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetNewJobCardSpeDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(context, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void PrintEpson_fM30_Printer() {
        try {
            android.util.Log.v("Result3", "Now Trying to connect");
//            if (!Connect_Epson_M_30_Printer()) {
//                finalizeObject();
//                return;
//            }
            mPrinter = new Printer(Printer.TM_M30, 0, context);
//            mPrinter.connect("BT:00:01:90:84:FD:9", Printer.PARAM_DEFAULT);
//            mPrinter.connect("BT:00:01:90:77:25:00", Printer.PARAM_DEFAULT);
            mPrinter.connect(printerIp, Printer.PARAM_DEFAULT);
            mPrinter.clearCommandBuffer();
            mPrinter.setReceiveEventListener(null);

            for (int PrintLine = 0; PrintLine < printerList.size(); PrintLine++) {
                String strPrintLine = printerList.get(PrintLine);
                if (strPrintLine.equalsIgnoreCase("CompName")) {
                    Bitmap logoData = BitmapFactory.decodeResource(context.getResources(), R.drawable.myaccounts600x135);
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
            android.util.Log.v("Result3", "msg" + Printer.PARAM_DEFAULT);

            After_Epson_M_30_Print();
        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.v("PrintEpson_fM30_Printer", "QuickBilling");
        }
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
    private void PreparePrintList(String vehicleRegNumber,String name,String mobileNo,String ModleName,
                                  String InvNumber,String jcDate,String jcTime,String jcRemarks) {
        try {
            printerList = new ArrayList<String>();
            int NoOfCols = 0;
            String CounrterValue = "";

            if (printerName == Enums.Printers.SUNMI_28_COLUMN.toString()) {
                NoOfCols = 28;
            } else if (printerName == Enums.Printers.MAESTROS.toString()) {
                NoOfCols = 40;
            } else if (printerName == Enums.Printers.EPSON.toString()) {
                NoOfCols = 42;
            } else if (printerName == Enums.Printers.EPSON_M30.toString()) {
                NoOfCols = 48;
            } else if (printerName == Enums.Printers.SUNMI_42_COLUMN.toString()) {
                NoOfCols = 42;
            } else if (printerName == Enums.Printers.SCANTECH.toString()) {
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
           /* printerList.add(AddSpace("MRF Tyres And Service", NoOfCols, Enums.AlignAt.Middle));
            printerList.add(AddSpace(" VIJAYA TYRES ",NoOfCols, Enums.AlignAt.Middle));
//            printerList.add(AddSpace(" UNDI ROAD, Near BYPASS " ,NoOfCols,Enums.AlignAt.Middle));
            printerList.add(AddSpace(" GSTNO : "+"37ACOPV7275N1ZD",NoOfCols,Enums.AlignAt.Middle));
            printerList.add(AddSpace(" BHIMAVARAM- 534202 ", NoOfCols, Enums.AlignAt.Middle));
            printerList.add(AddSpace("Phone: 8816 250 617", NoOfCols, Enums.AlignAt.Middle));*/
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
            String printDate = (df.format(currentdate));

            String BilldateandTime = AddSpace("Date: " +jcDate+","+hour+minute+second, 50, Enums.AlignAt.Left);
            String VehicleNumber = AddSpace("Vehicle Regd  No:"+ vehicleRegNumber, 50, Enums.AlignAt.Left);

            String CustName1 = AddSpace("Date: " +jcDate+","+jcTime, 40, Enums.AlignAt.Left);
            String MobNo1 = AddSpace("Vehicle Regd  No:"+ vehicleRegNumber, 40, Enums.AlignAt.Left);
            String CustName = AddSpace("Name: " +name, 40, Enums.AlignAt.Left);
            String MobNo = AddSpace("Phone No :" + mobileNo, 24, Enums.AlignAt.Left);
            String VehicleModel=AddSpace("Vehicle Model:"+ ModleName,40,Enums.AlignAt.Left);
            String invText=AddSpace("Invoice Number:"+ InvNumber,40,Enums.AlignAt.Left);
            /*printerList.add(ginvNo_A_Value + Space + BillDate);
            printerList.add(CustName + Space + MobNo);*/
            printerList.add(CustName1);
            printerList.add(invText);
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
            float totalVal=0.00f,totServices=0.00f;
            for (int i = 0; i < generailServiceDetailsArrayList.size(); i++) {
                String BodyItemName = AddSpace(generailServiceDetailsArrayList.get(i).getSubServiceName(), NameLen, Enums.AlignAt.Left) + "  ";
                String BodyQty = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getmQty()), QtyLen, Enums.AlignAt.Left) ;
                String BodyMRP = AddSpace(String.valueOf(generailServiceDetailsArrayList.get(i).getServiceCharge()), MRPLen, Enums.AlignAt.Middle)+" ";
                String BodyAmount;
                if(generailServiceDetailsArrayList.get(i).getmIssueType().equals("Paid")) {
                    totalVal=Float.parseFloat(generailServiceDetailsArrayList.get(i).getmTotalVal());//String.format("%.2f", totalVal)
                    BodyAmount = AddSpace(String.format("%.2f", totalVal), AmtLen, Enums.AlignAt.Right);
                    totServices+= Float.valueOf(totalVal);
                }else
                    BodyAmount="Free";

                printerList.add(BodyItemName  + BodyQty + BodyMRP + BodyAmount);
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
            String GenSerAmt=String.format("%.2f",totServices);
            /*if(!ser.getText().toString().isEmpty()){
                GenSerAmt=(GenSerTotAmt.getText().toString());
            }*/
            if(!GenSerAmt.isEmpty()){
                String ServiceTotalText=(AddSpace("Service Total:", 14, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("        ", 9, Enums.AlignAt.Middle);
                String ServiceTotalValue=(AddSpace(GenSerAmt, 10, Enums.AlignAt.Right) + " ");
                printerList.add(ServiceTotalText+space +space1+ServiceTotalValue);
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

            float totSpares=0.00f;
            float bodyMRPFloat=0.00f,bodyMRPToatlFloat=0.00f;
            for (int i = 0; i < sparePartEstDetailsArrayList.size(); i++) {
                if(i==0)
                    printerList.add(headline);
//                String BodySNo = AddSpace(String.valueOf(i + 1), SnoLen, Enums.AlignAt.Right);
                String strName = AddSpace(sparePartEstDetailsArrayList.get(i).getmSparePartName(),NameLen,Enums.AlignAt.Left)+" ";
//                String BodyItemName = AddSpace(strName, NameLen, Enums.AlignAt.Left) + " ";
                String BodyQty = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getmQty()), QtyLen, Enums.AlignAt.Left) ;//
                // String BodyUOM = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getUomName()), UOMLen, Enums.AlignAt.Right);
                bodyMRPFloat=Float.parseFloat(sparePartEstDetailsArrayList.get(i).getmMrp());
                String BodyMRP1 = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getmMrp()), MRPLen, Enums.AlignAt.Middle) + " ";//String.format("%.2f", sparePartEstDetailsArrayList.get(i).getmTotalValue()
                bodyMRPToatlFloat=Float.parseFloat(sparePartEstDetailsArrayList.get(i).getmTotalValue());
                String BodyMRP = AddSpace(String.format("%.2f", bodyMRPToatlFloat), AmtLen, Enums.AlignAt.Right);
                totSpares += bodyMRPToatlFloat;
//                printerList.add(strName+BodyMRP);
                printerList.add(strName  + BodyQty + BodyMRP1 + BodyMRP);

                //  *****************To Print Item Name In Two Lines with alignment dont delete****************
                if (strName.length() > NameLen) {
                    strName = strName.substring(NameLen, strName.length());
                    if (strName.length() > 2) {
                        //  PrintList_DailySalesSummary.add(AddSpace("", BodySNo.length(), Enums.AlignAt.Left) + AddSpace(strName, BodyItemName.length(), Enums.AlignAt.Left) + AddSpace("", BodyQty.length(), Enums.AlignAt.Left) + AddSpace("", BodyMRP.length(), Enums.AlignAt.Left) + AddSpace("", BodyAmount.length(), Enums.AlignAt.Left));
                    }
                }
            }
            String GenSparesAmt=String.format("%.2f",totSpares);
            /*if(!TotalAmtTv.getText().toString().isEmpty()){
                GenSparesAmt=(TotalAmtTv.getText().toString());
            }*/
            if(!GenSparesAmt.isEmpty()){
                String SparesTotalText=(AddSpace("Spares Total:", 15, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("        ", 9, Enums.AlignAt.Middle);
                String SparesTotalValue=(AddSpace(GenSparesAmt, 10, Enums.AlignAt.Right) + " ");
                printerList.add(SparesTotalText +space+space1+SparesTotalValue);
//                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }//TotalAmtTv
            printerList.add(ProjectMethods.GetLine(NoOfCols));
            String grossAmount="0.00",netAmount="",discount="",discountAmount="0.00",paymodeCash="",paymodeCard="",
                    PayModeAmountUpi="",PayModeAmountInvoice="",RemarksStr="";
            Float netAmountDecimal=0.00f,disDecimal=0.00f;
            netAmountDecimal=Float.parseFloat(GenSerAmt)+Float.parseFloat(GenSparesAmt);
            for (int i = 0; i < estDetailsArrayList.size(); i++) {
                grossAmount = estDetailsArrayList.get(i).getmGrossAmount();
                netAmountDecimal=Float.parseFloat(grossAmount);
                netAmount = estDetailsArrayList.get(i).getmNetAmount();
                discount = estDetailsArrayList.get(i).getmDiscount();
                discountAmount = estDetailsArrayList.get(i).getmDiscAmount();
                disDecimal = Float.parseFloat(discountAmount);
                paymodeCash = estDetailsArrayList.get(i).getmPayModeAmountCash();
                paymodeCard = estDetailsArrayList.get(i).getmPayModeAmountCard();
                PayModeAmountUpi = estDetailsArrayList.get(i).getmPayModeAmountOnline();
                PayModeAmountInvoice = estDetailsArrayList.get(i).getmPayModeAmountInvoice();
                RemarksStr=estDetailsArrayList.get(i).getmRemarks();

            }
            if(!grossAmount.isEmpty()){
                String TotalText=(AddSpace("Total:", 10, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("        ", 9, Enums.AlignAt.Middle);
                String TotalValue=(AddSpace(String.format("%.2f",Float.parseFloat(grossAmount)), 15, Enums.AlignAt.Right) + " ");
                printerList.add(TotalText+space+space1+TotalValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }
            if(!discountAmount.isEmpty()) {
                String DiscountText=(AddSpace("Discount:", 10, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("        ", 9, Enums.AlignAt.Middle);
                String DiscountValue=(AddSpace(String.format("%.2f",disDecimal), 15, Enums.AlignAt.Right) + " ");
                printerList.add(DiscountText+space+space1+DiscountValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }

            if(!grossAmount.isEmpty()){
                netAmountDecimal=netAmountDecimal-disDecimal;//74E299100159
                String NetAmountText=(AddSpace("Net Amount:", 12, Enums.AlignAt.Left));
                String space=AddSpace("         ", 10, Enums.AlignAt.Middle);
                String space1=AddSpace("        ", 9, Enums.AlignAt.Middle);
                String NetValue=(AddSpace(String.format("%.2f",netAmountDecimal), 15, Enums.AlignAt.Right) + " ");
                printerList.add(NetAmountText+space+space1+NetValue);
                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }
            printerList.add(AddSpace("Mode of Payment:", 22, Enums.AlignAt.Left));
            printerList.add(ProjectMethods.GetLine(NoOfCols));
            if(!paymodeCash.isEmpty()) {
                String cashText=(AddSpace("Cash:", 10, Enums.AlignAt.Left));//cardEdt="0",cashEdt="0",upiEdt="0"
                String cashValue=(AddSpace(paymodeCash, 10, Enums.AlignAt.Left));//cardEdt="0",cashEdt="0",upiEdt="0"
                printerList.add(cashText+cashValue);
            }
            if(!paymodeCard.isEmpty()) {
                String cardText=(AddSpace("Card:" , 10, Enums.AlignAt.Left));
                String cardValue=(AddSpace(paymodeCard, 10, Enums.AlignAt.Left));
                printerList.add(cardText+cardValue);
            }
            if(!PayModeAmountUpi.isEmpty()) {
                String upiText=(AddSpace("UPI:", 10, Enums.AlignAt.Left));
                String upiValue=(AddSpace(PayModeAmountUpi, 10, Enums.AlignAt.Left));
                printerList.add(upiText+upiValue);
            }
            if(!PayModeAmountInvoice.isEmpty()) {
                String invoiceText=(AddSpace("Invoice:", 10, Enums.AlignAt.Left));
                String invoiceValue=(AddSpace(PayModeAmountInvoice, 10, Enums.AlignAt.Left));
                printerList.add(invoiceText+invoiceValue);

            }
            float taxableCalValue=0.00f;
            if(netAmountDecimal!=0.00f)
//            taxableCalValue=((netAmountDecimal-disDecimal)*100)/118;
                taxableCalValue=(netAmountDecimal*100)/118;
            DecimalFormat twoDForm = new DecimalFormat("#.##");
           double finaltaxableAmt= Double.valueOf(twoDForm.format(taxableCalValue));
            String taxableText=(AddSpace("Taxable Amount:", 16, Enums.AlignAt.Left));
            String taxableValue=(AddSpace(String.valueOf(finaltaxableAmt), 10, Enums.AlignAt.Left));
            printerList.add(taxableText+taxableValue);

            float gstCalValue=0.00f;
            if(taxableCalValue!=0)
            gstCalValue=((netAmountDecimal)-taxableCalValue)/2;
            String gstText=(AddSpace("CGST 9%:", 13, Enums.AlignAt.Left));
            String gstTextValue=(AddSpace(String.valueOf(gstCalValue), MRPLen, Enums.AlignAt.Left));
            printerList.add(gstText+gstTextValue);

            String sgstText=(AddSpace("SGST 9%:", 13, Enums.AlignAt.Left));
            String sgstTextValue=(AddSpace(String.valueOf(gstCalValue), MRPLen, Enums.AlignAt.Left));
            printerList.add(sgstText+sgstTextValue);
            RemarksStr=RemarksStr+jcRemarks;
            if(!RemarksStr.isEmpty()){
                printerList.add(ProjectMethods.GetLine(NoOfCols));
                String remarksText=(AddSpace("Remarks :", 9, Enums.AlignAt.Left));
                String remarksValue=(AddSpace(RemarksStr, 50, Enums.AlignAt.Left));
                printerList.add(remarksText+remarksValue);
            }
            printerList.add(AddSpace("THANK YOU VISIT AGAIN", NoOfCols, Enums.AlignAt.Middle));
            printerList.add(" ");
            printerList.add(" ");
            /*Log.d("ANUSHA "," ****completed Print copy ");
            for(int i=0;i<printerList.size();i++){
                Log.d("ANUSHA "," ****  "+printerList.get(i).toString());
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
    private void PreparePrintListTest() {
        printerList = new ArrayList<String>();
        int NoOfCols = 48;
        String BillPrintType = ProjectMethods.getBillPrinterType().toString();
        printerList.add(" PRINTNAME ");
    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
        Log.d("clikedd", "clciked");
    }

    /*Action Bar Using SearchView Functionly*/
    public void setFilter(List<JobCardDetails> taskList) {
        jobCardDetailsArrayList = new ArrayList<>();
        jobCardDetailsArrayList.addAll(taskList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(layout_rowitems, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mSno.setText(String.valueOf(position+1));

        holder.bind(jobCardDetailsArrayList.get(position),mItemClickListener );

       /* holder.mJobCardNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewJobCardDetailsMain.class);

                sessionManager.storeJobCardId(customerList.getJobCardNo(),customerList.getVehicleNo(), customerList.getMobileNo(),customerList.getCustomerName());
                intent.putExtra("jobcardNo", customerList.getJobCardNo());
                context.startActivity(intent);
            }
        });
*/

      /*  holder.mVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewJobCardDetailsMain.class);

                sessionManager.storeJobCardId(customerList.getJobCardNo(),customerList.getVehicleNo(), customerList.getMobileNo(),customerList.getCustomerName());
                intent.putExtra("jobcardNo", customerList.getJobCardNo());
                context.startActivity(intent);
            }
        });


        holder.mCustomerMobNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewJobCardDetailsMain.class);

                sessionManager.storeJobCardId(customerList.getJobCardNo(),customerList.getVehicleNo(), customerList.getMobileNo(),customerList.getCustomerName());
                intent.putExtra("jobcardNo", customerList.getJobCardNo());
                context.startActivity(intent);
            }
        });


        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("2clikedd", "2clciked");

            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return jobCardDetailsArrayList == null ? 0 : jobCardDetailsArrayList.size();
    }
    /*private void CustomDailog(String Title, String msg, int value, String btntxt, int position) {
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(context, CustomDialogClass.class);
            intent.putExtra("msgbtn", btntxt);
            startActivityForResult(intent, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            finalCalculateData();
        }catch (Exception e){
        }
    }
    private void finalCalculateData() {
        finalAmt=0.00f;
        float sparepartvalue=0.0f,generalservicevalue=0.0f,totalValue=0.0f,GenSerAnt=0.0f,GenSerFreeAmt=0.0f,generalservicefreevalue=0.0f;
        try {
            if(totalAmount!=0.0f){
                totalValue=totalAmount;
            }else{
                totalValue=0.0f;
            }
            if(totalAmount!=0.0f){
                GenSerAnt=totalAmount;
            }else{
                GenSerAnt=0.00f;
            }
            if(totalFreeAmount!=0.0f)
                GenSerFreeAmt=totalFreeAmount;
            else
                GenSerFreeAmt=0.00f;

            sparepartvalue = Float.valueOf(totalValue);
            generalservicevalue = Float.valueOf(GenSerAnt);
            generalservicefreevalue=Float.valueOf(GenSerFreeAmt);
            finalAmt += sparepartvalue + generalservicevalue;//

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void GetCompanyDetailsToPrint() {
        try {
            if (AppUtil.isNetworkAvailable(context)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(context, false);
                    jsonObject.accumulate("CompId", "C001");
                    requestName = "GetCompanyDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetCompanyDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(context, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                        Toast.makeText(context, Result, Toast.LENGTH_SHORT).show();
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
                        Print_BillFormat(tempvehicleRgdNo,tempname,tempmobileno,modleName,jobcardIdValue,jcDate,jcTime,jcRemarks);

//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
