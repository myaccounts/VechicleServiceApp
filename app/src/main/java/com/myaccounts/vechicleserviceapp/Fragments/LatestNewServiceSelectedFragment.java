package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.myaccounts.vechicleserviceapp.Activity.NewServicesActivity;
import com.myaccounts.vechicleserviceapp.Adapter.JListner;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.NewServiceMasterIssueAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
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
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain.tabLayout;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.ModelId;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.modelClick;
import static com.myaccounts.vechicleserviceapp.Utils.SessionManager.KEY_VEHICLE_ID;

public class LatestNewServiceSelectedFragment extends Fragment {
    String finalRowsNew;
    static int adapterPosition;
    boolean editClick=false;
    String qtyValueCheck,serviceId,subServiceId;
    View view;
    private ArrayList<ServiceMaster> servicesArrayList;
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
    String modelId = null,makemodel=null;
    String qty = "1";
    public static final String TAG = "Services";
    Button serviceSelectedFragmentBtn;
    String[] issueType = {"Paid", "Free"};
    int size;
    Spinner MainServiceServiceIssueTypeSpinner;

    String issueTypeString,newVehicleId,newVehicleIdLocal;

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
    }

    public ArrayList<NewServiceMasterDetails> getEdiServiceDetailsArrayList() {
        return ediServiceDetailsArrayList;
    }

    public void setEdiServiceDetailsArrayList(ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList) {
        this.ediServiceDetailsArrayList = ediServiceDetailsArrayList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.new_service_selected_fragment, container, false);
        ServiceMasterRecyclerview = (RecyclerView) view.findViewById(R.id.ServiceMasterRecyclerview);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getVehicleDetails();

        try {

            modelId = user.get(KEY_VEHICLE_ID);
            makemodel=user.get(SessionManager.KEY_VEHICLE_MAKE);
            jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);

            Log.d("ANUSHA ","1____"+"Oncreateview modelId"+modelId+" "+makemodel+jobCardId);
        } catch (NullPointerException e) {
            Log.d("ANUSHA ","1____"+"Oncreateview modelId"+"EXCEPTION"+e.toString());
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
                if(view!=null) {//added null check
                    view.setSelected(true);
                    issueTypeString = issueType[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddListToGrid("first");

        IdServiceEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user1 = sessionManager.getVehicleIdDetails();
                try {

                    modelId = user1.get(KEY_VEHICLE_ID);
                    makemodel=user1.get(SessionManager.KEY_VEHICLE_MAKE);
//            jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);
//
//            Log.d("modeiddd", modelId + "," + jobCardId);
                    Log.d("modeiddd", jobCardId);//lpref.getString(KEY_VEHICLE_ID,null)
                    Log.d("ANUSHA ","1____"+"Oncreateview modelId"+modelId+" "+makemodel);
                    Log.d("ANUSHA ","1____"+"Oncreateview modelId"+ModelId+" "+makemodel);
                } catch (NullPointerException e) {
                    Log.d("ANUSHA ","1____"+"Oncreateview modelId"+"EXCEPTION"+e.toString());
                }
                Log.d("ANUSHA ","2____"+"Edit text onclick "+modelId+"}}"+makemodel);
                if(modelId !=null){
                    Intent intent = new Intent(getActivity(), NewServicesActivity.class);
                    startActivityForResult(intent, 103);

                }else {
                    Toast.makeText(getContext(), "Please Select Model in previous Tab", Toast.LENGTH_SHORT).show();
                    NewMainVehicleDetailsFragment.CustVehicleModelEdt.requestFocus();
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                    tabhost.getTabAt(0).select();
                }
            }
        });

        AddImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IdServiceEdt.getText().toString().length() == 0) {
                    IdServiceEdt.setError("Select Service");
                    Toast.makeText(getContext(), "Select Service", Toast.LENGTH_SHORT).show();

                } else {
                    IdServiceEdt.setError(null);
                    qty = "1";
                    AddToGridView();
                    PrepareItemDetailsList("COMING_FROM_ADD_BTN");
                }

            }
        });


        serviceSelectedFragmentBtn = (Button) view.findViewById(R.id.serviceSelectedFragmentBtn);
        serviceSelectedFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("newfinalSparePart", "" + finalServiceDetailsDetailList);
                /*DatabaseHelper db = new DatabaseHelper(view.getContext());
                Cursor cursor1 = db.getImageList("Checkups");
                if (cursor1 != null) {
                    if (cursor1.getCount() > 0) {
                        for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
                            String Immage = cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.SERVICENAME));
                            Log.d("newfinalSparePart", "" + Immage);

                        }
                    }
                }*/

//                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//                String Qry = "SELECT * FROM  " + DatabaseHelper.TABLE_NAME;
//                Cursor mCursor = getReadableDatabase().rawQuery(Qry, null);
//                Cursor cursor = dbManager.fetch();
                PrepareItemDetailsList("COMING_FROM_SERVICE_SELECTED_FRAGMENT");
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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try{
                    PrepareItemDetailsList("C");
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
                    AddListToGrid("Second");
                }
            }


        } catch (NullPointerException e) {
        }
    }

    private void getDetailsFromModelId() {
        HashMap<String, String> user = sessionManager.getVehicleDetails();
        Log.e("GetEditServiceDetails", "GetEditServiceDetails called");

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                newVehicleIdLocal = user.get(KEY_VEHICLE_ID);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("VehicleId", newVehicleIdLocal);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                requestName = "JCServices";
                Log.d("ANUSHA "," "+jsonObject.toString());
                Log.d("ANUSHA "," "+ProjectVariables.BASE_URL + ProjectVariables.NEW_GET_SERVICE_MASTER);
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.NEW_GET_SERVICE_MASTER, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }
//AddToGridView(selectedListItemRowPojo2.getRowNo(),selectedListItemRowPojo2.getmSubServiceName(),selectedListItemRowPojo2.getmQty(),selectedListItemRowPojo2.getmRate(),issueType);
    private void AddToGridView(String rowNoLocal,String subservicenameLocal,String quantityLocal,String rateLocal,String issueTypeStringLocal,String subserviceIdLocal,String serviceIdLocal) {

        Log.e("AddToGridView called", "AddToGridView called");

        float mrp = 0.0f, freeMrp = 0.0f, Qty = 0.0f;

        try {
            Log.d("ANUSHA ","+++"+" INSIDE ADDTOGRIDVIEW "+issueTypeStringLocal);

                Log.d("issuetype", issueTypeStringLocal);//{"Rate":"50","Result":null,"ServiceId":"SR10062","ServiceName":"Checkups","SubServiceId":"SRT10145","SubServiceName":"1 PUNTCHER CHECK UP"}
                NewServiceMasterDetails newServiceMasterDetails = new NewServiceMasterDetails();

                newServiceMasterDetails.setmSetSerStatus("false");

                if (issueTypeStringLocal.equalsIgnoreCase("Paid")) {
                    newServiceMasterDetails.setmRate(rateLocal);
                    Log.d("ANUSHA ","+++ paid condition "+newServiceMasterDetails.getmRate());

                } else {
                    newServiceMasterDetails.setmRate(rateLocal);
                    newServiceMasterDetails.setFreeMrp(rateLocal);
//                    FreeTotalTv.setText(rateLocal);
                    Log.d("ANUSHA ","+++ free condition "+rateLocal);
                    Log.d("ANUSHA ","+++"+FreeTotalTv.getText().toString());


                }
                newServiceMasterDetails.setmSubServiceName(subservicenameLocal);
            newServiceMasterDetails.setmIssueType(issueTypeStringLocal);
            Log.d("ANUSHA ","+++"+newServiceMasterDetails.getmSubServiceName());
            Log.d("ANUSHA ","+++"+newServiceMasterDetails.getmIssueType());
                newServiceMasterDetails.setmSubServiceId(subserviceIdLocal);
            Log.d("ANUSHA ","+++"+newServiceMasterDetails.getmSubServiceId());

                //  newServiceMasterDetails.setmQty("1");

                mrp = Float.valueOf(rateLocal);
                Log.e("mrpTv", mrpTv.getText().toString());

                //    freeMrp = Float.valueOf(freemrpTv.getText().toString());
                //   Log.e("freemrpTv",freemrpTv.getText().toString());

//            Qty = Float.valueOf(IdQtyEdt.getText().toString());
                float finalValue = mrp * Qty;
                Log.e("finalValue", String.valueOf(finalValue));

//                newServiceMasterDetails.setmQryMrp(String.valueOf(finalValue));
//                 sparePartDetailsArrayList1.add(sparePartDetails);
                newServiceMasterDetails.setmServiceId(serviceIdLocal);
            Log.d("ANUSHA ","+++"+newServiceMasterDetails.getmServiceId());
//                newServiceMasterDetails.setmSubServiceId(subServiceId);
//                AddtemToGrid(newServiceMasterDetails,"");
//                finalRowsNew=IdQtyEdt.getText().toString();
                CalculationPart("");
//                freemrpTv.setText("ANUSHA ");

        } catch (NullPointerException e) {

        }

    }

    private void CalculationPart(String check) {
        Log.d("ANUSHA "," *** CHECK CALICULATION PART "+check);
        Log.d("ANUSHA 3"," *** in caliculationpart "+ediServiceDetailsArrayList.size());
        Log.d("ANUSHA 3"," *** in caliculationpart "+finalRowsNew);
        if (ediServiceDetailsArrayList == null) {
            return;
        }

        // Log.e("publiclist", String.valueOf(((NewJobCardDetailsMain)getActivity()).publiclist.size()));
        TotalGrossAmt = "";
        TotalQtyValue = "";
        TotalFreeAmount = "";
        float TotalAmtValue = 0.0f;
        int TotalRows = 0;

        totalAmount = 0.0f;
        totalFreeAmount = 0.0f;
        totalQty = 0.0f;
        int finalrows = 0;

        for (int i = 0; i < ediServiceDetailsArrayList.size(); i++) {
            finalrows++;
            TotalGrossAmt = ediServiceDetailsArrayList.get(i).getmRate();
            TotalQtyValue = ediServiceDetailsArrayList.get(i).getmQty();
            Log.d("ANUSHA 3", " *** in caliculationpart P " + " type " + ediServiceDetailsArrayList.get(i).getmIssueType() + ediServiceDetailsArrayList.get(i).getmRate());
            //  Log.d("Freeeeamt", TotalFreeAmount + "," + TotalGrossAmt);
            if (ediServiceDetailsArrayList.get(i).getmIssueType().equalsIgnoreCase("Free"))
            {
                totalFreeAmount += Float.parseFloat(TotalQtyValue) * Float.parseFloat(ediServiceDetailsArrayList.get(i).getmRate());
                Log.d("ANUSHA ", " finalrows " + totalFreeAmount);
            }
            else
            {
                TotalAmtValue = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalGrossAmt);
              // Log.d("ANUSHA 3", " *** in caliculationpart F " + ediServiceDetailsArrayList.get(i).getmRate() + " " + TotalAmtValue);

                Log.e(TAG, "CalculationPart: " + TotalAmtValue );

                if (TotalQtyValue.length() != 0) {//Added Null check
                    Log.e(TAG, "before: " + totalQty );
                    totalQty += Float.valueOf(TotalQtyValue);
                    Log.e(TAG, "after: " + totalQty );
                }

                if (TotalGrossAmt.length() != 0) {
                    Log.e(TAG, "before: " + totalAmount );
                    totalAmount += Float.valueOf(TotalAmtValue);
                    Log.e(TAG, "after: " + totalAmount );
                }
            }
        }

        if (ediServiceDetailsArrayList.size() > 0)
        {
            sessionManager.storeNoService("" + ediServiceDetailsArrayList.size());
            sessionManager.storeSecondFragmentDetails(finalServiceDetailsDetailList, "" + totalAmount,"SR10060");
            sessionManager.storeSecondFragmentFreeDetails(String.format("%.2f", totalFreeAmount));
        }
        if(ediServiceDetailsArrayList.size()==0)
        {
            sessionManager.storeNoService("" + 0);
            sessionManager.storeSecondFragmentDetails(null, "" + 0,"SR10060");
            sessionManager.storeSecondFragmentFreeDetails("");
        }

        IdtotalRows.setText(String.valueOf(finalrows));
        Log.e(TAG, "CalculationPart: " + totalQty + " " + totalAmount );
        IdTotalQty.setText(String.format("%.0f", totalQty));
        IdTotalAmt.setText(String.format("%.2f", totalAmount));
        FreeTotalTv.setText(String.format("%.2f", totalFreeAmount));
        sessionManager.storeSecondFragmentFreeDetails(String.format("%.2f", totalFreeAmount));

        if (newServiceMasterIssueAdapter != null) {
            newServiceMasterIssueAdapter.notifyDataSetChanged();
        }
    }

    private void AddtemToGrid(NewServiceMasterDetails sp,String check) {
        Log.e("AddtemToGrid called", "AddtemToGrid called");
        Log.d("ANUSHA "," CHECK "+check);

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

            PrepareItemDetailsList(check);


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

    private void AddListToGrid(String caller) {

        Log.e(TAG, "AddListToGrid: " + caller );
        newServiceMasterIssueAdapter = new NewServiceMasterIssueAdapter(getActivity(), R.layout.new_service_issue_row_item, ediServiceDetailsArrayList, "jobcard");
        ServiceMasterRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ServiceMasterRecyclerview.setItemAnimator(new DefaultItemAnimator());
        ServiceMasterRecyclerview.setHasFixedSize(true);
        ServiceMasterRecyclerview.setAdapter(newServiceMasterIssueAdapter);
        Log.d("ANUSHA addlisttogrid"," "+ediServiceDetailsArrayList.size());
        PrepareItemDetailsList("COMING_FROM_ADDLISTTOGRID");
        newServiceMasterIssueAdapter.notifyDataSetChanged();
        /*newServiceMasterIssueAdapter.SetAfterTextChanged(new NewServiceMasterIssueAdapter.AfterTextChanged() {
            @Override
            public void afterTextChanged(String s, int position, String itemName) {
                adapterPosition=position;
                try {
                    Log.d("ANUSHA ","POSITION "+adapterPosition);
                    if(NewServiceMasterIssueAdapter.clickQty)
                        EditData();
//                            CustomDailog("Service Issue", "Do You Want to Edit Details?", 133, "Edit", position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });*/
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, issueType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        newServiceMasterIssueAdapter.SetOnItemSelected(new NewServiceMasterIssueAdapter.OnItemSelected() {
            @Override
            public void onItemSelected(AdapterView view, int i, String itemName) {
                switch (view.getId()){
                    case R.id.ServiceIssueTypeTv:
                        ServiceIssueTypeTv.setAdapter(adapter);
                        Toast.makeText(getActivity(), "service Selected User: " + issueType[i].toString(), Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });*/
        newServiceMasterIssueAdapter.SetOnItemSelected(new NewServiceMasterIssueAdapter.OnItemSelected() {

            @Override
            public void onItemSelected(AdapterView adapter, int adapterPosition, View view, boolean result, String issueType) {
                switch (adapter.getId()) {
                    case R.id.ServiceIssueTypeSpinner:
                        if(result) {
                            try {
//                                Toast.makeText(getActivity(), "JESUS " + issueType, Toast.LENGTH_SHORT).show();
                                NewServiceMasterDetails selectedListItemRowPojo2 = ediServiceDetailsArrayList.get(adapterPosition);
                                selectedListItemRowPojo2.setmIssueType(issueType);
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getmIssueType());
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getRowNo());
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getmRate());
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getmQty());
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getmSubServiceName());
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getmSubServiceId());
                                Log.d("ANUSHA ", "+++" + selectedListItemRowPojo2.getmServiceId());
                                AddToGridView(String.valueOf(selectedListItemRowPojo2.getRowNo()), selectedListItemRowPojo2.getmSubServiceName(), selectedListItemRowPojo2.getmQty(), selectedListItemRowPojo2.getmRate(), issueType, selectedListItemRowPojo2.getmSubServiceId(), selectedListItemRowPojo2.getmServiceId());
                                NewServiceMasterIssueAdapter.spinnerTouched=false;
                                Log.d("ANUSHA ","+++ size "+ediServiceDetailsArrayList.size());
                                PrepareItemDetailsList("COMING_FROM_JCSERVICE");
//                                newServiceMasterIssueAdapter.notifyDataSetChanged();
                           /* finalServiceDetailsDetailList="";
                            int qtyL=Integer.parseInt(selectedListItemRowPojo2.getmQty());
                            if(qtyL>0){//14!SR10062!SRT10142!3!50!175.0!N\/A!Paid!false~
                                finalServiceDetailsDetailList=finalServiceDetailsDetailList+selectedListItemRowPojo2.getRowNo()
                                +"!"+selectedListItemRowPojo2.getmSubServiceName()+"!"
                                +selectedListItemRowPojo2.getmQty()+"!"+
                                        selectedListItemRowPojo2.getmRate()+"!"
                                ;

                            }*/
//                            PrepareItemDetailsList("COMING_FROM_ADD_BTN");
//                            newServiceMasterIssueAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });

        newServiceMasterIssueAdapter.SetOnItemClickListener(new NewServiceMasterIssueAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                boolean value = false;
                adapterPosition=position;
                switch (view.getId()) {
                    case R.id.plus:
                        try {
                            NewServiceMasterDetails selectedListItemRowPojo2 = ediServiceDetailsArrayList.get(position);
                            String actuvalQty=selectedListItemRowPojo2.getmQty();
                            float quantity2 = Float.valueOf(selectedListItemRowPojo2.getmQty());

                            quantity2 += 1;
                            int qty1=(int)quantity2;
                            float value1 = Float.valueOf(quantity2 * Float.valueOf(selectedListItemRowPojo2.getmRate()));
//                            selectedListItemRowPojo2.setValue(String.valueOf(value1) + ".00");
                            selectedListItemRowPojo2.setmQty(String.valueOf(qty1));
                            /*if(actuvalQty.startsWith("0"))
                             PrepareItemDetailsList("COMING_FROM_JCSERVICE");
                            else*/
                            PrepareItemDetailsList("COMING_FROM_EDIT");
//                            CalculateTotals();
                            newServiceMasterIssueAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case R.id.IdEditIconImg:
                        try {
                            NewServiceMasterDetails selectedListItemRowPojo = ediServiceDetailsArrayList.get(position);

                            float quantity = Float.valueOf(selectedListItemRowPojo.getmQty());

                            Log.e(TAG, "onItemClick: " + quantity );

                            if (quantity > 1.0) {

                                quantity -= 1;

                                if (quantity > 0) {
                                    int qty2=(int)quantity;
                                    selectedListItemRowPojo.setmQty(String.valueOf(qty2));
                                    float value2 = quantity * Float.valueOf(selectedListItemRowPojo.getmRate());
//                                    selectedListItemRowPojo.setValue(String.valueOf(value) + ".00");
                                }

                                /*if (quantity == 0) {

                                    *//*int qty2=(int)quantity;
                                    selectedListItemRowPojo.setmQty(String.valueOf(qty2));*//*
                                    ediServiceDetailsArrayList.remove(position);

                                }*/

                            }
                            else
                            {
                                quantity = 0;
                                int qty2=(int)quantity;
                                selectedListItemRowPojo.setmQty(String.valueOf(qty2));
                                setUserVisibleHint(true);

                            }

//                            CalculateTotals();

                            PrepareItemDetailsList("COMING_FROM_EDIT");
                            newServiceMasterIssueAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case R.id.IdDeleteIconImg:
                        Log.d("ANUSHA ","POSITION "+adapterPosition);
                        if(!ediServiceDetailsArrayList.get(position).getmQty().equals("0")) {
                            Log.d("ANUSHA "," IF "+adapterPosition);
                            try {
                                CustomDailog("Service Issue", "Do You Want to Delete Details?", 134, "Delete", position);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.d("ANUSHA "," ELSE "+adapterPosition);
                            DeleteDetails();

                        }
                        break;
                }
            }
        });

        CalculationPart("");
        //AddListToGrid();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
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
            Log.d("ANUSHA 4","____"+SparePart.length());
            IdServiceEdt.setError("Please Select Service");
            Toast.makeText(getActivity(), "Please Select Service", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (qtyValue.length() == 0) {
            IdServiceEdt.setError(null);
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
//            String jobType=data.getStringExtra("JobType");

            Log.d("sdsdsdd", ServiceName);

//            IdServiceEdt.setText(ServiceName);
            IdServiceEdt.setText(SubServiceName);
//            IdServiceEdt.setTag(ServiceId);
            IdServiceEdt.setTag(SubServiceId);
            mrpTv.setText(ServiceRate);
            freemrpTv.setText(ServiceFreeRate);
            serviceId=data.getStringExtra("ServiceId");
            subServiceId=data.getStringExtra("SubServiceId");
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
        Log.d("ANUSHA ","POSITION"+position);
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
                IdServiceEdt.setError(null);
                IdQtyEdt.requestFocus();
            }
        } else if (requestCode == 133 && data != null && resultCode == RESULT_OK) {
//            EditData();
        } else if (requestCode == 134 && data != null && resultCode == RESULT_OK) {
            Log.d("ANUSHA "," "+adapterPosition);
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



    public interface OnSpinnerClick {
        public void OnSpinnerClick(View view, int position, String itemName);

    }
    private void PrepareItemDetailsList(String check) {
        Log.d("ANUSHA "," CHECK PrepareItemDetailsList "+check);
        Log.d("ddddddd", " 1 PrepareItemDetails " + ediServiceDetailsArrayList.size());
        Log.d("ddddddd", " 1 PrepareItemDetails " + finalServiceDetailsDetailList);
        try {
            finalServiceDetailsDetailList = "";
            if(!check.equalsIgnoreCase("COMING_FROM_JCSERVICE")) {
                for (int j = 0; j < ediServiceDetailsArrayList.size(); j++) {
                    Log.d("ANUSHA 5", "NOT Equals condition to 0.1 ELSE CONDITION " + ediServiceDetailsArrayList.get(j).getmSetSerStatus());
                    Log.d("JESUS 5", ">>>> PrepareList" + ediServiceDetailsArrayList.get(j).getmSubServiceId() +ediServiceDetailsArrayList.get(j).getmRate());
                    Log.d("ddddddd", "" + ediServiceDetailsArrayList.get(j).getRowNo());
                    Log.d("JESUS 5", ">>>> " + ediServiceDetailsArrayList.get(j).getmSubServiceId());
                    Log.d("JESUS 5", ">>>> " + ediServiceDetailsArrayList.get(j).getmQty());
                    Log.d("ANUSHA 5", "" + ediServiceDetailsArrayList.get(j).getmQty());

                    if(!ediServiceDetailsArrayList.get(j).getmQty().equalsIgnoreCase("0")){
                        Log.d("ANUSHA 5", "NOT Equals condition to 0" + ediServiceDetailsArrayList.get(j).getmQty());
                        if (ediServiceDetailsArrayList.get(j).getRowNo() >= 0) {
                            Log.d("execute", "" + ediServiceDetailsArrayList.get(j).getRowNo());

                            finalServiceDetailsDetailList += (j + 1) + "!";//row
                        }
                        if (ediServiceDetailsArrayList.get(j).getmServiceId() != null) {
                            finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmServiceId() + "!";//serviceId
                        }
                        if (ediServiceDetailsArrayList.get(j).getmSubServiceId() != null) {
                            finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmSubServiceId() + "!";//subserviceId
                        }
                        if (ediServiceDetailsArrayList.get(j).getmQty() != null) {
                            finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmQty() + "!";//qty
                        }
                        if (ediServiceDetailsArrayList.get(j).getmRate() != null) {
                            finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmRate() + "!";//rate
                        }
                        if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                            finalServiceDetailsDetailList += totalAmount + "!";//total amount
                        }
                        if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                            finalServiceDetailsDetailList += "N/A" + "!";//result
                        }
                        if (ediServiceDetailsArrayList.get(j).getmIssueType() != null) {
                            finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmIssueType() + "!";//issue type
                        }

                        if(ediServiceDetailsArrayList.get(j).getmSetSerStatus() != null) {
                            Log.d("ANUSHA 5", "NOT Equals condition to 0.1 " + ediServiceDetailsArrayList.get(j).getmSetSerStatus());
                            finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmSetSerStatus();//this field added as per status changing of job card
                        }

                        Log.d("length", "" + ediServiceDetailsArrayList.size());

                        if (ediServiceDetailsArrayList.size() > 0) {

                            if (ediServiceDetailsArrayList.size() > 1) {
                                finalServiceDetailsDetailList = finalServiceDetailsDetailList + "~";
                            } else {
                                finalServiceDetailsDetailList = finalServiceDetailsDetailList;
                            }

                            qtyValueCheck = ediServiceDetailsArrayList.get(j).getmIssueType();
                            finalRowsNew = ediServiceDetailsArrayList.get(j).getmQty();
                            CalculationPart(check);


                        } else {
                            finalServiceDetailsDetailList = "";
                        }

                        if (ediServiceDetailsArrayList.size() == 0) {////added this condition after adding delete all items last item is not deleteing in shared preferences
                            finalServiceDetailsDetailList = "";
                            sessionManager.storeNoService("" + 0);
                            sessionManager.storeSecondFragmentDetails(null, "" + 0, "SR10060");
                        }else{
                            Log.d("ANUSHA 5", "NOT Equals condition to 0 ELSE CONDITION " + ediServiceDetailsArrayList.get(j).getmQty());
                        }
                    }//if qty check ending
                }//for loop end
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ANUSHA 5", "exception in preparedlist " +e.toString());
        }
//        PrepareItemDetailsToDatabase();
    }

    private void DeleteDetails() {

        try {
            ediServiceDetailsArrayList.remove(adapterPosition);
            Log.d("ddddddd", " 1 " + ediServiceDetailsArrayList.size());
            Log.d("ANUSHA "," AFTER REMOVING "+ediServiceDetailsArrayList.size()+"Position "+adapterPosition);
            PrepareItemDetailsList("COMING_FROM_EDIT");
            newServiceMasterIssueAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void EditData() {
        try {
            editClick=true;
            NewServiceMasterDetails SI = ediServiceDetailsArrayList.get(adapterPosition);
            Log.d("ANUSHA "," "+adapterPosition+" EDIT "+SI.getmSubServiceName());
            Log.d("JESUS 5"," "+adapterPosition+" EDIT "+"EDIT"+SI.getmSubServiceId());
            IdServiceEdt.setText(ediServiceDetailsArrayList.get(adapterPosition).getmSubServiceName());
//            IdServiceEdt.setTag(SI.getmServiceId());
            IdServiceEdt.setTag(ediServiceDetailsArrayList.get(adapterPosition).getmSubServiceId());
            Log.d("JESUS 5"," "+adapterPosition+" EDIT TAG"+SI.getmServiceId());
            Log.d("JESUS 5"," "+adapterPosition+" EDIT "+IdServiceEdt.getTag().toString());
            IdQtyEdt.setText(SI.getmQty());
            IdQtyEdt.requestFocus();
            mrpTv.setText(SI.getmRate());
            ServiceSubServiceTv.setText(SI.getmSubServiceName());
            UomTv.setText(SI.getmServiceName());
            UomTv.setTag(SI.getRowNo());
            serviceId=ediServiceDetailsArrayList.get(adapterPosition).getmServiceId();
            subServiceId=ediServiceDetailsArrayList.get(adapterPosition).getmSubServiceId();
            DeleteDetails();//ServiceSubServiceIdTv
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeEditServiceDetails(JSONArray jsonArray) {

        if (jsonArray.length() > 0) {
            if (ediServiceDetailsArrayList == null) {
                ediServiceDetailsArrayList = new ArrayList<>();
            }
            //  ediServiceDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Log.e("handeEditServiceDetails", "handeEditServiceDetails: " + object );
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found") ||
                            Result.equalsIgnoreCase("No Details Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        Log.d("JESUS 5","#### handeEditServiceDetails inside no records found "+object.toString());
//                        ((NewJobCardDetailsMain)getActivity()).serviceMasterArrayList.clear();
                        ediServiceDetailsArrayList.clear();
                    } else{
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
                        documentTypes.setmSetSerStatus(object.getString("SerStatus"));
                        documentTypes.setTotalValue(object.getString("TotValue"));
                        Log.d("JESUS 5","#### handeEditServiceDetails inside "+object.toString());
                        Log.d("JESUS 5","#### handeEditServiceDetails inside "+documentTypes.getmSetSerStatus());
                        Log.d("ANUSHA 5", "NOT Equals condition to 0.1 ELSE CONDITION " + object.getString("SerStatus"));
                        Log.d("ANUSHA 5", "NOT Equals condition to 0.1 ELSE CONDITION " + documentTypes.getmSetSerStatus());
                        Log.d(" ANUSHA ","____SerStatus "+object.toString());
                        Log.d(" ANUSHA ","____SerStatus "+object.getString("SerStatus"));
                        ediServiceDetailsArrayList.add(documentTypes);//SerStatus
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AddListToGrid("third");
        }

    }


    private void GetEditServiceDetails() {
        Log.e("GetEditServiceDetails", "GetEditServiceDetails called");

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
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
                    CalculationPart("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("JCServices")) {
                try {
                    //   mswiperefreshlayout.setRefreshing(false);
                    handeGetSparePartsDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mswiperefreshlayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume called", "onresume called");
        if (newServiceMasterIssueAdapter != null) {
            newServiceMasterIssueAdapter.notifyDataSetChanged();
        }
        if (ediServiceDetailsArrayList == null) {
            return;
        }
        servicesArrayList = new ArrayList<>();
        if(modelClick) {
            getDetailsFromModelId();
        }
        Log.d("ANUSHA "," servicesArrayList +++++"+servicesArrayList.size());

    }
    private void handeGetSparePartsDetails(JSONArray jsonArray) {
        boolean result = false;

        if (jsonArray.length() > 0) {
            if (ediServiceDetailsArrayList == null) {
                ediServiceDetailsArrayList = new ArrayList<>();
            }

          //  ediServiceDetailsArrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    Log.d("ANUSHA "," ++++++ "+object.toString());
                    if (Result.equalsIgnoreCase("No Records Found") ||
                            Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        ediServiceDetailsArrayList.clear();

                    } else {
//                        DatabaseHelper db = new DatabaseHelper(view.getContext());
//                            db.insert_ImagesintoDb(object.getString("Rate"), object.getString("ServiceName"), "0");
//                            db.close();
                        //{"Rate":"50","Result":null,"ServiceId":"SR10062","ServiceName":"Checkups","SubServiceId":"SRT10145","SubServiceName":"1 PUNTCHER CHECK UP"}
                        NewServiceMasterDetails newServiceMasterDetails = new NewServiceMasterDetails();
                        newServiceMasterDetails.setmServiceName(object.getString("ServiceName"));
                        newServiceMasterDetails.setmServiceId(object.getString("ServiceId"));

                        newServiceMasterDetails.setmQty("0");
                        newServiceMasterDetails.setmIssueType("Paid");

                        newServiceMasterDetails.setmRate(object.getString("Rate"));

                        newServiceMasterDetails.setmSubServiceName(object.getString("SubServiceName"));
                        newServiceMasterDetails.setmSubServiceId(object.getString("SubServiceId"));

                        newServiceMasterDetails.setmSetSerStatus("false");
                        Log.d("JESUS 5",">>>> "+newServiceMasterDetails.getmSubServiceId());
                        Log.d("JESUS 5","#### COMING_FROM_JCSERVICE"+newServiceMasterDetails.getmSetSerStatus());
                        AddtemToGrid(newServiceMasterDetails,"COMING_FROM_JCSERVICE");
//                        dbManager.insert(object.getString("ServiceName"),object.getString("SubServiceName"),"0");
//                        CalculationPart();
                        modelClick=false;
                       /* ServiceMaster documentTypes = new ServiceMaster();
                        documentTypes.setRate(object.getString("Rate"));
                        documentTypes.setResult(object.getString("Result"));
                        documentTypes.setServiceId(object.getString("ServiceId"));
                        documentTypes.setServiceName(object.getString("ServiceName"));
                        documentTypes.setSubServiceId(object.getString("SubServiceId"));
                        documentTypes.setSubServiceName(object.getString("SubServiceName"));
                        servicesArrayList.add(documentTypes);*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void PrepareItemDetailsToDatabase() {
        Log.d("ddddddd", " 1 PrepareItemDetails " + ediServiceDetailsArrayList.size());
        Log.d("ddddddd", " 1 PrepareItemDetails " + finalServiceDetailsDetailList);
        String ServiceId=null,SubServiceId=null,qtyValue=null,rate=null,issueType=null,status=null,totalAmount=null,notApplicable=null;
        try {
            finalServiceDetailsDetailList = "";
            for (int j = 0; j < ediServiceDetailsArrayList.size(); j++) {
                Log.d("JESUS 5",">>>> PrepareList"+ediServiceDetailsArrayList.get(j).getmSubServiceId());
                Log.d("ddddddd", "" + ediServiceDetailsArrayList.get(j).getRowNo());
                Log.d("JESUS 5",">>>> "+ediServiceDetailsArrayList.get(j).getmSubServiceId());
                if (ediServiceDetailsArrayList.get(j).getmServiceId() != null) {
                    ServiceId=ediServiceDetailsArrayList.get(j).getmServiceId();
                    //    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmServiceId() + "!";//serviceId
                }
                if (ediServiceDetailsArrayList.get(j).getmSubServiceId() != null) {
                    SubServiceId=ediServiceDetailsArrayList.get(j).getmSubServiceId();
//                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmSubServiceId() + "!";//subserviceId
                }
                if (ediServiceDetailsArrayList.get(j).getmQty() != null) {
                    qtyValue=ediServiceDetailsArrayList.get(j).getmQty();
//                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmQty() + "!";//qty
                }
                if (ediServiceDetailsArrayList.get(j).getmRate() != null) {
                    rate=ediServiceDetailsArrayList.get(j).getmRate();
//                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmRate() + "!";//rate
                }
                if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                    totalAmount =ediServiceDetailsArrayList.get(j).getTotalValue();
//                    finalServiceDetailsDetailList += totalAmount + "!";//total amount
                }
                if (ediServiceDetailsArrayList.get(j).getmResult() != null) {
                    notApplicable="N/A";
//                    finalServiceDetailsDetailList += "N/A" + "!";//result
                }
                if (ediServiceDetailsArrayList.get(j).getmIssueType() != null) {
                    issueType=ediServiceDetailsArrayList.get(j).getmIssueType();
//                    finalServiceDetailsDetailList += ediServiceDetailsArrayList.get(j).getmIssueType() + "!";//issue type
                }
                status="false";
                Log.d("length", "" + ediServiceDetailsArrayList.size());
                if (ediServiceDetailsArrayList.size() > 0) {

   /*                 if (ediServiceDetailsArrayList.size() > 1) {
                        DatabaseHelper db = new DatabaseHelper(view.getContext());
                        db.insert_ServiceDetails(serviceId,subServiceId,qtyValue,rate,totalAmount,notApplicable,status);
                    }*/

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ANUSHA 5", "exception in preparedlist " +e.toString());
        }
    }

    private void AddToGridView() {

        Log.e("AddToGridView called", "AddToGridView called");

        float mrp = 0.0f, freeMrp = 0.0f, Qty = 0.0f;

        try {

            if (validate()) {

                Log.d("issuetype", issueTypeString);//{"Rate":"50","Result":null,"ServiceId":"SR10062","ServiceName":"Checkups","SubServiceId":"SRT10145","SubServiceName":"1 PUNTCHER CHECK UP"}
                IdQtyEdt.clearFocus();
                NewServiceMasterDetails newServiceMasterDetails = new NewServiceMasterDetails();
                newServiceMasterDetails.setmServiceName(IdServiceEdt.getText().toString());

                newServiceMasterDetails.setmServiceId(IdServiceEdt.getTag().toString());

                newServiceMasterDetails.setmQty(IdQtyEdt.getText().toString());

                newServiceMasterDetails.setmIssueType(issueTypeString);


                if (issueTypeString.equalsIgnoreCase("Paid")) {
                    newServiceMasterDetails.setmRate(mrpTv.getText().toString());
                    Log.e("mrpTv", mrpTv.getText().toString());

                } else {
                    newServiceMasterDetails.setmRate(mrpTv.getText().toString());
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

            Qty = Float.valueOf(IdQtyEdt.getText().toString());
                float finalValue = mrp * Qty;
                Log.e("finalValue", String.valueOf(finalValue));

//                newServiceMasterDetails.setmQryMrp(String.valueOf(finalValue));
//                 sparePartDetailsArrayList1.add(sparePartDetails);
                newServiceMasterDetails.setmServiceId(serviceId);
                newServiceMasterDetails.setmSubServiceId(subServiceId);
                AddtemToGrid(newServiceMasterDetails,"");
                finalRowsNew=IdQtyEdt.getText().toString();
                CalculationPart("");
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
}
