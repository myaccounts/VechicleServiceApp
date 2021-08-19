package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.NewServicesActivity;
import com.myaccounts.vechicleserviceapp.Adapter.JListner;
import com.myaccounts.vechicleserviceapp.Adapter.JobCardReportsAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterIssueAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.EditServiceDetails;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class ServiceStatusFragment extends Fragment {

    View view;
    JListner listner;
    private TextView IdtotalRows, IdTotalQty, IdTotalAmt, FreeTotalTv, ServiceSubServiceTv, ServiceSubServiceIdTv, UomTv, mrpTv, freemrpTv;
    private String AvlQty = "", UOMName = "", UOMId = "", MRP = "";
    float totalAmount, totalQty, totalFreeAmount;
    String TotalGrossAmt, TotalQtyValue, TotalFreeAmount;
    // private ArrayList<NewServiceMasterDetails> serviceMasterDetailsArrayList;
    private RecyclerView ServiceMasterRecyclerview;
    private NewServiceMasterIssueAdapter newServiceMasterIssueAdapter;
    private EditText IdServiceEdt, IdQtyEdt;
    ImageButton AddImgBtn;

    private int position;
    public String finalServiceDetailsDetailList = null, CloseServiceId = "";

    String newmodelId;

    SessionManager sessionManager;
    String modelId = null;
    String qty = "1";
    public static final String TAG = "Services";
    Button serviceSelectedFragmentBtn;
    String[] issueType = {"Paid", "Free"};
    int size;
    Spinner MainServiceServiceIssueTypeSpinner;

    String issueTypeString;

    //edit Job card
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mswiperefreshlayout;
    private ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList;

    private JSONObject jsonObject;
    String jobCardId;
    private String requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
        }
//        try {
//            size = ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.size();
//            Log.e("public arraylist",  ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.size() + "");
//        } catch (NullPointerException e) {
//            ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList = new ArrayList<>();
//        }

        //  Log.e("public arraylist out",  ediServiceDetailsArrayList.size() + "");

        // ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.size();
    }

    public ArrayList<NewServiceMasterDetails> getEdiServiceDetailsArrayList() {
        return ediServiceDetailsArrayList;
    }

    public void setEdiServiceDetailsArrayList(ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList) {
        this.ediServiceDetailsArrayList = ediServiceDetailsArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: called");
        view = inflater.inflate(R.layout.new_service_selected_fragment, container, false);
        listner= (JListner) view.getContext();

        ServiceMasterRecyclerview = (RecyclerView) view.findViewById(R.id.ServiceMasterRecyclerview);

        sessionManager = new SessionManager(getActivity());

        HashMap<String, String> user = sessionManager.getVehicleDetails();
        try {

            HashMap<String, String> user1 = sessionManager.getVehicleDetails();
            modelId = user.get(SessionManager.KEY_VEHICLE_ID);
//            jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);
//
//            Log.d("modeiddd", modelId + "," + jobCardId);
        } catch (NullPointerException e) {
        }
        jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);


        IdServiceEdt = (EditText) view.findViewById(R.id.IdServiceEdt);
        IdQtyEdt = (EditText) view.findViewById(R.id.IdQtyEdt);
        AddImgBtn = (ImageButton) view.findViewById(R.id.AddImgBtn);
        ServiceSubServiceTv = (TextView) view.findViewById(R.id.ServiceSubServiceTv);
        ServiceSubServiceIdTv = (TextView) view.findViewById(R.id.ServiceSubServiceIdTv);
        UomTv = (TextView) view.findViewById(R.id.UomTv);
        mrpTv = (TextView) view.findViewById(R.id.mrpTv);
        freemrpTv = (TextView) view.findViewById(R.id.freemrpTv);

        IdtotalRows = (TextView) view.findViewById(R.id.TotalNoRowsTv);
        IdTotalQty = (TextView) view.findViewById(R.id.TotalQtyTv);
        IdTotalAmt = (TextView) view.findViewById(R.id.TotalAmtTv);
        FreeTotalTv = (TextView) view.findViewById(R.id.FreeTotalTv);
//        IdQtyEdt = (EditText) view.findViewById(R.id.IdQtyEdt);

        MainServiceServiceIssueTypeSpinner = (Spinner) view.findViewById(R.id.MainServiceServiceIssueTypeSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, issueType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        MainServiceServiceIssueTypeSpinner.setAdapter(adapter);
        MainServiceServiceIssueTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                issueTypeString = issueType[position];
                // Toast.makeText(getActivity(), "service Selected User: " + issueType[position], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddListToGrid();
        IdServiceEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NewServicesActivity.class);
                startActivityForResult(intent, 103);
//                if (modelId != null) {
//
//
//                } else {
//                    Toast.makeText(getContext(), "Please Select Model in previous Tab", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        AddImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (IdServiceEdt.getText().toString().length() == 0) {

                    Toast.makeText(getContext(), "Select Service", Toast.LENGTH_SHORT).show();

                } else {
                    qty = "1";
                    AddToGridView();
                    PrepareItemDetailsList();
                }

            }
        });


        serviceSelectedFragmentBtn = (Button) view.findViewById(R.id.serviceSelectedFragmentBtn);
        serviceSelectedFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("newfinalSparePart", "" + finalServiceDetailsDetailList);

                if (ediServiceDetailsArrayList == null) {
                    Toast.makeText(getContext(), "Please Add Services", Toast.LENGTH_SHORT).show();
                } else {
//                    sessionManager.storeSecondFragmentDetails(finalServiceDetailsDetailList, String.valueOf(finalrows));
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                    tabhost.getTabAt(2).select();
                }
//                Log.d("finalSpar", finalServiceDetailsDetailList);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("onviewcreated called", "onviewcreated called");
        try {

            if (jobCardId.equalsIgnoreCase("empty")) {
//            serviceMasterDetailsArrayList.clear();
            } else {
                if (ediServiceDetailsArrayList == null || ediServiceDetailsArrayList.size() == 0) {
                    GetEditServiceDetails();
                } else {
                    AddListToGrid();
                }
            }
        } catch (NullPointerException e) {
        }
    }

    private void AddToGridView() {

        Log.e("AddToGridView called", "AddToGridView called");

        float mrp = 0.0f, freeMrp = 0.0f, Qty = 0.0f;

        try {

            if (validate()) {

                Log.d("issuetype", issueTypeString);

                NewServiceMasterDetails newServiceMasterDetails = new NewServiceMasterDetails();
                newServiceMasterDetails.setmServiceName(IdServiceEdt.getText().toString());

                newServiceMasterDetails.setmServiceId(IdServiceEdt.getTag().toString());

                newServiceMasterDetails.setmQty(IdQtyEdt.getText().toString());

                newServiceMasterDetails.setmIssueType(issueTypeString);


                if (issueTypeString.equalsIgnoreCase("Paid")) {
                    newServiceMasterDetails.setmRate(mrpTv.getText().toString());
                    Log.e("mrpTv", mrpTv.getText().toString());

                } else {
                    newServiceMasterDetails.setmRate("0");
                    newServiceMasterDetails.setFreeMrp(freemrpTv.getText().toString());
                    Log.e("freemrpTv", freemrpTv.getText().toString());

                }
                newServiceMasterDetails.setmSubServiceName(ServiceSubServiceTv.getText().toString());
                Log.e("ServiceSubServiceTv", ServiceSubServiceTv.getText().toString());

                newServiceMasterDetails.setmSubServiceId(ServiceSubServiceIdTv.getText().toString());
                Log.e("ServiceSubServiceIdTv", ServiceSubServiceIdTv.getText().toString());

                //  newServiceMasterDetails.setmQty("1");

                mrp = Float.valueOf(mrpTv.getText().toString());
                Log.e("mrpTv", mrpTv.getText().toString());

                //    freeMrp = Float.valueOf(freemrpTv.getText().toString());
                //   Log.e("freemrpTv",freemrpTv.getText().toString());

//            Qty = Float.valueOf(IdQtyEdt.getText().toString());
                float finalValue = mrp * Qty;
                Log.e("finalValue", String.valueOf(finalValue));

//                newServiceMasterDetails.setmQryMrp(String.valueOf(finalValue));
//                 sparePartDetailsArrayList1.add(sparePartDetails);
                AddtemToGrid(newServiceMasterDetails);
                CalculationPart();
                //  AddtemToGrid(newServiceMasterDetails);
                //clear edit values
                IdServiceEdt.setText("");
                IdServiceEdt.setTag("");
                IdQtyEdt.setText("");
                // mrpEdt.setText("");

                try {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(AddImgBtn.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (NullPointerException e) {

        }

    }

    private void CalculationPart() {

        if (ediServiceDetailsArrayList == null) {
            return;
        }

        // Log.e("publiclist", String.valueOf(((NewJobCardDetailsMain)getActivity()).publiclist.size()));
        TotalGrossAmt = "";
        TotalQtyValue = "";
        TotalFreeAmount = "";
        float TotalAmtValue = 0.0f;
        float TotalFreeAmtValue = 0.0f;
        int TotalRows = 0;

        totalAmount = 0.0f;
        totalFreeAmount = 0.0f;
        totalQty = 0.0f;
        int finalrows = 0;

        for (int i = 0; i < ediServiceDetailsArrayList.size(); i++) {
            finalrows++;
            TotalGrossAmt = ediServiceDetailsArrayList.get(i).getmRate();
            TotalFreeAmount = ediServiceDetailsArrayList.get(i).getFreeMrp();
            TotalQtyValue = ediServiceDetailsArrayList.get(i).getmQty();
            Log.d("Freeeeamt", TotalFreeAmount + "," + TotalGrossAmt);

            try {
                TotalAmtValue = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalGrossAmt);
                TotalFreeAmtValue = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalFreeAmount);
            } catch (NumberFormatException e) {
            }

            if (TotalQtyValue.length() != 0 && totalQty!=0) {//Null check

                totalQty += Float.valueOf(TotalQtyValue);
            }
            if (TotalGrossAmt.length() != 0) {
                totalAmount += Float.valueOf(TotalAmtValue);
            }

            if (TotalFreeAmount.length() != 0) {
                totalFreeAmount += Float.valueOf(TotalFreeAmtValue);
            }
        }


        if (ediServiceDetailsArrayList.size() > 0) {
            sessionManager.storeNoService("" + ediServiceDetailsArrayList.size());
            sessionManager.storeSecondFragmentDetails(finalServiceDetailsDetailList, "" + totalAmount,"SR10060");
        }
//        sessionManager.storeNoService("" + finalrows);
        Log.d("freeam", "" + totalFreeAmount);

        IdtotalRows.setText(String.valueOf(finalrows));
        IdTotalQty.setText(String.format("%.0f", totalQty));
        IdTotalAmt.setText(String.format("%.2f", totalAmount));
        FreeTotalTv.setText(String.format("%.2f", totalFreeAmount));
        if (newServiceMasterIssueAdapter != null) {
            newServiceMasterIssueAdapter.notifyDataSetChanged();
        }
    }

    private void AddtemToGrid(NewServiceMasterDetails sp) {
        Log.e("AddtemToGrid called", "AddtemToGrid called");

        try {
            float mrp = 0.0f, Qty = 0.0f;


            int selectedPosition = getItemExistency(sp.getmServiceId(), sp.getmSubServiceId());
            boolean IsItemAdded = false;

            if (selectedPosition == -1) {
                ediServiceDetailsArrayList.add(sp);
                ServiceMasterRecyclerview.smoothScrollToPosition(ediServiceDetailsArrayList.size() - 1);
                IsItemAdded = true;

            } else {
                NewServiceMasterDetails ET1 = ediServiceDetailsArrayList.get(selectedPosition);
                float Quantity = Float.parseFloat(ET1.getmQty());
                Quantity += Float.parseFloat(sp.getmQty());
                ET1.setmQty(String.valueOf(Quantity));
                IsItemAdded = true;
                ServiceMasterRecyclerview.smoothScrollToPosition(selectedPosition);
                mrp = Float.valueOf(ET1.getmRate());
                Qty = Float.valueOf(ET1.getmQty());
                float finalValue = mrp * Qty;
                //  ET1.setmRate(String.valueOf(finalValue));
                ET1.setmIssueType(issueTypeString);
                ET1.setmSubServiceId(ET1.getmSubServiceId());
            }

            PrepareItemDetailsList();


            if (IsItemAdded) {


                newServiceMasterIssueAdapter.notifyDataSetChanged();
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

    private void AddListToGrid() {
        Log.e("AddListToGrid called", "AddListToGrid called");


        newServiceMasterIssueAdapter = new NewServiceMasterIssueAdapter(getActivity(), R.layout.new_service_issue_row_item, ediServiceDetailsArrayList, "jobcard");
        ServiceMasterRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ServiceMasterRecyclerview.setItemAnimator(new DefaultItemAnimator());
        ServiceMasterRecyclerview.setHasFixedSize(true);
        ServiceMasterRecyclerview.setAdapter(newServiceMasterIssueAdapter);


        newServiceMasterIssueAdapter.notifyDataSetChanged();

        /*newServiceMasterIssueAdapter.SetOnItemClickListener(new NewServiceMasterIssueAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                boolean value = false;
                switch (view.getId()) {

                    case R.id.IdEditIconImg:
                        try {
                            CustomDailog("Service Issue", "Do You Want to Edit Details?", 133, "Edit", position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.IdDeleteIconImg:
                        try {
                            CustomDailog("Service Issue", "Do You Want to Delete Details?", 134, "Delete", position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });*/

        CalculationPart();
        //AddListToGrid();

    }

    private int getItemExistency(String SpareId, String spareSubId) {

        if (ediServiceDetailsArrayList.size() > 0) {
            try {
                for (int i = 0; i < ediServiceDetailsArrayList.size(); i++) {
                    if (ediServiceDetailsArrayList.get(i).getmServiceId().equalsIgnoreCase(SpareId) && ediServiceDetailsArrayList.get(i).getmSubServiceId().equalsIgnoreCase(spareSubId)) {
                        return i;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }
        return -1;
    }
    private boolean validate() {
        boolean valid = true;
        String SparePart = IdServiceEdt.getText().toString().trim();
        String qtyValue = IdQtyEdt.getText().toString().trim();
        if (SparePart.length() == 0) {
            Toast.makeText(getActivity(), "Please Select Service", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (qtyValue.length() == 0) {
            IdQtyEdt.setError("Enter Qty");
            IdQtyEdt.requestFocus();
            valid = false;
        } else if (MainServiceServiceIssueTypeSpinner.getSelectedItem().toString().trim().equals("Select")) {
            Toast.makeText(getActivity(), "Please Select Issue Type", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void SparPartInformation(Intent data) {
        try {
            AvlQty = "";
            UOMId = "";
            UOMName = "";
            String ServiceRate = data.getStringExtra("ServiceRate");
            String ServiceFreeRate = data.getStringExtra("ServiceRate");
            String ServiceResult = data.getStringExtra("ServiceResult");
            String ServiceId = data.getStringExtra("ServiceId");
            String ServiceName = data.getStringExtra("ServiceName");
            String SubServiceId = data.getStringExtra("SubServiceId");
            String SubServiceName = data.getStringExtra("SubServiceName");

            Log.d("sdsdsdd", ServiceName);

            IdServiceEdt.setText(ServiceName);
            IdServiceEdt.setTag(ServiceId);
            mrpTv.setText(ServiceRate);
            freemrpTv.setText(ServiceFreeRate);
           /* AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;*/
            //mrpTv
            // QtyEdt.setText(AvlQty);
            ServiceSubServiceTv.setText(SubServiceName);
            ServiceSubServiceIdTv.setText(SubServiceId);
//            UomTv.setText(ShortUomName);
//            UomTv.setTag(ShortUomId);

        } catch (Exception e) {
            e.printStackTrace();

        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 103) {
            if (data != null && resultCode == RESULT_OK) {
                Log.d("spareee", "sparee");
                SparPartInformation(data);
            }
        } else if (requestCode == 133 && data != null && resultCode == RESULT_OK) {
            EditData();
        } else if (requestCode == 134 && data != null && resultCode == RESULT_OK) {
            DeleteDetails();
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

        } else if (resultCode == RESULT_OK && requestCode == 003) {


        } else if (requestCode == 101 && data != null && resultCode == RESULT_OK) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    private void PrepareItemDetailsList() {
        Log.e("PrepareItem called", "PrepareItemDetailsList called");
        try {
            finalServiceDetailsDetailList = "";
            for (int j = 0; j < ediServiceDetailsArrayList.size(); j++) {

                Log.d("ddddddd", "" + ediServiceDetailsArrayList.get(j).getRowNo());
                if (ediServiceDetailsArrayList.get(j).getRowNo() >= 0) {
                    Log.d("execute", "" + ediServiceDetailsArrayList.get(j).getRowNo());

                    finalServiceDetailsDetailList += (j + 1) + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmServiceId() != null) {
                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmServiceId() + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmSubServiceId() != null) {
                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmSubServiceId() + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmQty() != null) {
                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmQty() + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmRate() != null) {
                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmRate() + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                    finalServiceDetailsDetailList += totalAmount + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                    finalServiceDetailsDetailList += "N/A" + "!";
                }
                if (ediServiceDetailsArrayList.get(j).getmIssueType() != null) {
                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmIssueType();
                }

//                if (serviceMasterDetailsArrayList.get(j).getmServiceName() != null) {
//                    finalServiceDetailsDetailList += serviceMasterDetailsArrayList.get(j).getmServiceName() + "!";
//                }
//                if (serviceMasterDetailsArrayList.get(j).getmSubServiceName() != null) {
//                    finalServiceDetailsDetailList += serviceMasterDetailsArrayList.get(j).getmSubServiceName() + "!";
//                }

//                if (serviceMasterDetailsArrayList.get(j).getmSparePartID() != null) {
//                    finalSparePartDetailList += serviceMasterDetailsArrayList.get(j).getmSparePartID();
//                }

                Log.d("length", "" + ediServiceDetailsArrayList.size());

//                if (serviceMasterDetailsArrayList.size() == 1) {
//                    finalServiceDetailsDetailList = finalServiceDetailsDetailList + "";
////                    Log.d("finalSparePart", "" + finalrows);
//
//                    Log.d("finnnnnyyyyy", "" + finalServiceDetailsDetailList.length() + "," + finalServiceDetailsDetailList);
//
//                    CalculationPart();
//                }
                if (ediServiceDetailsArrayList.size() > 0) {

                    if (ediServiceDetailsArrayList.size() > 1) {
                        finalServiceDetailsDetailList = finalServiceDetailsDetailList + "~";
                    } else {
                        finalServiceDetailsDetailList = finalServiceDetailsDetailList;
                    }

//                    Log.d("finalSparePart", "" + finalrows);

                    Log.e("finnnnn", "" + finalServiceDetailsDetailList.length() + "," + finalServiceDetailsDetailList);

                    CalculationPart();


                } else {
                    finalServiceDetailsDetailList = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DeleteDetails() {
        try {
            ediServiceDetailsArrayList.remove(position);
            CalculationPart();
            newServiceMasterIssueAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void EditData() {
        try {
            NewServiceMasterDetails SI = ediServiceDetailsArrayList.get(position);
            IdServiceEdt.setText(SI.getmServiceName());
            IdServiceEdt.setTag(SI.getmServiceId());
            IdQtyEdt.setText(SI.getmQty());
            mrpTv.setText(SI.getmRate());
            ServiceSubServiceTv.setText(SI.getmSubServiceName());
            UomTv.setText(SI.getmServiceName());
            UomTv.setTag(SI.getRowNo());
            DeleteDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeEditServiceDetails(JSONArray jsonArray) {
        Log.e("handeEditServicecalled", "handeEditServiceDetails called");
//        mswiperefreshlayout.setRefreshing(false);
        if (jsonArray.length() > 0) {
            if (ediServiceDetailsArrayList == null) {
                ediServiceDetailsArrayList = new ArrayList<>();
            }
            //  ediServiceDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.clear();
                        ediServiceDetailsArrayList.clear();
                    } else {
                        NewServiceMasterDetails documentTypes = new NewServiceMasterDetails();
                        documentTypes.setmIssueType(object.getString("IssueType"));
                        documentTypes.setmQty(object.getString("Qty"));
                        documentTypes.setmRate(object.getString("Rate"));
                        documentTypes.setRemarks(object.getString("Remarks"));
                        documentTypes.setmResult(object.getString("Result"));
                        documentTypes.setmServiceId(object.getString("ServiceId"));
                        documentTypes.setmServiceName(object.getString("ServiceName"));
                        documentTypes.setSno(object.getString("SlNo"));
                        documentTypes.setmSubServiceId(object.getString("SubServiceId"));
                        documentTypes.setmSubServiceName(object.getString("SubServiceName"));
                        documentTypes.setTotalValue(object.getString("TotValue"));
                        ediServiceDetailsArrayList.add(documentTypes);
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AddListToGrid();
            //  GetServiceManListRelatedToServices();
        }

//        UIUpdateListLeadView();

    }

//    private void UIUpdateListLeadView() {
//        mswiperefreshlayout.setRefreshing(false);
//        // GetServiceManListRelatedToServices();
//        newServiceMasterIssueAdapter = new NewServiceMasterIssueAdapter(getContext(), serviceMasterDetailsArrayList, R.layout.new_service_issue_row_item);
//        ServiceMasterRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        ServiceMasterRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        ServiceMasterRecyclerview.setHasFixedSize(true);
//        ServiceMasterRecyclerview.setAdapter(newServiceMasterIssueAdapter);
//
////        jobCardReportsAdapter.SetOnItemClickListener(new JobCardReportsAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(View view, int position, String itemName, String screenName) {
////                boolean value = false;
////                switch (view.getId()) {
////                    case R.id.JobCardRowLayout:
////                        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.JobCardRowLayout);
////                       /* if (sSelectedItems.get(position)) {
////                            holder.mLabel.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
////                        } else {
////                            holder.mLabel.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
////                        }
////                        linearLayout.setBackgroundColor(getResources().getColor(R.color.viewcolour));*/
////                        value = true;
////                        // JobCardNo = jobCardDetailsArrayList.get(position).getJobCardNo();
////                        JobCardNo = itemName;
////                        if (value) {
////                            mswiperefreshlayout.setRefreshing(false);
////                            pd = new ProgressDialog(context);
////                            pd.setMessage("Downloading");
////                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////                            pd.setIndeterminate(true);
////                            pd.setProgress(0);
////                            pd.show();
////                            GetJobCardDetailedGrid(JobCardNo, screenName);
////                            // GetServiceListDetails(JobCardNo);
////
////                        }
////
////                       /* final Dialog dialog = new Dialog(context);
////                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                        dialog.setContentView(R.layout.dialog_show_jobcarddetails);
////                        dialog.setCancelable(false);
////                        dialog.setCanceledOnTouchOutside(true);
////                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////                        dialog.show();
////                        ImageButton CloseButton = (ImageButton) dialog.findViewById(R.id.IdCloseImgBtn);
////                        JobCardNoTv = (TextView) dialog.findViewById(R.id.IdJobCardNoTv);
////                        VehicleNoTv = (TextView) dialog.findViewById(R.id.IdVehicleNoTv);
////                        NameTv = (TextView) dialog.findViewById(R.id.IdNameTv);
////                        ModelTv = (TextView) dialog.findViewById(R.id.IdMobileNoTv);
////                        RemarksTv = (TextView) dialog.findViewById(R.id.IdRemarksTv);
////                        ServiceListTv = (TextView) dialog.findViewById(R.id.IdServiceListTv);
////                        DocumentsTv = (TextView) dialog.findViewById(R.id.IdDocumentsTv);
////                        MobileNoTv = (TextView) dialog.findViewById(R.id.IdModelTv);
////                        MakeTv = (TextView) dialog.findViewById(R.id.IdMakeTv);
////                        AvgkmsTv = (TextView) dialog.findViewById(R.id.IdAvgKmsTv);
////                        RegNoTv = (TextView) dialog.findViewById(R.id.IdRegNoTv);
////                        OdomerterTv = (TextView) dialog.findViewById(R.id.IdOdometerReadingTv);
////                        TimeInTv = (TextView) dialog.findViewById(R.id.IdTimeInTv);
////                        DateTv = (TextView) dialog.findViewById(R.id.IdDateTv);
////                        IdCapImgView = (ImageView) dialog.findViewById(R.id.IdCapImgView);
////                        IdDrawImgView = (ImageView) dialog.findViewById(R.id.IdDrawImgView);
////                       // Detailsrecyclerview = (RecyclerView) dialog.findViewById(R.id.Detailsrecyclerview);
////                        CloseButton.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {
////                                if (dialog != null && dialog.isShowing()) {
////                                    dialog.dismiss();
////                                }
////                            }
////                        });*/
////
////                        break;
////                }
////            }
////        });
//
//
//    }

    private void GetEditServiceDetails() {
        Log.e("GetEditServiceDetails", "GetEditServiceDetails called");

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("JobCardId", jobCardId);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                Log.d("servicecall", "" + serviceCall);
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

    private class OnServiceCallCompleteListenerReportsImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {
        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetServicesAgstJobCard")) {
                try {
                    //   mswiperefreshlayout.setRefreshing(false);
                    handeEditServiceDetails(jsonArray);
                    CalculationPart();
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

    @Override
    public void onResume() {
        super.onResume();
//        AddListToGrid();
        Log.e("resume called", "onresume called");
        if (newServiceMasterIssueAdapter != null) {
            newServiceMasterIssueAdapter.notifyDataSetChanged();
        }
        if (ediServiceDetailsArrayList == null) {
            return;
        }
    }
}
