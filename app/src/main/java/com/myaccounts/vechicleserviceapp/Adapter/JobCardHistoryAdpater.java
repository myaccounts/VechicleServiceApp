package com.myaccounts.vechicleserviceapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Pojo.EstDetails;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardHistory;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceList;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartEstDetails;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.DownloadImageServer;
import com.myaccounts.vechicleserviceapp.Utils.DownloadImageSignature;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class JobCardHistoryAdpater extends RecyclerView.Adapter<JobCardHistoryAdpater.Itemholder> {
    private ArrayList<String> printerList;
    private String TotalGrossAmt, TotalQtyValue,filepath,vehicleIdValue="";
    private float totalAmount=0.0f, totalQty,totalFreeAmount;
    private float finalAmt=0.0f;
    private ProgressDialog pDialog;
    private String imageURI = "",local_capture_image1=null,local_signature_image1=null,requestName,signature_image1,capture_image1;
    private ArrayList<ServiceList> generailServiceDetailsArrayList;
    private ArrayList<EstDetails> estDetailsArrayList;
    private ArrayList<SparePartEstDetails> sparePartEstDetailsArrayList;
    private ArrayList<NewServiceMasterDetails> ediServiceDetailsArrayList;
    private Context context;
    private int layout_rowitems;
    SessionManager sessionManager;
    private OnItemClickListener mItemClickListener;
    private ArrayList<JobCardHistory> jobCardHistoryArrayList;
    private PopupWindow mPopupWindow;
    private String vehicleNo;
    private LinearLayout lr;
    private String jobcardIdValue,vehicleModle,customerName,mobileNo;
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName, String screenName);
    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
        Log.d("clikedd", "clciked");
    }
    public JobCardHistoryAdpater(Context context, int jobcard_history_row_item, ArrayList<JobCardHistory> jobCardHistoryArrayList) {
        this.context=context;
        this.layout_rowitems=jobcard_history_row_item;
        estDetailsArrayList=new ArrayList<>();
        generailServiceDetailsArrayList = new ArrayList<>();
        sparePartEstDetailsArrayList = new ArrayList<>();
        this.jobCardHistoryArrayList=jobCardHistoryArrayList;
        sessionManager = new SessionManager(context);
    }

    public void setFilter(ArrayList<JobCardHistory> countryLists) {

    }


    @NonNull
    @Override
    public Itemholder onCreateViewHolder(ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.jobcard_history_row_item,parent,false);
        Itemholder itemViewHolder=new Itemholder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(JobCardHistoryAdpater.Itemholder holder, int position) {
        JobCardHistory jobCardHistory=jobCardHistoryArrayList.get(position);
        jobCardHistory.setSno(position + 1);
        holder.IdSNoTv.setText(String.valueOf(jobCardHistory.getSno()));
        holder.IdJobCardNoTv.setText(String.valueOf(jobCardHistory.getmJobCardNo()));
        holder.IdDateTv.setText(String.valueOf(jobCardHistory.getmDate()));
        holder.IdVehicleNoTv.setText(String.valueOf(jobCardHistory.getmVehicleNo()));
        holder.IdTimeInTv.setText(String.valueOf(jobCardHistory.getmTimeIn()));
        holder.IdTimeOutTv.setText(String.valueOf(jobCardHistory.getmTineOut()));
        holder.IdSparePartsTv.setText(String.valueOf(jobCardHistory.getMspareParts()));
        holder.IdServChargeTv.setText(String.valueOf(jobCardHistory.getMserviceCharge()));
        holder.IdStatusTv.setText(String.valueOf(jobCardHistory.getStatus()));

    }

    @Override
    public int getItemCount() {
        return jobCardHistoryArrayList.size();
    }

    public class Itemholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView IdSNoTv,IdJobCardNoTv,IdDateTv,IdVehicleNoTv,IdTimeInTv,IdTimeOutTv,IdSparePartsTv,IdServChargeTv,IdStatusTv;
        public Itemholder(View itemView) {
            super(itemView);
            IdSNoTv=(TextView)itemView.findViewById(R.id.IdSNoTv);
            IdJobCardNoTv=(TextView)itemView.findViewById(R.id.IdJobCardNoTv);
            IdDateTv=(TextView)itemView.findViewById(R.id.IdDateTv);
            IdVehicleNoTv=(TextView)itemView.findViewById(R.id.IdVehicleNoTv);
            IdTimeInTv=(TextView)itemView.findViewById(R.id.IdTimeInTv);
            IdTimeOutTv=(TextView)itemView.findViewById(R.id.IdTimeOutTv);
            IdSparePartsTv=(TextView)itemView.findViewById(R.id.IdSparePartsTv);
            IdServChargeTv=(TextView)itemView.findViewById(R.id.IdServChargeTv);
            IdStatusTv=(TextView)itemView.findViewById(R.id.IdStatusTv);
            lr = (LinearLayout) itemView.findViewById(R.id.lrReports);
            //IdVehicleNoTv.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JobCardHistory jobCardHistory=jobCardHistoryArrayList.get(getPosition());
                        jobcardIdValue=jobCardHistory.getmJobCardNo();
                        vehicleNo=jobCardHistory.getmVehicleNo();
                        getServiceDetails(jobcardIdValue);
//                        showPopupForPrint("fdfdf", "dfdfdf", "dgdgdg", "fdfdf", jobcardIdValue, "kjfkdjf", "kfjksjfks");
                    }catch(Exception e){
                    }
                }
            });

        }


        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition(), jobCardHistoryArrayList.get(getPosition()).getmJobCardNo(), jobCardHistoryArrayList.get(getPosition()).getmVehicleNo());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


        private void getServiceDetails(String jobcardIdValue) {
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
            }else if (requestName.equalsIgnoreCase("GetJobCardDetailsReport")) {
                try
                {
                    pDialog.dismiss();
                    handeVehicleDetails(jsonArray);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            pDialog.dismiss();
        }
    }

    private void showPopupForPrint(final String vehicleNo, final String custName, final String custNo, final String modleName, final String jobcardIdValue,final String jcDate,final String jcRemarks,final String captureImage,final String signatureImage) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        final View customView = inflater.inflate(R.layout.reports_popup,null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        GridView mobileNo=(GridView)customView.findViewById(R.id.gridview) ;
        Button readCapture=(Button)customView.findViewById(R.id.read_captureImage);
        Button readSignature=(Button)customView.findViewById(R.id.read_signature);
        final ImageView sigIv=(ImageView)customView.findViewById(R.id.signimageView);
        final ImageView captureIv=(ImageView)customView.findViewById(R.id.captureimageView);
        if(captureImage.length()!=0){
            readCapture.setVisibility(View.VISIBLE);
        }else{
            readCapture.setVisibility(View.INVISIBLE);
        }

        if(signatureImage.length()!=0){
            readSignature.setVisibility(View.VISIBLE);
        }else{
            readSignature.setVisibility(View.INVISIBLE);
        }
        readCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {// image download from server
                    DownloadImageServer server = new DownloadImageServer(captureImage,context);
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> userrL = sessionManager.getCaptureImage1LocalPath();
                        local_capture_image1=userrL.get(SessionManager.KEY_CAPTURE_IMAGE1_LOCAL);
                        if(local_capture_image1!=null) {
                            try {
                                imageURI = local_capture_image1;
                                captureIv.setImageDrawable(Drawable.createFromPath(local_capture_image1));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 5000);
            }
        });
        readSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sigIv.setImageDrawable(Drawable.createFromPath(local_signature_image1));
                try {
                    DownloadImageSignature server1 = new DownloadImageSignature(signatureImage, context);
                    server1.execute();
                } catch (Exception e) {
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something here
                        HashMap<String, String> userrS = sessionManager.getSignatureImage1LocalPath();
                        local_signature_image1 = userrS.get(SessionManager.KEY_SIGNATURE_IMAGE1_LOCAL);
                        if (local_signature_image1 != null) {
                            try {
//                                    imageURI = local_signature_image1;
                                sigIv.setImageDrawable(Drawable.createFromPath(local_signature_image1));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 5000);
            }

        });
//        mobileNo.setText(custNo);
        PreparePrintList("vehicleRegNo","name","mobileNumber","modleName",jobcardIdValue,jcDate,"jcTime",jcRemarks);
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, printerList);
//        mobileNo.getChildAt(i).setBackgroundColor(Color.parseColor("#18A608"));*/


        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, printerList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if(position==0 || position==1 || position==2 || position==3){
                    view.setBackgroundColor(context.getResources().getColor(android.R.color.tab_indicator_text));
                }

                /*else{
                    view.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                }*/
               /* for(int i=0;i<printerList.size();i++) {
                    if (printerList.get(i).toString().equals("Rate")) {
                        color = 0xFF0000FF; // Opaque Blue
                    }
                }

                view.setBackgroundColor(color);*/

                return view;
            }
        };
        mobileNo.setAdapter(adapter);
        for(int i=0;i<printerList.size();i++){


            /*if(printerList.get(i).toString().equals("Service Total")) //Your condition!
            {
                mobileNo.Item(0, 1).Style.ForeColor = Color.red();
                mobileNo.Item(0, 1).Style.BackColor = Color.Yellow;
            }*/
//            mobileNo.append(printerList.get(i));
//            mobileNo.append("\n");
        }
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);


    }
    private void PreparePrintList(String vehicleRegNumber,String name,String mobileNo,String ModleName,
                                  String InvNumber,String jcDate,String jcTime,String jcRemarks) {
        try {
            printerList = new ArrayList<String>();
            int NoOfCols = 0;
            String CounrterValue = "";

            NoOfCols = 48;



            //************************************Header Part2 Start**************************************************
            int intExtraCols = 0;
            if (NoOfCols > 40) {
                intExtraCols = NoOfCols - 40;
            }


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

            String HeaderItemName = "Service Name";
            String HeaderQty = "QTY - Rate";
//            String HeaderUOM = AddSpace("Rate", UOMLen, Enums.AlignAt.Right);
//            String HeaderMRP = "Rate";
            String HeaderAmount = "Total";
            SnoLen = 0;
            UOMLen = 3;
            NameLen = NameLen - 1;

            if (NoOfCols < 40) {
                NameLen = NoOfCols - SnoLen - 2;
                NextLineSpace = NoOfCols - QtyLen - UOMLen - MRPLen - AmtLen - 1;
            }
            printerList.add(HeaderItemName);
            printerList.add(HeaderQty);
//            printerList.add(HeaderMRP);
            printerList.add( HeaderAmount);

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
                String BodyItemName = generailServiceDetailsArrayList.get(i).getSubServiceName();
                String BodyQty = String.valueOf(generailServiceDetailsArrayList.get(i).getmQty());
                String BodyMRP = String.valueOf(generailServiceDetailsArrayList.get(i).getServiceCharge());
                String BodyAmount;
                if(generailServiceDetailsArrayList.get(i).getmIssueType().equals("Paid")) {
                    totalVal=Float.parseFloat(generailServiceDetailsArrayList.get(i).getmTotalVal());//String.format("%.2f", totalVal)
                    BodyAmount = String.format("%.2f", totalVal);
                    totServices+= Float.valueOf(totalVal);
                }else
                    BodyAmount="Free";

                printerList.add(BodyItemName);
                printerList.add(BodyQty+" - "+BodyMRP);
//                printerList.add(BodyMRP);
                printerList.add(BodyAmount);
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
                String ServiceTotalText="Service Total:";
                String ServiceTotalValue=GenSerAmt;
                printerList.add(ServiceTotalText);
                printerList.add(" ");
                printerList.add(ServiceTotalValue);
//                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }


            String HeaderItemName1 = "Spare Part";
            String HeaderQty1 = "QTY - Rate";
//            String HeaderUOM = AddSpace("Rate", UOMLen, Enums.AlignAt.Right);
//            String HeaderMRP1 = "Rate";
            String HeaderAmount1 = "Total";


            //************************************Body Part End**************************************************
            String SparesList = "Spares Details:";
//            String ServicesNames=AddSpace(,18,Enums.AlignAt.Left);
//            printerList.add(SparesList);
//            printerList.add(ProjectMethods.GetLine(NoOfCols));
            String headline=HeaderItemName1 + HeaderQty1 + HeaderAmount1;

            float totSpares=0.00f;
            float bodyMRPFloat=0.00f,bodyMRPToatlFloat=0.00f;
            for (int i = 0; i < sparePartEstDetailsArrayList.size(); i++) {
                if(i==0) {
                    printerList.add(HeaderItemName1);
                    printerList.add(HeaderQty1);
                    printerList.add(HeaderAmount1);
                }
//                String BodySNo = AddSpace(String.valueOf(i + 1), SnoLen, Enums.AlignAt.Right);
                String strName = (sparePartEstDetailsArrayList.get(i).getmSparePartName());
//                String BodyItemName = AddSpace(strName, NameLen, Enums.AlignAt.Left) + " ";
                String BodyQty = String.valueOf(sparePartEstDetailsArrayList.get(i).getmQty());
                // String BodyUOM = AddSpace(String.valueOf(sparePartEstDetailsArrayList.get(i).getUomName()), UOMLen, Enums.AlignAt.Right);
                bodyMRPFloat=Float.parseFloat(sparePartEstDetailsArrayList.get(i).getmMrp());
                String BodyMRP1 = (String.valueOf(sparePartEstDetailsArrayList.get(i).getmMrp()));//String.format("%.2f", sparePartEstDetailsArrayList.get(i).getmTotalValue()
                bodyMRPToatlFloat=Float.parseFloat(sparePartEstDetailsArrayList.get(i).getmTotalValue());
                String BodyMRP = String.format("%.2f", bodyMRPToatlFloat);
                totSpares += bodyMRPToatlFloat;
 //                printerList.add(strName+BodyMRP);
                printerList.add(strName);
                printerList.add(BodyQty+" - "+BodyMRP1);
                printerList.add(BodyMRP);

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
                String SparesTotalText=("Spares Total:");
                String SparesTotalValue=(GenSparesAmt);
                printerList.add(SparesTotalText);
                printerList.add(" ");
                printerList.add(SparesTotalValue);
//                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }//TotalAmtTv
//            printerList.add(ProjectMethods.GetLine(NoOfCols));
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
                String TotalText=("Total:");
                String TotalValue=(String.format("%.2f",Float.parseFloat(grossAmount)));
//                printerList.add(TotalText+space+space1+TotalValue);
//                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }
            if(!discountAmount.isEmpty()) {
                String DiscountText=("Discount:");
                String DiscountValue=String.format("%.2f",disDecimal);
                printerList.add(DiscountText);
                printerList.add(" ");
                printerList.add(DiscountValue);
//                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }

            if(!grossAmount.isEmpty()){
                netAmountDecimal=netAmountDecimal-disDecimal;//74E299100159
                String NetAmountText=("Net Amount:");
                String NetValue=(String.format("%.2f",netAmountDecimal));
                printerList.add(NetAmountText);
                printerList.add(" ");
                printerList.add(NetValue);
//                printerList.add(ProjectMethods.GetLine(NoOfCols));
            }
            printerList.add("Mode of Payment:");
            printerList.add(" ");
            printerList.add(" ");

//            printerList.add(ProjectMethods.GetLine(NoOfCols));
            if(!paymodeCash.isEmpty()) {
//                String cashText=(AddSpace("Cash:", 10, Enums.AlignAt.Left));//cardEdt="0",cashEdt="0",upiEdt="0"
//                String cashValue=(AddSpace(paymodeCash, 10, Enums.AlignAt.Left));//cardEdt="0",cashEdt="0",upiEdt="0"
//                printerList.add(cashText+cashValue);
            }
            if(!paymodeCard.isEmpty()) {
                String cardText=("Card:");
                String cardValue=(paymodeCard);
                printerList.add(cardText);
                printerList.add(" ");
                printerList.add(cardValue);
            }
            if(!PayModeAmountUpi.isEmpty()) {
                String upiText=("UPI:");
                String upiValue=PayModeAmountUpi;
                printerList.add(upiText);
                printerList.add(" ");
                printerList.add(upiValue);
            }
            if(!PayModeAmountInvoice.isEmpty()) {
                String invoiceText=("Invoice:");
                String invoiceValue=PayModeAmountInvoice;
                printerList.add(invoiceText);
                printerList.add(" ");
                printerList.add(invoiceValue);

            }
            float taxableCalValue=0.00f;
            if(netAmountDecimal!=0.00f)
//            taxableCalValue=((netAmountDecimal-disDecimal)*100)/118;
                taxableCalValue=(netAmountDecimal*100)/118;
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            double finaltaxableAmt= Double.valueOf(twoDForm.format(taxableCalValue));
            String taxableText="Taxable Amount:";
            String taxableValue=(String.valueOf(finaltaxableAmt));
            printerList.add(taxableText);
            printerList.add(" ");
            printerList.add(taxableValue);

            float gstCalValue=0.00f;
            if(taxableCalValue!=0)
                gstCalValue=((netAmountDecimal)-taxableCalValue)/2;
            String gstText=("CGST 9%:");
            String gstTextValue=(String.valueOf(gstCalValue));
            printerList.add(gstText);
            printerList.add(" ");
            printerList.add(gstTextValue);

            String sgstText="SGST 9%:";
            String sgstTextValue=(String.valueOf(gstCalValue));
            printerList.add(sgstText);
            printerList.add(" ");
            printerList.add(sgstTextValue);

            //************************************Footer Part End**************************************************
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
            GetUserDetails(jobcardIdValue);
//            PreparePrintList("vehicle","name","hsjdhsjdhjs","dhsjhd",jobcardIdValue);
//            showPopupForPrint(vehicleNo,"custName","custNo",
//                    "modleName",jobcardIdValue,"jcDate","jcRemarks");
        }
    }
    private void handeVehicleDetails(JSONArray jsonArray) {

//        mswiperefreshlayout.setRefreshing(false);

        try {

            if (jsonArray.length() > 0) {

                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(context, result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    capture_image1=jsonArray.getJSONObject(0).getString("JobCard_Image1");
                    signature_image1=jsonArray.getJSONObject(0).getString("Sigature_Image1");

                    /*if(capture_image1!=null){
                        try {
                            DownloadImageServer server = new DownloadImageServer(capture_image1,getActivity());
                            server.execute();

                        }catch (Exception e){
                        }
                    }*/

                    showPopupForPrint(vehicleNo,"custName","custNo",
                            "modleName",jobcardIdValue,"jcDate","jcRemarks",capture_image1,signature_image1);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void GetUserDetails(String jobcardidValue) {
        pDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Please Wait..");
        pDialog.show();
        if (AppUtil.isNetworkAvailable(context)) {
            try {
//                mswiperefreshlayout.setRefreshing(true);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("JobCardId", jobcardidValue);
                BackendServiceCall serviceCall = new BackendServiceCall(context, false);
                Log.e("GetUserDetailsservice", "" + serviceCall);
                requestName = "GetJobCardDetailsReport";
                Log.e("jcimage", "" + jsonObject);
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSaveImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetJobCardDetailsReport, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            pDialog.dismiss();
            Toast.makeText(context, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }
    private void startActivityForResult(Intent intent, int i) {
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
}
