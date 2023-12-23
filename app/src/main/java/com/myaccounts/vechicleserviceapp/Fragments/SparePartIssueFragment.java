package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.JobCardNoActivity;
import com.myaccounts.vechicleserviceapp.Activity.ServiceManNamesActivity;
import com.myaccounts.vechicleserviceapp.Activity.SparePartsActivity;
import com.myaccounts.vechicleserviceapp.Adapter.SparePartIssueAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class SparePartIssueFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView IdRightArrow, IdLeftArrow;
    View view;
    private String jobCardTime,jobCardDate;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    private Context context;
    @BindView(R.id.jobCardNoEdt)
    EditText jobCardNoEdt;
    @BindView(R.id.ServiceManNameEdt)
    EditText ServiceManNameEdt;
    @BindView(R.id.SparePartEdt)
    EditText SparePartEdt;
    @BindView(R.id.CustNameEdt)
    EditText CustNameEdt;
    @BindView(R.id.mobileNoEdt)
    EditText mobileNoEdt;
    @BindView(R.id.ReceiptDateEdt)
    EditText ReceiptDateEdt;
    @BindView(R.id.mrpEdt)
    EditText mrpEdt;
    @BindView(R.id.qtyEdt)
    EditText qtyEdt;
    @BindView(R.id.SparePartrecyclerview)
    RecyclerView SparePartrecyclerview;
    @BindView(R.id.AddImgBtn)
    ImageButton AddImgBtn;
    @BindView(R.id.IdtotalRows)
    TextView totalRowsTv;
    @BindView(R.id.IdTotalQty)
    TextView TotalQtyTv;
    @BindView(R.id.IdTotalAmt)
    TextView TotalAmtTv;
    @BindView(R.id.AvlQtyTv)
    TextView AvlQtyTv;
    @BindView(R.id.UomTv)
    TextView UomTv;
    private String finalSparePartDetailList = null;
    private SparePartIssueAdapter sparePartIssueAdapter;
    private String JCDate,dayDifference;
    /* @BindView(R.id.swipe_refresh_layout)
     SwipeRefreshLayout mswiperefreshlayout;*/
    private JSONObject jsonObject;
    private String requestName,spareID;
    private ArrayList<JobCardDetails> jobCardDetailsArrayList;
    private ArrayList<UserList> usersList = new ArrayList<>();
    private ArrayList<SparePartDetails> sparePartDetailsArrayList;
    private ArrayList<SparePartDetails> sparePartDetailsArrayList1;
    private int month, day, year;
    private String AvlQty = "", UOMName = "", UOMId = "";
    float totalAmount, totalQty;
    String TotalGrossAmt, TotalQtyValue, TotalAmtValue, TotalRows;
    private int seconds, minutes, hour, starthour, endhour, startmin, endmin, position;
    private ProgressDialog pDialog;
    SessionManager sessionManager;
    DatePickerDialog picker;

    public static SparePartIssueFragment newInstance() {
        Bundle args = new Bundle();
        SparePartIssueFragment fragment = new SparePartIssueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view = inflater.inflate(R.layout.sparepartissue_layout, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>SparePart Issue</font>"));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
        Log.d("nelyyyy", "newlyyyy");
        sessionManager = new SessionManager(getActivity());

      /*  mswiperefreshlayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mswiperefreshlayout.setOnRefreshListener(this);
        mswiperefreshlayout.setRefreshing(false);*/
        jobCardNoEdt.setOnClickListener(this);
        ServiceManNameEdt.setOnClickListener(this);
        SparePartEdt.setOnClickListener(this);
        AddImgBtn.setOnClickListener(this);
        jobCardDetailsArrayList = new ArrayList<>();
        sparePartDetailsArrayList = new ArrayList<>();
        sparePartDetailsArrayList1 = new ArrayList<>();
        dateFormat();
        AddListToGrid();
        return view;
    }

    private void AddListToGrid() {
        sparePartIssueAdapter = new SparePartIssueAdapter(getActivity(), R.layout.sparepart_issue_row_item, sparePartDetailsArrayList, "");
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
                            CustomDailog("Spare Part Issue", "Do You Want to Edit Details?", 33, "Edit", position);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                    case R.id.IdDeleteIconImg:
                        try {
                            CustomDailog("Spare Part Issue", "Do You Want to Delete Details?", 34, "Delete", position);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                }
            }
        });

    }

  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }*/

    /*@Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }*/

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
                    if (jobCardNoEdt.getText().toString() != "") {
                        picker = new DatePickerDialog(getActivity(),
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
                                        String dateStr = "" + fd + "-" + fm + "-" + year;
                                        ReceiptDateEdt.setText(dateStr);

                                        //ReceiptDateEdt.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : (monthOfYear)) + "-" + year);
                                        // ReceiptDateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, year, month, day);
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            picker.getDatePicker().setMinDate(formatter.parse(JCDate).getTime());
                        } catch (Exception e) {

                        }
                        picker.show();
                    }else{
                        Toast.makeText(getActivity(), "Please Select JobCardNo", Toast.LENGTH_SHORT).show();
                    }
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
                case R.id.jobCardNoEdt:
                    Intent intent = new Intent(getActivity(), JobCardNoActivity.class);
                    intent.putExtra("ServiceStatus","Ready");
                    startActivityForResult(intent, 100);
                    break;
                case R.id.ServiceManNameEdt:
                    Intent serviceman = new Intent(getActivity(), ServiceManNamesActivity.class);
                    startActivityForResult(serviceman, 101);
                    break;
                case R.id.SparePartEdt:
                    Intent spareparts = new Intent(getActivity(), SparePartsActivity.class);
                    startActivityForResult(spareparts, 102);
                    break;
                case R.id.AddImgBtn:
                    AddToGridView();
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
                SparePartDetails sparePartDetails = new SparePartDetails();
                sparePartDetails.setmJobCardNo(jobCardNoEdt.getText().toString());
                sparePartDetails.setmCustName(CustNameEdt.getText().toString());
                sparePartDetails.setmCustMobileNo(mobileNoEdt.getText().toString());
                sparePartDetails.setmSparePartName(SparePartEdt.getText().toString());
                sparePartDetails.setmSparePartID(SparePartEdt.getTag().toString());
                sparePartDetails.setmServiceManName(ServiceManNameEdt.getText().toString());
                sparePartDetails.setmIssueDate(ReceiptDateEdt.getText().toString());
                sparePartDetails.setmMRP(mrpEdt.getText().toString());
                sparePartDetails.setmQTY(qtyEdt.getText().toString());

                sparePartDetails.setmAVLQTY(AvlQtyTv.getText().toString());
                sparePartDetails.setmUOMName(UomTv.getText().toString());
                sparePartDetails.setmUOMID(UomTv.getTag().toString());
                mrp = Float.valueOf(mrpEdt.getText().toString());
                Qty = Float.valueOf(qtyEdt.getText().toString());
                float finalValue = mrp * Qty;
                sparePartDetails.setmQryMrp(String.valueOf(finalValue));
                // sparePartDetailsArrayList1.add(sparePartDetails);
                AddtemToGrid(sparePartDetails);
                CalculationPart();
               /* sparePartIssueAdapter = new SparePartIssueAdapter(getActivity(), R.layout.sparepart_issue_row_item, sparePartDetailsArrayList);
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
                                    CustomDailog("Spare Part Issue", "Do You Want to Edit Details?", 33, "Edit",position);
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                                break;
                            case R.id.IdDeleteIconImg:
                                try {
                                    CustomDailog("Spare Part Issue", "Do You Want to Delete Details?", 34, "Delete",position);
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                                break;
                        }
                    }
                });
*/
                //  CalculationPart();


                //clear edit values
                SparePartEdt.setText("");
                SparePartEdt.setTag("");
                qtyEdt.setText("");
                mrpEdt.setText("");

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

    private void AddtemToGrid(SparePartDetails SP) {
        try {
            float mrp = 0.0f, Qty = 0.0f;
            Log.e("selectedPosition",SP.getmSparePartID());
            int selectedPosition = getItemExistency(SP.getmSparePartID());
            spareID=SP.getmSparePartID();
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
                ET1.setmIssueType("Free");
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
                Log.e("list item ",sparePartDetailsArrayList.get(i).getmSparePartID());
                Log.e("selected item ",SpareId);

                if (sparePartDetailsArrayList.get(i).getmSparePartID().equalsIgnoreCase(SpareId)) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
            if (TotalQtyValue.length() != 0) {//Null check

                totalQty += Float.valueOf(TotalQtyValue);
            }
            if (TotalGrossAmt.length() != 0) {
                totalAmount += Float.valueOf(TotalAmtValue);
            }
        }
       if (sparePartDetailsArrayList.size() > 0) {
            sessionManager.storeNoSpares("" + sparePartDetailsArrayList.size());
            sessionManager.storeThirdSparePartsDetails(finalSparePartDetailList, "" + totalAmount,String.format("%.0f", totalQty));
        }
        totalRowsTv.setText(String.valueOf(finalrows));
        TotalQtyTv.setText(String.format("%.0f", totalQty));
        TotalAmtTv.setText(String.format("%.2f", totalAmount));
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null && resultCode == RESULT_OK) {
                JobInformation(data);
            }
        } else if (requestCode == 101) {
            if (data != null && resultCode == RESULT_OK) {
                ServiemanInformation(data);
            }
        } else if (requestCode == 102) {
            if (data != null && resultCode == RESULT_OK) {
                SparPartInformation(data);
            }
        } else if (requestCode == 33 && data != null && resultCode == RESULT_OK) {
            EditData();
        } else if (requestCode == 34 && data != null && resultCode == RESULT_OK) {
            DeleteDetails();
        } else if (requestCode == 38 && data != null && resultCode == RESULT_OK) {
            if(validateSave())
            SaveDetailsToServer();
        }
    }

//    {"IssueDate":"21-09-2020","UserId":"U10001","ItemDetails":"1!Brake Disc!One Unit !12870!!2!25740!GM10559!SPT10093~2!Gallardo Cover!One Unit !37400!!3!112200!GM10559!SPT10100~3!Thurst Bearing!One Unit !67780!!2!135560!GM10559!SPT10099~","SMId":"GM10564","JobCardId":"01370025"}
    private void SaveDetailsToServer() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String JobCardNo = jobCardNoEdt.getText().toString().trim();
            String SmId = ServiceManNameEdt.getTag().toString().trim();
            String issueDate = ReceiptDateEdt.getText().toString().trim();
            PrepareItemDetailsList();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("IssueDate", issueDate);
                    jsonObject.accumulate("UserId", ProjectMethods.getUserId());
                    jsonObject.accumulate("ItemDetails", finalSparePartDetailList);
                    jsonObject.accumulate("SMId", SmId);
                    jsonObject.accumulate("JobCardId", JobCardNo);
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "SaveSparePartIssueDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SaveSparePartIssueDetails, jsonObject, Request.Priority.HIGH);
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

    private void PrepareItemDetailsList() {
//@ItemDetails='1!Bolts!Dozen!0!0!7!0.00!GM10049!SPT10022~2!Steering pumps!Pair!0!0!4!0.00!GM10048!SPT10021'
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
     /*       SparePartDetails sparePartDetails = new SparePartDetails();
            sparePartDetails.setmJobCardNo(sparePartDetailsArrayList.get(position).getmJobCardNo());
            sparePartDetails.setmCustName(sparePartDetailsArrayList.get(position).getmCustName());
            sparePartDetails.setmCustMobileNo(sparePartDetailsArrayList.get(position).getmCustMobileNo());
            sparePartDetails.setmSparePartName(sparePartDetailsArrayList.get(position).getmSparePartName());
            sparePartDetails.setmServiceManName(sparePartDetailsArrayList.get(position).getmServiceManName());
            sparePartDetails.setmIssueDate(sparePartDetailsArrayList.get(position).getmIssueDate());
            sparePartDetails.setmMRP(sparePartDetailsArrayList.get(position).getmMRP());
            sparePartDetails.setmQTY(sparePartDetailsArrayList.get(position).getmQTY());
            sparePartDetails.setmAVLQTY(sparePartDetailsArrayList.get(position).getmAVLQTY());
            sparePartDetails.setmUOMName(sparePartDetailsArrayList.get(position).getmUOMName());
            sparePartDetails.setmUOMID(sparePartDetailsArrayList.get(position).getmUOMID());*/
            SparePartEdt.setText(SI.getmSparePartName());
            SparePartEdt.setTag(SI.getmSparePartID());
            qtyEdt.setText(SI.getmQTY());
            mrpEdt.setText(SI.getmMRP());
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
            mrpEdt.setText(SparePartMRP);
            AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;
            AvlQtyTv.setText(AvlQty);
            UomTv.setText(UOMName);
            UomTv.setTag(UOMId);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void ServiemanInformation(Intent data) {

        try {
            String UserId = data.getStringExtra("ServiceManId");
            String UserName = data.getStringExtra("ServiceManName");

            ServiceManNameEdt.setText(UserName);
            ServiceManNameEdt.setTag(UserId);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void JobInformation(Intent data) {
        try {
            String jobCardN = data.getStringExtra("JoBCardNo");
            String CustName = data.getStringExtra("CustName");
            String CustMobNo = data.getStringExtra("CustMobNo");
            String VehicleNo = data.getStringExtra("VehicleNo");
            JCDate = data.getStringExtra("JCDate");
//            String ModifiedDate = data.getStringExtra("ModifiedDate");
//            String ModifiedTime = data.getStringExtra("ModifiedTime");
            jobCardNoEdt.setText(jobCardN);
            CustNameEdt.setText(CustName);
            mobileNoEdt.setText(CustMobNo);
            if (jobCardNoEdt.getText().toString() != "") {
                GetJoBCardRelatedServices();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void GetJoBCardRelatedServices() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            String jobcardId = jobCardNoEdt.getText().toString().trim();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject jsonObject = new JSONObject();//JoBcardNo
                    jsonObject.accumulate("JoBcardNo", jobcardId);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRefresh() {

    }

    private class OnServiceCallCompleteListenerReportsSpareImp implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetJobCardDetails")) {
                try {


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.menu_header, menu);
        try {
            getActivity().getMenuInflater().inflate(R.menu.menu_header, menu);
            super.onCreateOptionsMenu(menu, inflater);
            MenuItem pinMenuItem = menu.findItem(R.id.action_Add);
            menu.findItem(R.id.datetv).setTitle("");
            menu.findItem(R.id.datebtn).setIcon(0);
            menu.findItem(R.id.datebtn).setEnabled(false);
            menu.findItem(R.id.datetv).setEnabled(false);
//            menu.findItem(R.id.alltv).setTitle("");
            menu.findItem(R.id.datetv).setEnabled(false);
            pinMenuItem.setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (sparePartDetailsArrayList.size() > 0) {
                    try {
                        CustomDailog("Spare Part Issue", "Do You Want to Save Details?", 38, "SAVE", 0);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                } else {
                    Toast toast = Toast.makeText(context, "Please Select Atleast One Spare Part", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.WHITE);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    // Toast.makeText(context, "Please Select Atleast One Spare Part ", Toast.LENGTH_SHORT).show();
                }


                return true;
            case R.id.action_Add:
                try {
                    if (validate()) {
                        SparePartDetails sparePartDetails = new SparePartDetails();
                        sparePartDetails.setmJobCardNo(jobCardNoEdt.getText().toString());
                        sparePartDetails.setmCustName(CustNameEdt.getText().toString());
                        sparePartDetails.setmCustMobileNo(mobileNoEdt.getText().toString());
                        sparePartDetails.setmSparePartName(SparePartEdt.getText().toString());
                        sparePartDetails.setmServiceManName(ServiceManNameEdt.getText().toString());
                        sparePartDetails.setmIssueDate(ReceiptDateEdt.getText().toString());
                        sparePartDetails.setmMRP(mrpEdt.getText().toString());
                        sparePartDetails.setmQTY(qtyEdt.getText().toString());

                        sparePartDetailsArrayList.add(sparePartDetails);
                        sparePartIssueAdapter = new SparePartIssueAdapter(getActivity(), R.layout.sparepart_issue_row_item, sparePartDetailsArrayList, "");
                        SparePartrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        SparePartrecyclerview.setItemAnimator(new DefaultItemAnimator());
                        SparePartrecyclerview.setHasFixedSize(true);
                        SparePartrecyclerview.setAdapter(sparePartIssueAdapter);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validate() {
        boolean valid = true;
        String JobcardNoTv = jobCardNoEdt.getText().toString().trim();
        String CustNameTv = CustNameEdt.getText().toString().trim();
        String MobileNoTv = mobileNoEdt.getText().toString().trim();
        String ServiceManName = ServiceManNameEdt.getText().toString().trim();
        String SparePart = SparePartEdt.getText().toString().trim();
        String IssueDate = ReceiptDateEdt.getText().toString().trim();
        String mrpTv = mrpEdt.getText().toString().trim();
        String qtyTv = qtyEdt.getText().toString().trim();
        if (JobcardNoTv.length() == 0) {
         /*   jobCardNoEdt.requestFocus();
            jobCardNoEdt.setError("Please Select JobCardNo");*/
            Toast.makeText(getActivity(), "Please Select JobCardNo", Toast.LENGTH_SHORT).show();
            valid = false;
        }
      /*  else if (CustNameTv.length() == 0) {
            CustNameEdt.requestFocus();
            CustNameEdt.setError("Please Enter Name");
            valid = false;
        } else if (MobileNoTv.length() == 0 || MobileNoTv.length() < 10) {
            mobileNoEdt.requestFocus();
            mobileNoEdt.setError("Please Enter Valid Mobile No");
            valid = false;
        }*/
        else if (SparePart.length() == 0) {
           /* SparePartEdt.requestFocus();
            SparePartEdt.setError("Please Select SparePart");*/
            Toast.makeText(getActivity(), "Please Select SparePart", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (ServiceManName.length() == 0) {
          /*  ServiceManNameEdt.requestFocus();
            ServiceManNameEdt.setError("Please Select ServiceManName");*/
            Toast.makeText(getActivity(), "Please Select ServiceManName", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (IssueDate.length() == 0) {
            ReceiptDateEdt.requestFocus();
            ReceiptDateEdt.setError("Please Select IssueDate");
            valid = false;
        } else if (mrpTv.length() == 0) {
            mrpEdt.requestFocus();
            mrpEdt.setError("Please Enter MRP");
            valid = false;
        } else if (qtyTv.length() == 0) {
            qtyEdt.requestFocus();
            qtyEdt.setError("Please Enter QTY");
            valid = false;
        }

        return valid;
    }
    private boolean validateSave() {
        boolean valid = true;
        String JobcardNoTv = jobCardNoEdt.getText().toString().trim();
        String ServiceManName = ServiceManNameEdt.getText().toString().trim();
        String IssueDate = ReceiptDateEdt.getText().toString().trim();
        if (JobcardNoTv.length() == 0) {
         /*   jobCardNoEdt.requestFocus();
            jobCardNoEdt.setError("Please Select JobCardNo");*/
            Toast.makeText(getActivity(), "Please Select JobCardNo", Toast.LENGTH_SHORT).show();
            valid = false;
        }
         else if (ServiceManName.length() == 0) {
          /*  ServiceManNameEdt.requestFocus();
            ServiceManNameEdt.setError("Please Select ServiceManName");*/
            Toast.makeText(getActivity(), "Please Select ServiceManName", Toast.LENGTH_SHORT).show();
            valid = false;
        } /*else if (IssueDate.length() == 0) {
            ReceiptDateEdt.requestFocus();
            ReceiptDateEdt.setError("Please Select IssueDate");
            valid = false;
        }*/

        return valid;
    }


    private class OnServiceCallCompleteListenerSaveImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("SaveSparePartIssueDetails")) {
                try {
                    handeSaveSparePartIssueDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetNewJobCardSpeDetails")) {
                try {
                    pDialog.dismiss();
                    handeServiceDetails(jsonArray);

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


    private void handeServiceDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            sparePartDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        sparePartDetailsArrayList.clear();
                    } else {
                        SparePartDetails documentTypes = new SparePartDetails();
                        documentTypes.setmSparePartName(object.getString("SPNAME"));
                        documentTypes.setmSparePartID(object.getString("SpareId"));
                        documentTypes.setmUOMName(object.getString("UOMNAME"));
                        documentTypes.setmUOMID(object.getString("UOMID"));
                        documentTypes.setmQTY(object.getString("QTY"));
                        documentTypes.setmAVLQTY(object.getString("AvailQty"));
                        documentTypes.setmMRP(object.getString("RATE"));
                        documentTypes.setRowNo(object.getInt("SlNo"));
                        documentTypes.setmQryMrp(object.getString("Value"));
                        sparePartDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AddListToGrid();
            CalculationPart();
        }
    }

    private void handeSaveSparePartIssueDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                try {
                    JSONObject object = jsonArray.getJSONObject(0);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                        clearData();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearData() {
        try {
            jobCardNoEdt.setText("");
            CustNameEdt.setText("");
            mobileNoEdt.setText("");
            ServiceManNameEdt.setText("");
            totalRowsTv.setText("0");
            TotalQtyTv.setText("0");
            TotalAmtTv.setText("0.00");
            sparePartDetailsArrayList.clear();
            sparePartIssueAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*public Date convertStringToDate(String dateString)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {

            Date date = formatter.parse(dateString);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return "";
    }*/
}
