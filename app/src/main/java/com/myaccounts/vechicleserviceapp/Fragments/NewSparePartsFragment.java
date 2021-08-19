package com.myaccounts.vechicleserviceapp.Fragments;

import android.content.Context;
import android.content.Intent;
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
import com.myaccounts.vechicleserviceapp.Activity.SparePartsActivity;
import com.myaccounts.vechicleserviceapp.Adapter.SparePartIssueAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
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

import static android.app.Activity.RESULT_OK;

public class NewSparePartsFragment extends Fragment {

    public static final String TAG = "Spare Parts";
    View view;
    float totalAmount, totalQty, totalFreeAmount;
    String TotalGrossAmt, TotalQtyValue, TotalFreeAmount;
    ImageButton IdMakeImg, IdModelImg, IdVehicleTypeImg, AddImgBtn;
    int adapterposition;
    SessionManager sessionManager;
    Button sparePartsSelectedFragmentBtn;
    Spinner MainSparePartsIssueTypeSpinner;
    String issueTypeString;
    String[] issueType = {"Paid", "Free"};
    int newFinalRows = 0;
    String qty = "1";
    String jobCardId, modelId;
    SwipeRefreshLayout mswiperefreshlayout;
    TextView txtDocumentList, FreeTotalTv, IdtotalRows, IdTotalQty, IdTotalAmt, AvlQtyTv, UomTv, mrpTv, freemrpTv;
    private String AvlQty = "";
    private String UOMName = "";
    private String UOMId = "";
    private final String MRP = "";
    private ArrayList<SparePartDetails> sparePartDetailsArrayList;
    private RecyclerView mWheelServiceRecyclerviewid, SparePartrecyclerview, SelectedServiceRecyclerviewid;
    private SparePartIssueAdapter sparePartIssueAdapter;
    private EditText VehicleNoEdt, CustVehicleTypeEdt, CustNameEdt, CustEmailIdEdt, CustMobileNoEdt, QtyEdt, JobCardNoEdt, SparePartEdt, CustDateEdt, CustVehiclemakemodelEdt, CustVehicleModelEdt, CustOdometerReadingEdt, AvgkmsperdayEdt,
            RegNoEdt, MileageEdt, PlaceEdt, CustTimeInEdt, RemarksEdt;
    private String finalSparePartDetailList = null;
    private final String CloseServiceId = "";
    private String spareId;
    //EditSpares
    private JSONObject jsonObject;
    private String requestName;
    private final AlertDialogManager dialogManager = new AlertDialogManager();

    public ArrayList<SparePartDetails> getEdiSpareDetailsArrayList() {
        return sparePartDetailsArrayList;
    }

    public void setEdiSpareDetailsArrayList(ArrayList<SparePartDetails> sparePartDetailsArrayList) {
        this.sparePartDetailsArrayList = sparePartDetailsArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: called");
        Log.d("ANUSHA ", "onCreateView: called" + "NewSparePartsFragment");
        view = inflater.inflate(R.layout.new_spare_parts_fragment, container, false);
        SparePartrecyclerview = (RecyclerView) view.findViewById(R.id.SparePartrecyclerview);

        sessionManager = new SessionManager(getActivity());

        HashMap<String, String> user = sessionManager.getVehicleDetails();

        try {

            HashMap<String, String> user1 = sessionManager.getVehicleDetails();
            modelId = user.get(SessionManager.KEY_VEHICLE_ID);


            //Log.d("newmodeiddd", modelId + "," + jobCardId);
        } catch (NullPointerException e) {
        }

        jobCardId = user.get(SessionManager.KEY_JOBCARD_ID);


        SparePartEdt = (EditText) view.findViewById(R.id.IdSparePartEdt);
        AddImgBtn = (ImageButton) view.findViewById(R.id.AddImgBtn);
        AvlQtyTv = (TextView) view.findViewById(R.id.AvlQtyTv);
        UomTv = (TextView) view.findViewById(R.id.UomTv);
        mrpTv = (TextView) view.findViewById(R.id.mrpTv);
        freemrpTv = (TextView) view.findViewById(R.id.freemrpTv);
        QtyEdt = (EditText) view.findViewById(R.id.IdQtyEdt);
        IdtotalRows = (TextView) view.findViewById(R.id.TotalNoRowsTv);


        IdTotalQty = (TextView) view.findViewById(R.id.TotalQtyTv);
        IdTotalAmt = (TextView) view.findViewById(R.id.TotalAmtTv);
        FreeTotalTv = (TextView) view.findViewById(R.id.FreeTotalTv);
        MainSparePartsIssueTypeSpinner = (Spinner) view.findViewById(R.id.MainServiceServiceIssueTypeSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, issueType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        MainSparePartsIssueTypeSpinner.setAdapter(adapter);
        MainSparePartsIssueTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                issueTypeString = issueType[position];
                //  Toast.makeText(getActivity(), "spare Selected User: " + issueType[position], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddListToGrid();

        SparePartEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spareparts = new Intent(getActivity(), SparePartsActivity.class);
                startActivityForResult(spareparts, 103);
            }
        });

        AddImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AddToGridView();
                // PrepareItemDetailsList();
                if (SparePartEdt.getText().toString().length() == 0) {
                    SparePartEdt.setError("Select spare part");
                    Toast.makeText(getContext(), "Select spare part", Toast.LENGTH_SHORT).show();

                } else {
                    SparePartEdt.setError(null);
                    qty = "1";
                    AddToGridView();
                    PrepareItemDetailsList();
                }
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.detach(NewSparePartsFragment.this).attach(NewSparePartsFragment.this).commit();
            }
        });


        sparePartsSelectedFragmentBtn = (Button) view.findViewById(R.id.sparePartsSelectedFragmentBtn);

        sparePartsSelectedFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("newfinalSparePart", "ANUSHA " + sparePartDetailsArrayList);
                PrepareItemDetailsList();
                if (sparePartDetailsArrayList == null) {
                    Toast.makeText(getContext(), "Please Add Spare Parts", Toast.LENGTH_SHORT).show();
                } else {
//                    sessionManager.storeSecondFragmentDetails(finalServiceDetailsDetailList, String.valueOf(finalrows));
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                    tabhost.getTabAt(3).select();
                }
//


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
                if (sparePartDetailsArrayList == null || sparePartDetailsArrayList.size() == 0) {
                    GetEditSparesDetails();
                } else {
                    AddListToGrid();
                }
            }
        } catch (NullPointerException e) {
        }
    }

    private void AddToGridView() {
        float mrp = 0.0f, freeMrp = 0.0f, Qty = 0.0f;
        try {

            if (validate()) {
                QtyEdt.clearFocus();
                SparePartDetails sparePartDetails = new SparePartDetails();
                sparePartDetails.setmSparePartName(SparePartEdt.getText().toString());

                sparePartDetails.setmSparePartID(SparePartEdt.getTag().toString());

                sparePartDetails.setmQTY(QtyEdt.getText().toString());


                sparePartDetails.setmIssueType(issueTypeString);

                sparePartDetails.setmAVLQTY(AvlQtyTv.getText().toString());

                sparePartDetails.setmSprStatus("false");

                sparePartDetails.setmUOMName(UomTv.getText().toString());
//                Log.e("uom tag",UomTv.getTag().toString());
                sparePartDetails.setmUOMID(UomTv.getTag().toString());

                if (issueTypeString.equalsIgnoreCase("Paid")) {
                    sparePartDetails.setmRate(mrpTv.getText().toString());
                    Log.e("mrpTv", mrpTv.getText().toString());

                } else {
                    sparePartDetails.setmRate("0");
                    sparePartDetails.setFreeMrp(freemrpTv.getText().toString());
                    Log.e("freemrpTv", freemrpTv.getText().toString());

                }
                mrp = Float.valueOf(mrpTv.getText().toString());
                //  Qty = Float.valueOf(QtyEdt.getText().toString());
                float finalValue = mrp * Qty;
                //   sparePartDetails.setmRate(String.valueOf(finalValue));
//                 sparePartDetailsArrayList1.add(sparePartDetails);
                Log.e("finalValue", String.valueOf(finalValue));

                AddtemToGrid(sparePartDetails);
                CalculationPart();

//PrepareItemDetailsList();
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

        if (sparePartDetailsArrayList == null) {
            return;
        }

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

        for (int i = 0; i < sparePartDetailsArrayList.size(); i++) {
            finalrows++;
            TotalGrossAmt = sparePartDetailsArrayList.get(i).getmRate();
            TotalFreeAmount = sparePartDetailsArrayList.get(i).getFreeMrp();
            TotalQtyValue = sparePartDetailsArrayList.get(i).getmQTY();
            Log.d("Freeeeamt", TotalFreeAmount + "," + TotalGrossAmt);

            try {
                TotalAmtValue = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalGrossAmt);
                TotalFreeAmtValue = Float.parseFloat(TotalQtyValue) * Float.parseFloat(TotalFreeAmount);

            } catch (NumberFormatException e) {
            }
            if (TotalQtyValue.length() != 0) {//Null check

                totalQty += Float.valueOf(TotalQtyValue);

            }
            if (TotalGrossAmt.length() != 0) {
                totalAmount += Float.valueOf(TotalAmtValue);

            }
            if (TotalFreeAmount.length() != 0) {
                totalFreeAmount += Float.valueOf(TotalFreeAmtValue);
            }
        }

        if (sparePartDetailsArrayList.size() > 0) {
            sessionManager.storeNoSpares("" + sparePartDetailsArrayList.size());
            sessionManager.storeThirdSparePartsDetails(finalSparePartDetailList, "" + totalAmount);
            //Log.d("finalSpa", finalSparePartDetailList);
        }
        if (sparePartDetailsArrayList.size() == 0) {
            sessionManager.storeNoSpares("" + 0);
            sessionManager.storeThirdSparePartsDetails(null, "" + 0);
        }
        Log.d("freeam", "" + totalFreeAmount);

        IdtotalRows.setText(String.valueOf(finalrows));
        IdTotalQty.setText(String.format("%.0f", totalQty));
        IdTotalAmt.setText(String.format("%.2f", totalAmount));
        FreeTotalTv.setText(String.format("%.2f", totalFreeAmount));

        if (sparePartIssueAdapter != null) {
            sparePartIssueAdapter.notifyDataSetChanged();
        }

    }

    private void AddtemToGrid(SparePartDetails SP) {
        try {
            float mrp = 0.0f, Qty = 0.0f;
            int selectedPosition = getItemExistency(SP.getmSparePartID());
            spareId = SP.getmSparePartID();
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
                mrp = Float.valueOf(ET1.getmRate());
                Qty = Float.valueOf(ET1.getmQTY());
                float finalValue = mrp * Qty;
                // ET1.setmRate(String.valueOf(finalValue));
                ET1.setmIssueType(issueTypeString);

            }

            PrepareItemDetailsList();


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

    private void AddListToGrid() {
        Log.e("AddListToGrid caalled..", "AddListToGrid");
        sparePartIssueAdapter = new SparePartIssueAdapter(getActivity(), R.layout.sparepart_issue_row_item, sparePartDetailsArrayList, "jobcard");
        SparePartrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        SparePartrecyclerview.setItemAnimator(new DefaultItemAnimator());
        SparePartrecyclerview.setHasFixedSize(true);
        SparePartrecyclerview.setAdapter(sparePartIssueAdapter);
//                Log.d("ANUSHA addlisttogrid"," "+sparePartDetailsArrayList.size());
        PrepareItemDetailsList();
        sparePartIssueAdapter.notifyDataSetChanged();

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
        CalculationPart();

    }


    private int getItemExistency(String SpareId) {
        if (sparePartDetailsArrayList.size() > 0) {
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
        return -1;
    }


    private boolean validate() {
        boolean valid = true;
        String SparePart = SparePartEdt.getText().toString().trim();
        String qtyValue = QtyEdt.getText().toString().trim();
        if (SparePart.length() == 0) {
            SparePartEdt.setError("Please Select SparePart");
            Toast.makeText(getActivity(), "Please Select SparePart", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (qtyValue.length() == 0) {
            SparePartEdt.setError(null);
            QtyEdt.setError("Enter Qty");
            QtyEdt.requestFocus();
            valid = false;
        } else if (MainSparePartsIssueTypeSpinner.getSelectedItem().toString().trim().equals("Select")) {
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
            String SprptId = data.getStringExtra("SprptId");
            String SprptName = data.getStringExtra("SprptName");
            String ShortBalQty = data.getStringExtra("ShortBalQty");
            String ShortUomId = data.getStringExtra("ShortUomId");
            String ShortUomName = data.getStringExtra("ShortUomName");
            String SparePartMRP = data.getStringExtra("SparePartMRP");
            String SpareFreeRate = data.getStringExtra("SparePartMRP");


            SparePartEdt.setText(SprptName);
            SparePartEdt.setTag(SprptId);
            mrpTv.setText(SparePartMRP);
            freemrpTv.setText(SpareFreeRate);

           /* AvlQty = ShortBalQty;
            UOMName = ShortUomName;
            UOMId = ShortUomId;*/
            //mrpTv
            // QtyEdt.setText(AvlQty);
            AvlQtyTv.setText(ShortBalQty);
            UomTv.setText(ShortUomName);
            Log.e("Uomname", ShortUomName);
            UomTv.setTag(ShortUomId);
            Log.e("uomid", ShortUomId);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void CustomDailog(String Title, String msg, int value, String btntxt, int position) {
        try {
            adapterposition = position;
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
                QtyEdt.requestFocus();
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
        Log.d("ANUSHA addlisttogrid", " " + sparePartDetailsArrayList.size());
        try {
            finalSparePartDetailList = "";
            for (int j = 0; j < sparePartDetailsArrayList.size(); j++) {
                if (sparePartDetailsArrayList.get(j).getRowNo() >= 0) {
                    finalSparePartDetailList += (j + 1) + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmSparePartName() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSparePartName() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmUOMName() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmUOMName() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmRate() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmRate() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmAVLQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmAVLQTY() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmQTY() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmQTY() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmResult() != null) {
                    finalSparePartDetailList += totalAmount + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmUOMID() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmUOMID() + "!";
                }
                if (sparePartDetailsArrayList.get(j).getmSparePartID() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSparePartID() + "!";
                }

                if (sparePartDetailsArrayList.get(j).getmIssueType() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmIssueType() + "!";
                }
                finalSparePartDetailList += "subtype" + "!";

                if (sparePartDetailsArrayList.get(j).getmSprStatus() != null) {
                    finalSparePartDetailList += sparePartDetailsArrayList.get(j).getmSprStatus();
                }//this field added as per status changing of job card
                //if (sparePartDetailsArrayList.get(j).getmSparePartID() != null) {

                //}

                if (sparePartDetailsArrayList.size() > 0) {

                    if (sparePartDetailsArrayList.size() > 1) {
                        finalSparePartDetailList = finalSparePartDetailList + "~";

                    } else {
                        finalSparePartDetailList = finalSparePartDetailList;
                    }

//                    Log.d("finalSparePart", "" + finalrows);

                    Log.d("SPAREEEE", "" + finalSparePartDetailList.length() + "," + finalSparePartDetailList);

                    CalculationPart();


                } else {
                    finalSparePartDetailList = "";
                }
            }
            if (sparePartDetailsArrayList.size() == 0) {//added this condition after adding delete all items last item is not deleteing in shared preferences
                sessionManager.storeNoSpares("" + sparePartDetailsArrayList.size());
                sessionManager.storeThirdSparePartsDetails(finalSparePartDetailList, "" + totalAmount);
                //Log.d("finalSpa", finalSparePartDetailList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DeleteDetails() {
        try {

            Log.e("spare_parts_frag", "DeleteDetails: " + adapterposition);

            sparePartDetailsArrayList.remove(adapterposition);
            PrepareItemDetailsList();
            sparePartIssueAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void EditData() {
        try {
            SparePartDetails SI = sparePartDetailsArrayList.get(adapterposition);
            SparePartEdt.setText(SI.getmSparePartName());
            SparePartEdt.setTag(SI.getmSparePartID());
            QtyEdt.setText(SI.getmQTY());
            mrpTv.setText(SI.getmRate());
            AvlQtyTv.setText(SI.getmAVLQTY());
            UomTv.setText(SI.getmUOMName());
            UomTv.setTag(SI.getmUOMID());
            DeleteDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handeEditSparesDetails(JSONArray jsonArray) {
        Log.e("handeEditSparesDetails", "handeEditSparesDetails called");
        if (jsonArray.length() > 0) {
            if (sparePartDetailsArrayList == null) {
                sparePartDetailsArrayList = new ArrayList<>();
            }
            // sparePartDetailsArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Records Found")) {
                        Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                        sparePartDetailsArrayList.clear();
                    } else {
                        SparePartDetails documentTypes = new SparePartDetails();
                        documentTypes.setmIssueType(object.getString("IssueType"));
                        Log.e("IssueType", object.getString("IssueType"));

                        documentTypes.setmQTY(object.getString("Qty"));
                        Log.e("Qty", object.getString("Qty"));

                        documentTypes.setmRate(object.getString("Rate"));
                        Log.e("Rate", object.getString("Rate"));

                        documentTypes.setmResult(object.getString("Result"));
                        Log.e("Result", object.getString("Result"));

                        documentTypes.setmSno(object.getString("SlNo"));
                        Log.e("SlNo", object.getString("SlNo"));

                        documentTypes.setmSparePartID(object.getString("SpareId"));
                        Log.e("SpareId", object.getString("SpareId"));

                        documentTypes.setmSparePartName(object.getString("SpareName"));
                        Log.e("SpareName", object.getString("SpareName"));

                        documentTypes.setmTotlValue(object.getString("TotValue"));
                        Log.e("TotValue", object.getString("TotValue"));

                        documentTypes.setmUOMID(object.getString("UOMId"));
                        Log.e("UOMId", object.getString("UOMId"));

                        documentTypes.setmUOMName(object.getString("UOMName"));
                        Log.e("UOMName", object.getString("UOMName"));

                        documentTypes.setmAVLQTY(object.getString("AvailQty"));
                        Log.e("AvailQty", object.getString("AvailQty"));//SprStatus

                        documentTypes.setmSprStatus(object.getString("SprStatus"));//SprStatus

                        Log.d("ANUSHA ", " ### SPARE STATUS " + object.getString("SprStatus"));
                        sparePartDetailsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AddListToGrid();
        }

    }

    private void GetEditSparesDetails() {

        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                jsonObject = new JSONObject();
                jsonObject.accumulate("JobCardId", jobCardId);
                Log.e("Jobcardid ", jobCardId);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                Log.d("servicecall", "" + serviceCall);
                requestName = "GetSparesAgstJobCard";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerReportsImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetEditSparesDetails, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogManager.showAlertDialog(getActivity(), Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        AddListToGrid();
        Log.e("resume called", "onresume called");
        if (sparePartDetailsArrayList != null) {
            sparePartIssueAdapter.notifyDataSetChanged();
        }
        if (sparePartDetailsArrayList == null) {
            return;
        }
    }

    private class OnServiceCallCompleteListenerReportsImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {
        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("GetSparesAgstJobCard")) {
                try {
                    //   mswiperefreshlayout.setRefreshing(false);
                    handeEditSparesDetails(jsonArray);
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
}
